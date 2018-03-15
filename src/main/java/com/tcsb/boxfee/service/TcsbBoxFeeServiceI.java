package com.tcsb.boxfee.service;
import com.tcsb.boxfee.entity.TcsbBoxFeeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbBoxFeeServiceI extends CommonService{
	
 	public void delete(TcsbBoxFeeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbBoxFeeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbBoxFeeEntity entity) throws Exception;
 	
}
