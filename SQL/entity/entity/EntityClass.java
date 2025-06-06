package SQL.entity.entity;


import java.util.ArrayList;
import java.util.List;

public class EntityClass {

    // 实体类名称
    protected final String name;
    // 属性列表
    protected List<Property> propertyList;

    public EntityClass(String name, List<Property> propertyList) {
        this.name = name;
        this.propertyList = propertyList;
    }

    public EntityClass(String name) {
        this.name = name;
        this.propertyList = new ArrayList<Property>();
    }

    public String getName() { return name; }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    protected String propertiesToString() {
        if(propertyList == null) return "";

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
