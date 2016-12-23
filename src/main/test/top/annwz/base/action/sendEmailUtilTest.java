package top.annwz.base.action;

import org.junit.Test;
import top.annwz.base.entity.Mail;
import top.annwz.base.uitl.email.EmailUtil;
import top.annwz.base.uitl.email.MailUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Wuhuahui on 2016/12/3.
 */
public class sendEmailUtilTest {
	@org.junit.Test
	public void sendEmail() {
		MailUtil mailUtil = new MailUtil();
		Mail mail = new Mail();
		mail.setHost("smtp.qq.com");
		mail.setMessage("测试数据");
		mail.setReceiver("623281847@qq.com");
		mail.setSender("1941247390@qq.com");
		mail.setUsername("1941247390@qq.com");
		mail.setPassword("WHH88913HH233123..");
		mail.setSubject("测试");
		mailUtil.sendEmail(mail);
	}

	@Test
	public void sendEmailTest() throws Exception{
		String userName = "1941247390@qq.com"; // 发件人邮箱
		String password = "WHH88913HH233123.."; // 发件人密码
		String smtpHost = "smtp.qq.com"; // 邮件服务器

		String to = "623281847@qq.com"; // 收件人，多个收件人以半角逗号分隔
		String cc = "1941247390@qq.com"; // 抄送，多个抄送以半角逗号分隔
		String subject = "这是邮件的主题"; // 主题
		String body = "<h2 style='color:red'>这是邮件的正文</h2>"; // 正文，可以用html格式的哟
		List<String> attachments = Arrays.asList("D:\\test.txt"); // 附件的路径，多个附件也不怕

		EmailUtil email = EmailUtil.entity(smtpHost, userName, password, to, cc, subject, body, attachments);

		email.send(); // 发送！
	}
}
