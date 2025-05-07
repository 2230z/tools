package Web;

import Impl.dao.entity.Entity;
import Web.vo.VO;
import base.api.CommonBuildMethods;
import base.entity.Directory;
import base.entity.Module;
import base.entity.ObjFile;
import main.Project;

public class Web extends Module {
    public Web(String moduleName) {
        super(moduleName);
    }

    @Override
    public void buildDirectories() {
        // step1: 生成开发的一级文件夹
        this.createFunctionDirectory();
        // step2: controller
        this.getDirectory().appendSubDirectory(new Directory("controller"))
                .addSavedFile(new ObjFile("Controller","java"));
        // step3: vo
        this.getDirectory().appendSubDirectory(new Directory("vo"))
                .addSavedFile(new ObjFile("VO","java", this.createWebVO()));
    }

    private String createWebVO() {
        Project project = Project.getInstance();
        CommonBuildMethods vo = new VO(project.parseSqlEntity());
        return vo.createStoredString();
    }

}