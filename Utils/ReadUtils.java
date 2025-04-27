package Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadUtils {

    /**
     * 读取 SQL 脚本文件，返回 SQL 语句
     * @param filePath
     * @return
     */
    public static String readSqlFile(String filePath) {
        final Path path = Paths.get(filePath);
        if(filePath == null || filePath.isEmpty() || !filePath.endsWith(".sql") || !Files.exists(path)) {
            System.out.println("文件路径无效");
            return "";
        }
        StringBuilder sqlStatements = new StringBuilder();
        try{
            Stream<String> lines = Files.lines(path);
            lines.forEach(line -> {
                sqlStatements.append(line);
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sqlStatements.toString();
    }

}
