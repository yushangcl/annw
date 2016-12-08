package top.annwz.base.uitl;

/**
 * Created by Wuhuahui on 2016/12/2.
 */


import top.annwz.base.entity.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

	//服务器地址
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_PORT = "mail.smtp.port";
	// 服务器验证
	private static String KEY_PROPS = "mail.smtp.auth";
	private static String KEY_STARTTLS = "mail.smtp.starttls.enable";

	public String sendEmail(final Mail mail) {

		Properties props = new Properties();
		props.put(KEY_PROPS, "true");
		props.put(KEY_STARTTLS, "true");
		props.put(KEY_SMTP, mail.getHost());
		props.put(VALUE_PORT, "25");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mail.getUsername(), mail.getPassword());
					}
				});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(mail.getSender()));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceiver()));

			// Set Subject: header field
			message.setSubject(mail.getSubject());

			// Now set the actual message
			message.setContent(mail.getMessage(), "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富

			message.setSentDate(mail.getDate());//设置发信时间

			message.saveChanges();//存储邮件信息

			// Send message
			Transport.send(message);


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return "success";
	}


}