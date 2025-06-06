package base.entity;

import Utils.IoUtils;
import base.api.StructureMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 文件夹类
 */
public class Directory implements StructureMethod {
    // 文件夹名称
    private final String name;
    // 所属模块（module 和 parentDirectory 必须有一个是 null）
    private Module module;
    // 所属文件夹
    private Directory parentDirectory;
    // 子文件夹
    private List<Directory> directoryList;
    // 文件
    private List<ObjFile> fileList;

    public Directory(String name) { this.name = name; }

    public Module getModule() { return module; }

    public void setModule(Module module) { this.module = module; }

    public Directory getParentDirectory() { return parentDirectory; }

    public void setParentDirectory(Directory parentDirectory) { this.parentDirectory = parentDirectory; }

    /**
     * 文件操作（赋值，追加，批量）
     * @param
     */
    public void addSavedFile(ObjFile file) {
        file.setDirectory(this);
        this.fileList = Optional.ofNullable(this.fileList).orElseGet(ArrayList::new);
        this.fileList.add(file);
    }

    /**
     * 文件夹（赋值，追加，批量）
     * @param
     */
    public Directory appendSubDirectory(Directory subDirectory) {
        subDirectory.setParentDirectory(this);
        this.directoryList = Optional.ofNullable(this.directoryList).orElseGet(ArrayList::new);
        this.directoryList.add(subDirectory);
        return subDirectory;
    }

    @Override
    public String getAbsolutePath() {
        String absolutePath = "/" + this.name;
        return Optional.ofNullable(this.module)
                .map(Module::getAbsolutePath)
                .flatMap(modulePath -> Optional.of(modulePath + absolutePath))
                .orElseGet(() -> Optional.ofNullable(this.parentDirectory)
                        .map(Directory::getAbsolutePath)
                        .flatMap(parentPath -> Optional.of(parentPath + absolutePath))
                        .orElse(absolutePath));
    }

    // 保存文件
    public void iteratorSave() {
        // step1: 创建子文件夹
        if(this.directoryList != null && !this.directoryList.isEmpty()) {
            this.directoryList.forEach(e -> {
                IoUtils.mkdir(e.getAbsolutePath());
                e.iteratorSave();
            });
        }
        // step2: 保存当前文件夹内容
        if(this.fileList != null && !this.fileList.isEmpty()) {
            this.fileList.forEach(ObjFile::save);
        }
    }
}
