package com.tcsb.distributionaddressrea.service;
import com.tcsb.distributionaddressrea.entity.TcsbDistributionAddressReaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDistributionAddressReaServiceI extends CommonService{
	
 	public void delete(TcsbDistributionAddressReaEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDistributionAddressReaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDistributionAddressReaEntity entity) throws Exception;
 	
}
