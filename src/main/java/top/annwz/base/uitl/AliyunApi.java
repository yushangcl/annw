package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云api接口
 * Created by Wuhuahui on 2016/12/15.
 */

public class AliyunApi {
	private static Logger log = LogManager.getLogger(AliyunApi.class);

	private static final String methodGet = "GET";
	private static final String methodPost = "POST";

	private static final String appCode = "af8670f2f0534c469b0597b6fd834022";

	/**
	 * 发送短信验证码接口
	 *
	 * @param SignName 签名名称
	 * @param TemplateCode 模板CODE
	 * @param phoneNum 目标手机号,多条记录可以英文逗号分隔
	 * @param code 验证码
	 * @param name 用户名
	 * @return
	 */
	public static int sendVerificationCode(String SignName, String TemplateCode, String phoneNum,  String code, String name) {
		final String host = "http://sms.market.alicloudapi.com";
		final String path = "/singleSendSms";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appCode);
		Map<String, String > querys = new HashMap<String, String>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", name);
		jsonObj.put("msg", code);
		querys.put("ParamString", jsonObj.toJSONString());
		querys.put("RecNum", phoneNum);
		querys.put("SignName", SignName);
		querys.put("TemplateCode", TemplateCode);
		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doGet(host, path, methodGet, headers, querys);
			System.out.println(response);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			log.error("发送失败：" + e);
			return 1;
		}
		return 0;
	}

	/**
	 * {
	 "inputs": [
	 {
	 "image": {
	 "dataType": 50,
	 "dataValue": "base64_image_string(#括号内为描述，不需上传，图片以base64编码的string)"
	 },
	 "configure": {
	 "dataType": 50,
	 "dataValue": "{"side":"face(#括号内为描述，不需上传，身份证正反面类型:face/back)"}"
	 }
	 }
	 ]
	 }
	 * @param args
	 */
	public static void idCard(String[] args) {
		String host = "https://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appCode);
		Map<String, String> querys = new HashMap<String, String>();
		String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\"base64_image_string(#括号内为描述，不需上传，图片以base64编码的string)\"},\"configure\":{\"dataType\":50,\"dataValue\":\"{\\\"side\\\":\\\"face(#括号内为描述，不需上传，身份证正反面类型:face/back)\\\"}\"}}]}";
		try {
			HttpResponse response = HttpUtils.doPost(host, path, methodPost, headers, querys, bodys);
			System.out.println(response.toString());
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getCity(String[] args) {
		String host = "http://jisutqybmf.market.alicloudapi.com";
		String path = "/weather/city";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appCode);
		Map<String, String> querys = new HashMap<String, String>();


		try {
			HttpResponse response = HttpUtils.doGet(host, path, methodGet, headers, querys);
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getWeather(String[] args) {
		String host = "http://jisutqybmf.market.alicloudapi.com";
		String path = "/weather/query";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appCode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("city", "安顺");
		querys.put("citycode", "citycode");
		querys.put("cityid", "cityid");


		try {
			HttpResponse response = HttpUtils.doGet(host, path, methodGet, headers, querys);
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getWeatherByIP(String ip) {
		String host = "http://saweather.market.alicloudapi.com";
		String path = "/ip-to-weather";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appCode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("ip", ip);
		querys.put("need3HourForcast", "0");
		querys.put("needAlarm", "0");
		querys.put("needHourData", "0");
		querys.put("needIndex", "0");
		querys.put("needMoreDay", "0");
		try {
			HttpResponse response = HttpUtils.doGet(host, path, methodGet, headers, querys);
//			System.out.println(response.toString());
			//获取response的body
			return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
//			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}

	}

	public static void main(String[] args) {
		sendVerificationCode("懒人科技", "SMS_34305396", "18368093869", "54385", "吴华辉");
	}
}
