package Web.vo;

import Utils.SQL.entity.entity.EntityClass;
import base.api.CommonBuildMethods;

public class VO extends EntityClass implements CommonBuildMethods {

    public VO(EntityClass entityClass) {
        super(entityClass.getName()+"VO", entityClass.getPropertyList());
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
