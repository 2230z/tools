package base.entity;

import SQL.entity.entity.EntityClass;
import SQL.entity.table.Table;
import SQL.parseSql;
import Utils.StringUtil;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @description 项目类
 * @author znzhang
 * @date 2025/4/28
 */
public class Project {
    // 项目名称
    private String projectName;
    // 项目路径  e.g. E:/xxx
    private static String projectPath;
    // 项目名称公共前缀 e.g. src/main/java/(com/iss/cms)/trust/xxx
    private String prefix;
    // SQL 脚本路径
    // todo sql脚本应该从项目中遍历搜索，自动判断是否需要生成文件
    private static final String sqlScript;
    // 模块列表
    private List<Module> moduleList;
    // sql 解析对象
    private parseSql parsedSql;

    public static String getProjectPath() { return projectPath; }

    public String getPrefix() { return this.prefix; }

    public String getCommonName() { return this.parsedSql.getCommonName(); }

    public Table parseSqlTable() { return this.parsedSql.getTable(); }

    public EntityClass parseSqlEntity() { return this.parsedSql.getEntityClass(); }

    /**
     * 单例模式
     * @return
     */
    private static final Project project;

    static {
        projectPath = "C:/Users/issuse/Desktop/cms.trust";
        sqlScript = "C:/Users/issuse/Desktop/cms.trust/cms.trust.war/src/datascript/mysql/00004-trust-financial-subscribe-apply.sql";
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
    }

    /**
     * 解析项目名称
     */
    private void parseProjectName() {
        if(StringUtil.isNotBlank(projectPath)) {
            projectPath = projectPath.contains("\\") ?
                    projectPath.replaceAll("\\\\", "/") :
                    projectPath;
            // 判断文件路径真实有效
            File file = new File(projectPath);
            if(file.exists() && file.isDirectory()) {
                // 截取模块名称
                String[] parts = projectPath.split("/");
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
        this.parsedSql = new parseSql(sqlScript);
    }

    /**
     * 构建模块列表
     */
    private void buildModuleList() {
        Map<String, String> classForNameMapping = new HashMap<>();
        classForNameMapping.put("common","target.Common.target.Common");
        classForNameMapping.put("impl","target.Impl.target.Impl");
        classForNameMapping.put("service","target.Service.target.Service");
        classForNameMapping.put("job","target.Job.target.Job");
        classForNameMapping.put("web","target.Web.target.Web");
        classForNameMapping.put("war","target.War.target.War");

        this.getFixedProjectPrefix();
        if (StringUtil.isNotBlank(projectPath)) {
            String regex = this.projectName + ".(\\w+)";
            Pattern pattern = Pattern.compile(regex);
            File file = new File(projectPath);
            this.moduleList = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter(f -> pattern.matcher(f.getName()).find())
                    .map(e -> {
                        Matcher matcher = pattern.matcher(e.getName());
                        if (matcher.find()) {
                            String name = matcher.group(1);
                            try {
                                Class<?> moduleClass = Class.forName(classForNameMapping.get(name));
                                Constructor<?> constructor = moduleClass.getConstructor(String.class);
                                return (Module) constructor.newInstance(e.getName());
                            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        return null;
                    }).collect(Collectors.toList());
        }
    }

    // 迭代法取项目公共前缀
    // 从war包出发，一直找到项目启动类
    private void getFixedProjectPrefix() {
        this.prefix = "";
        final String startApplication = "startApplication.java";
        // 构建war包路径
        String[] parts = projectPath.split("/");
        String path = projectPath + "/" + parts[parts.length - 1] + ".war/src/main/java";
        if(!Files.exists(Paths.get(path))) {
            System.out.println("项目路径无效");
        }
        while(true) {
            File file = new File(path);
            if(file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length == 1 && files[0].isDirectory()) {
                    path += "/" + files[0].getName();
                    this.prefix += "/" + files[0].getName();
                }
            } else if(file.isFile() && file.getName().equals(startApplication)) break;
        }
    }


    public void run() {
        // step3: 构建模块列表
        this.buildModuleList();
        // step4: 创建文件夹
        this.moduleList.forEach(Module::buildDirectories);
        // step4: 生成文件
//        this.moduleList.forEach(Module::buildFiles);
    }

}
