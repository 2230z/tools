package Entity;

import java.util.List;

public class BO extends Table{
    public BO() {
        super();
    }

    public BO(String name) {
        super(name);
    }

    public BO(String name, List<Property> propertyList) {
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
                .append("BO");
        return sb.toString();
    }
}
