package com.tcsb.distributionscopeattr.service;
import com.tcsb.distributionscopeattr.entity.TcsbDistributionScopeAttrEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDistributionScopeAttrServiceI extends CommonService{
	
 	public void delete(TcsbDistributionScopeAttrEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDistributionScopeAttrEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDistributionScopeAttrEntity entity) throws Exception;
 	
}
