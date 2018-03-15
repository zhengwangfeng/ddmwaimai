package com.tcsb.socket.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.tcsb.socket.VO.MessageVo;



@Controller
@RequestMapping("/socket")
public class AppletSocketController {
	
	@RequestMapping("/send")
	@ResponseBody
	public void sendSock(MessageVo msg) throws IOException, URISyntaxException{
		
		WebSocketClient client = new WebSocketClient(new URI("ws://www.beckjo.cn/waimai/websocket.ws/123"),new Draft_17()) {
		//WebSocketClient client = new WebSocketClient(new URI("ws://192.168.0.164:9090/ddmShopV2/websocket.ws/123"),new Draft_17()) {
	    	
	        @Override
	        public void onOpen(ServerHandshake arg0) {
	            System.out.println("打开链接");
	        }

	        @Override
	        public void onMessage(String arg0) {
	            System.out.println("收到消息"+arg0);
	        }

	        @Override
	        public void onError(Exception arg0) {
	            arg0.printStackTrace();
	            System.out.println("发生错误已关闭");
	        }

	        @Override
	        public void onClose(int arg0, String arg1, boolean arg2) {
	            System.out.println("链接已关闭");
	        }

	        @Override
	        public void onMessage(ByteBuffer bytes) {
	            try {
	                System.out.println(new String(bytes.array(),"utf-8"));
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	        }


	    };

	    client.connect();
	    while(!client.getReadyState().equals(READYSTATE.OPEN)){
	        System.out.print("1");
	    }
	    System.out.println("2");

		//msg.setBusinessCode("0");
		//msg.setShopId("8a9ad8035c0c5381015c0c7461db0023");
		//msg.setBusinessId("20171130183641887268815631655230");
		
		String ms = JSON.json(msg);
		System.out.println(ms);
	    client.send(ms);
		
	    client.close();
		
	}
	

}
