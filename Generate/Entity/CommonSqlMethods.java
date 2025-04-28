package Generate.Entity;

public interface CommonSqlMethods {

    // 包名
    public abstract String buildPackageName();

    // 依赖
    public abstract String buildImportStatement();

    // 类名称和继承关系
    public abstract String buildClassNameAndExtends();

    // 生成语句
    public abstract  String buildEntityString();

}
