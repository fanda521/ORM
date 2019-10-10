package test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import dao.DbDao;
import dao.DbDaoImpl;
import entity.CustomerEvent;
import entity.CustomerInfo;
import entity.CustomerUser;
import service.CustomService;
import service.CustomServiceImpl;

public class TestDao {

	public static void main(String[] args) throws Exception {
		
//		DbDao db = new DbDaoImpl();
//		
//		long key = db.executeSql("insert into cus_user(user_name,user_pwd) values(?,?)",
//				new Object[] {"admins","admins"}, true);
//		System.out.println(key);
//		
//		String sql = "select * from cus_user";
//		
//		
//		
//		String json = db.getJsonDatas(sql, new Object[] {});
//		
//		System.out.println(json);

		
//		 testSave();
//		testSave2();
//		testSave3();
//		testGetTotals();
		testGetDatasLimt();
	}
	
	public static void testSave() throws Exception
	{
		//验证类sql是正确
		DbDaoImpl dbti=new DbDaoImpl();
		CustomerUser obj=new CustomerUser();
		obj.setId(1004);
		obj.setName("wangjie");
		obj.setPassword("1234");
		obj.setUpdateTime(new Timestamp(200000));
		System.out.println(obj);
		try {
			dbti.save(obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(obj);
	}
	
	public static void testSave2() throws Exception
	{
		//验证类sql是正确
		DbDaoImpl dbti=new DbDaoImpl();
		CustomerInfo obj=new CustomerInfo();
		obj.setId(3);
		obj.setName("wangjie");
		obj.setDegree("大专");
		obj.setSex("女");
		obj.setCareer("教师");
		obj.setQq("qq1234");
		obj.setWeChat("微信333");
		obj.setPhone("333333333");
		obj.setSource("小红书");
		obj.setCity("南昌");
		obj.setPurpose("兼职");
		obj.setIsCus(1);
		obj.setService(1);
		obj.setDate(new Date(2019, 8, 7));
		obj.setUpdateTime(new Timestamp(200000));
		System.out.println(obj);
		try {
			dbti.save(obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(obj);
	}
	
	public static void testSave3() throws Exception
	{
		//验证类sql是正确
		DbDaoImpl dbti=new DbDaoImpl();
		CustomerEvent obj=new CustomerEvent();
		obj.setId(4);
		obj.setContent("22222");
		obj.setApply("22222");
		obj.setNext("22222");
		obj.setUpdateTime(new Timestamp(40004494));
		obj.setDate(new Date(1000, 8, 7));
		obj.setInfoId(2);
		System.out.println(obj);
		try {
			dbti.save(obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(obj);
	}
	
	public static void testGetTotals() throws Exception
	{
		CustomService cs=new CustomServiceImpl();
		String sql="select count(*) num from cus_info where info_phone!='' or info_wechat!=null or info_qq!=''";
		long totals = cs.getTotals(sql);
		System.out.println("totals="+totals);
	}
	
	public static void testGetDatasLimt() throws Exception
	{
		CustomService cs=new CustomServiceImpl();
		String sql="select * from cus_info limit 0,4";
		List<CustomerInfo> datasLimt = cs.getDatasLimt(CustomerInfo.class, sql, new Object[] {});
		for (CustomerInfo customerInfo : datasLimt) {
			System.out.println(customerInfo.toString());
		}
	}

}
