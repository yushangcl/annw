package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.entity.Mail;
import top.annwz.base.uitl.EmailUtil;
import top.annwz.base.uitl.MailUtil;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/email")
public class SendEmailAction extends BasicAction{

	@RequestMapping("/sendEmail")
	public String sendEmail(@RequestBody() Mail mail){
		MailUtil mailUtil = new MailUtil();
		for (int i = 0; i < 10; i++){
			mail.setMessage("<h2>测试第" + i + "次</h2>");
			mailUtil.sendEmail(mail);
		}
		return "success";
	}
}
