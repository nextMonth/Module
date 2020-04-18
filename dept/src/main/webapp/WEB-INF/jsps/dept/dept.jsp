<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="dept,承保机构">
<meta name="description" content="dept manager">
<title>承保机构</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/pathurl.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dept/dept_index.js?V=1.00"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dept/dept_oper.js?V=1.00"></script>
</head>
<body>
	<%-- 查询条件 --%>
	<div id="qy_index" style="height: 30px; line-height: 30px;">
		<label style="vertical-align: middle; margin-left: 10px;font-size:13px;">机构代码：</label>
		<input id='qy_deptId' name='qy_deptId' class='easyui-textbox' style="height:20px">
		<label style="vertical-align: middle; margin-left: 10px;font-size:13px;">机构名称：</label>
		<input id='qy_deptName' name='qy_deptName' class='easyui-textbox' style="height:20px">
	</div>
	<%-- 功能按钮 --%>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" onclick="queryDept();"
			data-options="iconCls:'icon-search',plain:true">查询</a> <a href="#"
			class="easyui-linkbutton" onclick="addDeptWindow();"
			data-options="iconCls:'icon-add',plain:true">新增</a> <a href="#"
			class="easyui-linkbutton" onclick="editDeptWindow();"
			data-options="iconCls:'icon-edit',plain:true">修改</a> <a href="#"
			class="easyui-linkbutton" onclick="delDeptWindow();"
			data-options="iconCls:'icon-remove',plain:true">删除</a>
	</div>
	<%-- 数据表格模式 --%>
	<div id="qy_list">
		<table id="querypage"></table>
	</div>
	<%-- 树形表格模式 --%>
	<div id="qy_tree">
		<table id="index"></table>
	</div>
	<%--编辑窗口 --%>
	<div id="editDept" class="easyui-dialog"
		style="width: 500px; height: 350px;" closed="true">
		<form id="editfm" method="post" style="padding: 10px 10px 10px 10px;">
			<table border="0">
				<tr style="height: 30px;">
					<td>机构类型:</td>
					<td colspan="3"><input id="tb_kind" name="kind"
						style="width: 250px;"></td>
				</tr>
				<tr style="height: 30px;">
					<td>机构代码:</td>
					<td colspan="3"><input id="tb_deptId" name="deptId"
						class="easyui-validatebox"
						data-options="required:true,
          validType:'wordcode'"></td>
				</tr>
				<tr style="height: 30px;">
					<td>机构名称:</td>
					<td colspan="3"><input id="tb_deptName" name="deptName"
						class="easyui-validatebox" data-options="required:true"></td>
				</tr>
				<tr id="updept" ,style="height: 30px;">
					<td>上级机构:</td>
					<td colspan="3"><input id="tb_abvbranch" name="abvbranch"
						style="width: 250px;"></td>
				</tr>
				<tr style="height: 30px;">
					<td>使用状态</td>
					<td colspan="3"><input id="tb_isUsed" name="isUsed"
						class="easyui-combobox" style="width: 250px;"></td>
				</tr> 
			</table>
		</form>
	</div>
</body>
</html>