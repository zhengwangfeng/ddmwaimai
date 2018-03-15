package com.weixin.core.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.MD5;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.RandomUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.weixin.core.util.JsonResult;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.WxPayUtil;
import com.weixin.core.util.XMLUtil;

@Controller
@RequestMapping("/wechatPayController")
public class WechatPayController {

	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	@Autowired
	private SystemService systemService;

	/**
	 * 
	 * couponsConfirm(调起微信支付)
	 * 
	 * @param request
	 * @param totalFee
	 *            //支付金额
	 * @param body
	 *            //支付描述
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@RequestMapping(params = "couponsConfirm")
	@ResponseBody
	public AjaxJsonApi couponsConfirm(HttpServletRequest request,@RequestParam("openid") String openid,
			@RequestParam("orderId") String orderId) throws ParseException {
		String body = "点单么平台";
		// 获取订单
		TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.get(TcsbOrderEntity.class, orderId);
		Double boxFeePrice = 0.0;
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getBoxFeePrice())) {
			boxFeePrice = tcsbOrderEntity.getBoxFeePrice();
		}
		Double distributionPrice = tcsbOrderEntity.getDistributionPrice();
		Double totalMoney = 0.0;
		Double toPayMoney = 0.0; 
		Double shopDisPrice = 0.0;
		Double userDisPrice = 0.0;
		//获取订单项并计算总价
		List<TcsbOrderItemEntity> tcsbOrderItemEntities = tcsbOrderService.findHql("from TcsbOrderItemEntity where orderId = ?", orderId);
		for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
			Double temp = BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice()); 
			totalMoney = BigDecimalUtil.add(totalMoney, temp);
		}
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getDiscountActivityId())) {
			TcsbDiscountActivityEntity tcsbDiscountActivityEntity = systemService.get(TcsbDiscountActivityEntity.class, tcsbOrderEntity.getDiscountActivityId());
			TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = systemService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
			shopDisPrice = Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+"");
		}
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
			TcsbCouponEntity tcsbCouponEntity = systemService.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
			TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
			userDisPrice = Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+"");
		}
		Double aExtra = BigDecimalUtil.add(boxFeePrice, distributionPrice);
		Double dExtra = BigDecimalUtil.add(shopDisPrice, userDisPrice);
		toPayMoney = BigDecimalUtil.sub(totalMoney, dExtra);
		
		toPayMoney = Double.parseDouble(BigDecimalUtil.numericRetentionDecimal(BigDecimalUtil.add(toPayMoney, aExtra), 2));
		String money = toPayMoney.toString();// 获取订单金额
		//保留两位小数位
		money = BigDecimalUtil.numericRetentionDecimal(money, 2);
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		// 商户订单号
		String out_trade_no = OrderNumberGenerateUtil.getOrderNumber()+RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。
		int intMoney = Integer.parseInt(finalmoney);
		// 总金额以分为单位，不带小数点
		String totalFee = String.valueOf(intMoney);
		String ipAddress = request.getRemoteAddr();
		String prepayId;
		SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
		try {
			prepayId = WxPayUtil.getPrepayId(totalFee, ipAddress, out_trade_no,
					body, openid);
			// 2 给H5页面传递参数 调起微信支付
			signParams = WxPayUtil.createPackageValue(prepayId);
			tcsbOrderEntity.setOrderNo(out_trade_no);
			tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		ajaxJson.setObj(signParams);
		ajaxJson.setSuccess(true);
		return ajaxJson;
	}


	/**
	 * 微信支付异步回调
	 * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay.action“（微信坑不支持带参的，否则接收不到微信的流信息）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "payNotifyUrl")
	@ResponseBody
	public JsonResult payNotifyUrl(HttpServletRequest request,
			HttpServletResponse response,String userId) throws Exception {
		BufferedReader reader = null;
		reader = request.getReader();
		String line = "";
		String xmlString = null;
		StringBuffer inputString = new StringBuffer();

		while ((line = reader.readLine()) != null) {
			inputString.append(line);
		}
		xmlString = inputString.toString();
		request.getReader().close();
		System.out.println("----接收到的数据如下：---" + xmlString);
		Map<String, String> map = new HashMap<String, String>();
		map = XMLUtil.doXMLParse(xmlString);
		if (checkSign(xmlString)) {
			//商户订单号
			String orderNumber = (String) map.get("out_trade_no");
			if (orderNumber != null) {
				// 调用微信查询订单接口，
				Map<String, String> orderMap = WxPayUtil.queryWeiXinOrder(orderNumber);
				if (orderMap.get("return_code") != null&& orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
					if (orderMap.get("result_code") != null&& orderMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
						if (orderMap.get("trade_state") != null&& orderMap.get("trade_state").equalsIgnoreCase("SUCCESS")) {
							//支付成功回调业务处理
							 TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNumber);
							 tcsbOrderEntity.setPayTime(new Date());
							 tcsbOrderEntity.setPayStatus("1");
							 tcsbOrderEntity.setStatus("completed");
							 tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
							 //是否用了优惠券
							 if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
								TcsbCouponEntity tcsbCouponEntity = systemService.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
								tcsbCouponEntity.setUseStatus("1");
								tcsbCouponEntity.setUseTime(new Date());
								systemService.saveOrUpdate(tcsbCouponEntity);
							}
							 return JsonResult.ok();
						}
					}
				}else {
					return JsonResult.fail("");
				}
			}
			
		} 
		return JsonResult.ok();
	}

	private boolean checkSign(String xmlString) {

		Map<Object, Object> map = null;

		try {

			map = XMLUtil.doXMLParse(xmlString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String signFromAPIResponse = map.get("sign").toString();

		if (signFromAPIResponse == "" || signFromAPIResponse == null) {

			System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");

			return false;

		}
		System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);

		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

		map.put("sign", "");

		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

		String signForAPIResponse = getSign(map);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {

			// 签名验不过，表示这个API返回的数据有可能已经被篡改了

			System.out
					.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为"
							+ signForAPIResponse);

			return false;

		}

		System.out.println("恭喜，API返回的数据签名验证通过!!!");

		return true;

	}
	public String getSign(Map<Object, Object> map) {
		SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
		for (Map.Entry<Object, Object> stringStringEntry : map.entrySet()) {
			signParams.put(stringStringEntry.getKey(),
					stringStringEntry.getValue());
		}
		signParams.remove("sign");
		String sign = PayCommonUtil.createSign(signParams);
		return sign;
	}
	
	@RequestMapping(params = "refund")
	public void refund(HttpServletRequest request,HttpServletResponse response){
		System.out.println("++++++++++++++++++++++++++++++");
        
        
        System.out.println("==============================");
	    
	        /*PageData pd = new PageData();
	        pd = this.getPageData();*/

