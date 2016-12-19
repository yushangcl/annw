package top.annwz.base.exception;

/**
 * 1** 参数类错误<br/>
 * 2**<br/>
 * 3** 计算逻辑类错误<br/>
 * 4**<br/>
 * 5**<br/>
 *
 * @author huangzhigang <br/>
 */
public enum ErrorCode {

    /**
     * 必要参数丢失
     */
    RequiredArgumentsMissing(105, "Missing required arguments"),

    /**
     * 参数不合法
     */
    InvalidArguments(110, "Invalid arguments"),

    /**
     * 方法不存在
     */
    MethodNotExist(140, "No Such Method"),

    /**
     * 计算签名错误
     */
    SignError(302, "Check sign error"),

    /**
     * 解析数据发生异常
     */
    ParseException(304, "Parse Data Exception"),

    /**
     * 检查权限失败
     */
    AccessUnauthorized(330, "Access Unauthorized"),

    ApiCallError(540, "Api Call Error"),

    HostException(700, "Host Process Exception"),;


    private int code;
    private String text;

    private ErrorCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getErrorCode() {
        return this.code;
    }

    public String getErrorText() {
        return this.text;
    }

    public String toString() {
        return String.valueOf(getErrorCode()) + ":" + getErrorText();
    }

    public static ErrorCode valueOf(int code) {
        for (ErrorCode err : ErrorCode.values()) {
            if (err.getErrorCode() == code) {
                return err;
            }
        }
        return null;
    }
}
