package Service.bo;

import Utils.SQL.entity.entity.EntityClass;
import base.api.CommonBuildMethods;

public class BO extends EntityClass implements CommonBuildMethods {

    public BO(EntityClass entityClass) {
        super(entityClass.getName()+"BO", entityClass.getPropertyList());
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
