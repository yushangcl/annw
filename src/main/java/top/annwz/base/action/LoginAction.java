package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.uitl.AbsResponse;
import top.annwz.base.uitl.Converter;

import javax.xml.ws.Response;
import java.util.HashMap;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Controller
@ResponseBody
@RequestMapping("/login")
public class LoginAction extends BasicAction {
	@RequestMapping("/login")
	public AbsResponse<HashMap<Object, String>> initLogin(@RequestBody HashMap<String, Object> params) {
		AbsResponse<HashMap<Object, String>> abs = new AbsResponse<HashMap<Object, String>>();
		String userId = Converter.getString(params, "userId");
		logger.debug("登录" + userId);
//		map.put("userId", userId);
		abs.setCode(0);
		return abs;
	}
}