	        /*--------1.初始化数据参数----------*/
	        String appId= "wxfd33b1c6f460c400";
	        String secret= "708d13ef1f6aeee90697c4f47eb493d3";
	        String shh= "1433028902";
	        String key= "diandanmexiamenguotongbaoyouxian";
	        String filePath ="‪D:/ddm/apiclient_cert.pem"; //退款需要提供证书数据，所以需要根据证书路径读取证书
	        //需要退款的商户订单号，对应提交订单中的out_trade_no
	        String orderId = "201709131011113626_1505272299478";
	        String total_fee="200";  //也可以根据业务场景从数据库中获取总金额和退款金额
	        String refund_fee="100";
	        Map<String,String> result = (Map<String, String>) wxRefund(request,response,appId,secret,shh,key,orderId,total_fee,refund_fee,filePath);
	        System.out.println("++++++++++++++++++++++++++++++");
	        
	        
	        System.out.println("++++++++++++++++++++++++++++++");
	        /*
	        根据result的返回状态，处理你的业务逻辑
	        .....
	        */
		
	}

	private Object wxRefund(HttpServletRequest request, HttpServletResponse response, String appId, String secret,
			String shh, String key, String orderId, String total_fee, String refund_fee, String path) {
		Map<String, String> result = new HashMap<String, String>();
		/*PageData pd = new PageData();
		pd = this.getPageData();*/
		String refundid = UUIDGenerator.generate();
		String nonce_str = MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());

		/*-----  1.生成预支付订单需要的的package数据-----*/
		SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", shh);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", shh);
		packageParams.put("out_trade_no", orderId);
		packageParams.put("out_refund_no", refundid);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		/*----2.根据package生成签名sign---- */
		
	/*	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", ConfigUtil.APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str",  PayCommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", orderNumber);*/
		String sign = PayCommonUtil.createSign(packageParams);
		
		//RequestHandler reqHandler = new RequestHandler(request, response);
		//reqHandler.init(appId, secret, key);
		//String sign = reqHandler.createSign(packageParams);

		/*----3.拼装需要提交到微信的数据xml---- */
		String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<mch_id>" + shh + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<op_user_id>" + shh + "</op_user_id>" + "<out_trade_no>" + orderId
				+ "</out_trade_no>" + "<out_refund_no>" + refundid + "</out_refund_no>" + "<refund_fee>" + refund_fee
				+ "</refund_fee>" + "<total_fee>" + total_fee + "</total_fee>" + "<sign>" + sign + "</sign>" + "</xml>";
		try {
			/*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
			System.out.println(path.substring(1));
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(path.substring(1));
			try {
				keyStore.load(instream, shh.toCharArray());
			} finally {
				instream.close();
			}
			
			// 实例化密钥库
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());  
			// 初始化密钥工厂
			kmf.init(keyStore, shh.toCharArray());
			
			// 创建SSLContext
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
			// 获取SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			// Trust own CA and all self-signed certs
			//SSLContext sslcontext = SSLContext.custom().loadKeyMaterial(keyStore, shh.toCharArray()).build();
			// Allow TLSv1 protocol only
			/*SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);*/
			//CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			String requestUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 设置当前实例使用的SSLSocketFactory
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			if (null != xml) { 
				OutputStream outputStream = conn.getOutputStream(); 
				// 注意编码格式 
				outputStream.write(xml.getBytes("UTF-8")); 
				outputStream.close(); 
				} 
			
			
			// 从输入流读取返回内容 
			InputStream inputStream = conn.getInputStream(); 
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); 
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
			String str = null; 
			StringBuffer buffer = new StringBuffer(); 
			while ((str = bufferedReader.readLine()) != null) { 
			buffer.append(str); 
			} 
			// 释放资源 
			bufferedReader.close(); 
			inputStreamReader.close(); 
			inputStream.close(); 
			inputStream = null; 
			conn.disconnect(); 
			//return buffer.toString(); 
			
			
			//conn.connect();
			
			
			/*----5.发送数据到微信的退款接口---- */
			
			//HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			//httpost.setEntity(new StringEntity(xml, "UTF-8"));
			//HttpResponse weixinResponse = httpClient.execute(httpost);
			//String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
			//logger.info(jsonStr);

			//Map map = GetWxOrderno.doXMLParse(jsonStr);
			
			//String result2 =CommonUtil.httpsRequest(url, "POST", xml);
			System.out.println(buffer.toString());
			Map<String, String> map = XMLUtil.doXMLParse(buffer.toString());//解析微信返回的信息，以Map形式存储便于取值
			if ("success".equalsIgnoreCase(map.get("return_code"))) {
				
				result.put("returncode", "ok");
				result.put("returninfo", "退款成功");
			} else {
			
				result.put("returncode", "error");
				result.put("returninfo", "退款失败");
			}
		} catch (Exception e) {
			System.out.println(e);
			result.put("returncode", "error");
			result.put("returninfo", "退款失败");
		}
		return result;

	}
	
	
}
