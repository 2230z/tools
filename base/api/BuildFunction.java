package base.api;


public interface BuildFunction {
    // todo 格式控制（换行，Tab缩进）
    // 注释
    String buildFunctionComment();

    // 返回值
    String buildReturnValue();

    // 函数名
    String buildFunctionName();

    // 参数
    String buildParams();

    // 函数体
    String buildFunctionBody();

    default String buildFunctionImpl() {
        return String.format("%s" +
                        "%s " + "%s " + "(%s){" +
                            "%s" +
                        "}",
                this.buildFunctionComment(),
                this.buildReturnValue(),
                this.buildFunctionName(),
                this.buildParams(),
                this.buildFunctionBody());
    }

    default String buildFunctionDefinition() {
        return String.format("\t%s\n" +
                "\t%s "+ "%s "+ "(%s);\n\n",
                this.buildFunctionComment(),
                this.buildReturnValue(),
                this.buildFunctionName(),
                this.buildParams());
    }

}
