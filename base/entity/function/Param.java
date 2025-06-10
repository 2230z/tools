package base.entity.function;

/**
 * 函数参数类
 */
public class Param {
    // 类型
    private String type;
    // 名称
    private String name;

    public Param(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, name);
    }
}
