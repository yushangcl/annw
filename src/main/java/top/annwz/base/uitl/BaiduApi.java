package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class BaiduApi {

	private static final String apikey= "7dc3593031bff796d02153132e7d52e1";

	/**
	 * 查询 ip地址信息
	 * @param ip
	 * @return
	 */
	public static JSONObject getIpInfo(String ip) {
		String httpUrl = "http://apis.baidu.com/apistore/iplookupservice/iplookup";
		String httpArg = "ip=" + ip;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * 查询城市的天气信息
	 * @param cityPinyin 拼音
	 * @return
	 */
	public static JSONObject getWeatherInfo(String cityPinyin) {
		String httpUrl = "http://apis.baidu.com/heweather/weather/free";
		String httpArg = "city=" + cityPinyin;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * 根据城市名称查询天气信息
	 * @param cityName 城市中文名
	 * @return
	 */
	public static JSONObject getWeathe(String cityName) {
		String httpUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";
		String httpArg = "cityname=" + cityName;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * 根据Ip查询城市地址，再查询天气信息
	 * @param ip
	 * @return
	 */
	public static JSONObject getWeatheByIP(String ip) {
		JSONObject obj = BaiduApi.getIpInfo(ip);
		String  city = obj.getJSONObject("retData").getString("district");
		JSONObject jsonObject = BaiduApi.getWeathe(city);
		return jsonObject;
	}

	/**
	 * 查询手机归属地
	 * @param mobile
	 * @return
	 */
	public static JSONObject getPhoneAttribution(String mobile) {
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/mobile/find";
		String httpArg = "num=" + mobile;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * @param httpUrl
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	private static String request(String httpUrl, String httpArg) {

		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apikey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
