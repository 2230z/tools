package Utils.SQL.entity.table;

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

    public Column(Boolean isKey, String type, String name, String comment) {
        this.isKey = isKey;
        this.type = type;
        this.name = name;
        this.comment = comment;
    }

    public Column(String type, String name, String comment) {
        this.isKey = false;
        this.type = type.toUpperCase();
        this.name = name.toLowerCase();
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getKey() {
        return isKey;
    }

    public String getEntityProperty() {
        return StringUtil.toCamelString(this.name);
    }
}
