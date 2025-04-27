package ParseSql.Objective;

import ParseSql.CommonSqlMethods;
import ParseSql.Entity.Property;
import ParseSql.Entity.Table;

import java.util.List;

public class Mapper extends Table implements CommonSqlMethods  {

    public Mapper(String name, List<Property> propertyList) {
        super(name, propertyList);
    }

    public Mapper(String name) {
        super(name);
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
        StringBuilder sb = new StringBuilder();
        sb.append(this.name)
                .append(" extends BaseEntity ");
        return sb.toString();
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
