package SQL.entity.entity;

import Utils.StringUtil;

/**
 *
 */
public class Property {
    // 属性 (public, protected, private)
    private final String attribute;
    // 字段类型 (Integer, String, BigDecimal...)
    private final String type;
    // 字段名称
    private final String name;
    // 字段注释 (// )
    private final String comment;

    public Property(String attribute, String type, String name, String comment) {
        this.attribute = attribute;
        this.type = type;
        this.name = name;
        this.comment = comment;
    }

    public Property(String type, String name, String comment) {
        this("private", type, name, comment);
    }

    // 注释语句
    public String getPropertyAnnotation() {
        return String.format("\t // %s \n", comment);
    }

    // 声明语句
    public String getPropertyDefine() {
        return String.format("\t %s %2s %2s;\n", attribute, type, name);
    }

    // set 方法
    public String getSetMethod() {
        return String.format("\t public void set%s (%s %s){ this.%s = %s; }\n", StringUtil.FirstUpperLeftLower(name), type, name, name, name);
    }

    // get 方法
    public String getGetMethod() {
        return String.format("\t public %s get%s (){ return %s; }\n", type, StringUtil.toCamelString(name), name);
    }

}
