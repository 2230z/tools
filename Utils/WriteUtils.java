package Utils;

import Entity.Table;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WriteUtils {

    /**
     * 持久化单个对象
     * @param filePath
     * @param content
     * @throws IOException
     */
    private static void writeEntityToFile(String filePath, String content) {
        Path path = Paths.get(filePath);
        try{
            if(Files.exists(path)) {
                Files.delete(path);
            }
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))){
                bufferedWriter.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 持久化
     */
    public static void WriteToFile(String path, List<Table> tableList) {
        for(Table table : tableList) {
            StringBuilder filePath = new StringBuilder(path);
            filePath.append(table.getName()).append(".java");
            writeEntityToFile(filePath.toString(),table.buildEntityString());
        }
    }

}
