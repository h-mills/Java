package project.pc.service;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import project.common.util.WebUtil;
import project.pc.dao.StatsMailDAO;


@Service
public class SendMailServiceImpl implements SendMailService {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	private StatsMailDAO statsMailDAO;
	
	@Override
	public void SendMail() throws Exception {
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			int totalDet = statsMailDAO.getDetCount(0);					// 전체 검출 횟수
			
			int todayDet = statsMailDAO.getDetCount(1);					// 기준일 검출 횟수
			int yesterDet = statsMailDAO.getDetCount(2);					// 기준전일 검출 횟수
			
			int weekDet = statsMailDAO.getPeriodCount(1, 7);				// 주간 검출 횟수
			int pastWeekDet = statsMailDAO.getPeriodCount(8, 14);		// 전주 검출 횟수
			
			int monthDet = statsMailDAO.getPeriodCount(1, 30);			// 월간 검출 횟수
			int pastMonthDet = statsMailDAO.getPeriodCount(31, 60);		// 전월 검출 횟수
			
			int totalCusCount = statsMailDAO.getCusCount(0);				// 총 등록업체 개수
			int cusDetCount = statsMailDAO.getCusDetCount();				// 기준일 검출업체 개수
			int cusRegDet = statsMailDAO.getCusCount(1);					// 기준일 신규등록업체 개수
			
			msg.setSubject(MimeUtility.encodeText("[CK&B HiddenTag_명함서비스]" + "서버 통계 보고", "UTF-8", "B"));
			String nowTime = WebUtil.getDateTime();
			String content = "1. Hiddentagbiz.com 내부 서버 : 상태 양호 <br>";
			content += "2. 동작 체크 시간 : " + nowTime + "<br><br>";
			
			content += "---------------------------------------------------------------------------------------------------------------------------<br>";
			
			content += "<table style='width:400px; font-size: 14px; font-family: 맑은 고딕;'>";
			content += "<tr>";
			content	+= "<td style='width:10%; text-align: left;'>총 검출 횟수</td>";
			content += "<td style='width:5%; text-align: right;'>"+String.format("%,d", totalDet)+"</td>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='width:10%; text-align: left;'>일일(어제) 검출 횟수</td>";
			content += "<td style='width:5%; text-align: right;'>"+String.format("%,d", todayDet)+"</td></tr>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>전일 대비 증감</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", todayDet-yesterDet)+"</td>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>주간(어제~7일간) 검출 횟수</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", weekDet)+"</td></tr>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>전주 대비 증감</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", weekDet-pastWeekDet)+"</td>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>월간(어제~30일간) 검출 횟수</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", monthDet)+"</td></tr>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>전월 대비 증감</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", monthDet-pastMonthDet)+"</td></tr>";
			content += "</tr>";
			content += "</table>";
			
			content += "---------------------------------------------------------------------------------------------------------------------------<br>";
			
			content += "<table style='width:400px; font-size: 14px; font-family: 맑은 고딕;'>";
			content += "<tr>";
			content += "<td style='text-align: left;'>총 등록업체 갯수</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", totalCusCount)+"</td></tr>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>기준일 검출업체 갯수</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", cusDetCount)+"</td></tr>";
			content += "</tr>";
			
			content += "<tr>";
			content += "<td style='text-align: left;'>오늘 등록업체 갯수</td>";
			content += "<td style='text-align: right;'>"+String.format("%,d", cusRegDet)+"</td>";
			content	+= "</tr>";
			content += "</table>";
			
			content += "---------------------------------------------------------------------------------------------------------------------------<br><br>";
			
	        content += "* 해당 메일은 24시간 주기로 자동발송 됩니다.";
			msg.setText(content, "utf-8", "html");
			msg.setFrom(new InternetAddress("cknb_alimy@daum.net", MimeUtility.encodeText("CK&B HiddenTagBiz", "UTF-8", "B")));
			//msg.addRecipient(RecipientType.TO, new InternetAddress("ktw@cknb.co.kr"));
			msg.addRecipient(RecipientType.TO, new InternetAddress("hiddentag@cknb.co.kr"));
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return;
		}
		try {
			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
	
}