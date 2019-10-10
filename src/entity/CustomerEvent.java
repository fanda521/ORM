package entity;

import java.sql.Date;
import java.sql.Timestamp;

import orm.MyClass;
import orm.MyColumn;
import orm.MyKey;

@MyClass(myTable = "cus_event")
public class CustomerEvent {
	
	//event_id
	@MyKey(columnName = "event_id",isGenerator = true)
	private int id;
	//event_content 内容
	@MyColumn(columnName = "event_content")
	private String content;
	//event_apply 反馈
	@MyColumn(columnName = "event_apply")
	private String apply;
	//event_next 下次回访建议
	@MyColumn(columnName = "event_next")
	private String next;
	//update_time
	@MyColumn(columnName = "update_time")
	private Timestamp updateTime;
	//event_time 回访时间
	@MyColumn(columnName = "event_time")
	private Date date;
	//info_id 
	@MyColumn(columnName = "info_id")
	private long infoId;
	
	public CustomerEvent() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
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

	public long getInfoId() {
		return infoId;
	}

	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}

	@Override
	public String toString() {
		return "CustomerEvent [id=" + id + ", content=" + content + ", apply=" + apply + ", next=" + next
				+ ", updateTime=" + updateTime + ", date=" + date + ", infoId=" + infoId + "]";
	}
	
	
}
