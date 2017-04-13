package top.annwz.base.service;

import java.io.IOException;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
public interface ILoginService {

	/**
	 * 创建token
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	String createToken(long userId) throws IOException;

}
