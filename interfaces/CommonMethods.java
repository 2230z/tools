package interfaces;

public interface CommonMethods {

    // 包名
    public abstract String buildPackageName();

    // 依赖
    public abstract String buildImportStatement();

    // 内容
    public abstract String buildEntityStatement();

}
