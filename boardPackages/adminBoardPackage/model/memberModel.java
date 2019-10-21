package project.pc.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class memberModel {

	private static final int ORDER		= 0x01;
	private static final int COMPANY 	= 0x02;
	private static final int CARD 		= 0x04;
	private static final int STATS 		= 0x08;
	private static final int NOTICE 	= 0x10;
	private static final int MEMBER 	= 0x20;
	private static final int INDUSTRY 	= 0x40;
	private static final int DEPT 		= 0x80;

	private int rownum = 0;
	private int no = 0;
	private String name = null;
	private String id = null;
	private String passwd = null;
	private int level = -1;
	private String email = null;
	private String number = null;
	private Date date = null;

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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public Map<String, Object> getLevels() {
		Map<String, Object> level = new HashMap<>();

		level.put("company", checkLevel(COMPANY) ? true : false);
		level.put("order", checkLevel(ORDER) ? true : false);
		level.put("stats", checkLevel(STATS) ? true : false);
		level.put("card", checkLevel(CARD) ? true : false);
		level.put("notice", checkLevel(NOTICE) ? true : false);
		level.put("member", checkLevel(MEMBER) ? true : false);
		level.put("industry", checkLevel(INDUSTRY) ? true : false);
		level.put("dept", checkLevel(DEPT) ? true : false);
		return level;
	}
	public boolean checkLevel(int menuValue) {
		return (level & menuValue) != 0;
	}
}
