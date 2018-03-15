package com.tcsb.tcsbweixinuser.controller;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.tcsbuserfooter.entity.TcsbUserFooterEntity;
import com.tcsb.tcsbuserfooter.service.TcsbUserFooterServiceI;
import com.tcsb.tcsbweixinuser.VO.UserInfoVo;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.tcsbweixinuser.service.TcsbWeixinUserServiceI;
import com.weixin.core.util.WeixinUtil;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.vo.TcsbWeixinUserEntityVo;

import freemarker.core.ParseException;

/**   
 * @Title: Controller  
 * @Description: tcsb_weixin_user
 * @author onlineGenerator
 * @date 2017-05-22 10:43:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbWeixinUserController")
public class TcsbWeixinUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbWeixinUserController.class);

	@Autowired
	private TcsbWeixinUserServiceI tcsbWeixinUserService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbUserFooterServiceI tcsbUserFooterService;
	


	/**
	 * tcsb_weixin_user列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbweixinuser/tcsbWeixinUserList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbWeixinUserEntity tcsbWeixinUser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		CriteriaQuery cq = new CriteriaQuery(TcsbWeixinUserEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbWeixinUser, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbWeixinUserService.getDataGridReturn(cq, true);
		
		List<TcsbWeixinUserEntity> TcsbWeixinUserEntity = dataGrid.getResults();
		System.out.println(dataGrid.getResults().size()+"+++++++++++++++++++++++");
		System.out.println(TcsbWeixinUserEntity.size()+"+++++++++++++++++++++++");
		List<TcsbWeixinUserEntityVo> TcsbWeixinUserEntityVoList = new ArrayList<>();
		TcsbWeixinUserEntityVo tcsbWeixinUserEntityVo2;
		for (TcsbWeixinUserEntity tcsbWeixinUserEntity2 : TcsbWeixinUserEntity) {
			tcsbWeixinUserEntityVo2 = new TcsbWeixinUserEntityVo();
			BeanUtils.copyProperties(tcsbWeixinUserEntityVo2, tcsbWeixinUserEntity2);
			System.out.println(tcsbWeixinUserEntityVo2.getOpenid()+"=======================");
			TcsbWeixinUserEntityVoList.add(tcsbWeixinUserEntityVo2);
		}
		System.out.println(TcsbWeixinUserEntityVoList.size());
		//遍历获取用户的消费信息
		int i = 0;
		/*for (TcsbWeixinUserEntityVo tcsbWeixinUserEntityVo : TcsbWeixinUserEntityVoList) {
			//消费次数
			int saleCount = 0;
			//消费金额
			BigDecimal saleMoney = new BigDecimal("0.0");
			int saleShopCount = 0;
			Set set=new HashSet();
			//根据用户openid获取用户参与的订单编号
			String userorderhql = "from TcsbUserOrderEntity where userId='"+tcsbWeixinUserEntityVo.getOpenid()+"'";
			List<TcsbUserOrderEntity> tcsbUserOrderEntity = tcsbWeixinUserService.findByQueryString(userorderhql);
			//根据订单编号检查该订单是否完成支付消费
			if(StringUtil.isNotEmpty(tcsbUserOrderEntity)){
				for (TcsbUserOrderEntity tcsbUserOrderEntity2 : tcsbUserOrderEntity) {
					String orderhql = "from TcsbOrderEntity where id='"+tcsbUserOrderEntity2.getOrderId()+"'";
					TcsbOrderEntity tcsbOrderList= tcsbWeixinUserService.singleResult(orderhql);
					if(StringUtil.isNotEmpty(tcsbOrderList)){
						if(tcsbOrderList.getPayStatus().equals("1")){
							//////该订单已支付成功
							//用户消费次数+1
							saleCount++;
							//用户消费金额累计
							BigDecimal a = new BigDecimal(saleMoney.toString());  
							double aa = tcsbOrderList.getTotalPrice();
							BigDecimal a1 = new BigDecimal(aa); 
							saleMoney = a.add(a1);
							
							//获取用户消费的店铺//去重复//set
							set.add(tcsbOrderList.getShopId());
						}
					}
				}
				
				
				if(saleCount == 0){
					TcsbWeixinUserEntityVoList.get(i).setAvgSaleMoney("0");
					TcsbWeixinUserEntityVoList.get(i).setSaleCount(saleCount);
					TcsbWeixinUserEntityVoList.get(i).setSaleMoney(saleMoney.toString());
					TcsbWeixinUserEntityVoList.get(i).setSaleShopCount(saleShopCount);
				}else{
					saleShopCount = set.size();
					BigDecimal b = new BigDecimal(saleMoney.toString());   
					BigDecimal one = new BigDecimal(saleCount);   
					double avgSaleMoney = b.divide(one,2,BigDecimal.ROUND_HALF_UP).doubleValue();   
					TcsbWeixinUserEntityVoList.get(i).setAvgSaleMoney(avgSaleMoney+"");
					TcsbWeixinUserEntityVoList.get(i).setSaleCount(saleCount);
					TcsbWeixinUserEntityVoList.get(i).setSaleMoney(saleMoney.toString());
					TcsbWeixinUserEntityVoList.get(i).setSaleShopCount(saleShopCount);
				}
				
				
				
			}else{
				
				TcsbWeixinUserEntityVoList.get(i).setAvgSaleMoney("0");
				TcsbWeixinUserEntityVoList.get(i).setSaleCount(saleCount);
				TcsbWeixinUserEntityVoList.get(i).setSaleMoney(saleMoney.toString());
				TcsbWeixinUserEntityVoList.get(i).setSaleShopCount(saleShopCount);
				
			}
			
			i++;
		}*/
		
		dataGrid.setResults(TcsbWeixinUserEntityVoList);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除tcsb_weixin_user
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbWeixinUserEntity tcsbWeixinUser, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbWeixinUser = systemService.getEntity(TcsbWeixinUserEntity.class, tcsbWeixinUser.getId());
		message = "tcsb_weixin_user删除成功";
		try{
			tcsbWeixinUserService.delete(tcsbWeixinUser);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "tcsb_weixin_user删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除tcsb_weixin_user
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "tcsb_weixin_user删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbWeixinUserEntity tcsbWeixinUser = systemService.getEntity(TcsbWeixinUserEntity.class, 
				id
				);
				tcsbWeixinUserService.delete(tcsbWeixinUser);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "tcsb_weixin_user删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加tcsb_weixin_user
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbWeixinUserEntity tcsbWeixinUser, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "tcsb_weixin_user添加成功";
		try{
			tcsbWeixinUserService.save(tcsbWeixinUser);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "tcsb_weixin_user添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新tcsb_weixin_user
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbWeixinUserEntity tcsbWeixinUser, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "tcsb_weixin_user更新成功";
		TcsbWeixinUserEntity t = tcsbWeixinUserService.get(TcsbWeixinUserEntity.class, tcsbWeixinUser.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbWeixinUser, t);
			tcsbWeixinUserService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "tcsb_weixin_user更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * tcsb_weixin_user新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbWeixinUserEntity tcsbWeixinUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbWeixinUser.getId())) {
			tcsbWeixinUser = tcsbWeixinUserService.getEntity(TcsbWeixinUserEntity.class, tcsbWeixinUser.getId());
			req.setAttribute("tcsbWeixinUserPage", tcsbWeixinUser);
		}
		return new ModelAndView("com/tcsb/tcsbweixinuser/tcsbWeixinUser-add");
	}
	/**
	 * tcsb_weixin_user编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbWeixinUserEntity tcsbWeixinUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbWeixinUser.getId())) {
			tcsbWeixinUser = tcsbWeixinUserService.getEntity(TcsbWeixinUserEntity.class, tcsbWeixinUser.getId());
			req.setAttribute("tcsbWeixinUserPage", tcsbWeixinUser);
		}
		return new ModelAndView("com/tcsb/tcsbweixinuser/tcsbWeixinUser-update");
	}
	
	/**
	 * 小程序登陆后台获取openid
	 * @param js_code(开发者服务器使用登录凭证 code 获取 session_key 和 openid。)
	 * @return openid 用户唯一标识 session_key 会话密钥  expires_in 有效时间
	 * 
	 */
	@RequestMapping("/appletLogin")
	@ResponseBody
	public AjaxJsonApi appletLogin(String js_code){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		String APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//服务号的appid 
		String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//服务号的appSecrect 
		String requestUrl = WeixinUtil.applet_jscode2session_url.replace("APPID",APPID).replace("APPSECRET",APP_SECRECT).replace("JSCODE", js_code);
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",null);
		ajaxJson.setMsg(null);
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(jsonObject);	
		return ajaxJson;
	}
	
	/**
	 * 注册平台与商家用户
	 * @param userInfo(小程序所获取的用户信息加上后台的openid,所属的店铺ID)
	 * @return
	 */
	@RequestMapping("/register")
    @ResponseBody
    public AjaxJsonApi register(UserInfoVo userInfo) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        if (StringUtil.isNotEmpty(userInfo.getOpenId())) {
            String hql = "from TcsbWeixinUserEntity where openid='" + userInfo.getOpenId() + "'";
            //查看平台是否有该用户
            TcsbWeixinUserEntity wxuser = tcsbWeixinUserService.singleResult(hql);
            //存在的情况
            if (StringUtil.isNotEmpty(wxuser)) {
                if (StringUtil.isNotEmpty(userInfo.getShopId())) {
                	//是否注册到商家用户中
                    String hqls = "from WeixinUserEntity where openid='" + userInfo.getOpenId() + "' and shopId='" + userInfo.getShopId() + "'";
                    WeixinUserEntity checkUser = tcsbWeixinUserService.singleResult(hqls);
                    if (StringUtil.isNotEmpty(checkUser)) {
                        ajaxJson.setMsg("用户已注册");
                        ajaxJson.setSuccess(true);
                    } else {
                        WeixinUserEntity wUser = new WeixinUserEntity();
                        wUser.setOpenid(userInfo.getOpenId());
                        wUser.setShopId(userInfo.getShopId());
                        wUser.setCreateTime(new Date());
                        try {
                        	tcsbWeixinUserService.save(wUser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ajaxJson.setMsg("用户绑定成功");
                        ajaxJson.setSuccess(true);
                        ajaxJson.setObj(Boolean.valueOf(false));
                    }
                } else {
                    ajaxJson.setMsg("用户登录成功");
                    ajaxJson.setSuccess(true);
                }
            } else {
                try {
                	tcsbWeixinUserService.saveBaseUserInfo(userInfo);
                } catch (Exception e) {
                    logger.error(ExceptionUtil.getExceptionMessage(e));
                    e.printStackTrace();
                }
                ajaxJson.setMsg("注册成功");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(Boolean.valueOf(false));
            }
            //添加我的足迹信息
            String hqls = "from TcsbUserFooterEntity where openid='" + userInfo.getOpenId() + "' and shopId='" + userInfo.getShopId() + "'";
            TcsbUserFooterEntity tcsbUserFooterEntity = tcsbUserFooterService.singleResult(hqls);
            if (StringUtil.isEmpty(tcsbUserFooterEntity)) {
                TcsbUserFooterEntity tcsbUserFooter = new TcsbUserFooterEntity();
                tcsbUserFooter.setOpenid(userInfo.getOpenId());
                tcsbUserFooter.setShopId(userInfo.getShopId());
                try {
					tcsbUserFooterService.save(tcsbUserFooter);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } else {
            ajaxJson.setMsg("error:openid cannot NULL");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }
	
	 /**
     * 去支付检测用户是需要绑定手机
     *
     * @param userId
     * @return
     */
    @RequestMapping("/checkBindMobile")
    @ResponseBody
    public AjaxJsonApi checkBindMobile(String userId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        TcsbWeixinUserEntity tuser = tcsbWeixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
        if (StringUtil.isNotEmpty(tuser)) {
            if (StringUtil.isNotEmpty(tuser.getMobile())) {
                ajaxJson.setMsg("用户已绑定手机");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(true);
            } else {
                ajaxJson.setMsg("未绑定手机");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(false);
            }
        } else {
            ajaxJson.setMsg("用户不存在");
            ajaxJson.setSuccess(true);
            ajaxJson.setObj(false);
        }
        return ajaxJson;
    }


    /**
     * 绑定手机号
     *
     * @param userId
     * @param mobile
     * @param smsCode
     * @return
     */
    @RequestMapping("/bingMobile")
    @ResponseBody
    public AjaxJsonApi bingMobile(String userId, @RequestParam String mobile, @RequestParam String smsCode) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();

        TcsbWeixinUserEntity tuser = tcsbWeixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
        if (StringUtil.isNotEmpty(tuser)) {
            if (tuser.getSmscode().equals(smsCode) && tuser.getWaitMobile().equals(mobile)) {
                TcsbWeixinUserEntity checkmobile = tcsbWeixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "mobile", mobile);
                if (StringUtil.isEmpty(checkmobile)) {
                    tuser.setMobile(mobile);
                    tuser.setSendtime(null);
                    tuser.setSmscode(null);
                    tuser.setWaitMobile(null);
                    try {
						tcsbWeixinUserService.saveOrUpdate(tuser);
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                ajaxJson.setMsg("绑定成功");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(null);
            } else {
                ajaxJson.setMsg("验证码不正确");
                ajaxJson.setSuccess(false);
                ajaxJson.setObj(null);
            }
        } else {
            ajaxJson.setMsg("用户不存在");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }

    /**
     * 获取手机绑定验证码
     *
     * @param userId
     * @param mobile
     * @return
     */
    @RequestMapping("/getSmsCode")
    @ResponseBody
    public AjaxJsonApi getSmsCode(String userId, String mobile) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(mobile)) {
            TcsbWeixinUserEntity tuser = tcsbWeixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "mobile", mobile);
            if (StringUtil.isNotEmpty(tuser)) {
                if (tuser.getOpenid().equals(userId)) {
                    ajaxJson.setMsg("已绑定该手机号码");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setObj(null);
                } else {
                    ajaxJson.setMsg("该手机号码已被绑定");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setObj(null);
                }
            } else {
                //检测验证码
                SellerSmsClient client = new SellerSmsClient();
                String smsCode;
                TcsbWeixinUserEntity weixinUser = tcsbWeixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
                if (StringUtil.isNotEmpty(weixinUser)) {
                    if (StringUtil.isEmpty(weixinUser.getSmscode())) {
                        try {
                            smsCode = client.sendMessage(mobile);
                            weixinUser.setSmscode(smsCode);
                            weixinUser.setWaitMobile(mobile);
                            weixinUser.setSendtime(new Date());
                            tcsbWeixinUserService.saveOrUpdate(weixinUser);
                            ajaxJson.setMsg("发送成功");
                            ajaxJson.setSuccess(true);
                            ajaxJson.setObj(null);
                        } catch (Exception e) {
                            ajaxJson.setMsg("发送失败");
                            ajaxJson.setSuccess(false);
                            ajaxJson.setObj(null);
                        }
                    } else {
                        Calendar calDes = null;
                        try {
                            calDes = DateUtils.parseCalendar(DateUtils.date2Str(weixinUser.getSendtime(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                            Calendar calSrc = null;
                            calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                        if (DateUtils.dateDiff('s', calSrc, calDes) < 120) {
                            ajaxJson.setMsg(ReturnMessageEnum.SMSISSEND.getMsg());
                            ajaxJson.setSuccess(false);
                            ajaxJson.setObj(null);
                        } else {
                            //短信过期重新发送
                                smsCode = client.sendMessage(mobile);
                                ajaxJson.setMsg(ReturnMessageEnum.SMSISOUTOFDATE.getMsg() + "重新发送");
                                ajaxJson.setSuccess(true);
                                weixinUser.setSmscode(smsCode);
                                weixinUser.setWaitMobile(mobile);
                                weixinUser.setSendtime(new Date());
                                ajaxJson.setObj(null);
								tcsbWeixinUserService.saveOrUpdate(weixinUser);

                        }
                        } catch (Exception e) {
                        	 ajaxJson.setMsg("发送失败");
                             ajaxJson.setSuccess(false);
                             ajaxJson.setObj(null);
                        }
                      
                    }
                } else {
                    ajaxJson.setMsg("用户不存在");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setObj(null);
                }
            }
        } else {
            ajaxJson.setMsg("用户ID，mobile不能为空");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbWeixinUserController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbWeixinUserEntity tcsbWeixinUser,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbWeixinUserEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbWeixinUser, request.getParameterMap());
		List<TcsbWeixinUserEntity> tcsbWeixinUsers = this.tcsbWeixinUserService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"tcsb_weixin_user");
		modelMap.put(NormalExcelConstants.CLASS,TcsbWeixinUserEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("tcsb_weixin_user列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbWeixinUsers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbWeixinUserEntity tcsbWeixinUser,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"tcsb_weixin_user");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbWeixinUserEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("tcsb_weixin_user列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TcsbWeixinUserEntity> listTcsbWeixinUserEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbWeixinUserEntity.class,params);
				for (TcsbWeixinUserEntity tcsbWeixinUser : listTcsbWeixinUserEntitys) {
					tcsbWeixinUserService.save(tcsbWeixinUser);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TcsbWeixinUserEntity> list() {
		List<TcsbWeixinUserEntity> listTcsbWeixinUsers=tcsbWeixinUserService.getList(TcsbWeixinUserEntity.class);
		return listTcsbWeixinUsers;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbWeixinUserEntity task = tcsbWeixinUserService.get(TcsbWeixinUserEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbWeixinUserEntity tcsbWeixinUser, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbWeixinUserEntity>> failures = validator.validate(tcsbWeixinUser);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbWeixinUserService.save(tcsbWeixinUser);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbWeixinUser.getId();
		URI uri = uriBuilder.path("/rest/tcsbWeixinUserController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbWeixinUserEntity tcsbWeixinUser) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbWeixinUserEntity>> failures = validator.validate(tcsbWeixinUser);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbWeixinUserService.saveOrUpdate(tcsbWeixinUser);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tcsbWeixinUserService.deleteEntityById(TcsbWeixinUserEntity.class, id);
	}
}
