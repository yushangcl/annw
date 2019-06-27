package top.annwz.base.uitl.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Wuhuahui on 2017/1/3.
 */
public class JuHeApiTest {
	@Test
	public void getRequest() throws Exception {
		JuHeApi.getRequest("520324198901154695", "json");
	}

}