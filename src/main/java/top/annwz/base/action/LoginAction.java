package top.annwz.base.action;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.dubbo.service.IBaUserService;
import top.annwz.base.entity.BaUser;
import top.annwz.base.uitl.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Observable;
import java.util.UUID;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/api")
public class LoginAction extends BasicAction {
	@Resource
	IBaUserService baUserService;

	/*
	 * @ApiMethod:true
	 *
	 * @ApiMethodName:登录
	 * @ApiRequestParamsDes:
	 * |userName|必须|String|username|用户名|
	 * |password|必须|String|123456|密码|
	 *
	 * @ApiRequestParams: {"userName" : "username", "password" : "123456"}
	 *
	 * @ApiResponse:
	 * 成功返回: {"code":0,"msg":"","data":""}
	 *
	 * 失败返回:{"code":1,"msg":"异常","data":""}
	 *
	 * @ApiMethodEnd
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AbsResponse<HashMap<String, Object>> initLogin(@RequestBody HashMap<String, Object> params) throws Exception {
		AbsResponse<HashMap<String, Object>> abs = new AbsResponse<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String password = Converter.getString(params, "password");
		String email = Converter.getString(params, "email");
		try {
			if (email == null || password == null) {
				ReqUtil.setErrAbs(abs, 1, "参数缺失");
				return abs;
			}
			BaUser baUser = baUserService.getByEmail(email);
			if (baUser == null) {
				ReqUtil.setErrAbs(abs, 1, "用户不存在");
				return abs;
			}
			if (baUser.getEmailStatus() == null || baUser.getEmailStatus() == 0) {
				ReqUtil.setErrAbs(abs, 1, "邮箱未激活");
				return abs;
			}

			password = EncryptUtil.encrypt(password);

			if (password.equals(baUser.getPassword())) {
				ReqUtil.setSucAbs(abs, 0, "登陆成功");
				map.put("userName", baUser.getUserName());
				map.put("email", baUser.getEmail());
				map.put("faceUrl", baUser.getFaceUrl());
				abs.setData(map);
				logger.info("登录成功: userId=" + baUser.getUserId());
			} else {
				ReqUtil.setErrAbs(abs, 1, "用户名或密码错误");
			}
		} catch (Exception e) {
			logger.error("登录异常：" + email);
		}
		return abs;
	}

}
