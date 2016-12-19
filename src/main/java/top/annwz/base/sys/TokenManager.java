package top.annwz.base.sys;

/**
 * Created by Wuhuahui on 2016/12/19.
 */
public interface TokenManager {

	String createToken(String username);

	boolean checkToken(String token);
}