package top.annwz.base.mybatis;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import top.annwz.base.uitl.DateUtil;
import top.annwz.base.uitl.PageParameter;
import top.annwz.base.uitl.StringUtils;

import java.io.StringReader;
import java.util.*;


public class DialectOracle implements Dialect {
	private static Log log = LogFactory.getLog(DialectOracle.class);
	private static CCJSqlParserManager csql = new CCJSqlParserManager();

	public String getMappedSql(String sql, PageParameter page) {
		sql = sql.trim();

		//判断是否有条件
		if (sql.indexOf("#where#") < 0 && sql.indexOf("#orderby#") < 0) {
			return sql;
		}
		// 分析sql显示列
		Map<String, String> map = getField(sql);

		if (sql.indexOf("#where#") >= 0) {
			String where = "";
//			if (!StringUtils.isEmpty(page.getGridFind())) {
//				String find = page.getGridFind();
//				Filter filter = new Filter("filter");
//				filter.setValue(find);
//				String s = "";
//				try {
//					s = filter.getSql(map);
//				} catch (Exception e) {
//					log.warn("Parse grid find sql error", e);
//				}
//				where = s;
//			} else 
			if (!StringUtils.isEmpty(page.getGridFilter())) {
				String filter = page.getGridFilter();
				List<String> list = new ArrayList<String>();

				String[] filters = StringUtils.split(filter, "&"); // 拆分多个字段
				for (int i = 0; i < filters.length; i++) {
					String f = filters[i];
					String col = StringUtils.split(f, "=")[0]; // 列名
					String[] vals = StringUtils.split(f, "=")[1].split(";");
					String compare = "";
					String datatype = "";
					List<String> values = new ArrayList<String>();
					for (int j = 0; j < vals.length; j++) {
						String val = vals[j];
						String v = StringUtils.split(val, ":")[0];
						if ("compare".equals(v)) {
							compare = StringUtils.split(val, ":")[1];
						} else if ("relvalue".equals(v) || "value".equals(v)) {
							values.add(FieldUtil.unescape(StringUtils.split(val, ":")[1]));
						} else if ("values".equals(v)) {
							String[] vs = FieldUtil.unescape(StringUtils.split(val, ":")[1]).split(",");
							for (int k = 0; k < vs.length; k++) {
								values.add(vs[k]);
							}
						} else if ("minvalue".equals(v)) {
							values.add(FieldUtil.unescape(StringUtils.split(val, ":")[1]));
						} else if ("maxvalue".equals(v)) {
							values.add(FieldUtil.unescape(StringUtils.split(val, ":")[1]));
						} else if ("datatype".equals(v)) {
							datatype = FieldUtil.unescape(StringUtils.split(val, ":")[1]);
						}
					}
					col = FieldUtil.camelToUnderline(col);
					if (map.containsKey(col)) {
						col = map.get(col);
					}
					list.add(this.getCompareExpression(col, compare, datatype, StringUtils.joinString(values, ","), ""));
				}
				if (list.size() > 0) {
					where = StringUtils.joinString(list, "and");
				}
			}
			if (!StringUtils.isEmpty(page.getGridWhere())) {
				String str = page.getGridWhere();
				List<String> list = new ArrayList<String>();

				String[] strs = StringUtils.split(str, "&"); // 拆分多个字段
				for (int i = 0; i < strs.length; i++) {
					String f = strs[i];
					String col = StringUtils.split(f, "=")[0]; // 列名
					String[] vals = StringUtils.split(f, "=")[1].split(":");
					String compare = vals[0];
					String value = FieldUtil.unescape(vals[1]);
					String datatype = vals[2];
					col = FieldUtil.camelToUnderline(col);
					if (map.containsKey(col)) {
						col = map.get(col);
					}
					list.add(this.getCompareExpression(col, compare, datatype, value, ""));
				}
				if (list.size() > 0) {
					if (StringUtils.isEmpty(where)) {
						where = StringUtils.joinString(list, "and");
					} else {
						where = where + " and " + StringUtils.joinString(list, "and");
					}
				}
			}
			if (StringUtils.isEmpty(where)) {
				where = " 1=1 ";
			}
			if (StringUtils.isNotEmpty(page.getCondition())) {
				where = where + " and (" + page.getCondition() + " ) ";
			}
			sql = sql.replace("#where#", where);
		}
		if (sql.indexOf("#orderby#") >= 0) {
			String sort = page.getSort().trim();
			String name = StringUtils.split(sort, "&")[0];
			name = FieldUtil.camelToUnderline(name);
			if (map.containsKey(name)) {
				name = map.get(name);
			}
			sql = sql.replace("#orderby#", " " + name + " " + StringUtils.split(sort, "&")[1] + " ");
		}
		return sql;
	}

