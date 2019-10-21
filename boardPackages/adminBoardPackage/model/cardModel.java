package project.pc.model;

import java.util.Date;

public class cardModel {

	private static final int KOREAN			= 0x08;
	private static final int ENGLISH 		= 0x04;
	private static final int CHINESE 		= 0x02;
	private static final int JAPANESE 		= 0x01;

	private int language = 0;
	private int rownum = 0;
	private int no = 0;
	private String image = null;
	private int card_config_no = 0;
	private int count = 0;
	private String name = null;
	private String part = null;
	private String position = null;
	private String duty = null;
	private String address = null;
	private String email = null;
	private String tel = null;
	private String fax = null;
	private String mobile = null;
	private int carddata_master_no = 0;
	private int order_no = 0;
	private int isdelete = 0;
	private String category = null;
	private Date date = null;
	private String flag = null;
	private String lang = null;
	private String landinginfo = null;
	private String imageViewPath = null;

	public int getLanguageCount() {
		String str = Integer.toBinaryString(language);
		int count = 0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='1')
				count++;
		}
		return count;
	}
	public String getFirstLang() {
		String firstLang = "";
		if ((language & KOREAN) > 0) {
			firstLang = "ko";
		} else if ((language & ENGLISH) > 0) {
			firstLang = "en";
		} else if ((language & CHINESE) > 0) {
			firstLang = "cn";
		} else if ((language & JAPANESE) > 0) {
			firstLang = "jp";
		}
		return firstLang;
	}
	public String getLanguageKo() {
		String result = "";
		if ((language & KOREAN) > 0) {result = "1";}else{result = "0";}
		return result;
	}
	public String getLanguageEn() {
		String result = "";
		if ((language & ENGLISH) > 0) {result = "1";}else{result = "0";}
		return result;
	}
	public String getLanguageCn() {
		String result = "";
		if ((language & CHINESE) > 0) {result = "1";}else{result = "0";}
		return result;
	}
	public String getLanguageJp() {
		String result = "";
		if ((language & JAPANESE) > 0) {result = "1";}else{result = "0";}
		return result;
	}
	public String getImageViewPath() {
		return imageViewPath;
	}
	public void setImageViewPath(String imageViewPath) {
		this.imageViewPath = imageViewPath;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getCard_config_no() {
		return card_config_no;
	}
	public void setCard_config_no(int card_config_no) {
		this.card_config_no = card_config_no;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getCarddata_master_no() {
		return carddata_master_no;
	}
	public void setCarddata_master_no(int carddata_master_no) {
		this.carddata_master_no = carddata_master_no;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLandinginfo() {
		return landinginfo;
	}
	public void setLandinginfo(String landinginfo) {
		this.landinginfo = landinginfo;
	}
}
