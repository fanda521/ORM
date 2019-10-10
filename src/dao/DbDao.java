package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import orm.MyException;

/**
 * 数据库访问接口
 * 完成数据库的查询，数据的添加，数据的删除，数据修改
 * 数据的查询可以返回map集合,确定对象的集合，
 * 数据的添加如果是主键自动的增长可以返回主键的值
 * 如果执行多条sql语句需要保证事务的一致性
 * @author Administrator
 * 
 *
 */
public interface DbDao {

	/**
	 * 允许传递的是 insert  update  delete操作
	 * @param sql 执行的sql语句  sql语句如果有参数永？占位符代替
	 * @param params 与？占位符对应的参数的值
	 * @param generateKey 是否需要返回主键的值 true 表示需要返回主键
	 * @return -1  表示执行出错了
	 * sql = update tableName set colName1 = ?,colname2 = ?
	 * params = new Object[]{colName1的值,colname2的值} 
	 */
	public Long executeSql(String sql,Object [] params,boolean  generateKey)  throws Exception;
	public Long executeSql(String sql,Object [] params)  throws Exception;
	public Long exeSql(String sql,Object ... params)  throws Exception;
	
	/**
	 * 
	 * map 的key 如果存放的是sql 那么 key只能是"sql"   如果存放的是参数  map的可以只能是“params”
	 * @param sqls  需要执行的sql语句 已经 参数   
	 * map.put("sql","update   ? ?   ")
	 * map.put("params",new Object[]{?对相应的参数})
	 * sqls.add(map)
	 * @return
	 */
	public Long executeSql(List<Map<String, Object>> sqls);
	
	
	public void save(Object obj) throws MyException, SQLException, Exception;
	
	
	
	//作业
	public void update(Object obj) throws SQLException, Exception;//对象的id
	
	public void delete (Object obj) throws SQLException, Exception;//delete from  表  where  
	
	public Object getObjectById(Object obj) throws SQLException, InstantiationException, IllegalAccessException;//通过主键获取一个对象
	
	//接口定义，查询
	/**
	 * 
	 * @param sql 执行的sql语句
	 * @param params sql中的参数
	 * @return
	 */
	public List<Map<String, Object>> getDatas(String sql,Object ... params) throws SQLException ;
	
	/*
	 * 返回的是json对象字符串
	 */
	public String getJsonDatas(String sql,Object ... params) throws SQLException ;
	
	
	//对方的调用者不会写sql  查询的表名称   查询的列  where  map  id=1  name =2 
	//传递sql的的参数
	//public List<Map<String, Object>> getDatas(Class clazz) throws SQLException ;
	//对象中的属性会赋值  根据对象的属性做精确的查询（部分）,知道到底是2个属性之间是是做and还是or查询
	//public List<Map<String, Object>> getDatas(Object obj) throws SQLException ;
	
	/**
	 * 
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return 对象的集合
	 * @throws SQLException
	 */
	public <T>  List<T> getDatas(Class<T> clazz,String sql,Object ... params) throws SQLException ;
	public ResultSet select(String sql,Object [] params) throws SQLException;
	
}
