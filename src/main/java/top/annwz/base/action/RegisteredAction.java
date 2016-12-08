package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.dubbo.service.IBaUserService;
import top.annwz.base.entity.BaUser;
import top.annwz.base.uitl.*;

import javax.annotation.Resource;
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

	@RequestMapping("/registered")
	public AbsResponse<HashMap<String, Object>> register(@RequestBody HashMap<String, Object> params) {
		AbsResponse<HashMap<String, Object>> abs = new AbsResponse<HashMap<String, Object>>();
		String userName = Converter.getString(params, "userName");
		String password = Converter.getString(params, "password");
		String mobile = Converter.getString(params, "mobile");
		String email = Converter.getString(params, "email");
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(email)) {
			ReqUtil.setErrAbs(abs, 10001, "参数错误");
			return abs;
		}
		BaUser baUser = new BaUser();
		baUser.setUserName(userName);
		//TODO 先对密码进行解密 然后 进行EncryptUtil.decrypt();
		baUser.setPassword(EncryptUtil.encrypt(password));
		baUser.setMobile(mobile);
		baUser.setEmail(email);
		userService.insert(baUser);
		ReqUtil.setErrAbs(abs, 0, "success");
		return abs;
	}
}

