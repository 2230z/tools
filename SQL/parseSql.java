package SQL;

import Utils.IoUtils;
import SQL.entity.table.Column;
import Utils.StringUtil;
import SQL.entity.entity.Property;
import SQL.entity.entity.EntityClass;
import SQL.entity.table.Table;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// todo 目前限于一个文件只有一条 create 语句，待后续扩展（可以包含多条建表语句，增删改某一列的操作）
public class parseSql {
    // 解析出的库表对象（供 Mybatis XML 文件使用）
    private Table table;
    // 解析出的实体对象（供生成BO，VO使用）
    private EntityClass entityClass;

    public Table getTable() { return table; }

    public EntityClass getEntityClass() { return entityClass; }

    public parseSql(String sqlPath) {
        String sqlContent = IoUtils.read(sqlPath);
        this.parseCreateTable(sqlContent);
        this.createByTable();
    }

    private static final Map<String,String> typeMapping = new HashMap<String,String>();

    static {
        typeMapping.put("int","Integer");
        typeMapping.put("varchar","String");
        typeMapping.put("decimal","BigDecimal");
    }

    public String getTableName() {return this.table.getTableName(); }
    public String getCommonName() {  return this.entityClass.getName(); }

    /**
     * 解析建表语句 生成
     * create table xxx {  };
     */
    private void parseCreateTable(String createSql) {
        try {
            this.table = this.table == null ? new Table() : this.table;
            // 正则表达式匹配
            String regex =  "create table\\s+(\\w+)\\s*\\((.*) primary key\\((\\w+)\\)\\)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(createSql);
            if (matcher.find()) {
                // 暂存主键名称
                String primaryKey = matcher.group(3);
                // 提取表名
                String tableName = matcher.group(1);
                this.table.setTableName(tableName);
                // 提取 SQL 字段
                List<Column> columnList = new ArrayList<>();
                String[] columns = matcher.group(2).split("',");
                for (String column : columns) {
                    // 正则表达式匹配取值
                    regex = "\\s*(\\w+)\\s+(\\w+)\\(.*comment\\s+'([^']+)";
                    pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(column);
                    if (matcher.find()) {
                        Column obj = new Column(matcher.group(1).equals(primaryKey), matcher.group(2), matcher.group(1), matcher.group(3));
                        columnList.add(obj);
                    }
                }
                this.table.setColumnList(columnList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据解析出的库表对象，创建实体对象
    private void createByTable() {
        if(this.table != null) {
            this.entityClass = new EntityClass(StringUtil.getEntityName(this.table.getTableName()));
            this.entityClass.setPropertyList(
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

}
