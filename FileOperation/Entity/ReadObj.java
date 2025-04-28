package FileOperation.Entity;

import Utils.StringUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadObj {
    // 文件路径（包括文件名和后缀类型）
    // e.g. /xxx.impl/xxx (以模块为根目录)
    private String finalPath;
    // 文件内容
    private String content;

    public ReadObj(String filePath) {
        this.finalPath = filePath.contains("\\") ? filePath.replaceAll("\\\\", "/") : filePath;
        this.read();
    }

    // 读取文件
    private void read() {
        try {
            if (StringUtil.isNotBlanket(this.finalPath)) {
                StringBuilder sqlStatements = new StringBuilder();
                Stream<String> lines = Files.lines(Paths.get(this.finalPath));
                lines.forEach(line -> { sqlStatements.append(line); });
                this.content = sqlStatements.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContent() {
        return this.content;
    }
}
