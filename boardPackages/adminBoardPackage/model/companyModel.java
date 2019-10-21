package project.pc.model;

import java.util.Date;

public class companyModel {
	private int rownum = 0;
	private int no = 0;
	private String biznumber = null;
	private String name = null;
	private String ceo = null;
	private String tel = null;
	private String fax = null;
	private String address = null;
	private String file = null;
	private int cardcount = 0;
	private int ordercount = 0;
	private Date date = null;
	private int usercount = 0;

	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getBiznumber() {
		return biznumber;
	}
	public void setBiznumber(String biznumber) {
		this.biznumber = biznumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCeo() {
		return ceo;
	}
	public void setCeo(String ceo) {
		this.ceo = ceo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getCardcount() {
		return cardcount;
	}
	public void setCardcount(int cardcount) {
		this.cardcount = cardcount;
	}
	public int getOrdercount() {
		return ordercount;
	}
	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getUsercount() {
		return usercount;
	}
	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}
}
