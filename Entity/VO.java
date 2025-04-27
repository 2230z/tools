package Entity;

import java.util.List;

public class VO extends Table{
    public VO() {
        super();
    }

    public VO(String name) {
        super(name);
    }

    public VO(String name, List<Property> propertyList) {
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
                .append("VO extends BaseVO");
        return sb.toString();
    }
}
