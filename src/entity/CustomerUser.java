package entity;

import java.sql.Timestamp;

import orm.MyClass;
import orm.MyColumn;
import orm.MyKey;

@MyClass(myTable = "cus_user")
public class CustomerUser {

	//id 管理员id
	@MyKey(columnName = "id" ,isGenerator = true)
	private int id;
	//user_name 管理员名字
	@MyColumn(columnName = "user_name")
	private String name;
	//user_pwd 管理员登入密码
	@MyColumn(columnName = "user_pwd")
	private String password;
	//update_time 
	@MyColumn(columnName = "update_time")
	private Timestamp updateTime;
	
	
	public CustomerUser() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerUser(int id,String name,String password,Timestamp updateTime) {
		this.id=id;
		this.name=name;
		this.password=password;
		this.updateTime=updateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "CustomerUser [id=" + id + ", name=" + name + ", password=" + password + ", updateTime=" + updateTime
				+ "]";
	}
	
	
}


