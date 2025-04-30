package base.api;

public interface createModuleMethods {

    // 创建文件夹
    void createDirectory();

    // 创建文件
    void createFile();

    // 迭代式创建所有文件夹
    default void createDirectoriesAndFiles() {

    }
}
