package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class BaiduApiTest {
	@Test
	public void getIpInfo() throws Exception {
		JSONObject obj = BaiduApi.getIpInfo("115.236.172.146");
		String  city = obj.getJSONObject("retData").getString("city");
		System.out.println(obj + city);
	}

	@Test
	public void getWeatherInfo() throws Exception {
		Object obj = BaiduApi.getWeatherInfo("hangzhou");
		System.out.println(obj);
	}

	@Test
	public void getWeather() throws Exception {
		JSONObject obj = BaiduApi.getIpInfo("115.236.172.146");
		String  city = obj.getJSONObject("retData").getString("district");
		JSONObject jsonObject = BaiduApi.getWeathe(city);
		System.out.println(jsonObject);
	}

}