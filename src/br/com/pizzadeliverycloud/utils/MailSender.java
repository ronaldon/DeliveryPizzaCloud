package br.com.pizzadeliverycloud.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	private static final String MAIL_FROM = "jjjrr.soa@gmail.com";
	private static Session session = Session.getDefaultInstance(new Properties()); 	
	public static boolean sendMail(String mailTO, String subject, String messageMail) {
		
		boolean result = false;
		
		try {
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(MailSender.MAIL_FROM));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTO));
			msg.setSubject(subject);
			msg.setText(messageMail);
			Transport.send(msg);
			
			result = true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return result;
	}	
}
