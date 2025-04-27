package ParseSql.Entity.Objective;

import ParseSql.Entity.Property;
import ParseSql.Entity.Table;

import java.util.List;

public class BO extends Table {

    public BO(String name, List<Property> propertyList) {
        super(name, propertyList);
        this.name += "BO";
    }

    public BO(String name) {
        super(name);
        this.name += "BO";
    }

    @Override
    public String buildPackageName() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    @Override
    public String buildImportStatement() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    @Override
    public String buildClassNameAndExtends() {
        return this.name;
    }

}
