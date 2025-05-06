package Impl.dao.entity;

import base.api.CommonBuildMethods;

public class Entity extends Utils.SQL.entity.entity.Entity implements CommonBuildMethods {

    public Entity(Utils.SQL.entity.entity.Entity entity) {
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
