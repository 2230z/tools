package Entity;

import java.util.List;

public class Mapper extends Table{
    public Mapper() {
        super();
    }

    public Mapper(String name) {
        super(name);
    }

    public Mapper(String name, List<Property> propertyList) {
        super(name, propertyList);
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
    public String buildClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name)
                .append(" extends BaseEntity");
        return sb.toString();
    }

}
