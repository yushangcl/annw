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
import java.util.HashMap;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/api")
public class RegisteredAction extends BasicAction {
	@Resource
	private IBaUserService bauserService;

	@Resource
	private IBaCodeService baCodeService;

	/*
	 * @ApiMethod:true
	 *
	 * @ApiMethodName:注册
	 * @ApiRequestParamsDes:
	 * |userName|必须|String|username|用户名|
	 * |password|必须|String|123456|密码|
	 * |mobile|必须|String|12345678900|手机号码|
	 * |email|必须|String|123456@qq.com|邮箱地址|
	 *
	 * @ApiRequestParams: {"userName" : "username", "password" : "123456", "mobile" : "12345678900", "email" : "123456@qq.com"}
	 *
	 * @ApiResponse:
	 * 成功返回: {"code":0,"msg":"","data":""}
	 *
	 * 失败返回:{"code":1,"msg":"异常","data":""}
	 *
	 * @ApiMethodEnd
	 */
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
		//验证邮箱
		if (!RegexUtils.checkEmail(email)) {
			ReqUtil.setErrAbs(abs, "邮箱格式错误");
			return abs;
		}
		//验证手机号码
		if (!RegexUtils.checkMobile(mobile)) {
			ReqUtil.setErrAbs(abs, "手机号码格式错误");
			return abs;
		}

		//验证用户名

		//验证密码
		if (RegexUtils.IsPassword(password) || RegexUtils.IsPasswLength(password)) {
			ReqUtil.setErrAbs(abs, "密码格式不正确");
			return abs;
		}
		BaUser baUser = bauserService.getByEmail(email);
		if (baUser != null ) {
			ReqUtil.setErrAbs(abs, "该邮箱已被注册");
			return abs;
		}
		baUser = new BaUser();
		try {
			baUser.setUserName(userName);
			baUser.setPassword(EncryptUtil.encrypt(password));//进行EncryptUtil.decrypt();
			baUser.setMobile(mobile);
			baUser.setEmail(email);
			baUser.setEmailStatus(0);
			baUser.setFaceUrl(Constants.USER_FACEURL);//注册的时候默认该头像
			ReqUtil.setSucAbs(abs, "success");
			// 验证邮箱信息
			String code = generateCode(email);
			boolean isSend = sendEmail(email, userName, code);//发送验证邮箱 邮件
			if (!isSend) {
				ReqUtil.setErrAbs(abs, "邮件发送失败");
				return abs;
			}
			bauserService.insert(baUser);
			//TODO 记录日志
		} catch (Exception e) {
			logger.error("注册失败：" + baUser);
		}
		return abs;
	}

	/*
	 * @ApiMethod:true
	 *
	 * @ApiMethodName:验证邮箱地址
	 * @ApiRequestParamsDes:
	 *
	 * @ApiRequestParams: code=rpsxMiiPkYmcTfgK23zoltJ5Ro5zA2gJ1enkdSsM
	 *
	 * @ApiResponse:
	 * 成功返回: {"code":0,"msg":"","data":""}
	 *
	 * 失败返回:{"code":1,"msg":"异常","data":""}
	 *
	 * @ApiMethodEnd
	 */
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
		int count = bauserService.updateStatusByEmail(baCode.getEmail());
		if (co != 1 || count != 1) {
			ReqUtil.setErrAbs(abs, "激活失败");
			return abs;
		}
		ReqUtil.setSucAbs(abs,"邮箱验证成功");
		return abs;
	}

	/**
	 * 发送邮件
	 * @param email
	 * @param userName
	 * @param code
	 * @return 发送成功返回true 失败返回false
	 */
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
			return false;
		}
		return true;
	}

	/**
	 * 生成 邮箱验证code
	 * @param email
	 * @return code
	 */
	private String generateCode(String email) {
		String code = StringUtils.getRandomString(40);
		BaCode baCode = new BaCode();
		baCode.setEmail(email);
		baCode.setCodeStatus(0);
		baCode.setCodeType("registered");
		baCode.setCodeValue(code);
		baCodeService.insert(baCode);
		return code;
	}


}

