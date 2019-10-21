package project.pc.model;

import java.util.Date;

public class orderModel {
	
	private static final int KOREAN			= 0x01;
	private static final int ENGLISH 		= 0x02;
	private static final int CHINESE 		= 0x04;
	private static final int JAPANESE 		= 0x08;
	
	private short category = 0;
	private String category_name = null;
	private int level = -1;
	private int no = 0;
	private int rownum = 0;
	private String id = null;
	private String title = null;
	private String tel = null;
	private String email = null;
	private String address = null;
	private Date orderdate = null;
	private String recvdate = null;
	private String distdate = null;
	private String enddate = null;
	private String content = null;
	private short status = 0;
	private String dsfile = null;
	private int orderth = 0;
	private int company_no = 0;
	private int user_no = 0;
	private int start = 0;
	private int finish = 0;
	private int language = 0;
	private int count = 0;
	private String name = null;
	private short isclientnew = 0;
	private short isadminnew = 0;
	
	public short getCategory() {
		return category;
	}
	public void setCategory(short category) {
		this.category = category;
	}
	public String getCategory_name()
	{
		return category_name;
	}
	public void setCategory_name(String category_name)
	{
		this.category_name = category_name;
	}
	public short getIsclientnew() {
		return isclientnew;
	}
	public void setIsclientnew(short isclientnew) {
		this.isclientnew = isclientnew;
	}
	public short getIsadminnew() {
		return isadminnew;
	}
	public void setIsadminnew(short isadminnew) {
		this.isadminnew = isadminnew;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRecvdate() {
		return recvdate;
	}
	public void setRecvdate(String recvdate) {
		this.recvdate = recvdate;
	}
	public String getDistdate() {
		return distdate;
	}
	public void setDistdate(String distdate) {
		this.distdate = distdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDsfile() {
		return dsfile;
	}
	public void setDsfile(String dsfile) {
		this.dsfile = dsfile;
	}
	public int getOrderth() {
		return orderth;
	}
	public void setOrderth(int orderth) {
		this.orderth = orderth;
	}
	public int getCompany_no() {
		return company_no;
	}
	public void setCompany_no(int company_no) {
		this.company_no = company_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getFinish() {
		return finish;
	}
	public void setFinish(int finish) {
		this.finish = finish;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean checkLevel(int menuValue) {
		return (language & menuValue) != 0;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		if ( checkLevel(KOREAN) ) {
			sb.append((sb.length() > 0?",":"") + "한국어");
		} else {
			sb.append((sb.length() > 0?",":"") + "false");
		}
		if ( checkLevel(ENGLISH) ) {
			sb.append((sb.length() > 0?",":"") + "영어");
		} else {
			sb.append((sb.length() > 0?",":"") + "false");
		}
		if ( checkLevel(CHINESE) ) {
			sb.append((sb.length() > 0?",":"") + "중국어");
		} else {
			sb.append((sb.length() > 0?",":"") + "false");
		}
		if ( checkLevel(JAPANESE) ) {
			sb.append((sb.length() > 0?",":"") + "일어");
		} else {
			sb.append((sb.length() > 0?",":"") + "false");
		}
		return sb.toString();
	}
}