	public String getPageSql(String sql, int start, int end) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (start > 1) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (start > 1) {
			pagingSelect.append(" ) row_ where rownum <= " + String.valueOf(end) + ") where rownum_ >= " + String.valueOf(start));
		} else {
			pagingSelect.append(" ) where rownum <= " + String.valueOf(end));
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

	public String getFullDeleteSql(MappedStatement ms, BoundSql boundSql, Object parameterObject) throws Exception {
		String sql = boundSql.getSql();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		if (parameterMappings != null) {
			Configuration configuration = ms.getConfiguration();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

			ParameterMapping parameterMapping = null;
			Object value = null;
			String propertyName = null;
			for (int i = 0; i < parameterMappings.size(); i++) {
				parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					propertyName = parameterMapping.getProperty();

					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(
									propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}

					String type = parameterMapping.getJavaType().getSimpleName();
					String v = "";
					if ("String".equals(type)) {
						v = "'" + value.toString() + "'";
					} else if ("Long".equals(type) || "Float".equals(type) || "Integer".equals(type) || "Double".equals(type)) {
						v = value.toString();
					} else if ("Date".equals(type)) {
						v = "to_date('" + DateUtil.toDateTimeString((Date) value) + "','yyyy-mm-dd hh24:mi:ss')";
					} else {
						throw new Exception("The Datatype is not supported");
					}
					if (sql.indexOf("?") > 0) {
						sql = sql.replaceFirst("\\?", v);
					}
				}
			}
		}
		return sql;
	}

