package top.annwz.base.sys;

import java.util.ResourceBundle;

/**
 * Created by Wuhuahui on 2016/12/13.
 */
public class Constants {
	public static final ResourceBundle rbn = ResourceBundle.getBundle("service-config");

	//email
	public final static String EMAIL_HOST = rbn.getString("email.host");
	public final static String EMAIL_SENDER = rbn.getString("email.sender");
	public final static String EMAIL_USERNAME = rbn.getString("email.username");
	public final static String EMAIL_PASSWORD = rbn.getString("email.password");
	public final static String EMAIL_URL = rbn.getString("email.url");

	//token
	public final  static String TOKEN_KEY = rbn.getString("token.key");

	//faceUrl
	public final static String USER_FACEURL = rbn.getString("user.faceUrl");

}
