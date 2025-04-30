import Utils.SQL.parseSqlUtil;
import base.entity.SaveObj;
import Service.bo.BO;
import Impl.Dao.entity.Entity;
import Web.vo.VO;
import Utils.StringUtil;
import java.io.File;
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
    // SQL 脚本路径
    // todo sql脚本应该从项目中遍历搜索，自动判断是否需要生成文件
    private static String sqlScript;
    // 模块列表
    private List<Module> moduleList;
    // sql 解析对象
    private parseSqlUtil parsedSql;
    // 写入列表
    private List<SaveObj> saveList;

    public String getModulePath() { return modulePath; }

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
                    this.projectName = parts[parts.length - 1];
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

    /**
     * 构建模块列表
     */
    private void buildModuleList() {

    }

    // 生成所有模块的文件
    public void saveAllFiles() {
        if(saveList != null && saveList.size() > 0) {
            for (SaveObj obj : saveList) {
                obj.save(this.modulePath);
            }
        }
    }

    /**
     * 追加持久化内容
     * @param obj
     */
    public void appendSaveObj(SaveObj obj) {
        if(saveList == null) {
            saveList = new ArrayList<SaveObj>();
        }
        saveList.add(obj);
    }


    // 创建 Mapper, BO, VO 实体类
    public void createEntities(List<Utils.SQL.entity.entity.Entity> entityList) {
        if(entityList != null && entityList.size() > 0) {
            for (Utils.SQL.entity.entity.Entity entity : entityList) {
                // todo 文件夹和指定路径
                Entity mapper = new Entity(entity);
                this.appendSaveObj(new SaveObj("/",mapper.getName(),"java",mapper.createStoredString()));
                BO bo = new BO(entity);
                this.appendSaveObj(new SaveObj("/",bo.getName(),"java",bo.createStoredString()));
                VO vo = new VO(entity);
                this.appendSaveObj(new SaveObj("/",vo.getName(),"java",vo.createStoredString()));
            }
        }
        this.saveAllFiles();
    }

    // 创建文件夹路径
    public void createDirectories() {
        if(StringUtil.isNotBlank(this.modulePath)) {
            String regex = this.projectName + ".(\\w+)";
            Pattern pattern = Pattern.compile(regex);

            File file = new File(modulePath);
            Map<String,String> hash = Arrays.stream(file.listFiles())
                    .filter(f -> pattern.matcher(f.getName()).find())
                    .collect(Collectors.toMap(
                            e-> {
                                Matcher matcher = pattern.matcher(e.getName());
                                if(matcher.find()) {
                                    return matcher.group(1);
                                } else return null;
                            },
                            File::getAbsolutePath,
                            (existing, replacing) -> existing
                    ));

            System.out.println();
        }


        // Mapper

        // Service

        // Controller
    }

    public static void main(String[] args) {
        Project project = Project.getInstance();
//        String sqlState = module.readFile("E:/isoftstone/projects/ZiJinXinTuo/trust/java/cms.trust/cms.trust.war/src/datascript/mysql/00004-trust-financial-subscribe-apply.sql");
//        List<Entity> entities = parseSqlUtil.run(sqlState);
//        module.createEntities(entities);
        project.createDirectories();
    }


}
