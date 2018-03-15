package org.jeecgframework.web.autoform.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.autoform.entity.AutoFormStyleEntity;

import java.io.Serializable;

public interface AutoFormStyleServiceI extends CommonService{
	
 	@Override
	public <T> void delete(T entity);
 	
 	@Override
	public <T> Serializable save(T entity);
 	
 	@Override
	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(AutoFormStyleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(AutoFormStyleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(AutoFormStyleEntity t);
}