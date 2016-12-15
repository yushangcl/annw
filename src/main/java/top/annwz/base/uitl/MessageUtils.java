package top.annwz.base.uitl;

import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wuhuahui on 2016/12/15.
 */
public class MessageUtils {
	public static void main(String[] args) {
		String host = "http://sms.market.alicloudapi.com";
		String path = "/singleSendSms";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE 你自己的AppCode");
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("ParamString", "{\"no\":\"123456\"}");
		querys.put("RecNum", "RecNum");
		querys.put("SignName", "SignName");
		querys.put("TemplateCode", "TemplateCode");


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
			System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
