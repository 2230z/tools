package ParseSql.Entity;


import java.util.List;

public class Table {

    // 实体类名称
    protected String name;
    // 属性列表
    protected List<Property> propertyList;

    public Table(String name, List<Property> propertyList) {
        this.name = name;
        this.propertyList = propertyList;
    }

    public Table(String name) {
        this.name = name;
    }

    public Table() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    // 实体类
    protected String buildEntityStatement() {
        StringBuilder sb = new StringBuilder();

        // 属性
        for(Property property : propertyList) {
            sb.append(property.getPropertyAnnotation());
            sb.append(property.getPropertyDefine());
        }
        sb.append("\n");

        // get 和 set方法
        for(Property property : propertyList) {
            sb.append(property.getGetMethod());
            sb.append("\n");
            sb.append(property.getSetMethod());
        }

        return sb.toString();
    }

}
