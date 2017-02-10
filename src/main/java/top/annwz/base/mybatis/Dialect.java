package top.annwz.base.mybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import top.annwz.base.uitl.PageParameter;


public interface Dialect {
    /**
     * 查询条件映射
     *
     * @param sql
     * @param page
     * @return
     */
    public String getMappedSql(String sql, PageParameter page);

    /**
     * 生成分页语句
     *
     * @param sql
     * @param start
     * @param end
     * @return
     */
    public String getPageSql(String sql, int start, int end);


    /**
     * 生成比较运算表达式
     *
     * @param column:    列名
     * @param compare:   比较符
     * @param datatype:  数据类型 string,number,date,code
     * @param values:    比较值,
     * @param columnexp: 列表达式
     * @return
     */
    public String getCompareExpression(String column, String compare, String datatype, String values, String columnexp);

    /**
     * 生成权限
     *
     * @param sql
     * @return
     */
//	public String getPermissionSql(String systemCode, String sql);
    public String getFullDeleteSql(MappedStatement ms, BoundSql sql, Object parameterObject) throws Exception;

//	public Long getLoginUserId();
}
