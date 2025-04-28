package Utils;

import Generate.Entity.Entity.Property;
import Generate.Entity.Entity.Table;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parseSqlUtil {

    private static Map<String,String> typeMapping = new HashMap<String,String>();

    static {
        typeMapping.put("int","Integer");
        typeMapping.put("varchar","String");
        typeMapping.put("decimal","BigDecimal");
    }

    /**
     * 分割成多条语句并分类
     * 建表语句 create table
     * 修改语句 alter table
     * @param sql 完整 sql 脚本语句
     * @return
     */
    private static Map<String,List<String>> splitSqlStatements(String sql) {
        Map<String,List<String>> categorizations = new HashMap<>();
        categorizations.put("create",new ArrayList<>());
        categorizations.put("alter",new ArrayList<>());
        String[] statements = sql.split(";");
        for (String statement : statements) {
            if(statement.trim().toLowerCase().startsWith("create table")) {
                categorizations.get("create").add(statement);
            } else if(statement.trim().toLowerCase().startsWith("alter table")) {
                categorizations.get("alter").add(statement);
            } else {
                // ignore
                // e.g. insert into ...
            }
        }
        return categorizations;
    }

    /**
     * 解析 sql 语句
     */
    private static List<Table> parseSqlStatements(Map<String,List<String>> categorizations) {
       List<Table> tables = new ArrayList<>();

       // 解析建表语句
        for(String statement : categorizations.get("create")) {
            Table table = parseCreateTable(statement);
            tables.add(table);
        }

        // 解析修改语句
        for(String statement : categorizations.get("alter")) {
            parseAlterTable(statement, tables);
        }

       return tables;
    }

    /**
     * 解析建表语句
     * create table xxx {  };
     * @param sql
     */
    private static Table parseCreateTable(String sql) {
        Table result = new Table();
        try {
            // 正则表达式匹配
            String regex =  "create table\\s+(\\w+)\\s*\\((.*)\\)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            if (matcher.find()) {
                // 提取表名
                String tableName = matcher.group(1);
                String entityName = StringUtil.getEntityName(tableName);
                result.setName(entityName);
                // SQL 字段到 java 对象映射
                List<Property> properties = new ArrayList<Property>();
                String[] columns = matcher.group(2).split("',");
                if(columns.length > 0) {
                    for(String column : columns) {
                        // 正则表达式匹配取值
                        regex = "\\s*(\\w+)\\s+(\\w+)\\(.*comment\\s+'([^']+)";
                        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                        matcher = pattern.matcher(column);
                        if(matcher.find()) {
                            Property property = new Property(typeMapping.get(matcher.group(2)),
                                                            StringUtil.toCamelString(matcher.group(1)),
                                                            matcher.group(3));
                            properties.add(property);
                        }
                    }
                }
                result.setPropertyList(properties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析修改语句
     * alter table add column xxx;
     * alter table drop column xxx;
     * alter table modify column xxx;
     * alter table rename column xxx to xxx;
     */
    // todo 后续扩展
    private static void parseAlterTable(String sql, List<Table> tables) {

        // 新增字段

        // 修改字段
    }

    /**
     * 启动函数
     */
    public static List<Table> run(String sql) {
        // step1: 分割 sql 语句
        Map<String,List<String>> categorizations = splitSqlStatements(sql);
        // step2: 解析语句
        List<Table> tables = parseSqlStatements(categorizations);
        return tables;
    }


}
