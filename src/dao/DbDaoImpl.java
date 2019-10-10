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
	 * 初始化conn，statement
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
	 * 初始化conn，PreparedStatement
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
				//需要返回的是主键的值
				stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(sql);
			}
			//给sql占位符赋值
			for(int i=0; i < params.length; i ++) {
				stmt.setObject(i+1, params[i]);
			}
			result = stmt.executeUpdate();
			
			if(generateKey) {
				//获取主键的值
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
		 * 定义变量1.拼接sql语句2.获取指定sql语句的参数params值
		 */
		//sql的前面部分
		StringBuffer sql=new StringBuffer("  insert into   ");
		//sql的中间部分
		StringBuffer sqlColumn=new StringBuffer();
		//sql的values部分
		StringBuffer sqlValue=new StringBuffer("  values( ");
		//sql的参数params部分
		ArrayList<Object> params=new ArrayList<Object>();
		//保存主键的属性
				Field fieldKey = null;
		
		/**
		 * 进性类的解析，注解的提取，sql语句的拼接，参数的获取，sql语句的执行
		 */
		
		//获得对象的字节码对象
		Class clazz=obj.getClass();
		//获取表名
		MyClass mytable=(MyClass) clazz.getAnnotation(MyClass.class);
		//进性判空操作，判断是否有这个表或者说对应的实体对象
		if (mytable!=null) {
			String tableName = mytable.myTable();
			sql.append(tableName).append("(");
			//获取类的属性
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
				//获取每个属性上的所有注解
				Annotation[] annotations = fields[i].getAnnotations();
				for (int j = 0; j < annotations.length; j++) {
					
					//如果是普通属性字段
					if (annotations[j] instanceof MyColumn && value!=null) {
						//取出字段的注解对象
						MyColumn column=(MyColumn) annotations[j];
						//取出对应表的列名
						String columnName = column.columnName();
						//开始拼接sql
						sqlColumn.append(columnName).append(" , ");
						sqlValue.append(" ? ").append(" , ");
						
						params.add(value);
					}
					//如果是主键
					else if(annotations[j] instanceof MyKey)
					{
						fieldKey=fields[i];
						//取出字段的注解对象
						MyKey myKey= (MyKey) annotations[j];
						//取出主键对应的列名
						String columnName = myKey.columnName();
						//取出主键是否是自增长的
						 autoIncrement = myKey.isGenerator();
						//不是自动增长的才需要拼接字段
						if(!autoIncrement&&value!=null)
						{
							sqlColumn.append(columnName).append(" , ");
							sqlValue.append(" ? ").append(" , ");
							params.add(value);
							
						}
					}
					
				}
				//将对应的列明参数值加入params数组之中
				
			}
			sqlColumn.append(")");
			sqlValue.append(")");
			
			//删除最后一个,
			sqlColumn.deleteCharAt(sqlColumn.lastIndexOf(","));
			sqlValue.deleteCharAt(sqlValue.lastIndexOf(","));
			
			sql.append(sqlColumn).append(sqlValue);
			//验证类sql是正确
			System.out.println(sql);
			//验证sql中的参数是否是正确的
			System.out.println(Arrays.toString(params.toArray()));
			
			//根据主键是否自增长执行对应的sql语句
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
			System.out.println("该表不存在！");
		}
		
	}
	@Override
	public void update(Object obj) throws Exception {
		//update t_student set stu_age=22 where stu_id=1005;
		
		//抽象化  update t_student set stu_age=?,stu_name=? where stu_id=? and stu_name=? and;
		
		/**
		 * 分析：
		 * 1.首先拼接sql语句
		 * 2.然后调用方法执行
		 */
		
		
		/**
		 * 1.拼接sql语句
		 *		1.1将语句的拼接分为三部分 
		 *			第一部分：update t_student 
		 *			第二部分：set stu_age=?,stu_name=?
		 *			第三部分： where stu_id=?
		 *		1.2定义相应的变量接收这三部分，同时定义好params的参数部分
		 */
		StringBuffer sql=new StringBuffer(" update ");
		StringBuffer sqlColumn=new StringBuffer(" set ");
		StringBuffer sqlWhere=new StringBuffer(" where ");
		ArrayList<Object> params=new ArrayList<Object>();
		//因为要将column的参数和where的参数分开，所以要另外定一个list，先暂存where的参数值
		//然后再最后加入到params中即可
		ArrayList<Object> WhereValues=new ArrayList<Object>();
		
		Class clazz = obj.getClass();
		//首先得到表明
		MyClass myTable = (MyClass) clazz.getAnnotation(MyClass.class);
		if (myTable!=null) {
			String tableName = myTable.myTable();
			sql.append(tableName);//第一部分拼接完成
			//先获取到所有的属性字段，然后再获取每个字段上的注解
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
				//如果某个属性值为空或者是初始的值，就不进行更新，也就不需要拼接了
				if(value==null||value.equals(0)||value.equals(" "))
				{
					continue;
				}
				//获取属性上的注解
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
						//不需要像insert那样判断自增长，这里是根据记录的主键ID 进行更新的
						String columnName = myKey.columnName();
//						sqlColumn.append(columnName).append("=?").append(" , ");
						sqlWhere.append(columnName).append("=?").append(" and ");
						//params.add(value);
						WhereValues.add(value);
//						System.out.println("是否需要修改主键："+columnName+"的值?  y or n");
//						Scanner read=new Scanner(System.in);
//						String choose = read.next();
//						if (choose.equalsIgnoreCase("Y")) {
//							System.out.println("请输入你要修改后的主键值：");
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
			//删除 sqlColumn多余的“ , ”,删除 sqlWhere多余的  and
			sqlColumn.deleteCharAt(sqlColumn.lastIndexOf(","));
			sqlWhere.delete(sqlWhere.length()-" and ".length(), sqlWhere.length());
			//将whereValues放入到params
			//[1, 王大傻, 3919-08-11, [1]]
			//params.add(WhereValues.toString());
			//上面的直接加入会有[1]出现
			//com.mysql.jdbc.MysqlDataTruncation: Data truncation: 
			//Truncated incorrect DOUBLE value: '[1]'
			for (int i = 0; i < WhereValues.size(); i++) {
				params.add(WhereValues.get(i));
			}
			//将拼好的三部分合并起来
			sql.append(sqlColumn).append(sqlWhere);
			System.out.println(sql);
			System.out.println(params);
			
			
			//执行对应的sql语句
			executeSql(sql.toString(), params.toArray());
		}
		else
		{
			System.out.println("传入对象的表不存在！");
			return;
		}
	}
	@Override
	public void delete(Object obj) throws Exception {
		// delete from t_student where stu_id=1019;
		//delete from t_student where stu_name='mimi2' and stu_age=26;
		
		//抽象化   delete from t_student where stu_name=? and stu_age=?
		/**
		 * 分析：
		 * 1.首先拼接sql语句
		 * 2.然后调用方法执行
		 */
		
		/**
		 * 1.sql语句的拼接
		 * 		1.1将sql语句分为两部分
		 * 			第一部分：delete from t_student 
		 * 			第二部分：where stu_name=? and stu_age=?
		 * 		1.2定义好变量接收这两部分，同时也定义好params的变量 
		 */
		
		StringBuffer sql=new StringBuffer(" delete from ");
		StringBuffer sqlWhere=new StringBuffer(" where ");
		ArrayList<Object> params=new ArrayList<Object>();
		
		Class clazz = obj.getClass();
		/**
		 * 1.拼接sql语句
		 */
		//获取表名
		 MyClass myTable= (MyClass) clazz.getAnnotation(MyClass.class);
		 String tableName = myTable.myTable();
		 if (tableName!=null) {
			sql.append(tableName);//第一部分拼接完毕
			//获取对象的属性字段
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
				//判断属性值是否为空或者初始化的值
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
			
			//去掉 sqlWhere中多余的 and
			sqlWhere.delete(sqlWhere.length()-" and ".length(), sqlWhere.length());
			//拼接好完整的sql语句
			sql.append(sqlWhere);
			System.out.println(sql);
			System.out.println(params);
			//执行相应的sql语句
			executeSql(sql.toString(), params.toArray());
		}
		 else
		 {
			 System.out.println("该表不存在！");
			 return;
		 }
		
		
	}
	@Override
	public Object getObjectById(Object obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		//select * from t_student where stu_id=1001;
		//考虑联合组建的情况，下面加入stu_name 也是联合主键之一
		//抽象化  select * from t_student where stu_id=? and stu_name=?;
		/**
		 * 分析：
		 * 		1.拼接sql语句
		 * 		2.执行相应的sql语句
		 * 		3.通过字节码获得相应的接收记录的实体对象，然后返回获取到的记录
		 */
		
		/**
		 * 1.拼接sql语句
		 * 		1.1将sql语句分为两部分
		 * 			第一部分：select * from t_student
		 * 			第二部分：where stu_id=? and stu_name=?
		 * 		1.2定义好变量接收这两部分，同时定义好参数值params的变量
		 */
		StringBuffer sql=new StringBuffer(" select * from ");
		StringBuffer sqlWhere=new StringBuffer(" where ");
		ArrayList<Object> params=new ArrayList<Object>();
		//最终的返回集合
		ArrayList<Object> results=new ArrayList<Object>();
		
		Class clazz=obj.getClass();
		clazz.newInstance();
		MyClass myTable= (MyClass) clazz.getAnnotation(MyClass.class);
		if (myTable!=null) {
			String tableName = myTable.myTable();
			sql.append(tableName);//拼接完第一部分
			//获取所有的属性字段
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
				//判断属性值是否为空或者初始化的值
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
			
			//删除sqlwhere中多余的 “ and ”
			sqlWhere.delete(sqlWhere.length()-" and ".length(), sqlWhere.length());
			//拼接好完整的sql语句
			sql.append(sqlWhere);
			System.out.println(sql);
			System.out.println(params);
			//执行相应的sql语句
			ResultSet result = select(sql.toString(), params.toArray());
			//进行数据接收，用对应的实体对象封装返回
			//遇到问题，object在运行时虽然知道他的具体实体对象，但是编写时是不知道的，所以在编写时无法
			//知道该用哪种对象来接收，并调用他的相应方法，
			//所以考虑只返回数据，并不能确却的对象接收，可以是string 或者集合
			
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
			System.out.println("对应的表不存在！");
		}
		return results;
	}
	
	
	

	

	@Override
	public List<Map<String, Object>> getDatas(String sql, Object... params) throws SQLException {
		//返回值
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs  = null;
		try {
			conn = DbUtil.getCon();
			stmt = conn.prepareStatement(sql);
			//给sql中的参数赋值
			for(int i = 0 ; i < params.length; i ++) {
				stmt.setObject(i+1, params[i]);
			}
			//执行sql语句
			rs = stmt.executeQuery();
			//读取数据  用查询的列的别名做为key  列对应值作为value
			//获取元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();//总共查询多少列
			while(rs.next()) {
				//while每循环一次，可以获取一行数据  但是一行数据中有很多列 select stu_id id ,stu_name from t_stu;
				//每一行数据封装陈给一个map
				Map<String, Object> data = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i ++) {
					String columnLabel = metaData.getColumnLabel(i);//查询列的别名，如果没有别名，那么和列名是一致
					Object value = rs.getObject(columnLabel);
					data.put(columnLabel, value);
				}
				//将一行数据放入到list中
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
			//给sql中的参数赋值
			for(int i = 0 ; i < params.length; i ++) {
				stmt.setObject(i+1, params[i]);
			}
			//执行sql语句
			rs = stmt.executeQuery();
			//读取数据  用查询的列的别名做为key  列对应值作为value
			//获取元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();//总共查询多少列
			while(rs.next()) {
				//while每循环一次，可以获取一行数据  但是一行数据中有很多列 select stu_id id ,stu_name from t_stu;
				//每一行数据封装一个对象  Class<T> clazz
				T obj = clazz.newInstance();
				//该对象的所有字段
				Field[] objFields = clazz.getDeclaredFields();
				String name=null;
				for(int i = 1; i <= columnCount; i ++) {
					String columnLabel = metaData.getColumnLabel(i);//查询列的别名，如果没有别名，那么和列名是一致
					for (Field field : objFields) {
						//此处可以优化直接按照下标取，不必重新来过，由于时间问题以后再优化
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
					//根据列名查找字节码中是否有该属性，如果有该属性直接给属性赋值
					
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
				//将一行数据放入到list中
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
