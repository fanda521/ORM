package entity;

import java.sql.Date;
import java.sql.Timestamp;

import orm.MyClass;
import orm.MyColumn;
import orm.MyKey;

@MyClass(myTable = "cus_info")
public class CustomerInfo {
	//info_id
	@MyKey(columnName = "info_id",isGenerator = true)
	private long id;
	//info_name 
	@MyColumn(columnName = "info_name")
	private String name;
	//info_degree
	@MyColumn(columnName = "info_degree")
	private String degree;
	//info_sex
	@MyColumn(columnName = "info_sex")
	private String sex;
	//info_career
	@MyColumn(columnName = "info_career")
	private String career;
	//info_qq
	@MyColumn(columnName = "info_qq")
	private String qq;
	//info_wechat
	@MyColumn(columnName = "info_wechat")
	private String weChat;
	//info_phone
	@MyColumn(columnName = "info_phone")
	private String phone;
	//info_source
	@MyColumn(columnName = "info_source")
	private String source;
	//info_city
	@MyColumn(columnName = "info_city")
	private String city;
	//info_purpose
	@MyColumn(columnName = "info_purpose")
	private String purpose;
	//info_iscus
	@MyColumn(columnName = "info_iscus")
	private int isCus;
	//info_service 
	@MyColumn(columnName = "info_service")
	private int service;
	//update_time
	@MyColumn(columnName = "update_time")
	private Timestamp updateTime;
	//info_time
	@MyColumn(columnName = "info_time")
	private Date date;
	
	public CustomerInfo() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public int getIsCus() {
		return isCus;
	}

	public void setIsCus(int isCus) {
		this.isCus = isCus;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CustomerInfo [id=" + id + ", name=" + name + ", degree=" + degree + ", sex=" + sex + ", career="
				+ career + ", qq=" + qq + ", weChat=" + weChat + ", phone=" + phone + ", source=" + source + ", city="
				+ city + ", purpose=" + purpose + ", isCus=" + isCus + ", service=" + service + ", updateTime="
				+ updateTime + ", date=" + date + "]";
	}
	
	
}
