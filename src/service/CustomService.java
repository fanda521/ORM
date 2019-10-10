package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import orm.MyException;

public interface CustomService {

	public boolean isLogin(String name,String pwd) throws SQLException;
	
	
	public List<Map<String, Object>> getCustoms() throws SQLException ;
	
	
	public Map<Map<String, Object>, List<Map<String, Object>>> getCustomInfo(String id,String name) throws SQLException;
	
	public void add(Object obj) throws MyException, SQLException, Exception;
	
	public Object selectOne(Object obj) throws SQLException, InstantiationException, IllegalAccessException;
	
	public void update(Object obj) throws SQLException, Exception; 
	
	public long getTotals(String sql) throws Exception;
	
	public <T> List<T> getDatasLimt(Class<T> clazz,String sql ,Object ... params) throws SQLException;
	
}
