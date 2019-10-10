package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DbDao;
import dao.DbDaoImpl;
import orm.MyException;

public class CustomServiceImpl implements CustomService {
	//执行sql语句
	DbDao db = new DbDaoImpl();
	@Override
	public boolean isLogin(String name, String pwd) throws SQLException {
		//去数据库判断该用户名和密码是否存在
		String sql = "select count(*) num from cus_user where user_name = ? and user_pwd=?";
		
		
		List<Map<String, Object>> datas = db.getDatas(sql, new Object[]{name,pwd});
		if(datas.size() == 1 && (long)datas.get(0).get("num") == 1){
			return true;
		}
		return false;
	}
	
	public List<Map<String, Object>> getCustoms() throws SQLException {
		String sql = "select * from cus_info  order by update_time desc"; // 排序之后在查询还是查询完成之后再排序
		List<Map<String, Object>> datas = db.getDatas(sql, new Object[] {});
		return datas;
	}
	
	public Map<Map<String, Object>, List<Map<String, Object>>> getCustomInfo(String id,String name) throws SQLException {
		String sql1 = "select * from cus_info where info_id = ? ";//and info_name = ?
		String sql2 =  "select *from  cus_event where info_id = ?";
		//执行第一条sql语句只有可能返回一条，如果一条记录都没有返回，没有必要执行第二条sql
		List<Map<String, Object>> info = db.getDatas(sql1, new Object[] {id});//,name
		if(info.size() == 1) {
			//执行第二条sql
			List<Map<String, Object>> event = db.getDatas(sql2, new Object[] {id});
			Map<Map<String, Object>, List<Map<String, Object>>> datas = 
					new HashMap<Map<String,Object>, List<Map<String,Object>>>();
			datas.put(info.get(0), event);
			return datas;
		}
		
		return null;
	}

	@Override
	public void add(Object obj) throws MyException, SQLException, Exception {
		// TODO Auto-generated method stub
		db.save(obj);
	}

	@Override
	public Object selectOne(Object obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return db.getObjectById(obj);
	}

	@Override
	public void update(Object obj) throws SQLException, Exception {
		// TODO Auto-generated method stub
		db.update(obj);
		
	}

	@Override
	public long getTotals(String sql) throws Exception {
		// TODO Auto-generated method stub
		 ResultSet select = db.select(sql, new Object[] {});
		 int i=1;
		 long num=0l;
		 while(select.next()) {
			num= (long) select.getObject(i);
			 i++;
		 }
		 return num;
	}


	@Override
	public <T> List<T> getDatasLimt(Class<T> clazz, String sql, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		return db.getDatas(clazz, sql, params);
	}

}
