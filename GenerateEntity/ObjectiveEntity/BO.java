package GenerateEntity.ObjectiveEntity;

import GenerateEntity.CommonSqlMethods;
import GenerateEntity.Entity.Property;
import GenerateEntity.Entity.Table;

import java.util.List;

public class BO extends Table implements CommonSqlMethods {

    public BO(Table table) {
        super(table.getName()+"BO", table.getPropertyList());
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
    public String buildClassNameAndExtends() {
        return this.name;
    }

    @Override
    public String buildEntityString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.buildPackageName())
                .append("\n")
                .append(this.buildImportStatement())
                .append("\n")
                .append("public class ")
                .append(this.buildClassNameAndExtends())
                .append(" {\n")
                .append(this.buildEntityStatement())
                .append("}");
        return sb.toString();
    }

}
