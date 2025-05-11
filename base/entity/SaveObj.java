package base.entity;

import Utils.StringUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveObj {
    // 保存路径
    private final String savePath;
    // 文件名称
    private final String fileName;
    // 文件类型（后缀）
    private final String fileType;
    // 文件内容
    private final String content;

    public SaveObj(String savePath, String fileName, String fileType, String content) {
        this.savePath = savePath.contains("\\") ? savePath.replaceAll("\\\\", "/") : savePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.content = content;
    }

    // 持久化
    public void save(String modulePath) {
        try {
            if (StringUtil.isNotBlank(this.savePath) &&
                    StringUtil.isNotBlank(this.fileName) &&
                    StringUtil.isNotBlank(this.fileType)) {
                final String str = new StringBuilder(modulePath)
                        .append(this.savePath)
                        .append("/")
                        .append(this.fileName)
                        .append(".")
                        .append(this.fileType)
                        .toString();

                Path path = Paths.get(str);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(str))) {
                    bufferedWriter.write(this.content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
