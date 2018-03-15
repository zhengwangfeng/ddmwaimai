package com.weixin.weixinnewsitem.service;
import com.weixin.weixinnewsitem.entity.WeixinNewsitemEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinNewsitemServiceI extends CommonService{
	
 	public void delete(WeixinNewsitemEntity entity) throws Exception;
 	
 	public Serializable save(WeixinNewsitemEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinNewsitemEntity entity) throws Exception;
 	
}
