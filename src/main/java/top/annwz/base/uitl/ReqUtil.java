package top.annwz.base.uitl;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpServletRequest与 HttpServletResponse工具类
 *
 */
public class ReqUtil {

	/**
	 * 获取客户机所使用的网络端口号
	 *
	 * @param req
	 * @return
	 */
	public static int getHttpPort(HttpServletRequest req) {
		return req.getRemotePort();
	}

	/**
	 * 获取Cookie
	 *
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] lCookies = request.getCookies();
		if (lCookies == null || cookieName == null) {
			return null;
		}
		for (int i = 0; i < lCookies.length; i++) {
			Cookie lCookie = lCookies[i];
			if (lCookie.getName().equals(cookieName)) {
				return lCookie;
			}
		}
		return null;
	}

	/**
	 * 设置Cookie
	 *
	 * @param resp
	 * @param cookieName
	 * @param cookieValue
	 * @param path
	 * @param domain
	 * @param expire
	 */
	public static void setCookie(HttpServletResponse resp, String cookieName,
	                             String cookieValue, String path, String domain, int expire) {
		Cookie c = new Cookie(cookieName, cookieValue);
		c.setSecure(false);
		c.setPath(path);
		if (domain != null) {
			c.setDomain(domain);
		}
		c.setMaxAge(expire);
		resp.addCookie(c);
	}

	/**
	 * 删除Cookie
	 *
	 * @param resp
	 * @param name
	 * @param path
	 * @param domain
	 */
	public static void delCookie(HttpServletResponse resp, String name,
	                             String path, String domain) {
		setCookie(resp, name, "", path, domain, 0);
	}

	/**
	 * 获取客户端Ip
	 *
	 * @param req
	 * @return
	 */
	public static String getClientIp(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("PRoxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 打印数据至控制台
	 *
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	public static void writeData(HttpServletResponse response, String data)
			throws IOException {
		writeData(response, data, "text/html", "UTF-8");
	}

	/**
	 * 打印数据至控制台
	 *
	 * @param response
	 * @param data
	 * @param contentType
	 * @param charset
	 * @throws IOException
	 */
	public static void writeData(HttpServletResponse response, String data,
	                             String contentType, String charset) throws IOException {
		response.setContentType(contentType);
		response.setCharacterEncoding(charset);

		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(data);
			pw.flush();
		} finally {
			if (null != pw) {
				pw.close();
			}
		}
	}

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static AbsResponse<?> setErrAbs(AbsResponse<?> abs, int code, String message) {
		return abs.setResult(code, message);
	}

	public static AbsResponse<?> setSucAbs(AbsResponse<?> abs, int code, String message) {
		return abs.setResult(code, message);
	}

}
