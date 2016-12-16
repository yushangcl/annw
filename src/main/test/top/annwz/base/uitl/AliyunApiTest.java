package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class AliyunApiTest {
	@Test
	public void sendVerificationCode() throws Exception {

	}

	@Test
	public void idCard() throws Exception {

	}

	@Test
	public void getCity() throws Exception {

	}

	@Test
	public void getWeather() throws Exception {

	}
	@Test
	public void getWeatherByIP() throws Exception {
		JSONObject jsonObject = AliyunApi.getWeatherByIP("115.236.172.146");
		System.out.println(jsonObject.getString("showapi_res_body"));
	}

}