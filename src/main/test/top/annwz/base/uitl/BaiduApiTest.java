package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import top.annwz.base.uitl.api.BaiduApi;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class BaiduApiTest {
	@Test
	public void getIpInfo() throws Exception {
		JSONObject obj = BaiduApi.getIpInfo("192.168.1.1");
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
		String  city = obj.getJSONObject("retData").getString("city");
		JSONObject jsonObject = BaiduApi.getWeathe(city);
		System.out.println(jsonObject);
	}

	@Test
	public void getMobile() throws Exception {
		JSONObject jsonObject = BaiduApi.getPhoneAttribution("18368093869");
		System.out.println(jsonObject);
	}

	@Test
	public void md5Dec() throws Exception {
		JSONObject jsonObject = BaiduApi.md5Dec("7e73d06707f5fb75ec77cc5f2bd9bb92");
		System.out.println(jsonObject);
	}

	@Test
	public void getNewChannel() throws Exception {
		JSONObject jsonObject = BaiduApi.getNewChannel();
		System.out.println(jsonObject);
	}

	@Test
	public void getNews() throws Exception {
		JSONObject jsonObject = BaiduApi.getNews("5572a108b3cdc86cf39001d9", "", "", "1", "0", "0");
		System.out.println(jsonObject);
	}
	@Test
	public void getIdCardInfo() throws Exception {
		JSONObject jsonObject = BaiduApi.getIdCardInfo("520324198901154695");
		System.out.println(jsonObject);
	}




}