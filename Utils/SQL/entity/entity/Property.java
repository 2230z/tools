package Utils.SQL.entity.entity;

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
        return new StringBuilder("\t")
                .append("// ")
                .append(comment)
                .append("\n")
                .toString();
    }

    // 声明语句
    public String getPropertyDefine() {
        return new StringBuilder("\t")
            .append(this.attribute)
            .append(" ")
            .append(this.type)
            .append(" ")
            .append(this.name)
            .append(";")
            .append("\n")
            .toString();
    }

    // set 方法
    public String getSetMethod() {
        return new StringBuilder("\t")
            .append("public void set")
            .append(this.name.substring(0,1).toUpperCase())
            .append(this.name.substring(1))
            .append("(")
            .append(this.type)
            .append(" ")
            .append(this.name)
            .append(") { this.")
            .append(this.name)
            .append(" = ")
            .append(this.name)
            .append("; }")
            .append("\n")
            .toString();
    }

    // get 方法
    public String getGetMethod() {
        return new StringBuilder("\t")
            .append("public ")
            .append(this.type)
            .append(" get")
            .append(this.name.substring(0,1).toUpperCase())
            .append(this.name.substring(1).toLowerCase())
            .append("() { return ")
            .append(this.name)
            .append("; }")
            .append("\n")
            .toString();
    }


}
