package base.entity;

import Utils.IoUtils;
import Utils.StringUtil;
import base.api.StructureMethod;


/**
 * 文件类
 */
public class ObjFile implements StructureMethod {
    // 文件名称
    private final String fileName;
    // 文件类型（后缀）
    private final String fileType;
    // 文件内容
    private final String content;
    // 所属文件夹
    private Directory directory;

    public Directory getDirectory() { return directory; }

    public void setDirectory(Directory directory) { this.directory = directory; }

    public ObjFile(String fileName, String fileType, String content) {
        this.fileName = Project.getInstance().getCommonName() + fileName;
        this.fileType = fileType;
        this.content = content;
    }

    public ObjFile(String fileType, String content) {
        this("", fileType, content);
    }

    // 持久化
    public void save() {
        IoUtils.write(this.getAbsolutePath(), this.content);
    }

    // 获取变量名
    public String getVariableName() {
        return StringUtil.onlyFirstLower(this.fileName);
    }

    @Override
    public String getAbsolutePath() {
        return String.format("%s/%s.%s",this.directory.getAbsolutePath(), this.fileName, this.fileType);
    }
}
