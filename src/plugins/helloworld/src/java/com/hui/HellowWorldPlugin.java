package com.hui;

import java.io.File;


import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;
/**
 * 
 * <pre>
 * helloworld 插件。
 * </pre>
 * @author 王文辉  946374340@qq.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class HellowWorldPlugin implements Plugin,PacketInterceptor {
	
	 private XMPPServer server;  
	    //B: 消息拦截器
	    private InterceptorManager interceptorManager;
	 
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		  server = XMPPServer.getInstance();  
	     System.out.println("初始化.. 安装插件: " + server.isStarted());  

	        // 将当前插件加入到消息拦截管理器（interceptorManager ）中，当消息到来或者发送出去的时候，会触发本插件的interceptPacket方法。
	        interceptorManager = InterceptorManager.getInstance();
	        interceptorManager.addInterceptor(this);
		
	}
	@Override
	public void destroyPlugin() {
		 System.out.println("服务器停止，销毁插件");  
		
	}
	/* (non-Javadoc)
	 * @see org.jivesoftware.openfire.interceptor.PacketInterceptor#interceptPacket(org.xmpp.packet.Packet, org.jivesoftware.openfire.session.Session, boolean, boolean)
	 */
	@Override
	public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed)
			throws PacketRejectedException {
		
		
		  // incoming表示本条消息刚进入openfire。processed为false，表示本条消息没有被openfire处理过。这说明这是一条处女消息，也就是没有被处理过的消息。
        if (incoming && processed == false) {
// packet可能是IQ、Presence、Message，这里当packet是message的时候，进行处理。
            if (packet instanceof Message) {
// 将packet强制转换为Message
                Message msg = (Message)packet;
// 取得message中的body内容，就是消息正文
                String body = msg.getBody();
              if(body!=null){
            	  System.out.println( "from->"+packet.getFrom());
                  System.out.println( "to->"+ packet.getTo());
                  System.out.println("message->"+body);
              }
               
                // 如果内容中包含fuck，则拒绝处理消息
                if(body != null  && body.contains("fuck")){
                    // F: 这里通过抛出异常的方式，来阻止程序流程继续执行下去。
                    PacketRejectedException rejectedException =  new PacketRejectedException();
 
                    rejectedException.setRejectionMessage("fuck is error");
 
                    throw rejectedException;
                }
 
            }
            
            //处理用户上线下线
            if (packet instanceof Presence) {
            	Presence presence = (Presence)packet;
            	if(presence.isAvailable()){
            		System.out.println( presence.getFrom().toBareJID()+"->online");
            	}else{
            		System.out.println( presence.getFrom().toBareJID()+"->offline");
            	}
            }
        }
		
	}

}
