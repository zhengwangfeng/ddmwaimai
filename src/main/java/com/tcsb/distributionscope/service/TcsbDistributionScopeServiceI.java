package com.tcsb.distributionscope.service;
import com.tcsb.distributionscope.entity.TcsbDistributionScopeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDistributionScopeServiceI extends CommonService{
	
 	public void delete(TcsbDistributionScopeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDistributionScopeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDistributionScopeEntity entity) throws Exception;
 	
}
