package top.annwz.base.service.imp;

import org.springframework.stereotype.Service;
import top.annwz.base.service.ILoginService;
import top.annwz.base.sys.Constants;
import top.annwz.base.uitl.DateUtil;
import top.annwz.base.uitl.EncryptUtil;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Service
public class LoginService implements ILoginService{

	@Override
	public String createToken(long userId) throws IOException {
		String time = DateUtil.toDateTimeString(new Date());
		String data = Constants.TOKEN_KEY + userId + time;
		return EncryptUtil.getMD5(data, "utf-8", true);
	}
}
