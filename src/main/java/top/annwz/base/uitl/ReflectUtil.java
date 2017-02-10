package top.annwz.base.uitl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.annwz.base.mybatis.FieldToken;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Created by xuchun on 16/3/9.
 */
public class ReflectUtil {
    private static final Log logger = LogFactory.getLog(ReflectUtil.class);

    public ReflectUtil() {
    }

    public static Object getFieldValue(Object object, String fieldName) {
        FieldToken token = new FieldToken(fieldName);
        Field field = getDeclaredField(object, token.getName());
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        } else {
            makeAccessible(field);
            Object result = null;

            try {
                result = field.get(object);
            } catch (IllegalAccessException var6) {
                logger.error("", var6);
            }

            return token.hasNext() && result != null ? getFieldValue(result, token.getChildren()) : result;
        }
    }

    public static String getFieldValueString(Object object, String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return "";
        } else {
            Object obj = getFieldValue(object, fieldName);
            return getValueString(obj);
        }
    }

    public static String getFieldValueString(Object object, String fieldName, String format) {
        if (StringUtils.isEmpty(fieldName)) {
            return "";
        } else {
            Object obj = getFieldValue(object, fieldName);
            return getValueString(obj, format);
        }
    }

    public static String getValueString(Object object) {
        return object instanceof Date ? DateUtil.toDateTimeString((Date) object) : (object != null ? object.toString() : "");
    }

    public static String getValueString(Object object, String format) {
        return object instanceof Date ? DateUtil.format((Date) object, format) : (object != null ? object.toString() : "");
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        FieldToken token = new FieldToken(fieldName);
        Field field = getDeclaredField(object, token.getName());
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        } else {
            makeAccessible(field);
            if (token.hasNext()) {
                try {
                    Object e = field.get(object);
                    setFieldValue(e, token.getChildren(), value);
                } catch (IllegalAccessException var7) {
                    logger.error("", var7);
                }
            } else {
                try {
                    field.set(object, value);
                } catch (IllegalAccessException var6) {
                    logger.error("", var6);
                }
            }

        }
    }

    public static Field getDeclaredField(Object object, String fieldName) {
        Class superClass = object.getClass();

        while (superClass != Object.class) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    private static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }

    }
}
