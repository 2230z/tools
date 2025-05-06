package Service.bo;

import base.api.CommonBuildMethods;
import Utils.SQL.entity.entity.Entity;

public class BO extends Entity implements CommonBuildMethods {

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
