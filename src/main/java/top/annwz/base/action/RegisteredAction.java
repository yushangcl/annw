package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.dubbo.service.IBaCodeService;
import top.annwz.base.dubbo.service.IBaUserService;
import top.annwz.base.entity.BaCode;
import top.annwz.base.entity.BaUser;
import top.annwz.base.entity.Mail;
import top.annwz.base.sys.Constants;
import top.annwz.base.uitl.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/api")
public class RegisteredAction extends BasicAction {
	@Resource
	private IBaUserService userService;

	@Resource
	private IBaCodeService baCodeService;

	//TODO 以后写在配置文件中
//	private static final String sender = "1941247390@qq.com";
//	private static final String password = "WHH88913HH233123..";
//	private static final String host = "smtp.qq.com";
//	private static final String url = "http://an.annwz.top/annw/api/verify";

	@RequestMapping("/registered")
	public AbsResponse<HashMap<String, Object>> register(@RequestBody HashMap<String, Object> params) {
		AbsResponse<HashMap<String, Object>> abs = new AbsResponse<HashMap<String, Object>>();
		String userName = Converter.getString(params, "userName");
		String password = Converter.getString(params, "password");
		String mobile = Converter.getString(params, "mobile");
		String email = Converter.getString(params, "email");
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(email)) {
			ReqUtil.setErrAbs(abs, "参数缺失");
			return abs;
		}
		BaUser baUser = userService.getByEmail(email);
		if (baUser != null ) {
			ReqUtil.setErrAbs(abs, "该邮箱已被注册");
			return abs;
		}
		baUser = new BaUser();
		try {
			baUser.setUserName(userName);
			//进行EncryptUtil.decrypt();
			baUser.setPassword(EncryptUtil.encrypt(password));
			baUser.setMobile(mobile);
			baUser.setEmail(email);
			baUser.setEmailStatus(0);
			userService.insert(baUser);
			// 验证邮箱信息
			ReqUtil.setSucAbs(abs, "success");
			String code = StringUtils.getRandomString(40);
			BaCode baCode = new BaCode();
			baCode.setEmail(email);
			baCode.setCodeStatus(0);
			baCode.setCodeType("registered");
			baCode.setCodeValue(code);
			baCodeService.insert(baCode);
			sendEmail(email, userName, code);//发送验证邮箱 邮件
		} catch (Exception e) {
			logger.error("注册失败：" + baUser);
		}
		return abs;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public AbsResponse<HashMap<String, Object>> verifyEmail(HttpServletRequest request) {
		AbsResponse<HashMap<String, Object>> abs = new AbsResponse<HashMap<String, Object>>();
		String codeValue = request.getParameter("code");
		BaCode baCode = baCodeService.getByCodeValue(codeValue);
		if (baCode == null) {
			ReqUtil.setErrAbs(abs, "验证链接失效");
			return abs;
		}
		if (baCode.getCodeStatus() == null || baCode.getCodeStatus() != 0) {
			ReqUtil.setErrAbs(abs, "邮箱已激活");
			return abs;
		}
		int co = baCodeService.updateCodeStatus(codeValue);
		//todo 将用户的状态改为 激活
		int count = userService.updateStatusByEmail(baCode.getEmail());
		if (co != 1 || count != 1) {
			ReqUtil.setErrAbs(abs, "激活失败");
			return abs;
		}
		ReqUtil.setSucAbs(abs,"邮箱验证成功");
		return abs;
	}

	private boolean sendEmail(String email, String userName, String code) {
		Mail mail = new Mail();
		try {
			mail.setHost(Constants.EMAIL_HOST);
			String info = "恭喜您注册成功，您的用户名：" + userName +
					"点击该链接验证邮箱<br><a href='"+ Constants.EMAIL_URL+"?code="+code +"'>"+Constants.EMAIL_URL+"?code="+code+"</a><br>如有问题请联系网站客服!!";
			mail.setMessage(info);
			mail.setReceiver(email);
			mail.setSender(Constants.EMAIL_SENDER);
			mail.setUsername(Constants.EMAIL_USERNAME);
			mail.setPassword(Constants.EMAIL_PASSWORD);
			mail.setSubject("懒人科技 用户注册验证邮箱");
			MailUtil.sendEmail(mail);
		} catch (Exception e) {
			logger.error("发送邮件失败：" + email);
		}
		return true;
	}

}

