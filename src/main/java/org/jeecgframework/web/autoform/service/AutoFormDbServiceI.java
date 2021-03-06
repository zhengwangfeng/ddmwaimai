package org.jeecgframework.web.autoform.service;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.autoform.entity.AutoFormDbEntity;
import org.jeecgframework.web.autoform.entity.AutoFormDbFieldEntity;
import org.jeecgframework.web.autoform.entity.AutoFormParamEntity;

public interface AutoFormDbServiceI extends CommonService{
	
 	@Override
	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(AutoFormDbEntity autoFormDb,
	        List<AutoFormDbFieldEntity> autoFormDbFieldList,List<AutoFormParamEntity> autoFormParamList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(AutoFormDbEntity autoFormDb,
	        List<AutoFormDbFieldEntity> autoFormDbFieldList,List<AutoFormParamEntity> autoFormParamList);
	public void delMain (AutoFormDbEntity autoFormDb);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(AutoFormDbEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(AutoFormDbEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(AutoFormDbEntity t);
 	
 	/**
 	 * 获取动态sql属性部分
 	 * @param sql
 	 * @return
 	 */
 	public List<String> getSqlFields(String sql);
 	
 	/**
 	 * 获取动态sql参数部分
 	 * @param sql
 	 * @return
 	 */
 	public List<String> getSqlParams(String sql);
 	
 	/**
 	 * 支持动态数据源查询
 	 */
 	public List<String> getField(String sql,String dbKey);
}
