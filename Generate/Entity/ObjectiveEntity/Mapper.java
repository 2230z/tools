package Generate.Entity.ObjectiveEntity;

import interfaces.CommonMethods;
import Generate.Entity.Entity.Entity;

public class Mapper extends Entity implements CommonMethods {

    public Mapper(Entity entity) {
        super(entity.getName(), entity.getPropertyList());
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
    public String buildEntityStatement() {
        return new StringBuilder()
                .append("public class ")
                .append(this.getName())
                .append(" extends BaseEntity {\n")
                .append(this.propertiesToString())
                .append("\n}")
                .toString();
    }

}
