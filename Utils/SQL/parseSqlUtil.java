package Utils.SQL;

import Utils.IoUtils;
import Utils.SQL.entity.table.Column;
import Utils.StringUtil;
import Utils.SQL.entity.entity.Property;
import Utils.SQL.entity.entity.Entity;
import Utils.SQL.entity.table.Table;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// todo 目前限于一个文件只有一条 create 语句
public class parseSqlUtil {
    // 表名的文件夹格式
    private String commonName;
    // 解析出的库表对象（供 Mybatis XML 文件使用）
    private Table table;
    // 解析出的实体对象（供生成BO，VO使用）
    private Entity entity;

    public parseSqlUtil(String sqlPath) {
        String sqlContent = IoUtils.read(sqlPath);
        this.parseCreateTable(sqlContent);
        this.createByTable();
        this.setTableName();
    }

    private static Map<String,String> typeMapping = new HashMap<String,String>();

    static {
        typeMapping.put("int","Integer");
        typeMapping.put("varchar","String");
        typeMapping.put("decimal","BigDecimal");
    }

    public String getCommonName() {  return this.commonName; }

    /**
     * 解析建表语句 生成
     * create table xxx {  };
     * @param createSql
     */
    private void parseCreateTable(String createSql) {
        try {
            this.table = this.table == null ? new Table() : this.table;
            // 正则表达式匹配
            String regex =  "create table\\s+(\\w+)\\s*\\((.*)\\)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(createSql);
            if (matcher.find()) {
                // 提取表名
                String tableName = matcher.group(1);
                this.table.setTableName(tableName);
                // 提取 SQL 字段
                List<Column> columnList = new ArrayList<>();
                String[] columns = matcher.group(2).split("',");
                if(columns != null && columns.length > 0) {
                    for(String column : columns) {
                        // 正则表达式匹配取值
                        regex = "\\s*(\\w+)\\s+(\\w+)\\(.*comment\\s+'([^']+)";
                        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                        matcher = pattern.matcher(column);
                        if(matcher.find()) {
                            Column obj = new Column(matcher.group(2), matcher.group(1), matcher.group(3));
                            columnList.add(obj);
                        }
                    }
                }
                this.table.setColumnList(columnList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createByTable() {
        if(this.table != null) {
            this.entity = new Entity();
            this.entity.setName(StringUtil.getEntityName(this.table.getTableName()));
            this.entity.setPropertyList(
                    this.table.getColumnList()
                            .stream()
                            .map(column ->
                                    new Property(typeMapping.get(column.getType()),
                                            StringUtil.toCamelString(column.getName()),
                                            column.getComment())
                            ).collect(Collectors.toList())
            );
        }
    }

    private void setTableName() {
        if(this.commonName == null || "".equals(this.commonName)) {
            this.commonName = this.entity.getName().toLowerCase();
        }
    }




//    /**
//     * 分割成多条语句并分类
//     * 建表语句 create table
//     * 修改语句 alter table
//     * @param sql 完整 sql 脚本语句
//     * @return
//     */
//    private static Map<String,List<String>> splitSqlStatements(String sql) {
//        Map<String,List<String>> categorizations = new HashMap<>();
//        categorizations.put("create",new ArrayList<>());
//        categorizations.put("alter",new ArrayList<>());
//        String[] statements = sql.split(";");
//        for (String statement : statements) {
//            if(statement.trim().toLowerCase().startsWith("create table")) {
//                categorizations.get("create").add(statement);
//            } else if(statement.trim().toLowerCase().startsWith("alter table")) {
//                categorizations.get("alter").add(statement);
//            } else {
//                // ignore
//                // e.g. insert into ...
//            }
//        }
//        return categorizations;
//    }
//
//    /**
//     * 解析 sql 语句
//     */
//    private static List<Entity> parseSqlStatements(Map<String,List<String>> categorizations) {
//       List<Entity> entities = new ArrayList<>();
//
//       // 解析建表语句
//        for(String statement : categorizations.get("create")) {
//            Entity entity = parseCreateTable(statement);
//            entities.add(entity);
//        }
//
//        // 解析修改语句
//        for(String statement : categorizations.get("alter")) {
//            parseAlterTable(statement, entities);
//        }
//
//       return entities;
//    }
//
//
//    /**
//     * 解析修改语句
//     * alter table add column xxx;
//     * alter table drop column xxx;
//     * alter table modify column xxx;
//     * alter table rename column xxx to xxx;
//     */
//    // todo 后续扩展
//    private static void parseAlterTable(String sql, List<Entity> entities) {
//
//        // 新增字段
//
//        // 修改字段
//    }
//
//    /**
//     * 启动函数
//     */
//    public static List<Entity> run(String sql) {
//        // step1: 分割 sql 语句
//        Map<String,List<String>> categorizations = splitSqlStatements(sql);
//        // step2: 解析语句
//        List<Entity> entities = parseSqlStatements(categorizations);
//        return entities;
//    }


}
