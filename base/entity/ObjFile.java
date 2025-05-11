package base.entity;

import Utils.IoUtils;
import Utils.StringUtil;
import base.api.StructureMethod;
import main.Project;

import java.nio.file.Paths;

public class ObjFile implements StructureMethod {
    // 文件名称
    private final String fileName;
    // 文件类型（后缀）
    private final String fileType;
    // 文件内容
    private String content;
    // 所属文件夹
    private Directory directory;

    public Directory getDirectory() { return directory; }

    public void setDirectory(Directory directory) { this.directory = directory; }

    public void setContent(String content) { this.content = content; }

    public ObjFile(String fileName, String fileType, String content) {
        this.fileName = Project.getInstance().getCommonName() + fileName;
        this.fileType = fileType;
        this.content = content;
    }

    public ObjFile(String fileType, String content) {
        this.fileName = Project.getInstance().getCommonName();
        this.fileType = fileType;
        this.content = content;
    }

    public ObjFile( String fileType) {
        this.fileName = Project.getInstance().getCommonName();
        this.fileType = fileType;
    }

    // 持久化
    public void save() {
        IoUtils.write(this.getAbsolutePath(), this.content);
    }

    @Override
    public String getAbsolutePath() {
        return String.format("%s/%s.%s",this.directory.getAbsolutePath(), this.fileName,this.fileType);
    }
}
