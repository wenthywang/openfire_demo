package com.hui;

import java.io.File;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;

/**
 * 
 * <pre>
 * helloworld。
 * </pre>
 * @author mmr  mmr@kungeek.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class HellowWorldPlugin implements Plugin {
	
	 private XMPPServer server;  
	 
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		  server = XMPPServer.getInstance();  
	     System.out.println("初始化.. 安装插件: " + server.isStarted());  
		
	}
	@Override
	public void destroyPlugin() {
		 System.out.println("服务器停止，销毁插件");  
		
	}

}
