package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import orm.MyException;

/**
 * ���ݿ���ʽӿ�
 * ������ݿ�Ĳ�ѯ�����ݵ���ӣ����ݵ�ɾ���������޸�
 * ���ݵĲ�ѯ���Է���map����,ȷ������ļ��ϣ�
 * ���ݵ��������������Զ����������Է���������ֵ
 * ���ִ�ж���sql�����Ҫ��֤�����һ����
 * @author Administrator
 * 
 *
 */
public interface DbDao {

	/**
	 * �����ݵ��� insert  update  delete����
	 * @param sql ִ�е�sql���  sql�������в�������ռλ������
	 * @param params �룿ռλ����Ӧ�Ĳ�����ֵ
	 * @param generateKey �Ƿ���Ҫ����������ֵ true ��ʾ��Ҫ��������
	 * @return -1  ��ʾִ�г�����
	 * sql = update tableName set colName1 = ?,colname2 = ?
	 * params = new Object[]{colName1��ֵ,colname2��ֵ} 
	 */
	public Long executeSql(String sql,Object [] params,boolean  generateKey)  throws Exception;
	public Long executeSql(String sql,Object [] params)  throws Exception;
	public Long exeSql(String sql,Object ... params)  throws Exception;
	
	/**
	 * 
	 * map ��key �����ŵ���sql ��ô keyֻ����"sql"   �����ŵ��ǲ���  map�Ŀ���ֻ���ǡ�params��
	 * @param sqls  ��Ҫִ�е�sql��� �Ѿ� ����   
	 * map.put("sql","update   ? ?   ")
	 * map.put("params",new Object[]{?����Ӧ�Ĳ���})
	 * sqls.add(map)
	 * @return
	 */
	public Long executeSql(List<Map<String, Object>> sqls);
	
	
	public void save(Object obj) throws MyException, SQLException, Exception;
	
	
	
	//��ҵ
	public void update(Object obj) throws SQLException, Exception;//�����id
	
	public void delete (Object obj) throws SQLException, Exception;//delete from  ��  where  
	
	public Object getObjectById(Object obj) throws SQLException, InstantiationException, IllegalAccessException;//ͨ��������ȡһ������
	
	//�ӿڶ��壬��ѯ
	/**
	 * 
	 * @param sql ִ�е�sql���
	 * @param params sql�еĲ���
	 * @return
	 */
	public List<Map<String, Object>> getDatas(String sql,Object ... params) throws SQLException ;
	
	/*
	 * ���ص���json�����ַ���
	 */
	public String getJsonDatas(String sql,Object ... params) throws SQLException ;
	
	
	//�Է��ĵ����߲���дsql  ��ѯ�ı�����   ��ѯ����  where  map  id=1  name =2 
	//����sql�ĵĲ���
	//public List<Map<String, Object>> getDatas(Class clazz) throws SQLException ;
	//�����е����Իḳֵ  ���ݶ������������ȷ�Ĳ�ѯ�����֣�,֪��������2������֮��������and����or��ѯ
	//public List<Map<String, Object>> getDatas(Object obj) throws SQLException ;
	
	/**
	 * 
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return ����ļ���
	 * @throws SQLException
	 */
	public <T>  List<T> getDatas(Class<T> clazz,String sql,Object ... params) throws SQLException ;
	public ResultSet select(String sql,Object [] params) throws SQLException;
	
}
