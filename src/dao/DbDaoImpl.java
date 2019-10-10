package dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

import orm.MyClass;
import orm.MyColumn;
import orm.MyException;
import orm.MyKey;
import orm.MyTable;

public class DbDaoImpl implements DbDao {

	private Connection conn=null;
	private Statement s=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	private long result=-1;
	

	/**
	 * ��ʼ��conn��statement
	 */
	public void open_s()
	{
	
		conn=DbUtil.getCon();
		try {
			s=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ʼ��conn��PreparedStatement
	 * @throws SQLException 
	 */
	public void open_ps(String sql) throws SQLException
	{
		
		conn=DbUtil.getCon();
		
			ps=conn.prepareStatement(sql);
		
	}
	
	public void open_ps2(String sql) throws SQLException
	{
		
		conn=DbUtil.getCon();
		
			ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		
	}
	
	
	@Override
	public Long executeSql(String sql, Object[] params,boolean  generateKey) throws Exception {
		long result = -1;
		Connection conn = DbUtil.getCon();
		PreparedStatement stmt = null;
		try {
			if(generateKey) {
				//��Ҫ���ص���������ֵ
				stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(sql);
			}
			//��sqlռλ����ֵ
			for(int i=0; i < params.length; i ++) {
				stmt.setObject(i+1, params[i]);
			}
			result = stmt.executeUpdate();
			
			if(generateKey) {
				//��ȡ������ֵ
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				while(generatedKeys.next()) {
					 result = (Long)generatedKeys.getObject(1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				
			}
			throw e;
			
		} finally {
			
			DbUtil.close(null, stmt);
		}
		return result;
	}
	
	public Long executeSql(List<Map<String, Object>> sqls) {
		String sql = "";
		Object[] params = new Object[] {};
		for(Map<String, Object> map : sqls) {
			sql = (String)map.get("sql");
			params = (Object[] )map.get("params");
			try {
				executeSql(sql, params,false );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			DbUtil.getCon().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close2();
		}
		
		return 0l;
	}
	
	
	@Override
	public Long exeSql(String sql, Object... params) throws Exception {
		return executeSql(sql,params);
	}

	@Override
	public Long executeSql(String sql, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return executeSql(sql,params,false);
	}

	

	@Override
	public void save(Object obj) throws Exception {
		// TODO Auto-generated method s
		/**
		 * insert into t_stu( stu_name,stu_no,stu_age,stu_birth) values(?,?,?,?)
		 * �������1.ƴ��sql���2.��ȡָ��sql���Ĳ���paramsֵ
		 */
		//sql��ǰ�沿��
		StringBuffer sql=new StringBuffer("  insert into   ");
		//sql���м䲿��
		StringBuffer sqlColumn=new StringBuffer();
		//sql��values����
		StringBuffer sqlValue=new StringBuffer("  values( ");
		//sql�Ĳ���params����
		ArrayList<Object> params=new ArrayList<Object>();
		//��������������
				Field fieldKey = null;
		
		/**
		 * ������Ľ�����ע�����ȡ��sql����ƴ�ӣ������Ļ�ȡ��sql����ִ��
		 */
		
		//��ö�����ֽ������
		Class clazz=obj.getClass();
		//��ȡ����
		MyClass mytable=(MyClass) clazz.getAnnotation(MyClass.class);
		//�����пղ������ж��Ƿ�����������˵��Ӧ��ʵ�����
		if (mytable!=null) {
			String tableName = mytable.myTable();
			sql.append(tableName).append("(");
			//��ȡ�������
			Field[] fields = clazz.getDeclaredFields();
			boolean autoIncrement=false;
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				Object value=null;
				try {
					 value= fields[i].get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//��ȡÿ�������ϵ�����ע��
				Annotation[] annotations = fields[i].getAnnotations();
				for (int j = 0; j < annotations.length; j++) {
					
					//�������ͨ�����ֶ�
					if (annotations[j] instanceof MyColumn && value!=null) {
						//ȡ���ֶε�ע�����
						MyColumn column=(MyColumn) annotations[j];
						//ȡ����Ӧ�������
						String columnName = column.columnName();
						//��ʼƴ��sql
						sqlColumn.append(columnName).append(" , ");
						sqlValue.append(" ? ").append(" , ");
						
						params.add(value);
					}
					//���������
					else if(annotations[j] instanceof MyKey)
					{
						fieldKey=fields[i];
						//ȡ���ֶε�ע�����
						MyKey myKey= (MyKey) annotations[j];
						//ȡ��������Ӧ������
						String columnName = myKey.columnName();
						//ȡ�������Ƿ�����������
						 autoIncrement = myKey.isGenerator();
						//�����Զ������Ĳ���Ҫƴ���ֶ�
						if(!autoIncrement&&value!=null)
						{
							sqlColumn.append(columnName).append(" , ");
							sqlValue.append(" ? ").append(" , ");
							params.add(value);
							
						}
					}
					
				}
				//����Ӧ����������ֵ����params����֮��
				
			}
			sqlColumn.append(")");
			sqlValue.append(")");
			
			//ɾ�����һ��,
			sqlColumn.deleteCharAt(sqlColumn.lastIndexOf(","));
			sqlValue.deleteCharAt(sqlValue.lastIndexOf(","));
			
			sql.append(sqlColumn).append(sqlValue);
			//��֤��sql����ȷ
			System.out.println(sql);
			//��֤sql�еĲ����Ƿ�����ȷ��
			System.out.println(Arrays.toString(params.toArray()));
			
			//���������Ƿ�������ִ�ж�Ӧ��sql���
			if(autoIncrement)
			{
				try {
					long result = executeSql(sql.toString(), params.toArray(), true);
					fieldKey.setAccessible(true);
					try {
						if(fieldKey.getType() == int.class || fieldKey.getType() == Integer.class) {
							fieldKey.set(obj, (int)result);
						} else {
							fieldKey.set(obj, result);
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				executeSql(sql.toString(), params.toArray());
			}
			
		}
		else
		{
			System.out.println("�ñ����ڣ�");
		}
		
	}
	@Override
	public void update(Object obj) throws Exception {
		//update t_student set stu_age=22 where stu_id=1005;
		
		//����  update t_student set stu_age=?,stu_name=? where stu_id=? and stu_name=? and;
		
		/**
		 * ������
		 * 1.����ƴ��sql���
		 * 2.Ȼ����÷���ִ��
		 */
		
		
		/**
		 * 1.ƴ��sql���
		 *		1.1������ƴ�ӷ�Ϊ������ 
		 *			��һ���֣�update t_student 
		 *			�ڶ����֣�set stu_age=?,stu_name=?
		 *			�������֣� where stu_id=?
		 *		1.2������Ӧ�ı��������������֣�ͬʱ�����params�Ĳ�������
		 */
		StringBuffer sql=new StringBuffer(" update ");
		StringBuffer sqlColumn=new StringBuffer(" set ");
		StringBuffer sqlWhere=new StringBuffer(" where ");
		ArrayList<Object> params=new ArrayList<Object>();
		//��ΪҪ��column�Ĳ�����where�Ĳ����ֿ�������Ҫ���ⶨһ��list�����ݴ�where�Ĳ���ֵ
		//Ȼ���������뵽params�м���
		ArrayList<Object> WhereValues=new ArrayList<Object>();
		
		Class clazz = obj.getClass();
		//���ȵõ�����
		MyClass myTable = (MyClass) clazz.getAnnotation(MyClass.class);
		if (myTable!=null) {
			String tableName = myTable.myTable();
			sql.append(tableName);//��һ����ƴ�����
			//�Ȼ�ȡ�����е������ֶΣ�Ȼ���ٻ�ȡÿ���ֶ��ϵ�ע��
			Field[] fields = clazz.getDeclaredFields();
			Object value=null;
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					value = fields[i].get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//���ĳ������ֵΪ�ջ����ǳ�ʼ��ֵ���Ͳ����и��£�Ҳ�Ͳ���Ҫƴ����
				if(value==null||value.equals(0)||value.equals(" "))
				{
					continue;
				}
				//��ȡ�����ϵ�ע��
				Annotation[] annotations = fields[i].getAnnotations();
				for (int j = 0; j < annotations.length; j++) {
					if (annotations[j] instanceof MyColumn) {
						MyColumn myColumn=(MyColumn) annotations[j];
						String columnName = myColumn.columnName();
						sqlColumn.append(columnName).append("=?").append(" , ");
						params.add(value);
					}
					else if(annotations[j] instanceof MyKey){
						MyKey myKey=(MyKey) annotations[j];
						//����Ҫ��insert�����ж��������������Ǹ��ݼ�¼������ID ���и��µ�
						String columnName = myKey.columnName();
//						sqlColumn.append(columnName).append("=?").append(" , ");
						sqlWhere.append(columnName).append("=?").append(" and ");
						//params.add(value);
						WhereValues.add(value);
//						System.out.println("�Ƿ���Ҫ�޸�������"+columnName+"��ֵ?  y or n");
//						Scanner read=new Scanner(System.in);
//						String choose = read.next();
//						if (choose.equalsIgnoreCase("Y")) {
//							System.out.println("��������Ҫ�޸ĺ������ֵ��");
//							String content=read.next();
//							if(fields[i].getType()==int.class||fields[i].getType()==Integer.class)
//							{
//								params.add(Integer.parseInt(content));
//							}
//							else
//							{
//								params.add(content);
//							}
//							
//						}
					}
				}
			}
			//ɾ�� sqlColumn����ġ� , ��,ɾ�� sqlWhere�����  and
			sqlColumn.deleteCharAt(sqlColumn.lastIndexOf(","));
			sqlWhere.delete(sqlWhere.length()-" and ".length(), sqlWhere.length());
			//��whereValues���뵽params
			//[1, ����ɵ, 3919-08-11, [1]]
			//params.add(WhereValues.toString());
			//�����ֱ�Ӽ������[1]����
			//com.mysql.jdbc.MysqlDataTruncation: Data truncation: 
			//Truncated incorrect DOUBLE value: '[1]'
			for (int i = 0; i < WhereValues.size(); i++) {
				params.add(WhereValues.get(i));
			}
			//��ƴ�õ������ֺϲ�����
			sql.append(sqlColumn).append(sqlWhere);
			System.out.println(sql);
			System.out.println(params);
			
			
			//ִ�ж�Ӧ��sql���
			executeSql(sql.toString(), params.toArray());
		}
		else
		{
			System.out.println("�������ı����ڣ�");
			return;
		}
	}
	@Override
	public void delete(Object obj) throws Exception {
		// delete from t_student where stu_id=1019;
		//delete from t_student where stu_name='mimi2' and stu_age=26;
		
		//����   delete from t_student where stu_name=? and stu_age=?
		/**
		 * ������
		 * 1.����ƴ��sql���
		 * 2.Ȼ����÷���ִ��
		 */
		
		/**
		 * 1.sql����ƴ��
		 * 		1.1��sql����Ϊ������
		 * 			��һ���֣�delete from t_student 
		 * 			�ڶ����֣�where stu_name=? and stu_age=?
		 * 		1.2����ñ��������������֣�ͬʱҲ�����params�ı��� 
		 */
		
		StringBuffer sql=new StringBuffer(" delete from ");
		StringBuffer sqlWhere=new StringBuffer(" where ");
		ArrayList<Object> params=new ArrayList<Object>();
		
		Class clazz = obj.getClass();
		/**
		 * 1.ƴ��sql���
		 */
		//��ȡ����
		 MyClass myTable= (MyClass) clazz.getAnnotation(MyClass.class);
		 String tableName = myTable.myTable();
		 if (tableName!=null) {
			sql.append(tableName);//��һ����ƴ�����
			//��ȡ����������ֶ�
			Field[] fields = clazz.getDeclaredFields();
			Object value=null;
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					value=fields[i].get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//�ж�����ֵ�Ƿ�Ϊ�ջ��߳�ʼ����ֵ
				if(value==null||value.equals(0)||value.equals(" "))
				{
					continue;
				}
				Annotation[] annotations = fields[i].getAnnotations();
				for (int j = 0; j < annotations.length; j++) {
					if (annotations[j] instanceof MyColumn) {
						MyColumn myColumn= (MyColumn) annotations[j];
						String columnName = myColumn.columnName();
						sqlWhere.append(columnName).append("=?").append(" and ");
						params.add(value);
					}
					else if(annotations[j] instanceof MyKey)
					{
						MyKey myKey=(MyKey) annotations[j];
						String columnName = myKey.columnName();
						sqlWhere.append(columnName).append("=?").append(" and ");
						params.add(value);
					}
				}
				
			}
			
			//ȥ�� sqlWhere�ж���� and
			sqlWhere.delete(sqlWhere.length()-" and ".length(), sqlWhere.length());
			//ƴ�Ӻ�������sql���
			sql.append(sqlWhere);
			System.out.println(sql);
			System.out.println(params);
			//ִ����Ӧ��sql���
			executeSql(sql.toString(), params.toArray());
		}
		 else
		 {
			 System.out.println("�ñ����ڣ�");
			 return;
		 }
		
		
	}
	@Override
	public Object getObjectById(Object obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		//select * from t_student where stu_id=1001;
		//���������齨��������������stu_name Ҳ����������֮һ
		//����  select * from t_student where stu_id=? and stu_name=?;
		/**
		 * ������
		 * 		1.ƴ��sql���
		 * 		2.ִ����Ӧ��sql���
		 * 		3.ͨ���ֽ�������Ӧ�Ľ��ռ�¼��ʵ�����Ȼ�󷵻ػ�ȡ���ļ�¼
		 */
		
		/**
		 * 1.ƴ��sql���
		 * 		1.1��sql����Ϊ������
		 * 			��һ���֣�select * from t_student
		 * 			�ڶ����֣�where stu_id=? and stu_name=?
		 * 		1.2����ñ��������������֣�ͬʱ����ò���ֵparams�ı���
		 */
		StringBuffer sql=new StringBuffer(" select * from ");
		StringBuffer sqlWhere=new StringBuffer(" where ");
		ArrayList<Object> params=new ArrayList<Object>();
		//���յķ��ؼ���
		ArrayList<Object> results=new ArrayList<Object>();
		
		Class clazz=obj.getClass();
		clazz.newInstance();
		MyClass myTable= (MyClass) clazz.getAnnotation(MyClass.class);
		if (myTable!=null) {
			String tableName = myTable.myTable();
			sql.append(tableName);//ƴ�����һ����
			//��ȡ���е������ֶ�
			Field[] fields = clazz.getDeclaredFields();
			Object value=null;
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					value=fields[i].get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//�ж�����ֵ�Ƿ�Ϊ�ջ��߳�ʼ����ֵ
				if(value==null||value.equals(0)||value.equals(" "))
				{
					continue;
				}
				Annotation[] annotations = fields[i].getAnnotations();
				for (int j = 0; j < annotations.length; j++) {
					if (annotations[j] instanceof MyKey) {
						MyKey key=(MyKey) annotations[j];
						String columnName = key.columnName();
						sqlWhere.append(columnName).append("=?").append(" and ");
						params.add(value);
					}
				}
				
				
			}
			
			//ɾ��sqlwhere�ж���� �� and ��
			sqlWhere.delete(sqlWhere.length()-" and ".length(), sqlWhere.length());
			//ƴ�Ӻ�������sql���
			sql.append(sqlWhere);
			System.out.println(sql);
			System.out.println(params);
			//ִ����Ӧ��sql���
			ResultSet result = select(sql.toString(), params.toArray());
			//�������ݽ��գ��ö�Ӧ��ʵ������װ����
			//�������⣬object������ʱ��Ȼ֪�����ľ���ʵ����󣬵��Ǳ�дʱ�ǲ�֪���ģ������ڱ�дʱ�޷�
			//֪���������ֶ��������գ�������������Ӧ������
			//���Կ���ֻ�������ݣ�������ȷȴ�Ķ�����գ�������string ���߼���
			
			while(result.next())
			{
				Map<Object, Object> map=new HashMap<Object, Object>();
				for (int i = 0; i < fields.length; i++) {
					map.put(i, result.getObject(i+1));
				}
				results.add(map);
			}
			
		}
		else
		{
			System.out.println("��Ӧ�ı����ڣ�");
		}
		return results;
	}
	
	
	

	

	@Override
	public List<Map<String, Object>> getDatas(String sql, Object... params) throws SQLException {
		//����ֵ
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs  = null;
		try {
			conn = DbUtil.getCon();
			stmt = conn.prepareStatement(sql);
			//��sql�еĲ�����ֵ
			for(int i = 0 ; i < params.length; i ++) {
				stmt.setObject(i+1, params[i]);
			}
			//ִ��sql���
			rs = stmt.executeQuery();
			//��ȡ����  �ò�ѯ���еı�����Ϊkey  �ж�Ӧֵ��Ϊvalue
			//��ȡԪ����
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();//�ܹ���ѯ������
			while(rs.next()) {
				//whileÿѭ��һ�Σ����Ի�ȡһ������  ����һ���������кܶ��� select stu_id id ,stu_name from t_stu;
				//ÿһ�����ݷ�װ�¸�һ��map
				Map<String, Object> data = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i ++) {
					String columnLabel = metaData.getColumnLabel(i);//��ѯ�еı��������û�б�������ô��������һ��
					Object value = rs.getObject(columnLabel);
					data.put(columnLabel, value);
				}
				//��һ�����ݷ��뵽list��
				datas.add(data);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close2();
			DbUtil.close(null, stmt, rs);
		}
		
		
		
		return datas;
	}

	@Override
	public String getJsonDatas(String sql, Object... params) throws SQLException {
		return new Gson().toJson(getDatas(sql, params));
	}

	@Override
	public <T> List<T> getDatas(Class<T> clazz, String sql, Object... params) throws SQLException {
		List<T> datas = new ArrayList<T>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs  = null;
		try {
			conn = DbUtil.getCon();
			stmt = conn.prepareStatement(sql);
			//��sql�еĲ�����ֵ
			for(int i = 0 ; i < params.length; i ++) {
				stmt.setObject(i+1, params[i]);
			}
			//ִ��sql���
			rs = stmt.executeQuery();
			//��ȡ����  �ò�ѯ���еı�����Ϊkey  �ж�Ӧֵ��Ϊvalue
			//��ȡԪ����
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();//�ܹ���ѯ������
			while(rs.next()) {
				//whileÿѭ��һ�Σ����Ի�ȡһ������  ����һ���������кܶ��� select stu_id id ,stu_name from t_stu;
				//ÿһ�����ݷ�װһ������  Class<T> clazz
				T obj = clazz.newInstance();
				//�ö���������ֶ�
				Field[] objFields = clazz.getDeclaredFields();
				String name=null;
				for(int i = 1; i <= columnCount; i ++) {
					String columnLabel = metaData.getColumnLabel(i);//��ѯ�еı��������û�б�������ô��������һ��
					for (Field field : objFields) {
						//�˴������Ż�ֱ�Ӱ����±�ȡ��������������������ʱ�������Ժ����Ż�
						 name= field.getName();
//						System.out.println(name);
						Annotation[] anno = field.getDeclaredAnnotations();
						if ( anno[0] instanceof MyKey) {
							MyKey key=(MyKey) anno[0];
							String keyName = key.columnName();
//							System.out.println(keyName);
							if(keyName.equalsIgnoreCase(columnLabel)) {
								break;
							}
						}
						else if ( anno[0] instanceof MyColumn) {
							MyColumn column=(MyColumn) anno[0];
							String columnName = column.columnName();
//							System.out.println(columnName);
							if(columnName.equalsIgnoreCase(columnLabel)) {
								break;
							}
						}
					}
					Object value = rs.getObject(columnLabel);
					//�������������ֽ������Ƿ��и����ԣ�����и�����ֱ�Ӹ����Ը�ֵ
					
					try {
						Field f = clazz.getDeclaredField(name);
						f.setAccessible(true);
						if(f.getType() == Integer.class || f.getType() == int.class) {
							if("java.lang.Long".equals(value.getClass().getTypeName())) {
								Long v = (Long)value;
								f.set(obj, v);
							}
							
						} else {
							f.set(obj, value);
						}
						
					} catch (NoSuchFieldException  | SecurityException e2) {
						e2.printStackTrace();
					}
				}
				//��һ�����ݷ��뵽list��
				datas.add(obj);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close2();
			DbUtil.close(null, stmt, rs);
		}
		
		return datas;
	}

	@Override
	public ResultSet select(String sql, Object[] params) throws SQLException {
		// TODO Auto-generated method stub
				open_ps(sql);
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1,params[i]);
				}
				rs= ps.executeQuery();
				return rs;
	}
	
	

}
