package Utils.SQL.entity.table;

import java.util.List;
import java.util.stream.Collectors;

public class Table {

    // 表名
    protected String tableName;
    // 字段
    protected List<Column> columnList;

    public Table(String tableName, List<Column> columnList) {
        this.tableName = tableName;
        this.columnList = columnList;
    }

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public Table() {}


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public String createQueryColumns() {
        return columnList.stream()
                .map(Column::getName)
                .collect(Collectors.joining(",\n"));
    }

    public String createResultMap() {
        return columnList.stream()
                .map(c -> c.getKey()
                        ? String.format("\t\t<id property=\"%s\" column=\"%s\" jdbcType=\"%s\" />",
                        c.getEntityProperty(), c.getName(), c.getType())
                        : String.format("\t\t<result property=\"%s\" column=\"%s\" jdbcType=\"%s\" />",
                        c.getEntityProperty(), c.getName(), c.getType()))
                .collect(Collectors.joining("\n"));
    }

    public String createQueryWhere() {
        return columnList.stream()
                .map(c -> {
                    StringBuilder sb = new StringBuilder();
                    switch (c.getType()) {
                        case "INTEGER", "DECIMAL":
                            sb.append(String.format("\t\t<if test=\"%s != null> \n" +
                                            "\t\t\t and %s = #{%s} \n" +
                                            "\t\t </if>",
                                    c.getEntityProperty(),c.getName(),c.getEntityProperty()));
                            break;
                        case "VARCHAR":
                            sb.append(String.format("\t\t<if test=\"%s != null && %s != \"\"> \n" +
                                    "\t\t\t and %s = #{%s} \n" +
                                    "\t\t </if>",
                                    c.getEntityProperty(),c.getEntityProperty(),c.getName(),c.getEntityProperty()));
                            break;
                    }
                    return sb.toString();
                }).collect(Collectors.joining("\n"));
    }

}