	//	public String getPermissionSql(String systemCode, String sql) {
//		if (sql.indexOf("p_get_next_number")>=0) {
//			return sql;
//		} else if (sql.trim().startsWith("{call")) {
//			return sql;
//		}
//		//777888
//		//log.debug("begin permission sql:" + sql);
//		CCJSqlParserManager c = new CCJSqlParserManager();
//		try {
//			Statement statement = c.parse(new StringReader(sql));
//			SelectBody body = ((Select)statement).getSelectBody();
//			SelectBody permissionBody = getPermissionBody(body,systemCode);
//			((Select)statement).setSelectBody(permissionBody);
//			sql = statement.toString();
//			if(sql.indexOf(Constants.authL)!=-1){
//			  sql = sql.replace(Constants.authL, "select  bu_id from sy_authorize_data where  user_id="+this.getLoginUserId());
//			} 
//		} catch (Exception e) {
//            //		log.error("getPermissionSql error: " + sql, e);
//			//TODO JSqlParser Error
//		}
//		//log.debug("after permission sql:" + sql);
//		return sql;
//	}
//	aa
	public static String removeOrderby(String sql) {
		try {
			Statement statement = csql.parse(new StringReader(sql));
			SelectBody body = ((Select) statement).getSelectBody();
			((PlainSelect) body).setOrderByElements(null);
			((Select) statement).setSelectBody(body);
			return statement.toString();
		} catch (Exception ex) {
			return sql;
		}

	}

//	private Expression getPermissionExp(Expression oldExp,Table table, String systemCode) {
//		String tableName = table.getName().toUpperCase();
//		
//		if (tableName.startsWith("\"") && tableName.endsWith("\"")) {
//			tableName = tableName.replace("\"", "");
//		}
//		
//		if (AuthorizeCache.accessTable(systemCode, tableName)) {
//			Long userId = this.getLoginUserId();
//			if (userId != null && userId > 0) {
//				String accessType = AuthorizeCache.getAccessType(userId);
//				List<SyTableAccess> list = AuthorizeCache.getAccessTable(systemCode, tableName, userId);
//				if (list != null && list.size() > 0) { //需要权限
//					Parenthesis exp = new Parenthesis();
//					for (int i=0;i < list.size();i++) {
//						SyTableAccess access = list.get(i);
//						Column col = new Column();
//						col.setColumnName(access.getColumnName());
//						Table t = new Table();
//						if (StringUtils.isNotEmpty(table.getAlias())) {
//							t.setName(table.getAlias());
//						} else {
//							t.setName(table.getName());
//						}
//						col.setTable(t);
//						
//						ExpressionList items = new ExpressionList();
//						List<Long> datas = AuthorizeCache.getAuthorizeData(userId);
//						items.setExpressions(datas);
//						InExpression auth = new InExpression();
//						auth.setLeftExpression(col);
//						auth.setItemsList(items);
//						
//						if (exp.getExpression() == null) {
//							exp.setExpression(auth);
//						} else if ("G".equals(accessType)) {
//							AndExpression expTemp = new AndExpression(auth,exp.getExpression());
//							exp.setExpression(expTemp);
//						} else {
//							OrExpression expTemp = new OrExpression(auth,exp.getExpression());
//							exp.setExpression(expTemp);
//						}
//					}
//					if (oldExp == null) {
//						oldExp = exp;
//					} else {
//						Parenthesis parenthesisExp = new Parenthesis(oldExp);
//						AndExpression expTemp = new AndExpression(parenthesisExp,exp);
//						oldExp = expTemp;
//					}
//				}
//			}
//		}
//		return oldExp;
//	}

//	private SelectBody getPermissionBody(SelectBody body, String systemCode) throws Exception {
//		if (body instanceof PlainSelect) {
//			FromItem fromItem = ((PlainSelect)body).getFromItem();
//			if (fromItem instanceof SubSelect) {
//				SelectBody subbody = ((SubSelect)fromItem).getSelectBody();
//				((SubSelect)fromItem).setSelectBody(getPermissionBody(subbody, systemCode));
//				((PlainSelect)body).setFromItem(fromItem);
//			} else if (fromItem instanceof SubJoin) {
//				throw new Exception("SubJoin");
//			} else if (fromItem instanceof Table) {
//				Expression exp = (Expression)((PlainSelect)body).getWhere();
//				processExpression(exp, systemCode);
//				
//				exp = this.getPermissionExp(exp, (Table)fromItem, systemCode);
//				((PlainSelect)body).setWhere(exp);
//				
//				List<Join> joins = ((PlainSelect)body).getJoins();
//				if (joins != null) {
//					for(int i=0;i < joins.size();i++) {
//						Join  join = joins.get(i);
//						FromItem rightItem = join.getRightItem();
//						if (rightItem instanceof SubSelect) {
//							SelectBody subbody = ((SubSelect)rightItem).getSelectBody();
//							((SubSelect)rightItem).setSelectBody(getPermissionBody(subbody, systemCode));
//							join.setRightItem(rightItem);
//							joins.set(i, join);
//						} else if (fromItem instanceof SubJoin) {
//							throw new Exception("SubJoin");
//						} else if (fromItem instanceof Table) {
//							if (join.isLeft()) {
//								exp = join.getOnExpression();
//								exp = this.getPermissionExp(exp, (Table)rightItem, systemCode);
//								join.setOnExpression(exp);
//								joins.set(i, join);
//							} else {
//								exp = (Expression)((PlainSelect)body).getWhere();
//								exp = this.getPermissionExp(exp, (Table)rightItem, systemCode);
//								((PlainSelect)body).setWhere(exp);
//							}
//						}
//					}
//					((PlainSelect)body).setJoins(joins);
//				}
//			}
//		} else if (body instanceof Union) {
//			List<PlainSelect> unions = ((Union)body).getPlainSelects();
//			if (unions != null) {
//				for(int i=0;i < unions.size();i++) {
//					PlainSelect union = unions.get(i);
//					union = (PlainSelect)getPermissionBody(union, systemCode);
//					unions.set(i, union);
//				}
//				((Union)body).setPlainSelects(unions);
//			}
//		}
//		return body;
//	}

//	private void processExpression(Expression exp, String systemCode) throws Exception{
//		// 
//		if (exp instanceof ExistsExpression) {
//			getPermissionBody(((SubSelect)((ExistsExpression)exp).getRightExpression()).getSelectBody(), systemCode);
//		} else if (exp instanceof AndExpression) {
//			Expression l = ((AndExpression)exp).getLeftExpression();
//			if (l instanceof ExistsExpression) {
//				getPermissionBody(((SubSelect)((ExistsExpression)l).getRightExpression()).getSelectBody(), systemCode);
//			} else if (l instanceof AndExpression) {
//				processExpression(l,systemCode);
//			}
//			Expression r = ((AndExpression)exp).getRightExpression();
//			if (r instanceof ExistsExpression) {
//				getPermissionBody(((SubSelect)((ExistsExpression)r).getRightExpression()).getSelectBody(), systemCode);
//			} else if (r instanceof AndExpression) {
//				processExpression(r,systemCode);
//			}
//		} else if (exp instanceof OrExpression) {
//			Expression l = ((OrExpression)exp).getLeftExpression();
//			if (l instanceof ExistsExpression) {
//				getPermissionBody(((SubSelect)((ExistsExpression)l).getRightExpression()).getSelectBody(), systemCode);
//			}
//			Expression r = ((OrExpression)exp).getRightExpression();
//			if (r instanceof ExistsExpression) {
//				getPermissionBody(((SubSelect)((ExistsExpression)r).getRightExpression()).getSelectBody(), systemCode);
//			}
//		}
//		//exp
//	}

