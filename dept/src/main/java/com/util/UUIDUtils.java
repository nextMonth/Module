package com.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.StringTokenizer;
import java.util.UUID;
 

public class UUIDUtils {

	private static SecureRandom seederStatic = null;
	private static String netAddrHexStr = null;
	private static String portHexStr = "0000";
	private static byte[] addrBytes = null;
	private static int[] addrIntAry = null;

	private static int nextSeq32767 = 0;

	private static int nextSeq999 = 0;

	// 16进制是0~F，32进制是0~V，36进制是0~Z
	private static char[] radixDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	static {
		try {
			seederStatic = new SecureRandom();
			seederStatic.nextInt();
			InetAddress.getLocalHost().getHostAddress();
			addrBytes = InetAddress.getLocalHost().getAddress();
			netAddrHexStr = toHex(toInt(addrBytes), 8);
		} catch (Exception ex) {
			throw new RuntimeException("get netAddr error" + ex.getMessage());
		}
	}

 

	public UUIDUtils() {
	}

	/**
	 * 设置机器的IP地址和端口号。 IP地址只支持IPv4格式，不支持IPv6格式。
	 * 
	 * @param ipAddr
	 *            IP地址(IPv4格式)，不能使用机器名
	 * @param port
	 *            端口号
	 * @throws UnknownHostException
	 */
	public static void setNetAddress(String ipAddr, int port) throws UnknownHostException {
		netAddrHexStr = getIPAddressHexStr(ipAddr);
		portHexStr = toHex(port, 4);
	}

	/**
	 * <pre>
	 * 产生一个32位定长字符串序列号。
	 * 序列号的前12位是系统时间，可以作为按时间的排序依据。
	 * </pre>
	 * 
	 * @return 32位定长字符串序列号
	 */
	public static String generate() {
		StringBuffer uid = new StringBuffer(32);

		// 得到系统时间的32进制字符串, 总共是9个字符
		uid.append(getSystemMillisRadix32());

		// 拼上32进制的三个字符的循环数，取值0~32767，并发的时候，即使是在同一个毫秒数，也能得到不同的ID
		// 在同一个毫秒数上，总共有32767个不同数, 按目前的机器速度不会产生重复
		uid.append(getSeqRadix32());

		// get the Internet address，8个十六进制字符
		uid.append(netAddrHexStr);

		// get the port，4个字符
		uid.append(portHexStr);

		// get the random number，8个16进制字符
		uid.append(toHex(getRandom(), 8));

		return uid.toString();
	}

	/**
	 * 从UUID中获得IP地址。
	 * 
	 * @param uuid
	 * @return
	 * @throws UnknownHostException
	 * @throws UnknownHostException
	 */
	public static String getIPAddress(String uuid) throws UnknownHostException {
		String ipHex = uuid.substring(12, 20);
		StringBuffer buf = new StringBuffer();
		buf.append(toInt(ipHex.substring(6, 8), 2)).append(".");
		buf.append(toInt(ipHex.substring(4, 6), 2)).append(".");
		buf.append(toInt(ipHex.substring(2, 4), 2)).append(".");
		buf.append(toInt(ipHex.substring(0, 2), 2));
		return buf.toString();
	}

	/**
	 * 从UUID中得到端口号。
	 * 
	 * @param uuid
	 * @return
	 */
	public static int getPort(String uuid) {
		int port = toInt(uuid.substring(20, 24), 4);
		return port;
	}

	/**
	 * 从UUID中得到时间戳
	 * 
	 * @param uuid
	 * @return
	 */
	public static long getTimestamp(String uuid) {
		String timestampStr = uuid.substring(0, 9);
		return Long.parseLong(timestampStr, 32);
	}

	/**
	 * <pre>
	 * 获取长整数类型的序列号，序列号对应的10进制数字是19位。
	 * 生成规则：系统时间 + 本机IP地址最后一个数字 + 0~999的区分序号
	 * 在同一个毫秒中，可以生成1000个不重复的ID
	 * </pre>
	 * 
	 * @return 长整数类型的序列号
	 */
	public static long getUniqueLong() {
		// 系统时间的10进制字符串是13个字符
		long l = System.currentTimeMillis();

		// 将后面留出6个数字位，前三个是IP地址的最后一个数字，用于集群环境中避免ID重复
		// 最后三个0~999是序号，使同一个毫秒中可以有1000个区分
		l = l * 1000000;

		// IP地址的最后一个数字，用于区分不同的IP地址，在集群中，一般就是IP地址的最后一个数字不同
		// long b1 = addrBytes[3] & 0x0FF;
		// long b2 = (addrBytes[2] & 0x0FF) * 1000;
		long ipv4_lastNumber = (addrBytes[3] & 0x0FF) * 1000;

		// 把同一个毫秒中的区分序号加进去
		l = l + ipv4_lastNumber + (long) getSeq999();

		return l;
	}

