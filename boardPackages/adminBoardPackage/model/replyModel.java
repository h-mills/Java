package project.pc.model;

import java.util.Date;

public class replyModel {
	private int no = 0;
	private int orderno = 0;
	private short isconfirm = 0;
	private Date regdate = null;
	private Date ansdate = null;
	private String content = null;
	private short isuser = 0;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public short getIsconfirm() {
		return isconfirm;
	}
	public void setIsconfirm(short isconfirm) {
		this.isconfirm = isconfirm;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getansdate() {
		return ansdate;
	}
	public void setansdate(Date ansdate) {
		this.ansdate = ansdate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public short getIsuser() {
		return isuser;
	}
	public void setIsuser(short isuser) {
		this.isuser = isuser;
	}
	
}
