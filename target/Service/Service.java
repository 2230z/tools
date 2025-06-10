package target.Service;

import target.Service.bo.BO;
import base.api.CommonBuildMethods;
import base.entity.Directory;
import base.entity.Module;
import base.entity.ObjFile;
import base.entity.Project;

public class Service extends Module {
    public Service(String moduleName) { super(moduleName); }

    @Override
    public void buildDirectories() {
        // step1: 生成开发的一级文件夹
        this.createFunctionDirectory();
        // step2: bo
        this.getDirectory().appendSubDirectory(new Directory("bo"))
                .addSavedFile(new ObjFile("BO","java", this.createServiceBO()));
        // step3: interface
        this.getDirectory().addSavedFile(new ObjFile("Service","java", this.createServiceInterface()));
    }

    // BO
    private String createServiceBO() {
        Project project = Project.getInstance();
        CommonBuildMethods bo = new BO(project.parseSqlEntity());
        return bo.createStoredString();
    }

    // Service 接口
    private String createServiceInterface() {
        CommonBuildMethods serviceInterface = new ServiceInterface();
        return serviceInterface.createStoredString();
    }

}
