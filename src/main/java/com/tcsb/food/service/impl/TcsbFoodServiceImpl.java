package com.tcsb.food.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodstock.entity.TcsbFoodStockEntity;

@Service("tcsbFoodService")
@Transactional
public class TcsbFoodServiceImpl extends CommonServiceImpl implements TcsbFoodServiceI {

	
 	@Override
	public void delete(TcsbFoodEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	@Override
	public Serializable save(TcsbFoodEntity entity) throws Exception{
 		Serializable t = super.save(entity);
		/*TcsbFoodSnapshotEntity tcsbFoodSnapshotEntity = new TcsbFoodSnapshotEntity();
		MyBeanUtils.copyBeanNotNull2Bean(entity,tcsbFoodSnapshotEntity);
		tcsbFoodSnapshotEntity.setSnapStatus(SnapshotOptEnum.ADD.getStatus());
		this.save(tcsbFoodSnapshotEntity);*/
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	@Override
	public void saveOrUpdate(TcsbFoodEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
		/*TcsbFoodSnapshotEntity tcsbFoodSnapshotEntity = new TcsbFoodSnapshotEntity();
		MyBeanUtils.copyBeanNotNull2Bean(entity,tcsbFoodSnapshotEntity);
		tcsbFoodSnapshotEntity.setSnapStatus(SnapshotOptEnum.UPDATE.getStatus());
		this.save(tcsbFoodSnapshotEntity);*/
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbFoodEntity t) throws Exception{



 	}

 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbFoodEntity t) throws Exception{

 	}

 	/**
	 * 删除操作增强业务
	 * @return
	 */
	private void doDelBus(TcsbFoodEntity t) throws Exception{
		/*TcsbFoodSnapshotEntity tcsbFoodSnapshotEntity = new TcsbFoodSnapshotEntity();
		MyBeanUtils.copyBeanNotNull2Bean(t, tcsbFoodSnapshotEntity);
		tcsbFoodSnapshotEntity.setSnapStatus(SnapshotOptEnum.DELETE.getStatus());
		this.save(tcsbFoodSnapshotEntity);*/
 	}
 	
 	private Map<String,Object> populationMap(TcsbFoodEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("name", t.getName());
		map.put("price", t.getPrice());
		map.put("food_type_id", t.getFoodTypeId());
		map.put("img", t.getImg());
		map.put("ischara", t.getIschara());
		map.put("status", t.getStatus());
		map.put("update_name", t.getUpdateName());
		map.put("update_date", t.getUpdateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TcsbFoodEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{price}",String.valueOf(t.getPrice()));
 		sql  = sql.replace("#{food_type_id}",String.valueOf(t.getFoodTypeId()));
 		sql  = sql.replace("#{img}",String.valueOf(t.getImg()));
 		sql  = sql.replace("#{ischara}",String.valueOf(t.getIschara()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

	@Override
	public void saveAndInitStock(TcsbFoodEntity tcsbFoodEntity) throws Exception{
		this.save(tcsbFoodEntity);
		//初始化存库信息
		TcsbFoodStockEntity tcsbFoodStockEntity = new TcsbFoodStockEntity();
		tcsbFoodStockEntity.setFoodId(tcsbFoodEntity.getId());
		tcsbFoodStockEntity.setFoodTypeId(tcsbFoodEntity.getFoodTypeId());
		tcsbFoodStockEntity.setShopId(tcsbFoodEntity.getShopId());
		//默认为无限制
		tcsbFoodStockEntity.setStockType("0");
		tcsbFoodStockEntity.setStock(9999);
		this.save(tcsbFoodStockEntity);
	}

	@Override
	public void saveOrUpdateAndInitStock(TcsbFoodEntity tcsbFoodEntity) throws Exception {
		this.saveOrUpdate(tcsbFoodEntity);
		//更新库存表信息
		List<TcsbFoodStockEntity> tcsbFoodStockEntitys = this.findHql("from  TcsbFoodStockEntity where shopId = ? and foodId = ?", tcsbFoodEntity.getShopId(),tcsbFoodEntity.getId());
		TcsbFoodStockEntity tcsbFoodStockEntity = tcsbFoodStockEntitys.get(0);
		tcsbFoodStockEntity.setFoodTypeId(tcsbFoodEntity.getFoodTypeId());
		this.saveOrUpdate(tcsbFoodStockEntity);
	}

}