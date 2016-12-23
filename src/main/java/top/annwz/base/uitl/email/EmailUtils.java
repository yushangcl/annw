package top.annwz.base.uitl.email;

import top.annwz.base.entity.BaUser;

import java.io.FileOutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

/**
 * @ClassName: Sendmail
 * @Description: Sendmail类继承Thread，因此Sendmail就是一个线程类，这个线程类用于给指定的用户发送Email
 * @author: Wuhuahui
 * @date: 2015-1-12 下午10:43:48
 */
public class EmailUtils extends Thread {
	//用于给用户发送邮件的邮箱
	private String from = "gacl@sohu.com";
	//邮箱的用户名
	private String username = "gacl";
	//邮箱的密码
	private String password = "邮箱密码";
	//发送邮件的服务器地址
	private String host = "smtp.sohu.com";

	private BaUser user;

	public EmailUtils(BaUser user) {
		this.user = user;
	}

	/* 重写run方法的实现，在run方法中发送邮件给指定的用户
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.host", host);
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.auth", "true");
			Session session = Session.getInstance(prop);
			session.setDebug(true);
			Transport ts = session.getTransport();
			ts.connect(host, username, password);
			Message message = createEmail(session, user);
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param session
	 * @param user
	 * @return
	 * @throws Exception
	 * @Method: createEmail
	 * @Description: 创建要发送的邮件
	 * @Anthor:孤傲苍狼
	 */
	public Message createEmail(Session session, BaUser user) throws Exception {

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
		message.setSubject("用户注册邮件");

		String info = "恭喜您注册成功，您的用户名：" + user.getUserName() + ",您的密码：" + user.getPassword() + "，请妥善保管，如有问题请联系网站客服!!";
		message.setContent(info, "text/html;charset=UTF-8");
		message.saveChanges();
		return message;
	}

	/**
	 * @param session
	 * @return
	 * @throws Exception
	 * @Method: createMixedMail
	 * @Description: 生成一封带附件和带图片的邮件
	 * @Anthor:孤傲苍狼
	 */
	public static MimeMessage createMixedMail(Session session) throws Exception {
		//创建邮件
		MimeMessage message = new MimeMessage(session);

		//设置邮件的基本信息
		message.setFrom(new InternetAddress("gacl@sohu.com"));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
		message.setSubject("带附件和带图片的的邮件");

		//正文
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("xxx这是女的xxxx<br/><img src='cid:aaa.jpg'>", "text/html;charset=UTF-8");

		//图片
		MimeBodyPart image = new MimeBodyPart();
		image.setDataHandler(new DataHandler(new FileDataSource("src\\3.jpg")));
		image.setContentID("aaa.jpg");

		//附件1
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("src\\4.zip"));
		attach.setDataHandler(dh);
		attach.setFileName(dh.getName());

		//附件2
		MimeBodyPart attach2 = new MimeBodyPart();
		DataHandler dh2 = new DataHandler(new FileDataSource("src\\波子.zip"));
		attach2.setDataHandler(dh2);
		attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

		//描述关系:正文和图片
		MimeMultipart mp1 = new MimeMultipart();
		mp1.addBodyPart(text);
		mp1.addBodyPart(image);
		mp1.setSubType("related");

		//描述关系:正文和附件
		MimeMultipart mp2 = new MimeMultipart();
		mp2.addBodyPart(attach);
		mp2.addBodyPart(attach2);

		//代表正文的bodypart
		MimeBodyPart content = new MimeBodyPart();
		content.setContent(mp1);
		mp2.addBodyPart(content);
		mp2.setSubType("mixed");

		message.setContent(mp2);
		message.saveChanges();

		message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
		//返回创建好的的邮件
		return message;
	}
}