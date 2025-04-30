package base.entity;

import main.Project;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Module {
    // 公共子路径
    private static final String fixedSubPath = "/src/main/java";
    // 模块名称
    protected String moduleName;
    // 模块路径
    protected String modulePath;

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
        if(matcher.find()) {
            this.moduleName = matcher.group(1);
        } else {
            this.moduleName = moduleName;
        }
    }

    // 除 war 包外， 还需要再加上一层项目名称
    private void extendPackage() {
        String filePath = Project.getModulePath() + this.modulePath;
        File file = new File(filePath);
        if(file.exists()) {
            File[] files = file.listFiles();
            if(files.length == 1 && files[0].isDirectory()) {
                this.modulePath += "/" + files[0].getName();
            }
        }
    }

}
