package com.weixin.weixinnewsitem.controller;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinnewsitem.entity.WeixinNewsitemEntity;
import com.weixin.weixinnewsitem.service.WeixinNewsitemServiceI;
import com.weixin.weixinnewstemplate.entity.WeixinNewstemplateEntity;

/**   
 * @Title: Controller  
 * @Description: 微信图文项
 * @author onlineGenerator
 * @date 2017-08-01 17:33:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/weixinNewsitemController")
public class WeixinNewsitemController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeixinNewsitemController.class);

	@Autowired
	private WeixinNewsitemServiceI weixinNewsitemService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 微信图文项列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weixin/weixinnewsitem/weixinNewsitemList");
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
	public void datagrid(WeixinNewsitemEntity weixinNewsitem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinNewsitemEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinNewsitem, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.weixinNewsitemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 微信单图消息列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goMessage")
	public ModelAndView goMessage(HttpServletRequest request) {
		String templateId = request.getParameter("templateId");
		//request.setAttribute("templateId", templateId);
		if(StringUtil.isNotEmpty(templateId)){
			String hql = "from WeixinNewsitemEntity where weixinNewstemplateEntity.id='"+templateId+"' order by orders asc";
			org.jeecgframework.core.util.LogUtil.info("...hql..."+hql);
			List<WeixinNewsitemEntity> headerList = this.systemService.findByQueryString(hql);
			if(headerList.size()>0){
				request.setAttribute("headerNews", headerList.get(0));
				if(headerList.size()>1){
					ArrayList list = new ArrayList(headerList);
					list.remove(0);
					request.setAttribute("newsList", list);
				}
			} 
			WeixinNewstemplateEntity newsTemplate = this.systemService.getEntity(WeixinNewstemplateEntity.class, templateId);
			/*String temp = newsTemplate.getCreateDate().replace("-", "/");
			Date addTime = new Date(temp);*/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			request.setAttribute("addtime", sdf.format(newsTemplate.getCreateDate()));
		}
		return new ModelAndView("com/weixin/weixinnewstemplate/showmessage");
	}

	/**
	 * 转向信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="goContent")
	public ModelAndView goContent(HttpServletRequest request){
		
		String id = request.getParameter("id");
		WeixinNewsitemEntity newsItem = this.systemService.getEntity(WeixinNewsitemEntity.class, id);
		request.setAttribute("newsItem", newsItem);
		return new ModelAndView("com/weixin/weixinnewsitem/newsContent");
		
	}
	/**
	 * 删除微信图文项
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WeixinNewsitemEntity weixinNewsitem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		weixinNewsitem = systemService.getEntity(WeixinNewsitemEntity.class, weixinNewsitem.getId());
		message = "微信图文项删除成功";
		try{
			weixinNewsitemService.delete(weixinNewsitem);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信图文项删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信图文项
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信图文项删除成功";
		try{
			for(String id:ids.split(",")){
				WeixinNewsitemEntity weixinNewsitem = systemService.getEntity(WeixinNewsitemEntity.class, 
				id
				);
				weixinNewsitemService.delete(weixinNewsitem);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信图文项删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信图文项
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WeixinNewsitemEntity weixinNewsitem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信图文项添加成功";
		try{
			String templateId = request.getParameter("templateId");
			WeixinNewstemplateEntity temp1 = this.systemService.getEntity(WeixinNewstemplateEntity.class, templateId);
			weixinNewsitem.setWeixinNewstemplateEntity(temp1);
			List<WeixinAccountEntity> weixinAccountEntities = systemService.findHql("from WeixinAccountEntity");
			if (!weixinAccountEntities.isEmpty()) {
				weixinNewsitem.setAccountId(weixinAccountEntities.get(0).getId());
				weixinNewsitemService.save(weixinNewsitem);
				}else{
					j.setSuccess(false);
					j.setMsg("请添加一个公众帐号。");
			}
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信图文项添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信图文项
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WeixinNewsitemEntity weixinNewsitem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信图文项更新成功";
		WeixinNewsitemEntity t = weixinNewsitemService.get(WeixinNewsitemEntity.class, weixinNewsitem.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(weixinNewsitem, t);
			weixinNewsitemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信图文项更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信图文项新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WeixinNewsitemEntity weixinNewsitem, HttpServletRequest req) {
		String templateId = req.getParameter("templateId");
		req.setAttribute("templateId", templateId);
		if (StringUtil.isNotEmpty(weixinNewsitem.getId())) {
			weixinNewsitem = weixinNewsitemService.getEntity(WeixinNewsitemEntity.class, weixinNewsitem.getId());
			req.setAttribute("weixinNewsitemPage", weixinNewsitem);
		}
		return new ModelAndView("com/weixin/weixinnewsitem/weixinNewsitem-add");
	}
	/**
	 * 微信图文项编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WeixinNewsitemEntity weixinNewsitem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinNewsitem.getId())) {
			weixinNewsitem = weixinNewsitemService.getEntity(WeixinNewsitemEntity.class, weixinNewsitem.getId());
			req.setAttribute("weixinNewsitemPage", weixinNewsitem);
		}
		return new ModelAndView("com/weixin/weixinnewsitem/weixinNewsitem-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","weixinNewsitemController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	 /**
     * 保存文件信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public  AjaxJson uploadImg(MultipartHttpServletRequest request, HttpServletResponse response) {
    	AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		TSTypegroup tsTypegroup=systemService.getTypeGroup("fieltype","文档分类");
		TSType tsType = systemService.getType("files","附件", tsTypegroup);
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));// 文件标题
		TSDocument document = new TSDocument();
		if (StringUtil.isNotEmpty(fileKey)) {
			document.setId(fileKey);
			document = systemService.getEntity(TSDocument.class, fileKey);
			document.setDocumentTitle(documentTitle);

		}
		document.setSubclassname(MyClassLoader.getPackPath(document));
		document.setCreatedate(DateUtils.gettimestamp());
		document.setTSType(tsType);
		UploadFile uploadFile = new UploadFile(request, document);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		document = systemService.uploadFile(uploadFile);
		attributes.put("url", document.getRealpath());
		attributes.put("fileKey", document.getId());
		attributes.put("name", document.getAttachmenttitle());
		attributes.put("viewhref", "commonController.do?openViewFile&fileid=" + document.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + document.getId());
		j.setMsg("文件添加成功");
		j.setAttributes(attributes);

		return j;
    }
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WeixinNewsitemEntity weixinNewsitem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WeixinNewsitemEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinNewsitem, request.getParameterMap());
		List<WeixinNewsitemEntity> weixinNewsitems = this.weixinNewsitemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信图文项");
		modelMap.put(NormalExcelConstants.CLASS,WeixinNewsitemEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信图文项列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,weixinNewsitems);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WeixinNewsitemEntity weixinNewsitem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信图文项");
    	modelMap.put(NormalExcelConstants.CLASS,WeixinNewsitemEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信图文项列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WeixinNewsitemEntity> listWeixinNewsitemEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WeixinNewsitemEntity.class,params);
				for (WeixinNewsitemEntity weixinNewsitem : listWeixinNewsitemEntitys) {
					weixinNewsitemService.save(weixinNewsitem);
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
	public List<WeixinNewsitemEntity> list() {
		List<WeixinNewsitemEntity> listWeixinNewsitems=weixinNewsitemService.getList(WeixinNewsitemEntity.class);
		return listWeixinNewsitems;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WeixinNewsitemEntity task = weixinNewsitemService.get(WeixinNewsitemEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WeixinNewsitemEntity weixinNewsitem, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinNewsitemEntity>> failures = validator.validate(weixinNewsitem);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinNewsitemService.save(weixinNewsitem);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = weixinNewsitem.getId();
		URI uri = uriBuilder.path("/rest/weixinNewsitemController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WeixinNewsitemEntity weixinNewsitem) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinNewsitemEntity>> failures = validator.validate(weixinNewsitem);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinNewsitemService.saveOrUpdate(weixinNewsitem);
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
		weixinNewsitemService.deleteEntityById(WeixinNewsitemEntity.class, id);
	}
}
