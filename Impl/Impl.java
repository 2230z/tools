package Impl;

import Utils.StringUtil;
import base.entity.Directory;
import base.entity.Module;
import base.entity.ObjFile;
import main.Project;

public class Impl extends Module {

    public Impl(String moduleName) {
        super(moduleName);
    }


    @Override
    public void buildDirectories() {
        // step1: 生成开发的一级文件夹
        this.createFunctionDirectory();
        // step2: Dao
        Directory dao = this.getDirectory().appendSubDirectory(new Directory("dao"));
        dao.appendSubDirectory(new Directory("entity"))
                .addSavedFile(new ObjFile("java"));
        dao.appendSubDirectory(new Directory("impl"))
                .addSavedFile(new ObjFile("DaoImpl","java"));
        dao.appendSubDirectory(new Directory("mapper"))
                .addSavedFile(new ObjFile("Mapper","xml"));
        dao.addSavedFile(new ObjFile( "Dao","java"));
        // step3: Service
        this.getDirectory().appendSubDirectory(new Directory("service"))
                .appendSubDirectory(new Directory("impl"))
                .addSavedFile(new ObjFile("ServiceImpl","java"));
    }

}
