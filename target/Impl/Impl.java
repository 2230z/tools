package target.Impl;

import SQL.entity.table.Table;
import target.Impl.dao.Dao;
import target.Impl.dao.entity.Entity;
import base.api.CommonBuildMethods;
import base.entity.Directory;
import base.entity.Module;
import base.entity.ObjFile;
import base.entity.Project;
import target.Impl.dao.impl.DaoImpl;
import target.Impl.dao.mapper.Mapper;
import target.Impl.service.impl.ServiceImpl;

public class Impl extends Module {

    private String nameSpace;
    private String entityPath;

    public Impl(String moduleName) {
        super(moduleName);
        Project project = Project.getInstance();
        nameSpace = getAbsolutePath() + "/dao/" + project.getCommonName() + "Dao.java";
        entityPath = getAbsolutePath() + "/dao/entity/" + project.getCommonName() + ".java";
    }

    @Override
    public void buildDirectories() {
        // step1: 生成开发的一级文件夹
        this.createFunctionDirectory();
        // step2: Dao
        Directory dao = this.getDirectory().appendSubDirectory(new Directory("dao"));
        dao.appendSubDirectory(new Directory("entity"))
                .addSavedFile(new ObjFile("java", this.createDaoEntity()));
        dao.appendSubDirectory(new Directory("impl"))
                .addSavedFile(new ObjFile("DaoImpl","java", this.createDaoInterfaceImpl()));
        dao.appendSubDirectory(new Directory("mapper"))
                .addSavedFile(new ObjFile("Mapper","xml", this.createMybatisXML()));
        dao.addSavedFile(new ObjFile( "Dao","java", this.createDaoInterface()));
        // step3: Service.impl
        this.getDirectory().appendSubDirectory(new Directory("service"))
                .appendSubDirectory(new Directory("impl"))
                .addSavedFile(new ObjFile("ServiceImpl","java", this.createServiceInterfaceImpl()));
    }


    // 实体类
    private String createDaoEntity() {
        Project project = Project.getInstance();
        CommonBuildMethods entity = new Entity(project.parseSqlEntity());
        return entity.createStoredString();
    }

    // Dao接口
    private String createDaoInterface() {
        CommonBuildMethods daoInterface = new Dao();
        return daoInterface.createStoredString();
    }

    // Dao接口实现类
    private String createDaoInterfaceImpl() {
        CommonBuildMethods dao = new DaoImpl(nameSpace);
        return dao.createStoredString();
    }

    // Mybatis XML文件
    private String createMybatisXML() {
        Mapper mapper = new Mapper(nameSpace, entityPath);
        return mapper.createMybatisXML();
    }

    // Service接口实现类
    private String createServiceInterfaceImpl() {
        CommonBuildMethods service = new ServiceImpl();
        return service.createStoredString();
    }

}
