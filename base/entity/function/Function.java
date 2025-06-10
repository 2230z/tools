package base.entity.function;

import base.api.FunctionBody;

import java.util.List;
import java.util.stream.Collectors;

public class Function {
    // 属性 private, protected, public
    private String type;
    // 返回值类型
    private String returnType;
    // 方法名称
    private String name;
    // 参数列表
    private final List<Param> params;

    public Function(String type, String returnType, String name, List<Param> params) {
        this.type = type;
        this.returnType = returnType;
        this.name = name;
        this.params = params;
    }

    public Function(String returnType, String name, List<Param> params) {
        this("public", returnType, name, params);
    }

    // 返回函数定义语句
    private String outToString() {
        return String.format("%s %s %s(%s)", type, returnType, name, params.stream()
                                                                           .map(Param::toString)
                                                                           .collect(Collectors.joining(", ")));
    }

    // 接口文件的方法
    public String createInterfaceMethod() {
        return String.format("%s;", this.outToString());
    }

    // 包含方法体的方法
    public String createClassMethod(FunctionBody body) {
        return String.format("%s {\n" +
                                "%s \n" +
                            "} \n", this.outToString(), body.createFunctionBody());
    }
}
