package top.annwz.base.uitl;

import org.apache.http.HttpResponse;
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

	private static final String method = "GET";
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
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("msg", code);
		querys.put("ParamString", map.toString());
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
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			System.out.println(response);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			log.error("发送失败：" + e);
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) {
		sendVerificationCode("测试", "SMS_34370181", "18268006317", "123456", "whh");
	}
}
