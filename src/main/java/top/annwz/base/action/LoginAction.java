package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.dubbo.service.IBaUserService;
import top.annwz.base.entity.BaUser;
import top.annwz.base.uitl.AbsResponse;
import top.annwz.base.uitl.Converter;
import top.annwz.base.uitl.ReqUtil;
import top.annwz.base.uitl.SpringContextHolder;

import javax.annotation.Resource;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Observable;
import java.util.UUID;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/login")
public class LoginAction extends BasicAction {
	@Resource
	IBaUserService baUserService;
	@RequestMapping("/login")
	public AbsResponse<HashMap<String, Object>> initLogin(@RequestBody HashMap<String, Object> params) {
		AbsResponse<HashMap<String, Object>> abs = new AbsResponse<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String userId = Converter.getString(params, "userId");
		BaUser baUser = baUserService.getUser(Long.valueOf(userId));
		String ukid = UUID.randomUUID().toString(); //获取唯一的ukid
		logger.debug("登录" + ukid);
		map.put("userId", ukid);
//		ReqUtil.setErrAbs(abs, 1001 , "error");
//		map.put("bean",SpringContextHolder.getBean(this.getClass()));
		map.put("user", baUser);
		abs.setData(map);

		return abs;
	}
}