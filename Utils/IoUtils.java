package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    // 写入文件
    public static void write(String path, String content) {
        try{
            Path filePath = Paths.get(path);
            if (Files.exists(filePath)) { Files.delete(filePath); }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mkdir(String path) {
        try{
            File file = new File(path);
            if(file.exists()) {
                file.delete();
            }
            file.mkdir();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
