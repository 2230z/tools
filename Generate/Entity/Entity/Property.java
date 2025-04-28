package Generate.Entity.Entity;

/**
 *
 */
public class Property {
    // 属性 (public, protected, private)
    private String attribute;
    // 字段类型 (Integer, String, BigDecimal...)
    private String type;
    // 字段名称
    private String name;
    // 字段注释 (// )
    private String comment;

    public Property(String attribute, String type, String name, String comment) {
        this.attribute = attribute;
        this.type = type;
        this.name = name;
        this.comment = comment;
    }

    public Property(String type, String name, String comment) {
        this.attribute = "private";
        this.type = type;
        this.name = name;
        this.comment = comment;
    }

    // 注释语句
    public String getPropertyAnnotation() {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("// ")
                .append(comment)
                .append("\n");
        return sb.toString();
    }

    // 声明语句
    public String getPropertyDefine() {
        StringBuilder sb = new StringBuilder("\t");
        sb.append(this.attribute)
                .append(" ")
                .append(this.type)
                .append(" ")
                .append(this.name)
                .append(";")
                .append("\n");
        return sb.toString();
    }

    // set 方法
    public String getSetMethod() {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("public void set")
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
                .append("\n");
        return sb.toString();
    }

    // get 方法
    public String getGetMethod() {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("public ")
                .append(this.type)
                .append(" get")
                .append(this.name.substring(0,1).toUpperCase())
                .append(this.name.substring(1).toLowerCase())
                .append("() { return ")
                .append(this.name)
                .append("; }")
                .append("\n");
        return sb.toString();
    }


}
