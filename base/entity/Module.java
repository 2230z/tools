package base.entity;

import Utils.IoUtils;
import base.api.StructureMethod;
import main.Project;
import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Module implements StructureMethod {
    // 公共子路径
    private static final String fixedSubPath = "/src/main/java";
    // 模块名称
    protected String moduleName;
    // 模块路径
    protected String modulePath;
    // 功能文件夹（二开）
    protected Directory directory;

    public Directory getDirectory() { return directory; }


    public Module(String moduleName) {
        Project project = Project.getInstance();
        this.parseModuleName(moduleName);
        this.modulePath = String.format("/%s%s%s", moduleName, fixedSubPath, project.getPrefix());
        this.extendPackage();
    }

    private void parseModuleName(String moduleName) {
        // 截取模块名称
        String regex = ".*\\.(\\w+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(moduleName);
        this.moduleName = matcher.find()? matcher.group(1):moduleName;
    }

    // 除 war 包外， 还需要再加上一层项目名称
    private void extendPackage() {
        String filePath = this.getAbsolutePath();
        File file = new File(filePath);
        if(file.exists()) {
            File[] files = file.listFiles();
            if(files != null && files.length == 1 && files[0].isDirectory()) {
                this.modulePath += "/" + files[0].getName();
            }
        }
    }

    // 生成一级目录
    protected void createFunctionDirectory() {
        this.directory = new Directory(Project.getInstance().getCommonName().toLowerCase());
        directory.setModule(this);
        IoUtils.mkdir(this.directory.getAbsolutePath());
    }

    /**
     * 两个抽象方法：1.创建文件夹
     * @return
     */
    // 构建文件夹结构
    abstract public void buildDirectories();

    // 外部调用的公共入口
    public void run() {
        // step1: 构建文件夹结构
        this.buildDirectories();
        // step2: 生成内容持久化
        this.directory.iteratorSave();
    }

    @Override
    public String getAbsolutePath() {
        return Project.getModulePath() + this.modulePath;
    }
}
