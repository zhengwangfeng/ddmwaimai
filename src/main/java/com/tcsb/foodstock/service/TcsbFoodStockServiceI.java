package com.tcsb.foodstock.service;
import com.tcsb.foodstock.entity.TcsbFoodStockEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodStockServiceI extends CommonService{
	
 	public void delete(TcsbFoodStockEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodStockEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodStockEntity entity) throws Exception;

 	
}
