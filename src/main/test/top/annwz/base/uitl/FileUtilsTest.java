package top.annwz.base.uitl;

import org.junit.Test;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public class FileUtilsTest {
    @Test
    public void getImageStr() throws Exception {

        String base64 = FileUtils.getImageStr("C:\\Users\\Wuhuahui\\Desktop\\QQ截图20170914165251.png");
        System.out.println(base64);
    }

}