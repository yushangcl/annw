package top.annwz.base.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.annwz.base.uitl.AbsResponse;

/**
 * Created by Wuhuahui on 2016/12/19.
 */
@ControllerAdvice
@ResponseBody
public class ActiveException {
	protected transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public AbsResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("参数解析失败", e);
		return new AbsResponse().setResult(0, "could_not_read_json");
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public AbsResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error("不支持当前请求方法", e);
		return new AbsResponse().setResult(0, "request_method_not_supported");
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public AbsResponse handleHttpMediaTypeNotSupportedException(Exception e) {
		logger.error("不支持当前媒体类型", e);
		return new AbsResponse().setResult(0, "content_type_not_supported");
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public AbsResponse handleException(Exception e) {
		logger.error("服务运行异常", e);
		return new AbsResponse().setResult(0, e.getMessage());
	}

}
