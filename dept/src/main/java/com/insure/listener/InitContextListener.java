package com.insure.listener;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
 
 
public class InitContextListener extends  ContextLoaderListener {

	private static final Logger logger = Logger.getLogger(InitContextListener.class);  
	
	public void contextDestroyed(ServletContextEvent arg0) {
		super.contextDestroyed(arg0); 
		logger.info("销毁应用程序资源");
		logger.info("关闭应用程序"); 
	}

	public void contextInitialized(ServletContextEvent event) {
		//初始化消息组件，并加载到缓存中
		logger.info("正在启动应用程序");
		logger.info("初始化中");
		super.contextInitialized(event);   
	}


}
