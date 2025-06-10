package base.api;

public interface CommonBuildMethods {

    // 包名
    public abstract String buildPackageName();

    // 依赖
    public abstract String buildImportStatement();

    // 主体内容
    public abstract String buildEntityStatement();

    default String createStoredString() {
        return String.format("%s\n%s\n%s\n",
                this.buildPackageName(), this.buildImportStatement(), this.buildEntityStatement());
    }

}
