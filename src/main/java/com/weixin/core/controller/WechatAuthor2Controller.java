package com.weixin.core.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.weixin.core.util.AdvancedUtil;
import com.weixin.p3.oauth2.pojo.oauth2.WeixinOauth2Token;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.service.WeixinUserServiceI;

@Controller
@RequestMapping("/wechatAuthor2Controller")
public class WechatAuthor2Controller {
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Autowired
	private WeixinUserServiceI weixinUserService;
	@Autowired
	private SystemService systemService;

	/**
	 * 桌位二维码扫描授权入口
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "wechatAuthor2")
	public void wechatAuthor2(HttpServletRequest request,HttpServletResponse response) {
		// 用户同意授权后，能获取到code 051Zr5kd0zLLCw1xWjhd0Ozdkd0Zr5kR
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String no = request.getParameter("no");
		String deskName = request.getParameter("deskName");
		try {
			deskName = URLEncoder.encode(deskName, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String shopId = request.getParameter("shopId");
		try {
		// 用户同意授权
				if (!"authdeny".equals(code)) {
					String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid 
		    		String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect 
		    		//List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
					//WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
					// 获取网页授权access_token
					WeixinOauth2Token weixinOauth2Token = AdvancedUtil
							.getOauth2AccessToken(
									APPID,
									APP_SECRECT, code);
					// 网页授权接口访问凭证
					String accessToken = weixinOauth2Token.getAccessToken();
					// 用户标识 oYc6vwMckVqZ5k5SzLYHxrmiO0TA
					String openId = weixinOauth2Token.getOpenId();
					// 获取用户信息
					Wxuser snsUserInfo =null;
						//snsUserInfo = JwUserAPI.getWxuser(accessToken, openId);//根据user_openid 获取关注用户的基本信息
					snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
					//保存授权用户信息(包括店铺信息)
					//WeixinUserEntity weixinUserEntity = weixinUserService.findUniqueByProperty(WeixinUserEntity.class, "openid", openId);
					List<WeixinUserEntity> weixinUserEntities = weixinUserService.findHql("from WeixinUserEntity where openid= ? and shopId=?", openId,shopId);
					WeixinUserEntity weixinUserEntity = new WeixinUserEntity();
					if (weixinUserEntities.isEmpty()) {
						//MyBeanUtils.copyBeanNotNull2Bean(snsUserInfo, weixinUserEntity);
						if (!StringUtil.isEmpty(snsUserInfo.getCity())) {
							//weixinUserEntity.setCity(snsUserInfo.getCity());
						}
						if (!StringUtil.isEmpty(snsUserInfo.getCountry())) {
							//weixinUserEntity.setCountry(snsUserInfo.getCountry());
						}
						if (!StringUtil.isEmpty(snsUserInfo.getHeadimgurl())) {
							//weixinUserEntity.setHeadimgurl(snsUserInfo.getHeadimgurl());
						}
						if (!StringUtil.isEmpty(snsUserInfo.getNickname())) {
							//过滤emoji表情
							System.out.println(snsUserInfo.getNickname());
							String niakname = snsUserInfo.getNickname().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""); 
							System.out.println(niakname);
							//weixinUserEntity.setNickname(niakname);
						}
						weixinUserEntity.setOpenid(snsUserInfo.getOpenid());
						//weixinUserEntity.setPrivilege(snsUserInfo.getPrivilegeList().toString());
						if (!StringUtil.isEmpty(snsUserInfo.getProvince())) {
							//weixinUserEntity.setProvince(snsUserInfo.getProvince());
						}
						if (!StringUtil.isEmpty(snsUserInfo.getSex())) {
							//weixinUserEntity.setSex(snsUserInfo.getSex());
						}
						weixinUserEntity.setShopId(shopId);
						weixinUserEntity.setCreateTime(new Date());
						weixinUserEntity.setIsSale("0");
						weixinUserService.save(weixinUserEntity);
					}
					else {
						weixinUserEntity =weixinUserEntities.get(0);
					}
					//该桌位是否已经有订单了如果有就到订单详情页面
					//(当前桌位是否已经有订单生成了并且未付款的)
					Map<String, Object>  map = systemService.findOneForJdbc("select o.id,o.order_no from tcsb_order o  where o.desk_id=? and o.pay_status='0'", no);
					if (map!=null) {
						String orderNo = (String) map.get("order_no");
						
						String deskBaseUrl = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl");
						String redirectUrl = deskBaseUrl+"/ddan/view/OrderDetails.html?orderNo="+orderNo+"&openId="+weixinUserEntity.getOpenid();
						response.sendRedirect(redirectUrl);
					}
					String deskBaseUrl = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl");
					String redirectUrl = deskBaseUrl+"/ddan/view/OrderDishes.html?no="+no+"&deskName="+deskName+"&shopId="+shopId+"&openid="+weixinUserEntity.getOpenid();
					response.sendRedirect(redirectUrl);
					
					//response.sendRedirect("http://www.diandanme.xyz/ddan/view/OrderDishes.html?no="+no+"&deskName="+deskName+"&shopId="+shopId+"&openid="+weixinUserEntity.getOpenid());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 授权个人中心
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "wechatAuthor2ByUserId")
	public void wechatAuthor2ByUserId(HttpServletRequest request,HttpServletResponse response) {
		// 用户同意授权后，能获取到code 051Zr5kd0zLLCw1xWjhd0Ozdkd0Zr5kR
		String code = request.getParameter("code");
		try {
		// 用户同意授权
				if (!"authdeny".equals(code)) {
					List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService
							.getList(WeixinAccountEntity.class);
					WeixinAccountEntity weixinAccountEntity = weixinAccountEntities
							.get(0);
					// 获取网页授权access_token
					WeixinOauth2Token weixinOauth2Token = AdvancedUtil
							.getOauth2AccessToken(
									weixinAccountEntity.getAccountappid(),
									weixinAccountEntity.getAccountappsecret(), code);
					// 网页授权接口访问凭证
					String accessToken = weixinOauth2Token.getAccessToken();
					// 用户标识 oYc6vwMckVqZ5k5SzLYHxrmiO0TA
					String openId = weixinOauth2Token.getOpenId();
					
					String deskBaseUrl = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl");
					String redirectUrl = deskBaseUrl+"/ddan/view/personal.html?openId="+openId;
					response.sendRedirect(redirectUrl);
					
					//response.sendRedirect("http://www.diandanme.xyz/ddan/view/personal.html?openId="+openId);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 授权个人订单
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "wechatAuthor2Order")
	public void wechatAuthor2Order(HttpServletRequest request,HttpServletResponse response) {
		// 用户同意授权后，能获取到code 051Zr5kd0zLLCw1xWjhd0Ozdkd0Zr5kR
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String no = request.getParameter("no");
		String deskName = request.getParameter("deskName");
		String shopId = request.getParameter("shopId");
		try {
		// 用户同意授权
				if (!"authdeny".equals(code)) {
					String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid 
		    		String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect 
					//List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
					//WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
					// 获取网页授权access_token
					WeixinOauth2Token weixinOauth2Token = AdvancedUtil
							.getOauth2AccessToken(
									APPID,
									APP_SECRECT, code);
					// 网页授权接口访问凭证
					String accessToken = weixinOauth2Token.getAccessToken();
					// 用户标识 oYc6vwMckVqZ5k5SzLYHxrmiO0TA
					String openId = weixinOauth2Token.getOpenId();
					String deskBaseUrl = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl");
					String redirectUrl = deskBaseUrl+"/ddan/view/OrderList.html?openid="+openId;
					response.sendRedirect(redirectUrl);
					//response.sendRedirect("http://www.diandanme.xyz/ddan/view/OrderList.html?openid="+openId);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 授权领取优惠卷
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "wechatAuthor2Coupon")
	public void wechatAuthor2Coupon(HttpServletRequest request,HttpServletResponse response) {
		// 用户同意授权后，能获取到code 051Zr5kd0zLLCw1xWjhd0Ozdkd0Zr5kR
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		try {
			// 用户同意授权
			if (!"authdeny".equals(code)) {
				String deskBaseUrl = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl");
//				AjaxJsonApi ajaxJson = appletUserInfoService.getUserInfoByCode(code);
//				String redirectUrl = deskBaseUrl+"/ddan/weixincoupon/index.html?unionid="+ajaxJson.getObj().toString();
				String redirectUrl = deskBaseUrl+"/ddan/weixincoupon/index.html";
				response.sendRedirect(redirectUrl);
				//response.sendRedirect("http://www.diandanme.xyz/ddan/view/OrderList.html?openid="+openId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getWeiXinUserByopenId",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getWeiXinUserByopenId(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		//WeixinUserEntity weixinUserEntity = weixinUserService.findUniqueByProperty(WeixinUserEntity.class, "openid", userId);
		List<WeixinUserEntity> weixinUserEntities = weixinUserService.findByProperty(WeixinUserEntity.class, "openid", userId);
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		if (weixinUserEntities.isEmpty()) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			ajaxJsonApi.setMsg(ReturnMessageEnum.WEIXINUSERNOTEXIT.getMsg());
			ajaxJsonApi.setSuccess(false);
			return new JSONPObject(callbackFunName, ajaxJsonApi); 
			
		}
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(weixinUserEntities.get(0));
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
}
