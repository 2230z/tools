package SQL.entity.table;

import Utils.StringUtil;

import java.util.List;

public class Table {

    // 表名
    private String tableName;
    // 字段
    private List<Column> columnList;
    // 实体名称（所有单词首字母大写）
    private String entityName;

    public Table(String tableName, List<Column> columnList) {
        this.tableName = tableName;
        this.columnList = columnList;
        this.entityName = StringUtil.getEntityName(tableName);
    }

    public Table() {}

    public String getEntityName() { return entityName; }
    public String getTableName() { return tableName; }

    public void setTableName(String tableName) { this.tableName = tableName; }

    public List<Column> getColumnList() { return columnList; }

    public void setColumnList(List<Column> columnList) { this.columnList = columnList; }

}