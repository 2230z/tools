package ParseSql.Objective;

import ParseSql.CommonSqlMethods;
import ParseSql.Entity.Property;
import ParseSql.Entity.Table;

import java.util.List;

public class BO extends Table implements CommonSqlMethods {

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

    @Override
    public String buildEntityString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.buildPackageName())
                .append("\n")
                .append(this.buildImportStatement())
                .append("\n")
                .append("public class")
                .append(this.buildClassNameAndExtends())
                .append(" {\n")
                .append(this.buildEntityStatement())
                .append("}");
        return sb.toString();
    }

}
