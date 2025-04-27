import Entity.Table;
import Utils.ReadUtils;
import Utils.SqlUtils;
import Utils.WriteUtils;
import java.util.List;

public class run {
    // 运行
    public static void main(String[] args) {
        String sqlPath = "E:/isoftstone/projects/ZiJinXinTuo/trust/java/cms.trust/cms.trust.war/src/datascript/mysql/00004-trust-financial-subscribe-apply.sql";
        String savePath = "E:/";
        String sqlStatement = ReadUtils.readSqlFile(sqlPath);
        List<Table> tables = SqlUtils.run(sqlStatement);
        WriteUtils.WriteToFile(savePath, tables);
    }

}
