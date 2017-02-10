package top.annwz.base.mybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import top.annwz.base.uitl.PageParameter;


public class DialectMySql implements Dialect {
    public String getMappedSql(String sql, PageParameter page) {
        return null;
    }

    public String getPageSql(String sql, int start, int end) {
        return null;
    }

    public String getCompareExpression(String column, String compare, String datatype, String values, String columnexp) {
        return null;
    }

    public String getPermissionSql(String systemCode, String sql) {
        return null;
    }

    public String getFullDeleteSql(MappedStatement ms, BoundSql sql, Object parameterObject) throws Exception {
        return null;
    }

    public Long getLoginUserId() {
        return null;
    }
}
