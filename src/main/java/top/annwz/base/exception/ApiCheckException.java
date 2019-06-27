package top.annwz.base.exception;

/**
 * Created by huahui.wu on 2017/2/22.
 */
public class ApiCheckException extends annwException {

	private static final long serialVersionUID = 3924562301L;

	public ApiCheckException() {
		super();
	}

	public ApiCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiCheckException(String message) {
		super(message);
	}

	public ApiCheckException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}

}
