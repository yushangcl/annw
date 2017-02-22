package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.annwz.base.uitl.AbsResponse;

import java.util.HashMap;

/**
 * Created by Wuhuahui on 2017/1/3.
 */
@Controller
@ResponseBody
@RequestMapping("api")
public class HomeAction extends BasicAction{

	@RequestMapping("home")
	public AbsResponse queryInfo(@RequestBody HashMap<String, Object> params) throws Exception{
		AbsResponse absResponse = new AbsResponse();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("home", "这是一个测试方法");
		absResponse.setData(map);
		return absResponse;
	}

	@RequestMapping("get")
	public AbsResponse getinfo() {
		AbsResponse absResponse = new AbsResponse();

		return absResponse;
	}
}
