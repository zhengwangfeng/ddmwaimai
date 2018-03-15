package com.tcsb.distributionaddress.service;
import com.tcsb.distributionaddress.VO.TcsbDistributionAddressVO;
import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDistributionAddressServiceI extends CommonService{
	
 	public void delete(TcsbDistributionAddressEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDistributionAddressEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDistributionAddressEntity entity) throws Exception;

	public void addDistributionaddress(TcsbDistributionAddressVO tcsbDistributionAddressVO)  throws Exception;

	public Boolean updateDistributionaddress(TcsbDistributionAddressVO tcsbDistributionAddressVO);


 	
}
