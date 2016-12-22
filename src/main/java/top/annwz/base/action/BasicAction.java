package top.annwz.base.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import top.annwz.base.uitl.PageData;
import top.annwz.base.uitl.UuidUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
public class BasicAction {
	protected transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		return request;
	}

	/**
	 * 得到32位的uuid
	 *
	 * @return
	 */
	public String get32UUID() {

		return UuidUtil.get32UUID();
	}

//	/**
//	 * 得到分页列表的信息
//	 */
//	public Page getPage(){
//
//		return new Page();
//	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}
}
