package top.annwz.base.uitl.api;

import com.alibaba.fastjson.JSONObject;
import top.annwz.base.sys.Constants;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class BaiduApi {


	/**
	 * 查询 ip地址信息
	 * @param ip
	 * @return
	 *
	 *
	 * {
	 * "errNum": 0,
	 * "errMsg": "success",
	 * "retData": {
	 * 		"ip": "117.89.35.58", //IP地址
	 * 		"country": "中国", //国家
	 * 		"province": "江苏", //省份 国外的默认值为none
	 * 		"city": "南京", //城市  国外的默认值为none
	 *	    "district": "鼓楼",// 地区 国外的默认值为none
	 *      "carrier": "中国电信" //运营商  特殊IP显示为未知
	 * 		}
	 * }
	 */
	public static JSONObject getIpInfo(String ip) {
		String httpUrl = "http://apis.baidu.com/apistore/iplookupservice/iplookup";
		String httpArg = "ip=" + ip;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 *
	 * @param mcc MCC国家代码
	 * @param mnc MNC运营商代码
	 * @param lac LAC小区代码
	 * @param ci CELLID基站代码
	 * @param coord 坐标系统，wgs84/gcj02/bd09
	 * @return {"errcode":0, "lat":"40.004880", "lon":"116.482670", "radius":"202", "address":"北京市朝阳区望京开发街道望京国际研发园D座;望京东路与溪阳东路路口东北110米"}
	 */
	public static JSONObject getIpInfo(String mcc, String mnc, String lac, String ci, String coord) {
		String httpUrl = "http://apis.baidu.com/lbs_repository/cell/query";
		String httpArg = "mcc="+ mcc + "&mnc=" + mnc + "&lac=" + lac + "&ci=" + ci + "&coord=" + coord;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * 查询城市的天气信息
	 * @param cityPinyin 拼音
	 * @return
	 * {
	 * errNum: 0,
	 * errMsg: "success",
	 * retData: {
	 *	 city: "北京", //城市
	 *	 pinyin: "beijing", //城市拼音
	 *	 citycode: "101010100",  //城市编码
	 *	 date: "15-02-11", //日期
	 *	 time: "11:00", //发布时间
	 *	 postCode: "100000", //邮编
	 *	 longitude: 116.391, //经度
	 *	 latitude: 39.904, //维度
	 *	 altitude: "33", //海拔
	 *	 weather: "晴",  //天气情况
	 *	 temp: "10", //气温
	 *	 l_tmp: "-4", //最低气温
	 *	 h_tmp: "10", //最高气温
	 *	 WD: "无持续风向",	 //风向
	 *	 WS: "微风(<10m/h)", //风力
	 *	 sunrise: "07:12", //日出时间
	 * 	 sunset: "17:44" //日落时间
	 *	 }
	 * }
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
	 *
	 * {
	 *	"showapi_res_code": 0,
	 *	"showapi_res_error": "",
	 *	"showapi_res_body": {
	 *		"areaCode": "0871",
	 *		"city": "昆明",
	 *		"name": "中国电信",
	 *		"num": 1890871,
	 *		"postCode": "650000",
	 *		"prov": "云南",
	 *		"provCode": "530000",
	 *		"ret_code": 0,
	 *		"type": 2
	 *		}
	 * }
	 */
	public static JSONObject getPhoneAttribution(String mobile) {
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/mobile/find";
		String httpArg = "num=" + mobile;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * md5 解密
	 * @param md5
	 * @return
	 *
	 *
	 *{
	 *	"data": {
	 *		"md5": "7e73d06707f5fb75ec77cc5f2bd9bb92",
	 *		"md5_src": "programming"
	 *	},
	 *	"error": 0,
	 *	"msg": ""
	 *}
	 */
	public static JSONObject md5Dec(String md5) {
		String httpUrl = "http://apis.baidu.com/chazhao/md5decod/md5decod";
		String httpArg = "md5=" + md5;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * 获取新闻频道id
	 * @return
	 *
	 *
	{
	"showapi_res_code": 0,
	"showapi_res_error": "",
	"showapi_res_body": {
	"channelList": [
	{
	"channelId": "5572a108b3cdc86cf39001cd",
	"name": "国内焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001ce",
	"name": "国际焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001cf",
	"name": "军事焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d0",
	"name": "财经焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d1",
	"name": "互联网焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d2",
	"name": "房产焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d3",
	"name": "汽车焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d4",
	"name": "体育焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d5",
	"name": "娱乐焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d6",
	"name": "游戏焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d7",
	"name": "教育焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d8",
	"name": "女人焦点"
	},
	{
	"channelId": "5572a108b3cdc86cf39001d9",
	"name": "科技焦点"
	},
	{
	"channelId": "5572a109b3cdc86cf39001da",
	"name": "社会焦点"
	},
	{
	"channelId": "5572a109b3cdc86cf39001db",
	"name": "国内最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001dc",
	"name": "台湾最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001dd",
	"name": "港澳最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001de",
	"name": "国际最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001df",
	"name": "军事最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e0",
	"name": "财经最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e1",
	"name": "理财最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e2",
	"name": "宏观经济最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e3",
	"name": "互联网最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e4",
	"name": "房产最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e5",
	"name": "汽车最新"
	},
	{
	"channelId": "5572a109b3cdc86cf39001e6",
	"name": "体育最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001e7",
	"name": "国际足球最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001e8",
	"name": "国内足球最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001e9",
	"name": "CBA最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001ea",
	"name": "综合体育最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001eb",
	"name": "娱乐最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001ec",
	"name": "电影最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001ed",
	"name": "电视最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001ee",
	"name": "游戏最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001ef",
	"name": "教育最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001f0",
	"name": "女人最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001f1",
	"name": "美容护肤最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001f2",
	"name": "情感两性最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001f3",
	"name": "健康养生最新"
	},
	{
	"channelId": "5572a10ab3cdc86cf39001f4",
	"name": "科技最新"
	},
	{
	"channelId": "5572a10bb3cdc86cf39001f5",
	"name": "数码最新"
	},
	{
	"channelId": "5572a10bb3cdc86cf39001f6",
	"name": "电脑最新"
	},
	{
	"channelId": "5572a10bb3cdc86cf39001f7",
	"name": "科普最新"
	},
	{
	"channelId": "5572a10bb3cdc86cf39001f8",
	"name": "社会最新"
	}
	],
	"ret_code": 0,
	"totalNum": 44
	}
	}
	 */
	public static JSONObject getNewChannel() {
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/channel_news";
		String httpArg = "";
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}
	/**
	 *
	 * @param channelId 新闻频道id，必须精确匹配
	 * @param channelName 新闻频道名称，可模糊匹配
	 * @param title 新闻标题，模糊匹配
	 * @param page 页数，默认1。每页最多20条记录。
	 * @param needContent 是否需要返回正文，1为需要，其他为不需要
	 * @param needHtml 是否需要返回正文的html格式，1为需要，其他为不需要
	 * @return
	 */
	public static JSONObject getNews(String channelId, String channelName, String title,
									 String page, String needContent, String needHtml) {
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
		String httpArg = "channelId=" + channelId + "&channelName="+ channelName + "&title=" + title
				+ "&page=" + page + "&needContent=" + needContent + "&needHtml=" + needHtml;
		String jsonResult = request(httpUrl, httpArg);
		return JSONObject.parseObject(jsonResult);
	}

	/**
	 * 身份证号码查询接口
	 * @param cardNum
	 * @return
	 */
	public static JSONObject getIdCardInfo(String cardNum) {
		String httpUrl = "http://apis.baidu.com/chazhao/idcard/idcard";
		String httpArg = "idcard=" + cardNum;
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
			connection.setRequestProperty("apikey", Constants.BAIDU_APPKEY);
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
