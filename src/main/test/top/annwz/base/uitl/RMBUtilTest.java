package top.annwz.base.uitl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Wuhuahui on 2016/12/22.
 */
public class RMBUtilTest {
	@Test
	public void numToRMBStr() throws Exception {
		String str = RMBUtil.numToRMBStr(123456789.22);
		System.out.println(str);
	}

}