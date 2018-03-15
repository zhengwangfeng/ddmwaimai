package test;

import org.junit.Test;

import com.weixin.core.util.WeixinUtil;

import net.sf.json.JSONObject;

public class MessageTest {
	
	
	/** 
     * 发送消息 
     * @param accessToken 
     * @param jsonMsg 
     * @return 
     */  
	@Test
    public void sendTemplateMessage(){  
        
		String jsonmsg = "{\"data\":{\"keyword5\":{\"color\":\"#000000\",\"value\":\"1\"},"
				+ "\"keyword6\":{\"color\":\"#000000\",\"value\":\"厦门市集美区软件园三期A区04幢1303之二\"},"
				+ "\"keyword7\":{\"color\":\"#000000\",\"value\":\"0592-5588739\"},"
				+ "\"keyword8\":{\"color\":\"#000000\",\"value\":\"\"},"
				+ "\"keyword1\":{\"color\":\"#000000\"},\"keyword2\":{\"color\":\"#000000\",\"value\":\"2017-12-17 08:00\"},"
				+ "\"keyword3\":{\"color\":\"#000000\",\"value\":\"0.01元(押金)\"},"
				+ "\"keyword4\":{\"color\":\"#000000\",\"value\":\"点单么官方旗舰店\"}},"
				+ "\"template_id\":\"Ro7S3qONk8wwDsAgbsf_CKIHiapf6YK2HllIMNqoKhc\","
				+ "\"form_id\":\"wx20171218172416ab673aca040134027630\","
				+ "\"touser\":\"oZdMf0TMWK658gFAKWoaMcYNOfq4\","
				+ "\"page\":\"\"}";
//		
//		MessageTemple msg = new MessageTemple();
//		msg.setPage("index");
//		msg.setData(null);
//		//msg.setEmphasis_keyword("keyword1.DATA");
//		msg.setForm_id("prepay_id=wx201712181539551b25933f4a0551086752");
//		//msg.setPage("index/index");
//		//msg.setPrepay_id("wx201712181539551b25933f4a0551086752");
//		msg.setTemplate_id("4RdMnZV1paxLyQIvXa4hrJBUliDyYRXVqMEw9agHcUs");
//		msg.setTouser("oZdMf0TMWK658gFAKWoaMcYNOfq4");
//		msg.setEmphasis_keyword("keyword1.DATA");
//		String jsonmsg = null;
//		try {
//			jsonmsg = JSON.json(msg);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(jsonmsg);
		//jsonmsg = "{'offset':0,'count':5}";
		//+ "'form_id': 'wx2017121812083517a3b147b00839412582',"
        boolean result = false;  
        //请求地址  
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";  
        //String requestUrl = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=ACCESS_TOKEN";  
        requestUrl = requestUrl.replace("ACCESS_TOKEN", "5_of0IcAEqBb6pOzPFTjLhIM0s412UWYiDXzbYvcBx4VfxYIugSQitCQSgHEBLT8QDP2id-Iw3jyx0a3yjDrNtn7lLCsK5lRYASzgtzOWdQMEyNnFpAfCeJ-GSOjyT8JizwCo9VIphS6yDB09vNDUaAJARHY");  
        //发送模板消息  
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", jsonmsg);  
        if(null != jsonObject){  
            int errorCode = jsonObject.getInt("errcode");  
            String errorMsg = jsonObject.getString("errmsg");  
            if(0 == errorCode){  
                result = true;
                System.out.println("模板消息发送成功errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");  
            }else{  
               
                System.out.println("模板消息发送失败errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");  
            }  
        }  
       System.out.println(result);
    }  


}
