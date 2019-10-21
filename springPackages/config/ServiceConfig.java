package project.common.config;

import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

	String rootPath;
	String templatePath;
	String excelPath;
	String JNIDir;
	String imgSavePath;
	String domainInfo;
	String landingUrl;
	int defaultPagingSize;
	int defaultListViewSize;
	
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getExcelPath() {
		return excelPath;
	}
	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}
	public String getJNIDir() {
		return JNIDir;
	}
	public void setJNIDir(String jNIDir) {
		JNIDir = jNIDir;
	}
	public String getImgSavePath() {
		return imgSavePath;
	}
	public void setImgSavePath(String imgSavePath) {
		this.imgSavePath = imgSavePath;
	}
	public String getDomainInfo() {
		return domainInfo;
	}
	public void setDomainInfo(String domainInfo) {
		this.domainInfo = domainInfo;
	}
	public String getLandingUrl() {
		return landingUrl;
	}
	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}
	public int getDefaultPagingSize() {
		return defaultPagingSize;
	}
	public void setDefaultPagingSize(int defaultPagingSize) {
		this.defaultPagingSize = defaultPagingSize;
	}
	public int getDefaultListViewSize() {
		return defaultListViewSize;
	}
	public void setDefaultListViewSize(int defaultListViewSize) {
		this.defaultListViewSize = defaultListViewSize;
	}
}
