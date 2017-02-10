package top.annwz.base.mybatis;

import java.util.ResourceBundle;

public class DialectUtil {
    public static Dialect getDialect() {
        final ResourceBundle rbn = ResourceBundle.getBundle("database");

        String dialectType = rbn.getString("database.dialect").toLowerCase();
        if ("oracle".equals(dialectType)) {
            return new DialectOracle();
//		} else if ("mysql".equals(dialectType)) {
//			return new DialectMySql();
        } else {
            return null;
        }
    }
}
