package top.annwz.base.service.imp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import top.annwz.base.dubbo.service.IBaCodeService;
import top.annwz.base.dubbo.service.IBaUserService;
import top.annwz.base.entity.BaCode;
import top.annwz.base.entity.BaUser;
import top.annwz.base.entity.Mail;
import top.annwz.base.service.IRegisteredService;
import top.annwz.base.sys.Constants;
import top.annwz.base.uitl.StringUtils;
import top.annwz.base.uitl.email.MailUtil;

import javax.annotation.Resource;

/**
 * Created by Wuhuahui on 2016/12/8.
 */
@Service
public class RegisteredService implements IRegisteredService {
	protected transient Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IBaUserService baUserService;

	@Resource
	private IBaCodeService baCodeService;
	@Override
	public BaUser saveRegisterInfo(String username, String password, String mobile, String email) throws Exception {
		return null;
	}

	@Override
	public boolean updateStatus(String codeValue, String email) throws Exception {

		//更新邮箱验证码状态
		int co = baCodeService.updateCodeStatus(codeValue);

		//更新用户邮箱的验证状态
		int count = baUserService.updateStatusByEmail(email);

		if (co == 1 && count == 1) {
			return true;
		}
		return false;
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
