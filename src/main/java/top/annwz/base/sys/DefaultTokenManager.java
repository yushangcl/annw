package top.annwz.base.sys;

import top.annwz.base.uitl.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token 创建和校验
 *
 * Created by Wuhuahui on 2016/12/19.
 */
public class DefaultTokenManager implements TokenManager {

	private static Map<String, String> tokenMap = new ConcurrentHashMap<>();

	@Override
	public String createToken(String username) {
		String token = UUID.randomUUID().toString();
		tokenMap.put(token, username);
		return token;
	}

	@Override
	public boolean checkToken(String token) {
		return !StringUtils.isEmpty(token) && tokenMap.containsKey(token);
	}
}