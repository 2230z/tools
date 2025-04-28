package Generate.Entity.ObjectiveEntity;

import interfaces.CommonMethods;
import Generate.Entity.Entity.Entity;

public class VO extends Entity implements CommonMethods {

    public VO(Entity entity) {
        super(entity.getName()+"VO", entity.getPropertyList());
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
                .append(" extends BaseVO {\n")
                .append(this.propertiesToString())
                .append("\n}")
                .toString();
    }
}
