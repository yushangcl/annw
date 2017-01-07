package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.entity.Mail;
import top.annwz.base.sys.Constants;
import top.annwz.base.uitl.Converter;
import top.annwz.base.uitl.email.MailUtil;

import java.util.HashMap;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/api")
public class SendEmailAction extends BasicAction{

	@RequestMapping("/sendEmail")
	public String sendEmail(@RequestBody() HashMap<String, Object> params){
		Mail mail = new Mail();
		String name = Converter.getString(params, "name");
		String email = Converter.getString(params, "email");
		String info = Converter.getString(params, "info");
		try {
			mail.setHost(Constants.EMAIL_HOST);
			mail.setMessage("<h3>发送者姓名：" + name + "发送者邮箱：" + email +"</h3><br>内容:" +info);
			mail.setReceiver("623281847@qq.com");
			mail.setSender(Constants.EMAIL_SENDER);
			mail.setUsername(Constants.EMAIL_USERNAME);
			mail.setPassword(Constants.EMAIL_PASSWORD);
			mail.setSubject("信息反馈");
			MailUtil.sendEmail(mail);
		} catch (Exception e) {
			logger.error("发送邮件失败：");
			return "error";
		}
		return "success";
	}
}
