package Utils.SQL.entity.entity;


import java.util.List;

public class Entity {

    // 实体类名称
    protected String name;
    // 属性列表
    protected List<Property> propertyList;

    public Entity(String name, List<Property> propertyList) {
        this.name = name;
        this.propertyList = propertyList;
    }

    public Entity(String name) {
        this.name = name;
    }

    public Entity() {}

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

    protected String propertiesToString() {
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
            sb.append(property.getSetMethod());
            sb.append("\n");
        }

        return sb.toString();
    }

}
