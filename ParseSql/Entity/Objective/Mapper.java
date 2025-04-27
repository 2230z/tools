package ParseSql.Entity.Objective;

import ParseSql.Entity.Property;
import ParseSql.Entity.Table;

import java.util.List;

public class Mapper extends Table {

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

}
