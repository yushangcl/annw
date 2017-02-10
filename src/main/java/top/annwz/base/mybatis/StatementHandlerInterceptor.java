package top.annwz.base.mybatis;


import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import top.annwz.base.uitl.PageParameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class StatementHandlerInterceptor implements org.apache.ibatis.plugin.Interceptor {
    private static Log log = LogFactory.getLog(StatementHandlerInterceptor.class);

    public Object intercept(Invocation invoc) throws Throwable {
        RoutingStatementHandler statement = (RoutingStatementHandler) invoc.getTarget();
        MappedStatement mapStatement = (MappedStatement) top.annwz.base.mybatis.ReflectUtil.getFieldValue(statement, "delegate.mappedStatement");
        SqlCommandType type = mapStatement.getSqlCommandType();
        DefaultParameterHandler parameterHandler = (DefaultParameterHandler) top.annwz.base.mybatis.ReflectUtil.getFieldValue(statement, "delegate.parameterHandler");
        Object parameterObject = parameterHandler.getParameterObject();
        if (parameterObject instanceof PageParameter) {
            BoundSql boundSql = statement.getBoundSql();
            PageParameter page = (PageParameter) parameterObject;
            String sql = boundSql.getSql();
            Dialect dialect = new DialectOracle();
            sql = dialect.getMappedSql(sql, page);
			if (log.isDebugEnabled()) {
				log.debug(sql);
			}
            //是否统计总行数
            if (page.isUseCount()) {
                //取得总行数
                String countSql = "select count(1) from (" + DialectOracle.removeOrderby(sql) + ") t";
                Connection connection = mapStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
                PreparedStatement countStmt = connection.prepareStatement(countSql);
                BoundSql countBS = new BoundSql(mapStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
                setParameters(countStmt, mapStatement, countBS, parameterObject);
                ResultSet rs = countStmt.executeQuery();
                if (rs.next()) {
                    int total = rs.getInt(1);
                    page.setTotalRows(total);
                }
                rs.close();
                countStmt.close();
                connection.close();
                
				if (log.isDebugEnabled()) {
					log.debug(countSql);
				}
            }

            //添加分页;
            if (page.isPagination()) {
                sql = dialect.getPageSql(sql, page.getStartRow(), page.getEndRow());
            }
            if (log.isDebugEnabled()) {
				log.debug(sql);
			}
            top.annwz.base.mybatis.ReflectUtil.setFieldValue(boundSql, "sql", sql);
        }

        return invoc.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties arg0) {

    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(
                                    propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
                                + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }
}
