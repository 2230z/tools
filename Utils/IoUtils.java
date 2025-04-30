package Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IoUtils {
    // 读取文件
    public static String read(String path) {
        String content = null;
        try {
            if (StringUtil.isNotBlank(path)) {
                StringBuilder sqlStatements = new StringBuilder();
                Stream<String> lines = Files.lines(Paths.get(path));
                lines.forEach(line -> { sqlStatements.append(line); });
                content = sqlStatements.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
