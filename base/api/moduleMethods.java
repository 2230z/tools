package base.api;

public interface moduleMethods {

    // 创建所需文件夹
    void createDirectories();

    // 生成文件
    void createFiles();

    // 运行文件
    default void create() {
        createDirectories();
        createFiles();
    }

}
