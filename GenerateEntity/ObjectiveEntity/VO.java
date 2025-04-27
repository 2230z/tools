package GenerateEntity.ObjectiveEntity;

import GenerateEntity.CommonSqlMethods;
import ParseSql.Entity.Property;
import ParseSql.Entity.Table;

import java.util.List;

public class VO extends Table implements CommonSqlMethods {

    public VO(String name, List<Property> propertyList) {
        super(name, propertyList);
        this.name += "VO";
    }

    public VO(String name) {
        super(name);
        this.name += "VO";
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
        StringBuilder sb = new StringBuilder();
        sb.append(this.name)
                .append(" extends BaseVO ");
        return sb.toString();
    }

    @Override
    public String buildEntityString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.buildPackageName())
                .append("\n")
                .append(this.buildImportStatement())
                .append("\n")
                .append("public class")
                .append(this.buildClassNameAndExtends())
                .append(" {\n")
                .append(this.buildEntityStatement())
                .append("}");
        return sb.toString();
    }

}
