package target.Web;

import target.Web.controller.Controller;
import target.Web.vo.VO;
import base.api.CommonBuildMethods;
import base.entity.Directory;
import base.entity.Module;
import base.entity.ObjFile;
import base.entity.Project;

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
                .addSavedFile(new ObjFile("Controller","java", this.createController()));
        // step3: vo
        this.getDirectory().appendSubDirectory(new Directory("vo"))
                .addSavedFile(new ObjFile("VO","java", this.createWebVO()));
    }

    // VO实体
    private String createWebVO() {
        Project project = Project.getInstance();
        CommonBuildMethods vo = new VO(project.parseSqlEntity());
        return vo.createStoredString();
    }

    // Controller类
    private String createController() {
        CommonBuildMethods controller = new Controller();
        return controller.createStoredString();
    }

}