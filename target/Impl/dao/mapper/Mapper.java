package target.Impl.dao.mapper;

import SQL.entity.table.Column;
import SQL.entity.table.Table;
import base.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private Table table;
    private final String entityPath;
    private final String DaoPath;

    public Mapper(String entityPath, String DaoPath) {
        this.entityPath = entityPath;
        this.DaoPath = DaoPath;
        Project project = Project.getInstance();
        table = project.parseSqlTable();
    }

    private String getTableName() { return table.getTableName(); }
    private String getEntityName() { return table.getEntityName(); }
    private List<Column> getColumns() { return table.getColumnList(); }
    // 公共查询字段
    private String createQueryColumns() {
        if(this.getColumns() == null || this.getColumns().isEmpty()) return "";
        return String.format("<sql id=\"CommonQueryColumn\">\n" +
                                "%s\n"+
                            "</sql>\n", this.getColumns().stream()
                                                         .map(Column::getName)
                                                         .collect(Collectors.joining(",\n")));
    }

    // resultMap
    private String createResultMap() {
        if(this.getColumns() == null || this.getColumns().isEmpty()) return "";
        return String.format("<sql id=\"queryResultMap\" type=\"%s\"\n >" +
                                "%s\n" +
                            "</sql>\n", entityPath, this.getColumns().stream()
                                                                     .map(Column::createResultMapRow)
                                                                     .collect(Collectors.joining("\n")));
    }

    // 公共过滤条件
    private String createQueryCondition() {
        if(this.getColumns() == null || this.getColumns().isEmpty()) return "";
        return String.format("<sql id=\"CommonQueryCondition\"> \n" +
                                "%s \n" +
                            "<sql> \n", this.getColumns().stream()
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
                            "</insert> \n", this.getEntityName(), this.getTableName(), this.getColumns().stream()
                                                                                                        .map(Column::createInsertCondition)
                                                                                                        .collect(Collectors.joining(",\n")));
    }

    // 删除
    private String createDeleteStatement() {
        return String.format("<delete id=\"delete%s\" parameterType=\"%s\"> \n" +
                                "delete from %s where id = #{id} \n" +
                            "</delete> \n", this.getEntityName(), entityPath, this.getTableName());
    }

    // 更新
    private String createUpdateStatement() {
        return String.format("<update id=\"update%s\" parameterType=\"%s\"> \n" +
                                "update %s set \n" +
                                    "%s \n" +
                                "update_time = #{updateTime},\n" +
                                "update_by_code = #{updateByCode},\n" +
                                "update_by_name = #{updateByName},\n" +
                                "data_version = data_version + 1\n" +
                                "where id = #{id} \n" +
                            "</update> \n", this.getEntityName(), entityPath, this.getTableName(), this.getColumns().stream()
                                                                                                                    .map(Column::createUpdateCondition)
                                                                                                                    .collect(Collectors.joining("\n")));
    }

    // 分页查询
    private String createQueryByPageStatement() {
        return String.format("<select id=\"query%sByPage\" parameterType=\"%s\" resultMap=\"QueryResultMap\"> \n"+
                                "select \n" +
                                    "<include refid=\"CommonQueryColumn\"></include> \n" +
                                "from %s \n" +
                                "where data_status = 1 \n" +
                                    "<include refid=\"CommonQueryCondition\"></include> \n" +
                            "</select> \n", this.getEntityName(), entityPath, this.getTableName());
    }

    // 查详情
    private String createQueryDetailStatement() {
        return String.format("<select id=\"query%sById\" parameterType=\"java.lang.String\" resultMap=\"QueryResultMap\"\n>" +
                                "select \n" +
                                    "<include refid=\"CommonQueryColumn\"></include> \n" +
                                "from %s \n" +
                                "where data_status = 1 and id = #{id} \n" +
                            "</select> \n", this.getEntityName(), this.getTableName());
    }

    public String createMybatisXML() {
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
                                     createResultMap(),
                                     createQueryColumns(),
                                     createQueryCondition(),
                                     createQueryByPageStatement(),
                                     createQueryDetailStatement(),
                                     createInsertStatement(),
                                     createUpdateStatement(),
                                     createDeleteStatement());
    }

}
