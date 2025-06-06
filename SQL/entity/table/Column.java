package SQL.entity.table;

import Utils.StringUtil;

/**
 *
 */
public class Column {
    // 是否主键
    private final Boolean isKey;
    // 字段类型 (int, varchar, decimal...)
    private final String type;
    // 字段名称 (_带下划线)
    private final String name;
    // 字段注释 (// )
    private final String comment;
    // 驼峰名称
    private String camelName;


    public Column(Boolean isKey, String type, String name, String comment) {
        this.isKey = isKey;
        this.type = type;
        this.name = name;
        this.comment = comment;
        camelName = StringUtil.toCamelString(name);
    }

    public Column(String type, String name, String comment) {
        this(false, type, name, comment);
    }

    public String getType() { return type; }

    public String getName() { return name; }

    public String getComment() { return comment; }

    public Boolean getKey() { return isKey; }

    public String getEntityProperty() { return StringUtil.toCamelString(name); }

    // 转化条件 <resultMap>
    public String createResultMapRow() {
        String tag = isKey ? "id" : "result";
        return String.format("<%s property=\"%s\" column=\"%s\" jdbcType=\"%s\" />",
                            tag, camelName, name, type);
    }

    // 查询条件
    public String createQueryCondition() {
        String res = null;
        switch (type) {
            case "INTEGER":
            case "DECIMAL":
                res = String.format("<if test=\"%s != null\"> \n" +
                                        "and %s = #{%s} \n" +
                                    "</if>",
                                    camelName, name, camelName);
                break;
            case "VARCHAR":
                res = String.format("<if test=\"%s != null and %s != ''\"> \n" +
                                        "and %s = #{%s} \n" +
                                    "</if>",
                                    camelName, camelName, name, camelName);
                break;
            default:
                break;
        }
        return res;
    }

    // 更新条件
    public String createUpdateCondition() {
        String res = null;
        switch (type) {
            case "INTEGER":
            case "DECIMAL":
                res = String.format("<if test=\"%s != null\"> \n" +
                                        "%s = #{%s}, \n" +
                                    "</if>",
                        camelName, name, camelName);
                break;
            case "VARCHAR":
                res = String.format("<if test=\"%s != null and %s != ''\"> \n" +
                                        "%s = #{%s}, \n" +
                                    "</if>",
                        camelName, camelName, name, camelName);
                break;
            default:
                break;
        }
        return res;
    }

    // 删除条件
    public String createDeleteCondition() {
        return String.format("%s = #{%s}", name, camelName);
    }

    // 增加
    public String createInsertCondition() {
        return String.format("#{%s},",camelName);
    }

}
