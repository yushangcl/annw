package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import top.annwz.base.mybatis.FieldUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuchun on 16/3/8.
 */
@JsonDeserialize(using = PageParameterDeserializer.class)
public class PageParameter extends HashMap<String, Object> {
	private static final long serialVersionUID = 5347238968261381701L;
	
	private String gridFilter = "";
	private String gridWhere = "";
	private String gridFind = "";
	private String condition = "";
	private boolean pagination = false;// 分页
	private String sortBy = "1&asc";
	private int startRow = 1;
	private int endRow = 99999999;
	private int usehis = 0;
	private boolean useCount = false;

	private int totalRows = 0;

	// public PageParameter(String filter, String where, String find, String
	// sort, boolean pagination) {
	// if(StringUtils.isNotEmpty(sort)) {
	// this.sortBy = sort;
	// }
	//
	// super.put("sort", "#orderby#");
	// super.put("where", "#where#");\
	// this.gridFilter = filter;
	// this.gridFind = find;
	// this.gridWhere = where;
	// this.pagination = pagination;
	// }

	/**
	 * @param filter
	 *            过滤条件
	 * @param sort
	 *            排序
	 * @param pagination
	 *            分页
	 * @param dataRole
	 *            数据角色
	 * @param userId
	 *            用户ID
	 * @throws Exception
	 */
	public PageParameter(String filter, String sort, boolean pagination,
                         String dataRole, String userId) throws Exception {
		if (StringUtils.isEmpty(dataRole) || StringUtils.isEmpty(userId)) {
			throw new Exception("dataRule or userId is null");
		}
		if (StringUtils.isNotEmpty(sort)) {
			this.sortBy = sort;
		}

		super.put("sort", "#orderby#");
		super.put("where", "#where#");
		super.put("dataRole", dataRole);
		super.put("userId", userId);
		this.gridFilter = filter;
		this.pagination = pagination;
	}

	public PageParameter() {
		super.put("sort", "#orderby#");
		super.put("where", "#where#");
	}

	// appName=compare:equal;value:淘宝;datatype:string&platformId=compare:equal;value:10;datatype:code
	public void setGridFilter(String gridFilter) {
		this.gridFilter = gridFilter;
	}

	public PageParameter(String condition) {
		super.put("sort", "#orderby#");
		super.put("where", "#where#");
		this.condition = condition.trim();
	}

	public void setCondition(String condition) {
		this.condition = condition.trim();
	}

	public void setSort(String column, SortType type) {
		if (StringUtils.isNotEmpty(column)) {
			this.sortBy = column + "&" + type.toString().toLowerCase();
		}

	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
		this.pagination = true;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
		this.pagination = true;
	}

	public void setRowRange(int startRow, int endRow) {
		this.startRow = startRow;
		this.endRow = endRow;
		this.pagination = true;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public int getEndRow() {
		return this.endRow;
	}

	public String getGridFilter() {
		return this.gridFilter;
	}

	public String getGridWhere() {
		return this.gridWhere;
	}

	public String getGridFind() {
		return this.gridFind;
	}

	public String getCondition() {
		return this.condition;
	}

	public String getSort() {
		return this.sortBy;
	}

	public boolean isPagination() {
		return this.pagination;
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(int rows) {
		this.totalRows = rows;
	}

	public final void setParameter(String name, Object value) throws Exception {
		if (!"startRow".equals(name) && !"endRow".equals(name)
				&& !"sort".equals(name) && !"where".equals(name)) {
			super.put(name, value);
		} else {
			throw new Exception("Can not set this parameter name:" + name);
		}
	}

	public void clear() {
		super.clear();
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public final Object put(String key, Object value) {
		return null;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public final void putAll(Map<? extends String, ? extends Object> m) {
	}

	public Object remove(Object key) {
		return super.remove(key);
	}

	public int getUsehis() {
		return this.usehis;
	}

	public void setUsehis(int usehis) {
		this.usehis = usehis;
	}

	public boolean isUseCount() {
		return useCount;
	}

	public void setUseCount(boolean useCount) {
		this.useCount = useCount;
	}
	public String encodedDataTime(String dataTime){
		String[] s=dataTime.split("\\s+");
		if(s.length==1){return dataTime;}
		dataTime="";
		dataTime=s[0]+" "+ FieldUtil.escape(s[1]);
		return dataTime;
	}
	public PageParameter(String queryData, String dataRole, String userId)
			throws Exception {
		// if (StringUtils.isEmpty(dataRole) || StringUtils.isEmpty(userId)) {
		// throw new Exception("dataRule or userId is null");
		// }
		JSONObject querystr = JSONObject.parseObject(queryData);
		int pageNum = querystr.getIntValue("currentPage");
		int pageSize = querystr.getIntValue("pageSize");
		String sort = querystr.getString("sort");
		JSONArray arr = querystr.getJSONArray("filter");
		StringBuffer query = new StringBuffer();
		if (arr != null && arr.size() != 0) {
			for (int i = 0; i < arr.size(); i++) {
				try {
					String isMultiLine = (arr.size() - 1 == i) ? "" : "&";
					JSONObject filter = arr.getJSONObject(i);
					String field = filter.getString("field");
					String compare = filter.getString("compare");
					String dataType = filter.getString("datatype");
					if (compare.equals("between")) {
						String minValue=encodedDataTime(filter.getString("minvalue"));
						String maxValue=encodedDataTime(filter.getString("maxvalue"));
						query.append(field + "=compare:" + compare + ";minvalue:"
								+ minValue +";maxvalue:"+maxValue +";datatype:" + dataType + isMultiLine);
					} else {
						String value = filter.getString("value");
						query.append(field + "=compare:" + compare + ";value:"
								+ value + ";datatype:" + dataType + isMultiLine);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		super.put("sort", "#orderby#");
		super.put("where", "#where#");
		super.put("dataRole", dataRole);
		super.put("userId", userId);
		this.startRow = (pageNum - 1) * pageSize + 1;
		this.endRow = startRow + pageSize - 1;
		this.pagination = true;
		this.useCount = true;
		if (StringUtils.isNotEmpty(sort)) {
			this.sortBy = sort;
		}
		this.gridFilter = query.toString();

	}
	
	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		Object value = super.get(key);
		if (value == null) {
			return null;
		}
		return value.toString();
	}
}
