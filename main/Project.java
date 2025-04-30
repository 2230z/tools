package main;

import Utils.SQL.parseSqlUtil;
import base.entity.SaveObj;
import base.entity.Module;
import Utils.StringUtil;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @description 一个 SQL 脚本，生成一堆文件
 * @author znzhang
 * @date 2025/4/28
 */
public class Project {
    // 项目名称
    private String projectName;
    // 项目路径  e.g. E:/xxx
    private static String modulePath;
    // 项目名称公共前缀 e.g. src/main/java/(com/iss/cms)/trust/xxx
    private static String prefix;
    // SQL 脚本路径
    // todo sql脚本应该从项目中遍历搜索，自动判断是否需要生成文件
    private static String sqlScript;
    // 模块列表
    private List<Module> moduleList;
    // sql 解析对象
    private parseSqlUtil parsedSql;
    // 写入列表
    private List<SaveObj> saveList;

    public static String getModulePath() { return modulePath; }

    public static String getPrefix() { return prefix; }

    public String getCommonFunctionName() { return this.parsedSql.getTableName(); }

    // 单例模式对象
    private static Project project;

    static {
        modulePath = "E:/isoftstone/projects/ZiJinXinTuo/trust/java/cms.trust";
        sqlScript = "E:/isoftstone/projects/ZiJinXinTuo/trust/java/cms.trust/cms.trust.war/src/datascript/mysql/00004-trust-financial-subscribe-apply.sql";
        project = new Project();
    }

    // 向外暴露唯一的创建对象方法
    public static Project getInstance() {
        return project;
    }
    // 私有化构造函数
    private Project() {
        // step1: 解析项目名称
        this.parseProjectName();
        // step2: 解析 SQL 脚本文件
        this.parseSqlScripts();
        // step3: 构建模块列表
        this.buildModuleList();
    }

    /**
     * 解析项目名称
     */
    private void parseProjectName() {
        if(StringUtil.isNotBlank(this.modulePath)) {
            this.modulePath = this.modulePath.contains("\\") ?
                    this.modulePath.replaceAll("\\\\", "/") :
                    this.modulePath;
            // 判断文件路径真实有效
            File file = new File(this.modulePath);
            if(file.exists() && file.isDirectory()) {
                // 截取模块名称
                String[] parts = this.modulePath.split("/");
                if (parts.length > 1) {
                    projectName = parts[parts.length - 1];
                }
            }
        }
    }

    /**
     * 解析 SQL 脚本文件
     */
    private void parseSqlScripts() {
        this.parsedSql = new parseSqlUtil(sqlScript);
    }

    // 迭代法取项目公共前缀
    private void getFixedProjectPrefix() {
        this.prefix = "";
        final String startApplication = "startApplication.java";
        // 构建war包路径
        String[] parts = this.modulePath.split("/");
        String path = new StringBuilder(modulePath).append("/").append(parts[parts.length - 1]).append(".war/src/main/java").toString();
        if(!Files.exists(Paths.get(path))) {
            System.out.println("项目路径无效");
        }
        while(true) {
            File file = new File(path);
            if(file.isDirectory()) {
                File[] files = file.listFiles();
                if(files.length == 1 && files[0].isDirectory()) {
                    path += "/" + files[0].getName();
                    this.prefix += "/" + files[0].getName();
                } else break;
            } else if(file.isFile() && file.getName().equals(startApplication)) break;
        }
    }

    /**
     * 构建模块列表
     */
    private void buildModuleList() {
        this.getFixedProjectPrefix();
        if(StringUtil.isNotBlank(this.modulePath)) {
            String regex = this.projectName + ".(\\w+)";
            Pattern pattern = Pattern.compile(regex);
            File file = new File(modulePath);
            this.moduleList = Arrays.stream(file.listFiles())
                    .filter(f -> pattern.matcher(f.getName()).find())
                    .map(e -> new Module(e.getName()))
                    .collect(Collectors.toList());
        }
    }

}
