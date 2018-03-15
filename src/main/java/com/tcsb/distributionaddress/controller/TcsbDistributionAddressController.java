package com.tcsb.distributionaddress.controller;
import com.tcsb.distributionaddress.VO.TcsbDistributionAddressListItemVO;
import com.tcsb.distributionaddress.VO.TcsbDistributionAddressVO;
import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;
import com.tcsb.distributionaddress.service.TcsbDistributionAddressServiceI;
import com.tcsb.distributionaddressrea.entity.TcsbDistributionAddressReaEntity;
import com.tcsb.distributionscope.entity.TcsbDistributionScopeEntity;
import com.tcsb.distributionscope.service.TcsbDistributionScopeServiceI;
import com.tcsb.distributionscopeattr.service.TcsbDistributionScopeAttrServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DistanceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import org.jeecgframework.core.util.ExceptionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller  
 * @Description: 用户配送地址
 * @author onlineGenerator
 * @date 2018-01-12 16:18:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbDistributionAddressController")
public class TcsbDistributionAddressController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbDistributionAddressController.class);

	@Autowired
	private TcsbDistributionAddressServiceI tcsbDistributionAddressService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbDistributionScopeAttrServiceI tcsbDistributionScopeAttrService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbDistributionScopeServiceI tcsbDistributionScopeService;
	


	/**
	 * 用户配送地址列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/distributionaddress/tcsbDistributionAddressList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbDistributionAddressEntity tcsbDistributionAddress,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDistributionAddressEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDistributionAddress, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbDistributionAddressService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除用户配送地址
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbDistributionAddressEntity tcsbDistributionAddress, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbDistributionAddress = systemService.getEntity(TcsbDistributionAddressEntity.class, tcsbDistributionAddress.getId());
		message = "用户配送地址删除成功";
		try{
			tcsbDistributionAddressService.delete(tcsbDistributionAddress);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户配送地址删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户配送地址
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户配送地址删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbDistributionAddressEntity tcsbDistributionAddress = systemService.getEntity(TcsbDistributionAddressEntity.class, 
				id
				);
				tcsbDistributionAddressService.delete(tcsbDistributionAddress);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户配送地址删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加用户配送地址
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbDistributionAddressEntity tcsbDistributionAddress, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户配送地址添加成功";
		try{
			tcsbDistributionAddressService.save(tcsbDistributionAddress);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户配送地址添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户配送地址
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbDistributionAddressEntity tcsbDistributionAddress, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户配送地址更新成功";
		TcsbDistributionAddressEntity t = tcsbDistributionAddressService.get(TcsbDistributionAddressEntity.class, tcsbDistributionAddress.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbDistributionAddress, t);
			tcsbDistributionAddressService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户配送地址更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户配送地址新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbDistributionAddressEntity tcsbDistributionAddress, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDistributionAddress.getId())) {
			tcsbDistributionAddress = tcsbDistributionAddressService.getEntity(TcsbDistributionAddressEntity.class, tcsbDistributionAddress.getId());
			req.setAttribute("tcsbDistributionAddressPage", tcsbDistributionAddress);
		}
		return new ModelAndView("com/tcsb/distributionaddress/tcsbDistributionAddress-add");
	}
	/**
	 * 用户配送地址编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbDistributionAddressEntity tcsbDistributionAddress, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDistributionAddress.getId())) {
			tcsbDistributionAddress = tcsbDistributionAddressService.getEntity(TcsbDistributionAddressEntity.class, tcsbDistributionAddress.getId());
			req.setAttribute("tcsbDistributionAddressPage", tcsbDistributionAddress);
		}
		return new ModelAndView("com/tcsb/distributionaddress/tcsbDistributionAddress-update");
	}
	
    /**
     * 添加用户配送地址
     * @param userId
     * @param address
     * @param longitude
     * @param latitude
     * @param shopId
     * @return
     */
    @RequestMapping("/addDistributionaddress")
    @ResponseBody
    public AjaxJsonApi addDistributionaddress(TcsbDistributionAddressVO tcsbDistributionAddressVO) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
			tcsbDistributionAddressService.addDistributionaddress(tcsbDistributionAddressVO);
		} catch (Exception e) {
			 ajaxJson.setMsg("添加用户配送地址失败");
		     ajaxJson.setSuccess(false);
		}
        ajaxJson.setMsg("添加用户配送地址");
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }
	
    /**
     * 获取用户配送地址
     * 并区分是不是在配送范围内
     * @param userId
     * @param shopId
     * @return
     */
    @RequestMapping("/getDistributionaddress")
    @ResponseBody
    public AjaxJsonApi getDistributionaddress(String userId,@RequestParam String shopId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Double mileage = 0.0;
        //获取店铺的经纬度信息
        TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);
        Double sLatitude = Double.parseDouble(tcsbShopEntity.getLatitude());
        Double sLongitude = Double.parseDouble(tcsbShopEntity.getLongitude());
        //获取店铺的配送范围
       
        TcsbDistributionScopeEntity tcsbDistributionScopeEntity = tcsbDistributionScopeService.findUniqueByProperty(TcsbDistributionScopeEntity.class, "shopId", shopId);
        if (StringUtil.isEmpty(tcsbDistributionScopeEntity)) {
        	  ajaxJson.setMsg("商家未设置配送范围");
              ajaxJson.setSuccess(true);
		}else {
			//获取配送距离公里数
			mileage = BigDecimalUtil.mul(1000, tcsbDistributionScopeEntity.getMileage());
		}
        //查询该用户所有的配送地址
        List<TcsbDistributionAddressReaEntity> tcsbDistributionAddressReaEntities = tcsbDistributionScopeAttrService.findHql("from TcsbDistributionAddressReaEntity where openId=?", userId);
        List<TcsbDistributionAddressEntity> tcsbDistributionAddressEntities = new ArrayList<>();
        Set set = new HashSet<>();
        if (!tcsbDistributionAddressReaEntities.isEmpty()) {
			for (TcsbDistributionAddressReaEntity tcsbDistributionAddressReaEntitie : tcsbDistributionAddressReaEntities) {
				set.add(tcsbDistributionAddressReaEntitie.getDistributionAddressId());
			}
			CriteriaQuery cq = new CriteriaQuery(TcsbDistributionAddressEntity.class);
			cq.add(Restrictions.eq("shopId", shopId));
			cq.add(Restrictions.in("id", set));
			tcsbDistributionAddressEntities = tcsbDistributionAddressService.getListByCriteriaQuery(cq, false);
		}
        //更新配送地址的性别为中文
        for (TcsbDistributionAddressEntity tcsbDistributionAddressEntity : tcsbDistributionAddressEntities) {
			if ("0".equals(tcsbDistributionAddressEntity.getSex())) {
				tcsbDistributionAddressEntity.setSexCh("女士");
			}else {
				tcsbDistributionAddressEntity.setSexCh("先生");
			}
		}
        //配送地址分配配送范围
        TcsbDistributionAddressListItemVO tcsbDistributionAddressListItemVO = new TcsbDistributionAddressListItemVO();
        List<TcsbDistributionAddressEntity> yDistributionAddressList = new ArrayList<>();
        List<TcsbDistributionAddressEntity> nDistributionAddressList = new ArrayList<>();
        if (!tcsbDistributionAddressEntities.isEmpty()) {
        	for (TcsbDistributionAddressEntity tcsbDistributionAddressEntity : tcsbDistributionAddressEntities) {
        		//开始配送地址
        		Double distance = DistanceUtil.getDistance(sLongitude, sLatitude, tcsbDistributionAddressEntity.getLongitude(), tcsbDistributionAddressEntity.getLatitude());
        		//超过配送范围
        		if (distance>=mileage) {
        			nDistributionAddressList.add(tcsbDistributionAddressEntity);
				}else {
					yDistributionAddressList.add(tcsbDistributionAddressEntity);
				}
        	}
		}
        if (!nDistributionAddressList.isEmpty()) {
        	tcsbDistributionAddressListItemVO.setnDistributionList(nDistributionAddressList);
		}
        if (!yDistributionAddressList.isEmpty()) {
        	tcsbDistributionAddressListItemVO.setyDistributionList(yDistributionAddressList);
		}
        ajaxJson.setMsg("获取用户配送地址成功");
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(tcsbDistributionAddressListItemVO);
        return ajaxJson;
    }
    /**
     * 更新用户配送地址
     * @param tcsbDistributionAddressVO
     * @return
     */
    @RequestMapping("/updateDistributionaddress")
    @ResponseBody
    public AjaxJsonApi updateDistributionaddress(TcsbDistributionAddressVO tcsbDistributionAddressVO) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Boolean bo = tcsbDistributionAddressService.updateDistributionaddress(tcsbDistributionAddressVO);
        if (bo) {
        	ajaxJson.setMsg("更新用户配送地址成功");
        	ajaxJson.setSuccess(true);
		}else {
			ajaxJson.setMsg("更新用户配送地址失败");
        	ajaxJson.setSuccess(false);
		}
        return ajaxJson;
    }
	
    /**
     * 更新用户配送地址
     * @param userId
     * @param shopId
     * @return
     */
    @RequestMapping("/delDistributionaddress")
    @ResponseBody
    public AjaxJsonApi delDistributionaddress(@RequestParam String distributionAddressId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        tcsbDistributionAddressService.deleteEntityById(TcsbDistributionAddressEntity.class, distributionAddressId);
        ajaxJson.setMsg("删除用户配送地址成功");
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }
	
    
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbDistributionAddressController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbDistributionAddressEntity tcsbDistributionAddress,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDistributionAddressEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDistributionAddress, request.getParameterMap());
		List<TcsbDistributionAddressEntity> tcsbDistributionAddresss = this.tcsbDistributionAddressService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户配送地址");
		modelMap.put(NormalExcelConstants.CLASS,TcsbDistributionAddressEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户配送地址列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbDistributionAddresss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbDistributionAddressEntity tcsbDistributionAddress,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"用户配送地址");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbDistributionAddressEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户配送地址列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbDistributionAddressEntity> listTcsbDistributionAddressEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbDistributionAddressEntity.class,params);
				for (TcsbDistributionAddressEntity tcsbDistributionAddress : listTcsbDistributionAddressEntitys) {
					tcsbDistributionAddressService.save(tcsbDistributionAddress);
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
	public List<TcsbDistributionAddressEntity> list() {
		List<TcsbDistributionAddressEntity> listTcsbDistributionAddresss=tcsbDistributionAddressService.getList(TcsbDistributionAddressEntity.class);
		return listTcsbDistributionAddresss;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbDistributionAddressEntity task = tcsbDistributionAddressService.get(TcsbDistributionAddressEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbDistributionAddressEntity tcsbDistributionAddress, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDistributionAddressEntity>> failures = validator.validate(tcsbDistributionAddress);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDistributionAddressService.save(tcsbDistributionAddress);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbDistributionAddress.getId();
		URI uri = uriBuilder.path("/rest/tcsbDistributionAddressController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbDistributionAddressEntity tcsbDistributionAddress) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDistributionAddressEntity>> failures = validator.validate(tcsbDistributionAddress);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDistributionAddressService.saveOrUpdate(tcsbDistributionAddress);
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
		tcsbDistributionAddressService.deleteEntityById(TcsbDistributionAddressEntity.class, id);
	}
}
