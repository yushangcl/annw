package top.annwz.base.action;

import org.junit.Test;
import top.annwz.base.uitl.FileUtils;

/**
 * Created by Wuhuahui on 2016/12/16.
 */
public class Base64Test {
	@Test
	public void getImageStr() {
		String str = FileUtils.getImageStr("D:/12591426.jpg");
		System.out.println(str);
	}

	@Test
	public void generateImage() {
		String str = FileUtils.getImageStr("D:/12591426.jpg");
		FileUtils.generateImage(str, "D:/123.jpg");
	}
}
