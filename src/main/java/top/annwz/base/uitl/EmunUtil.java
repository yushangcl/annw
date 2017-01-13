package top.annwz.base.uitl;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by TJF on 2016/12/23 0023.
 */
public class EmunUtil {

    private static BufferedWriter out;

    private static String[] filterKeys = {"TIME_UNIT"};

    /**
     * 所生成文件需要注意的地方：
     * code数据：
     * 如果为数字，则带有NUMBER前缀
     * 如果为负数，则带有NEGATIVE前缀
     * 如果类型为时间单位，则没有做大小写转换
     *
     * @param packagePath
     * @param fileName
     */
    public static void createEmunFile(String packagePath, String fileName) {
        JdbcUtil jdbc = new JdbcUtil();
        String filePath = System.getProperty("user.dir") + "/src/main/java/" + packagePath.replaceAll("\\.", "/") + "/" + handClassName(fileName) + ".java";
        File file = new File(filePath);
        try {

            ResultSet rs = jdbc.executeQuery(
                    "select t.defined_code_type, t.defined_code, t.defined_name\n" +
                            "from basic_owner.BA_DEFINED_CODE t order by defined_code_type");
            out = new BufferedWriter(new FileWriter(file));
            initFile(packagePath, fileName);
            createEmunFile(rs);
            out.write("}\n");
            out.flush();
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createEmunFile(ResultSet rs) throws IOException, SQLException {
        Map<String, List<JSONObject>> map = new HashMap<String, List<JSONObject>>();
        while (rs.next()) {
            String defined_code_type = rs.getString("DEFINED_CODE_TYPE");
            JSONObject obj = new JSONObject();
            obj.put("DEFINED_CODE", rs.getString("DEFINED_CODE"));
            obj.put("DEFINED_NAME", rs.getString("DEFINED_NAME"));
            if (map.containsKey(defined_code_type)) {
                map.get(defined_code_type).add(obj);
            } else {
                //修改 文件
                createEmunFile(map);
                //重新循环
                List<JSONObject> list = new ArrayList<JSONObject>();
                list.add(obj);
                map.put(defined_code_type, list);
            }
        }

    }

    private static String returnSpace = "   ";
    private static String enumSpace = "        ";
    private static String methodSpace = "              ";

    private static void createEmunFile(Map<String, List<JSONObject>> map) throws IOException {
        if (!map.isEmpty()) {
            String key = map.keySet().iterator().next();
            List<JSONObject> contents = map.get(key);
            StringBuffer conts = new StringBuffer("");
            conts.append("\n" + enumSpace + "public enum " + key.toUpperCase() + " {\n");

            for (int i = 0; i < contents.size(); i++) {
                JSONObject jso = contents.get(i);
                String paramValue = checkValidContent(jso.getString("DEFINED_CODE"), key);
                conts.append(methodSpace + paramValue)
                        .append("(").append("\"").append(jso.getString("DEFINED_CODE")).append("\", ")
                        .append("\"").append(jso.getString("DEFINED_NAME").replaceAll("[\n|\r]{1,}", "")).append("\"");
                if ((i + 1) == contents.size()) {
                    conts.append(");\n\r");
                } else {
                    conts.append("),\n");
                }
            }

            conts.append(methodSpace + "private String code;\n");
            conts.append(methodSpace + "private String name;\n\r");
            conts.append(methodSpace + key.toUpperCase()).append("(String code, String name) {\n")
                    .append(methodSpace + returnSpace + "this.code = code; \n")
                    .append(methodSpace + returnSpace + "this.name = name; \n" + methodSpace + "}\n");
            conts.append(methodSpace + "public String getCode").append("() {\n").append(methodSpace + returnSpace + "return this.code; \n" + methodSpace + "}\n");
            conts.append(methodSpace + "public String getName").append("() {\n").append(methodSpace + returnSpace + "return this.name; \n" + methodSpace + "}\n");
            conts.append(enumSpace + "}\n");
            out.write(conts.toString());
        }
        map.clear();
    }

    private static void initFile(String packagePath, String fileName) throws IOException {
        StringBuffer content = new StringBuffer("");


        content.append("package " + packagePath + ";\n\r");
        content.append("public class " + handClassName(fileName) + " {").append("\n");
        out.write(content.toString());
    }

    private static String checkValidContent(String strcontent, String key) {
        String regNumber = "^[0-9]{1,}";
        String regNegative = "^-";
        strcontent = strcontent.replaceAll("\\.", "_");

        String result = "";
        boolean flagNumber = Pattern.compile(regNumber).matcher(strcontent).find();
        boolean flagNegative = Pattern.compile(regNegative).matcher(strcontent).find();
        if (flagNumber) {
            result = "NUMBER_" + strcontent;
        } else if (flagNegative) {
            result = strcontent.replace("-", "NEGATIVE_");
        } else {
            result = strcontent;
        }

        if (inFilter(key)) {
            return result;
        } else {
            return result.toUpperCase();
        }
    }

    private static boolean inFilter(String key) {
        for (String filter : filterKeys) {
            if (filter.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static String handClassName(String cname) {
        if (cname == null || "".equals(cname)) {
            return "NoNameFile";
        } else {
            return cname.substring(0, 1).toUpperCase() + cname.substring(1);
        }
    }
}
