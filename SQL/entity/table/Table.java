package SQL.entity.table;

import Utils.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public String getTableName() { return tableName; }

    public void setTableName(String tableName) { this.tableName = tableName; }

    public List<Column> getColumnList() { return columnList; }

    public void setColumnList(List<Column> columnList) { this.columnList = columnList; }

    // 公共查询字段
    private String createQueryColumns() {
        if(columnList == null || columnList.isEmpty()) return "";
        return String.format("<sql id=\"CommonQueryColumn\">\n" +
                                "%s\n"+
                             "</sql>\n", columnList.stream()
                                       .map(Column::getName)
                                       .collect(Collectors.joining(",\n")));
    }

    // resultMap
    private String createResultMap(String entityPath) {
        if(columnList == null || columnList.isEmpty()) return "";
        return String.format("<sql id=\"queryResultMap\" type=\"%s\"\n >" +
                                "%s\n" +
                            "</sql>\n", entityPath, columnList.stream()
                                                  .map(Column::createResultMapRow)
                                                  .collect(Collectors.joining("\n")));
    }

    // 公共过滤条件
    private String createQueryCondition() {
        if(columnList == null || columnList.isEmpty()) return "";
        return String.format("<sql id=\"CommonQueryCondition\"> \n" +
                                    "%s \n" +
                            "<sql> \n", columnList.stream()
                                       .map(Column::createQueryCondition)
                                       .collect(Collectors.joining("\n")));
    }

    // 新增
    private String createInsertStatement() {
        return String.format("<insert id=\"insert%s\"> \n" +
                                "insert into %s ( \n" +
                                    "<include refid=\"CommonQueryColumn\"></include> \n" +
                                ") values ( \n" +
                                    "%s \n" +
                                ") \n" +
                            "</insert> \n", entityName, tableName, columnList.stream()
                                                                   .map(Column::createInsertCondition)
                                                                   .collect(Collectors.joining(",\n")));
    }

    // 删除
    private String createDeleteStatement(String entityPath) {
        return String.format("<delete id=\"delete%s\" parameterType=\"%s\"> \n" +
                                "delete from %s where id = #{id} \n" +
                             "</delete> \n", entityName, entityPath, tableName);
    }

    // 更新
    private String createUpdateStatement(String entityPath) {
        return String.format("<update id=\"update%s\" parameterType=\"%s\"> \n" +
                                "update %s set \n" +
                                    "%s \n" +
                                    "update_time = #{updateTime},\n" +
                                    "update_by_code = #{updateByCode},\n" +
                                    "update_by_name = #{updateByName},\n" +
                                    "data_version = data_version + 1\n" +
                                "where id = #{id} \n" +
                            "</update> \n", entityName, entityPath, tableName, columnList.stream()
                                                                               .map(Column::createUpdateCondition)
                                                                               .collect(Collectors.joining("\n")));
    }

    // 分页查询
    private String createQueryByPageStatement(String entityPath) {
        return String.format("<select id=\"query%sByPage\" parameterType=\"%s\" resultMap=\"QueryResultMap\"> \n"+
                                    "select \n" +
                                        "<include refid=\"CommonQueryColumn\"></include> \n" +
                                    "from %s \n" +
                                    "where data_status = 1 \n" +
                                    "<include refid=\"CommonQueryCondition\"></include> \n" +
                             "</select> \n", entityName, entityPath, tableName);
    }

    // 查详情
    private String createQueryDetailStatement() {
        return String.format("<select id=\"query%sById\" parameterType=\"java.lang.String\" resultMap=\"QueryResultMap\"\n>" +
                                    "select \n" +
                                        "<include refid=\"CommonQueryColumn\"></include> \n" +
                                    "from %s \n" +
                                    "where data_status = 1 and id = #{id} \n" +
                             "</select> \n", entityName, tableName);
    }

    public String createMybatisXML(String DaoPath, String entityPath) {
        return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                            "<mapper namespace=\"%s\"> \n" +
                                "%s \n" +
                                "%s \n" +
                                "%s \n" +
                                "%s \n" +
                                "%s \n" +
                                "%s \n" +
                                "%s \n" +
                                "%s \n" +
                            "</mapper>", DaoPath,
                                         createResultMap(entityPath),
                                         createQueryColumns(),
                                         createQueryCondition(),
                                         createQueryByPageStatement(entityPath),
                                         createQueryDetailStatement(),
                                         createInsertStatement(),
                                         createUpdateStatement(entityPath),
                                         createDeleteStatement(entityPath));
    }

}