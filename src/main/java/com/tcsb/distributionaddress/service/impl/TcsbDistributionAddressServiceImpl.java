package com.tcsb.distributionaddress.service.impl;
import com.tcsb.distributionaddress.service.TcsbDistributionAddressServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.tcsb.distributionaddress.VO.TcsbDistributionAddressVO;
import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;
import com.tcsb.distributionaddressrea.entity.TcsbDistributionAddressReaEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tcsbDistributionAddressService")
@Transactional
public class TcsbDistributionAddressServiceImpl extends CommonServiceImpl implements TcsbDistributionAddressServiceI {

	
 	public void delete(TcsbDistributionAddressEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TcsbDistributionAddressEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TcsbDistributionAddressEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbDistributionAddressEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbDistributionAddressEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TcsbDistributionAddressEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(TcsbDistributionAddressEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("address", t.getAddress());
		map.put("longitude", t.getLongitude());
		map.put("latitude", t.getLatitude());
		map.put("shop_id", t.getShopId());
		map.put("create_time", t.getCreateTime());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TcsbDistributionAddressEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{longitude}",String.valueOf(t.getLongitude()));
 		sql  = sql.replace("#{latitude}",String.valueOf(t.getLatitude()));
 		sql  = sql.replace("#{shop_id}",String.valueOf(t.getShopId()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
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
	public void addDistributionaddress(TcsbDistributionAddressVO tcsbDistributionAddressVO) throws Exception {
		//添加用户配送地址
		TcsbDistributionAddressEntity tcsbDistributionAddressEntity = new TcsbDistributionAddressEntity();
		tcsbDistributionAddressEntity.setAddress(tcsbDistributionAddressVO.getAddress());
		tcsbDistributionAddressEntity.setCreateTime(new Date());
		tcsbDistributionAddressEntity.setLatitude(Double.parseDouble(tcsbDistributionAddressVO.getLatitude()));
		tcsbDistributionAddressEntity.setLongitude(Double.parseDouble(tcsbDistributionAddressVO.getLongitude()));
		tcsbDistributionAddressEntity.setShopId(tcsbDistributionAddressVO.getShopId());
		tcsbDistributionAddressEntity.setDetailAddress(tcsbDistributionAddressVO.getDetailAddress());
		tcsbDistributionAddressEntity.setMobile(tcsbDistributionAddressVO.getMobile());
		tcsbDistributionAddressEntity.setNickName(tcsbDistributionAddressVO.getNickName());
		tcsbDistributionAddressEntity.setSex(tcsbDistributionAddressVO.getSex());
		this.save(tcsbDistributionAddressEntity);
		//添加用户配送地址关联
		TcsbDistributionAddressReaEntity  tcsbDistributionAddressReaEntity = new TcsbDistributionAddressReaEntity();
		tcsbDistributionAddressReaEntity.setCreateTime(new Date());
		tcsbDistributionAddressReaEntity.setDistributionAddressId(tcsbDistributionAddressEntity.getId());
		tcsbDistributionAddressReaEntity.setOpenId(tcsbDistributionAddressVO.getUserId());
		this.save(tcsbDistributionAddressReaEntity);
	}

	@Override
	public Boolean updateDistributionaddress(TcsbDistributionAddressVO tcsbDistributionAddressVO) {
		TcsbDistributionAddressEntity tcsbDistributionAddressEntity = this.get(TcsbDistributionAddressEntity.class, tcsbDistributionAddressVO.getDistributionAddressId());
		tcsbDistributionAddressEntity.setAddress(tcsbDistributionAddressVO.getAddress());
		tcsbDistributionAddressEntity.setLatitude(Double.parseDouble(tcsbDistributionAddressVO.getLatitude()));
		tcsbDistributionAddressEntity.setLongitude(Double.parseDouble(tcsbDistributionAddressVO.getLongitude()));
		tcsbDistributionAddressEntity.setDetailAddress(tcsbDistributionAddressVO.getDetailAddress());
		tcsbDistributionAddressEntity.setMobile(tcsbDistributionAddressVO.getMobile());
		tcsbDistributionAddressEntity.setNickName(tcsbDistributionAddressVO.getNickName());
		tcsbDistributionAddressEntity.setSex(tcsbDistributionAddressVO.getSex());
		tcsbDistributionAddressEntity.setUpdateTime(new Date());
		try {
			this.saveOrUpdate(tcsbDistributionAddressEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}