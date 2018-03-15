package com.tcsb.socket.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebSocket处理类
 * 基本的SERVLET连接SOCKET
 *
 * @author HUYONG
 * @create 2016-12-06
 */
@ServerEndpoint("/websocket.ws/{userCode}")
public class WebsocketController {

	private static int onlineCount = 0;
    private static Logger LOGGER = LoggerFactory.getLogger(WebsocketController.class);

    private static Map<String,Session> webSocketSet = new ConcurrentHashMap<>();
    private Session session;

    /**
     * 打开连接事触发
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userCode") String userCode,Session session){
        LOGGER.info("打开websocket连接...");
        this.session = session;

        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        webSocketSet.put(userCode,session);
    }

    /**
     * 收到客户端消息时触发，并发送给特定用户
     * @param textJson
     * @return
     */
    @OnMessage
    public String onMessage(String textJson){
        String userCode = "";
        //业务模块
        String businessCode = "";
        String shopId = "";
        String des = "";
        String orderId = "";
        //信息是复杂对象
        String message = "";
        /*String content ="";
        String from = "";
        String to = "";
        String fromName = "";
        String toName = "";*/
        try {
            if(StringUtils.isNotEmpty(textJson)){
            	/* '{"shopId":"'+sendName+'","businessCode":"order","des":"下单通知","orderId":""}'
                 '{"shopId":"'+sendName+'","businessCode":"preorder","des":"预约通知","orderId":""}'*/
                //解析字符串
                JSONObject object = JSONObject.fromObject(textJson);
                shopId = object.getString("shopId");
                des = object.getString("des");
                businessCode = object.getString("businessCode");
                orderId = object.getString("orderId");
               /* userCode = object.getString("sendName");
                businessCode = object.getString("businessCode");
                if (businessCode.equals(BusinessEnum.BUSINESSORDER.getCode())) { 
					System.out.println(1);
				}*/
                //message = object.getString("data");
                //解析字符串
                /*JSONObject data = JSONObject.fromObject(message);
                content = data.getString("msg");
                from = data.getString("from");
                to = data.getString("to");
                fromName = data.getString("fromName");
                toName = data.getString("toName");*/
                //MessageDTO messageDTO = JSONHelper.fromJsonToObject(content, MessageDTO.class);
                //Session session = webSocketSet.get(userCode);
                Session session = webSocketSet.get(shopId);
                if(null != session){
                    //session.getBasicRemote().sendText(message);
                	session.getBasicRemote().sendText(textJson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "已经给"+userCode+"发送消息啦！message："+message;
    }


    /**
     * 异常时触发
     */
    @OnError
    public void onError(Throwable throwable) {
        LOGGER.info("Websocket连接出现异常:");
        LOGGER.info(throwable.getMessage(), throwable);
    }

    /**
     * 关闭连接时触发
     */
    @OnClose
    public void onClose(@PathParam("userCode") String userCode) {
        LOGGER.info("Websocket 关闭连接...");
        webSocketSet.remove(userCode);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    
    public void sendMessage(String message)
	    throws IOException
	  {
	    this.session.getBasicRemote().sendText(message);
	  }

	  public static synchronized int getOnlineCount()
	  {
	    return onlineCount;
	  }

	  public static synchronized void addOnlineCount() {
	    onlineCount += 1;
	  }

	  public static synchronized void subOnlineCount() {
	    onlineCount -= 1;
	  }
}
