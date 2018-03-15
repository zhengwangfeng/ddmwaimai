package com.weixin.core.util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 调用微信H5支付统一下单接口 得到预支付ID
 * 
 * @author jimmy
 * 
 */

public class WxPayUtil {

	/**
	 * 
	 * getPrepayId(调用微信统一下单接口，生成微信预支付id)
	 * 
	 * @param totalFee
	 *            支付金额
	 * @param ipAddress
	 *            ip地址
	 * @param orderNumber
	 *            订单号
	 * @param body
	 *            商品或支付单简要描述
	 * @param openid
	 *            trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
	 * @return PrepayId
	 * @throws Exception
	 * @throws
	 * @author jimmy
	 */
	@SuppressWarnings("unchecked")
	public static String getPrepayId(String totalFee,
			String ipAddress, String orderNumber, String body, String openid)
			throws Exception {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", ConfigUtil.APPLET_APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("body", body);
		parameters.put("out_trade_no", orderNumber);
		parameters.put("total_fee", totalFee);
		parameters.put("spbill_create_ip", ipAddress);
		parameters.put("notify_url", ConfigUtil.APPLET_NOTIFY_URL);
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", openid);
		System.out.println("222222222222222222222"+ConfigUtil.APPLET_APPID);
		System.out.println("sendUrl2222="+ConfigUtil.APPLET_NOTIFY_URL);
		String sign = PayCommonUtil.createSign(parameters);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result = CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL,
				"POST", requestXML);
		System.out.println("result11111="+result);
		Map<String, String> map = XMLUtil.doXMLParse(result);// 解析微信返回的信息，以Map形式存储便于取值
		return map.get("prepay_id").toString();
	}
	

	/**
	 * 
	 * queryWeiXinOrder(微信订单查询)
	 * @param orderNumber  订单号
	 * @return   当返回  return_code="SUCCESS"  
	 *           和result_code="SUCCESS" 时 获取交易状态SUCCESS—支付成功
                                                   REFUND—转入退款
                                                   NOTPAY—未支付
                                                   CLOSED—已关闭
                                                   REVOKED—已撤销（刷卡支付）
                                                   USERPAYING--用户支付中
                                                   PAYERROR--支付失败(其他原因，如银行返回失败)
	 * @throws Exception 
	 * @throws
	 * @author jimmy
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String>  queryWeiXinOrder(String orderNumber) throws Exception{
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", ConfigUtil.APPLET_APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str",  PayCommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", orderNumber);
		String sign = PayCommonUtil.createSign(parameters);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result =CommonUtil.httpsRequest(ConfigUtil.CHECK_ORDER_URL, "POST", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return map;
	}
	
	/**
	 * 	
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 * @author Mar_x
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String>  queryAppletOrder(String orderNumber) throws Exception{
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", ConfigUtil.APPLET_APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str",  PayCommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", orderNumber);
		String sign = PayCommonUtil.createSign(parameters);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result =CommonUtil.httpsRequest(ConfigUtil.CHECK_ORDER_URL, "POST", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return map;
	}
	
	/**
	 * 
	 * createPackageValue(调起支付)
	 * @param appid
	 * @param appKey
	 * @param prepay_id
	 * @return 
	 * @throws
	 * @author jimmy
	 * @date  2016-7-5下午01:41:54
	 */
	 public static SortedMap<Object,Object> createPackageValue(String prepay_id)  {  
		    SortedMap<Object,Object> params = new TreeMap<Object,Object>();
	        params.put("appId", ConfigUtil.APPLET_APPID);
	        params.put("timeStamp", PayCommonUtil.create_timestamp());
	        params.put("nonceStr", PayCommonUtil.CreateNoncestr());
	        params.put("package", "prepay_id="+prepay_id);
	        params.put("signType", ConfigUtil.SIGN_TYPE);
	        String paySign =  PayCommonUtil.createSign(params);
	        params.put("packageValue", "prepay_id="+prepay_id);    //这里用packageValue是预防package是关键字在js获取值出错
	        params.put("paySign", paySign);                                                  //paySign的生成规则和Sign的生成规则一致
	        params.put("sendUrl", ConfigUtil.APPLET_NOTIFY_URL);                               //付款成功后跳转的页面     
	        return params;
	    }  
	 
}
