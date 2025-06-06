package target.Impl.dao.mapper;

import SQL.entity.table.Table;
import base.entity.Project;

public class Mapper {

    private Table table;

    public Mapper() {
        Project project = Project.getInstance();
        table = project.parseSqlTable();
    }

    // 获取namespace字符串
    private String getNameSpace() {
        return "";
    }

    // 头文件
    public String buildHeadInfo() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >";
    }

    // <mapper namespace="">
    public String buildMapperTag() {
        return String.format("<mapper namespace=\"%s\">\n%S\n</mapper>", this.getNameSpace(),this.buildMybatis());
    }

    // mybatis语句（公共语句，增删改查）
    public String buildMybatis() {
        return "";
    }

    // resultMap
    private String createResultMap() {
        return "";
    }

    // columns
    private String commonColumns() {
        return "";
    }

    // where conditions
    private String filterConditions() {
        return "";
    }

    //
}
