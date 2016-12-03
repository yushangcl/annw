package top.annwz.base.uitl;

import org.apache.commons.lang.math.NumberUtils;

import java.util.Map;

/**
 * 数据转换工具
 * 
 *
 * 
 */
public class Converter {

	/**
	 * 将Integer转换为Long
	 * 
	 * @param value
	 * @return
	 */
	public static Long toLong(Integer value) {
		return value == null ? 0L : Long.valueOf(value);
	}
	
	/**
	 * 将object转换为Long <br/>
	 * 	如果object为null, 返回null
	 * @param value
	 * @return
	 */
	public static Long toLong(Object value) {
		return value == null ? null : toLong(value.toString());
	}

	/**
	 * 将String转换为Long
	 * 
	 * @param value
	 * @return
	 */
	public static Long toLong(String value) {
		return NumberUtils.toLong(value);
	}

	/**
	 * 将double转换为long
	 * 
	 * @param value
	 * @return
	 */
	public static long tolong(double value) {
		return (long) value;
	}

	/**
	 * 将String转换为int
	 * 
	 * @param value
	 * @return
	 */
	public static int toint(String value) {
		return NumberUtils.toInt(value);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static int toint(double value) {
		return (int) value;
	}
	
	/**
	 * 将object转换为Integer <br/>
	 * 	如果object为null, 返回null
	 * @param value
	 * @return
	 */
	public static Integer toint(Object value) {
		return value == null ? null : toint(value.toString());
	}

	/**
	 * 将String转换为Double
	 * 
	 * @param value
	 * @return
	 */
	public static double toDouble(String value) {
		return NumberUtils.toDouble(value);
	}
	
	/**
	 * 将object转换为Double <br/>
	 * 	如果object为null, 返回null
	 * @param value
	 * @return
	 */
	public static Double toDouble(Object value) {
		return value == null ? null : toDouble(value.toString());
	}

	/**
	 * 将String转换为float
	 * 
	 * @param value
	 * @return
	 */
	public static float toFloat(String value) {
		return NumberUtils.toFloat(value);
	}
	
	/**
	 * 将object转换为Float <br/>
	 * 	如果object为null, 返回null
	 * @param value
	 * @return
	 */
	public static Float toFloat(Object value) {
		return value == null ? null : toFloat(value.toString());
	}

	/**
	 * 将String转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static byte toByte(String value) {
		return NumberUtils.toByte(value);
	}

	/**
	 * 将String转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static byte toShort(String value) {
		return NumberUtils.toByte(value);
	}
	
	/**
	 * 将object转换为Float <br/>
	 * 	如果object为null, 返回null
	 * @param value
	 * @return
	 */
	public static Byte toShort(Object value) {
		return value == null ? null : toShort(value.toString());
	}

	public static String getString(Map<String, Object> params, String key) {
		Object value = params.get(key);
		return value == null ? null : value.toString();
	}
}
