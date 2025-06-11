package target.Service;

import Utils.StringUtil;
import base.api.CommonBuildMethods;
import base.entity.Project;
import base.entity.function.Function;
import base.entity.function.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceInterface implements CommonBuildMethods {
    // 函数列表
    private List<Function> functionList;
    // BO实体类型
    private String boType;
    // BO实例
    private String boName;

    public ServiceInterface() {
        Project project = Project.getInstance();
        boType = project.getCommonName();
        boName = StringUtil.onlyFirstLower(boType);
        // 初始化函数列表，供后续生成字符串文本使用
        functionList = new ArrayList<Function>(Arrays.asList(
                this.createQueryByPage(),  // 分页查询
                this.createQueryDetailById(),  // 根据 ID 查询详情
                this.createInsert(),    // 新增
                this.createDelete(),    // 删除
                this.createUpdate()     // 更新
        ));
    }

    @Override
    public String buildPackageName() {
        return "";
    }

    @Override
    public String buildImportStatement() {
        return "";
    }

    @Override
    public String buildEntityStatement() {
        return String.format("public interface %sService { \n" +
                                    "%s \n" +
                                "}", boType, functionList.stream()
                                                                        .map(Function::createInterfaceMethod)
                                                                        .collect(Collectors.joining("\n")));
    }

    // 分页查询
    private Function createQueryByPage() {
        return new Function(
                String.format("PageResult<%s>",boType),   // 返回类型
                String.format("query%sByPage",boType),    // 函数名
                new ArrayList<>(Arrays.asList(         // 参数列表
                        new Param(boType, boName),
                        new Param("PageInfo","pageInfo")
                ))
        );
    }

    // 根据 ID 查询详情
    private Function createQueryDetailById() {
        return new Function(
                boType,   // 返回类型
                String.format("query%sById", boType),    // 函数名
                new ArrayList<>(Arrays.asList(         // 参数列表
                        new Param("String", "id")
                ))
        );
    }

    // 新增
    private Function createInsert() {
        return new Function(
                "int",
                String.format("save%s", boType),
                new ArrayList<>(Arrays.asList(
                        new Param(boType, boName)
                ))
        );
    }

    // 删除
    private Function createDelete() {
        return new Function(
                "int",
                String.format("delete%s", boType),
                new ArrayList<>(Arrays.asList(
                        new Param(boType, boName)
                ))
        );
    }

    // 更新
    private Function createUpdate() {
        return new Function(
                "int",
                String.format("update%s", boType),
                new ArrayList<>(Arrays.asList(
                        new Param(boType, boName)
                ))
        );
    }
}
