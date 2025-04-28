package Generate.Entity.ObjectiveEntity;

import interfaces.CommonMethods;
import Generate.Entity.Entity.Entity;

public class BO extends Entity implements CommonMethods {

    public BO(Entity entity) {
        super(entity.getName()+"BO", entity.getPropertyList());
    }

    @Override
    public String buildPackageName() {
        return new StringBuilder().toString();
    }

    @Override
    public String buildImportStatement() {
        return new StringBuilder().toString();
    }

    @Override
    public String buildEntityStatement() {
        return new StringBuilder()
                .append("public class ")
                .append(this.getName())
                .append(" {\n")
                .append(this.propertiesToString())
                .append("\n}")
                .toString();
    }

}
