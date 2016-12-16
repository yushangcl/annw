package top.annwz.base.uitl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class BaiduApiTest {
	@Test
	public void getIpInfo() throws Exception {
		Object obj = BaiduApi.getIpInfo("115.236.172.146");
		System.out.println(obj);
	}

	@Test
	public void getWeatherInfo() throws Exception {
		Object obj = BaiduApi.getWeatherInfo("hangzhou");
		System.out.println(obj);
	}

}