	/**
	 * 将IP地址字符串转换成8个十六进制字符的表示形式
	 * 
	 * @param ipAddr
	 * @return
	 * @throws UnknownHostException
	 * @throws UnknownHostException
	 */
	private static String getIPAddressHexStr(String ipAddr) throws UnknownHostException {
		addrIntAry = getIPAddressIntAry(ipAddr);
		for (int i = 0; i < addrIntAry.length; i++) {
			addrBytes[i] = (byte) (addrIntAry[i] & 0x00FF);
		}
		return toHex(addrIntAry);
	}
	/**
	 * 将IP地址串放在数据里
	 */
	private static int[] getIPAddressIntAry(String ipAddr) {
		String ipStr = ipAddr.trim();
		if ("localhost".equalsIgnoreCase(ipStr)) {
			ipStr = "127.0.0.1";
		}

		int[] addrInts = new int[4];
		StringTokenizer tokenizer = new StringTokenizer(ipStr, ".");
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			addrInts[i++] = Integer.parseInt(token);
		}
		return addrInts;
	}

	/**
	 * 得到9个32进制字符的系统时间
	 * 
	 * @return
	 */
	private static String getSystemMillisRadix32() {
		String millisStr = Long.toString(System.currentTimeMillis(), 32).toUpperCase();
		int len = millisStr.length();
		if (len < 9) {
			StringBuffer buffer = new StringBuffer(10);
			buffer.append(millisStr);
			int offset = 9 - len;
			for (int i = 0; i < offset; i++) {
				buffer.append("0");
			}
			millisStr = buffer.toString();
		} else if (len > 9) {
			millisStr = millisStr.substring(len - 9);
		}
		return millisStr;
	}

	private static synchronized int getRandom() {
		return seederStatic.nextInt();
	}

	/**
	 * 把整数转换成指定长度的16进制串 数值的低位是字符串的末尾
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	private static String toHex(int value, int length) {
		StringBuffer buffer = new StringBuffer(length);
		int shift = length - 1 << 2;
		for (int i = -1; ++i < length;) {
			buffer.append(radixDigits[value >> shift & 0x0f]);
			value <<= 4;
		}

		return buffer.toString();
	}
	/**
	 * 
	 */
	private static String toHex(int[] intAry) {
		StringBuffer buffer = new StringBuffer(intAry.length * 2);
		for (int i = 0; i < intAry.length; i++) {
			int int1 = intAry[i];
			int int2 = int1 >> 4;
			buffer.append(radixDigits[int2 & 0x0F]);
			buffer.append(radixDigits[int1 & 0x0F]);
		}
		return buffer.toString();
	}
	/**
	 * 
	 */
	private static int toInt(byte[] bytes) {
		int value = 0;
		int aryLen = bytes.length;
		for (int i = aryLen - 1; i >= 0; i--) {
			value <<= 8;
			value |= bytes[i] & 0x0FF;
		}

		return value;
	}

	/**
	 * 必须传入8个或4个十六进制字符的字符串，并且A~F必须是大写的字符
	 * 
	 * @param hexStr
	 * @return
	 */
	private static int toInt(String hexStr, int len) {
		int value = 0;
		int high;
		int low;
		for (int i = 0; i < len; i += 2) {
			char c = hexStr.charAt(i);
			if (c <= 57) {// 48~57是字符0～9
				high = c - 48;
			} else {
				high = 10 + c - 65;
			}
			c = hexStr.charAt(i + 1);
			if (c <= 57) {// 48~57是字符0～9
				low = c - 48;
			} else {
				low = 10 + c - 65;
			}
			value <<= 8;
			value = value | (((0x00FF & high) << 4) | (0x00FF & low));
		}
		return value;

	}

	/**
	 * 
	 */
	public static byte[] toBytes(int value) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (value & 0x0FF);
		value >>>= 8;
		bytes[1] = (byte) (value & 0x0FF);
		value >>>= 8;
		bytes[2] = (byte) (value & 0x0FF);
		value >>>= 8;
		bytes[3] = (byte) (value & 0x0FF);
		return bytes;
	}
	
	/**
	 * toHex
	 */
	public static String toHex(byte[] byteAry) {
		StringBuffer buffer = new StringBuffer(byteAry.length * 2);
		for (int i = byteAry.length - 1; i >= 0; i--) {
			int b1 = byteAry[i] & 0x0F;
			int b2 = (byteAry[i] >>> 4) & 0x0F;
			buffer.append(radixDigits[b2]);
			buffer.append(radixDigits[b1]);
		}
		return buffer.toString();
	}

	/**
	 * 从0~32767中循环获取一个整数
	 * 
	 * @return 0~32767之间的整数
	 */
	private static synchronized int getSeq32767() {
		int retv = nextSeq32767;
		nextSeq32767++;
		if (nextSeq32767 >= 32768) {
			nextSeq32767 = 0;
		}
		return retv;
	}

	/**
	 * 循环得到3个32进制字符的序号 对应的整数是0~32767
	 * 
	 * @return 0~VVV之间的字符串
	 */
	private static String getSeqRadix32() {
		String seqStr = Long.toString(getSeq32767(), 32).toUpperCase();
		int len = seqStr.length();
		if (len < 3) {
			StringBuffer buffer = new StringBuffer(3);
			int offset = 3 - len;
			for (int i = 0; i < offset; i++) {
				buffer.append("0");
			}
			buffer.append(seqStr);
			seqStr = buffer.toString();
		} else if (len > 3) {
			seqStr = seqStr.substring(len - 3);
		}
		return seqStr;
	}

	/**
	 * 从0~999中循环获取一个整数
	 * 
	 * @return 0~999之间的整数
	 */
	private static synchronized int getSeq999() {
		int retv = nextSeq999;
		nextSeq999++;
		if (nextSeq999 >= 1000) {
			nextSeq999 = 0;
		}
		return retv;
	}

	/**
	 * UUID转换为24位长的字符串
	 * 
	 * @param u
	 *            UUID
	 * @return 24位长的字符串
	 */
	public static String toShortString(UUID u) {
		long mostSigBits = u.getMostSignificantBits();
		long leastSigBits = u.getLeastSignificantBits();
		return (digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4)
				+ digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) 
				+ digits(leastSigBits, 12));
	}

	/**
	 * 数值转换为字符串
	 * 
	 * @param val
	 *            数值
	 * @param digits
	 *            数位数
	 * @return 字符串
	 */
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toString(hi | (val & (hi - 1)), 36).substring(1);
	}
	
	public static void main(String[] arg){
		System.out.println(UUIDUtils.generate());
	}
}
