package org.jeecgframework.web.demo.service.impl.test;

import java.util.List;
import org.jeecgframework.web.demo.dao.test.JeecgProcedureDao;
import org.jeecgframework.web.demo.service.test.JeecgProcedureServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jeecgProcedureServiceImpl")
@Transactional
public class JeecgProcedureServiceImpl extends JdbcDaoSupport implements JeecgProcedureServiceI{
	
	@Autowired
	private JeecgProcedureDao jeecgProcedureDao;
	@Override
	public List queryDataByProcedure(String tableName, String fields, String whereSql) {
		//return jeecgProcedureDao.queryDataByProcedure(tableName, fields, whereSql);
		String sql = "call formDataList('"+tableName+"','"+fields+"','"+whereSql+"')";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	@Override
	public List executeDataByProcedure(String sql) {
		//return jeecgProcedureDao.queryDataByProcedure(tableName, fields, whereSql);
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	@Override
	public void executeByProcedure(String sql,Object... params) {
		System.out.println(params.length);
		for(int i=0;i<params.length;i++){
			sql = sql.replace("?"+i, params[i].toString());
		}
		System.out.println(sql);
		this.getJdbcTemplate().execute(sql);
	}
	
}