	public String getCompareExpression(String column, String compare, String datatype, String values, String columnexp) {
		if (StringUtils.isNotEmpty(columnexp)) {
			column = columnexp;
		}
		if ("equal".equals(compare)) {
			String value = values.trim();
			if ("null".equals(value) || "NULL".equals(value)) {
				column += " is null";
			} else if ("*".equals(value) || "**".equals(value)) {
				column += " like " + praseDataType(datatype, "%");
			} else if (value.startsWith("*") && value.endsWith("*")) {
				column += " like " + praseDataType(datatype, "%" + value.substring(1, value.length() - 1) + "%");
			} else if (value.startsWith("*")) {
				column += " like " + praseDataType(datatype, "%" + value.substring(1));
			} else if (value.endsWith("*")) {
				column += " like " + praseDataType(datatype, value.substring(0, value.length() - 1) + "%");
			} else {
				column += " = " + praseDataType(datatype, value);
			}
		} else if ("unequal".equals(compare)) {
			String value = values.trim();
			if ("null".equals(value) || "NULL".equals(value)) {
				column += " is not null";
			} else {
				column += " != " + praseDataType(datatype, value);
			}
		} else if ("greater".equals(compare)) {
			column += " > " + praseDataType(datatype, values.trim());
		} else if ("less".equals(compare)) {
			column += " < " + praseDataType(datatype, values.trim());
		} else if ("start".equals(compare)) {
			column += " like " + praseDataType(datatype, values.trim() + "%");
		} else if ("end".equals(compare)) {
			column += " like " + praseDataType(datatype, "%" + values.trim());
		} else if ("like".equals(compare)) {
			column += " like " + praseDataType(datatype, "%" + values.trim() + "%");
		} else if ("notlike".equals(compare)) {
			column += " not like " + praseDataType(datatype, "%" + values.trim() + "%");
		} else if ("in".equals(compare)) {
			List<String> list = StringUtils.splitToList(values, ",");
			String value = "";
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					value += ",";
				}
				value += praseDataType(datatype, list.get(i));
			}
			column += " in (" + value + ")";
		} else if ("between".equals(compare)) {
			List<String> list = StringUtils.splitToList(values, ",");
			column += " between " + praseDataType(datatype, list.get(0)) + " and " + praseDataType(datatype, list.get(1));
		} else if ("relative".equals(compare)) {
			column += praseRelativeDate(values.trim());
		}
		return " " + column + " ";
	}

	private String praseDataType(String datatype, String value) {
		value = praseSqlEscape(value).trim();
		if ("string".equals(datatype) || "code".equals(datatype)) {
			value = "'" + value + "'";
		} else if ("datetime".equals(datatype)) {
			value = "to_date('" + value + "','yyyy-mm-dd hh24:mi:ss')";
		} else if ("date".equals(datatype)) {
			value = "to_date('" + value + "','yyyy-mm-dd')";
		} else if ("time".equals(datatype)) {
			value = "'" + value + "'";
		}
		return value;
	}

	private String praseRelativeDate(String value) {
		value = value.substring(1);
		String type = value.substring(0, 1);  // D W M Y
		String op = value.substring(1, 2); // P F
		String d1 = "";
		String d2 = "";
		int i = value.indexOf("~");
		if ("P".equals(op)) {
			d1 = " - " + value.substring(i + 1);
			d2 = " - " + value.substring(2, i);
		} else {
			d1 = " + " + value.substring(2, i);
			d2 = " + " + value.substring(i + 1);
		}
		if ("D".equals(type)) {
			d1 = "sysdate" + d1;
			d2 = "sysdate + 1" + d2;
		} else if ("W".equals(type)) {
			d1 = "trunc(sysdate,'d') + " + d1 + " * 7";
			d2 = "trunc(sysdate,'d') + (1" + d2 + ") * 7";
		} else if ("M".equals(type)) {
			d1 = "add_months(sysdate, " + d1 + "),'mm'";
			d2 = "add_months(sysdate, 1 " + d2 + "),'mm'";
		} else if ("Y".equals(type)) {
			d1 = "add_months(sysdate, " + d1 + " * 12),'yy'";
			d2 = "add_months(sysdate, (1 " + d2 + ") * 12),'yy'";
		}

		return " between trunc(" + d1 + ") and trunc(" + d2 + ")";
	}

	private String praseSqlEscape(String value) {
		return value.replace("'", "'");
	}

	private Map<String, String> getField(String sql) {
		Map<String, String> map = new HashMap<String, String>();
		String newsql = sql.replace("#where#", "xxx=xxx").replace("#orderby#", " 1 asc ");
		CCJSqlParserManager c = new CCJSqlParserManager();
		try {
			Statement statement = c.parse(new StringReader(newsql));
			if (statement instanceof Select) {
				SelectBody body = ((Select) statement).getSelectBody();
				while (body instanceof PlainSelect) {
					Expression exp = ((PlainSelect) body).getWhere();
					if (exp != null && exp.toString().indexOf("xxx = xxx") >= 0) {
						List list = ((PlainSelect) body).getSelectItems();
						for (int i = 0; i < list.size(); i++) {
							SelectItem s = (SelectItem) list.get(i);
							if (s instanceof SelectExpressionItem) {
								Alias alias = (Alias) ((SelectExpressionItem) s).getAlias();
								Expression e1 = ((SelectExpressionItem) s).getExpression();
								if (alias != null) {
									map.put(alias.getName(), e1.toString());
								} else if (e1 instanceof Column) {
									Table t = ((Column) e1).getTable();
									if (t.getName() != null) {
										map.put(((Column) e1).getColumnName(), e1.toString());
									}
								}
							}
						}
						break;
					}
					FromItem fromItem = ((PlainSelect) body).getFromItem();
					if (fromItem instanceof SubSelect) {
						body = ((SubSelect) fromItem).getSelectBody();
					} else {
						body = null;
					}
				}
			}
		} catch (JSQLParserException e) {
			log.error("pageed sql error:" + sql, e);
		}
		return map;
	}


//	public Long getLoginUserId() {
//		String userId = "";
//		try {
//			HttpServletRequest hr = ServletActionContext.getRequest();
//			userId = (String)hr.getSession().getAttribute(Constants.sessionUserId);
//		} catch (Exception ex) {
//			//非struts访问时  catch  
//		}
//		
//	    if(StringUtils.isNumber(userId)){
//	    	return Long.valueOf(userId);
//	    } else {
//	    	return 0L;
//	    }
//	}

//	private class PermissionBody {
//		private boolean isPermission;
//		private SelectBody body;
//	}
}
