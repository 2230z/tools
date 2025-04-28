import FileOperation.Entity.ReadObj;
import FileOperation.Entity.SaveObj;
import GenerateEntity.Entity.Table;
import GenerateEntity.ObjectiveEntity.BO;
import GenerateEntity.ObjectiveEntity.Mapper;
import GenerateEntity.ObjectiveEntity.VO;
import Utils.StringUtil;
import Utils.parseSqlUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @description 一个 SQL 脚本，生成一堆文件
 * @author znzhang
 * @date 2025/4/28
 */
public class Module {
    // 模块名称
    private String moduleName;
    // 模块路径
    // e.g. E:/xxx
    private String modulePath;
    // sql 文件对象
    private ReadObj readSql;
    // 写入列表
    private List<SaveObj> writeList;

    public Module(String modulePath) {
        // step1: 判断文件路径真实有效
        File file = new File(modulePath);
        if(file.exists() && file.isDirectory()) {
            // step2: 取值
            if(StringUtil.isNotBlanket(modulePath)) {
                this.modulePath = modulePath.contains("\\") ? modulePath.replaceAll("\\\\", "/") : modulePath;
                // 截取模块名称
                String[] parts = this.modulePath.split("/");
                if (parts.length > 1) {
                    this.moduleName = parts[parts.length - 1];
                }
            }
        }
    }

    public String readFile(String filePath) {
        this.readSql = new ReadObj(filePath);
        return this.readSql.getContent();
    }

    public void saveAllFiles() {
        if(writeList != null && writeList.size() > 0) {
            for (SaveObj obj : writeList) {
                obj.save(this.modulePath);
            }
        }
    }

    public void appendSaveObj(SaveObj obj) {
        if(writeList == null) {
            writeList = new ArrayList<SaveObj>();
        }
        writeList.add(obj);
    }

    public static void main(String[] args) {
        Module module = new Module("E:\\isoftstone\\projects\\ZiJinXinTuo\\trust\\java\\cms.trust");
        String sqlState = module.readFile("E:\\isoftstone\\projects\\ZiJinXinTuo\\trust\\java\\cms.trust\\cms.trust.war\\src\\datascript\\mysql\\00004-trust-financial-subscribe-apply.sql");
        List<Table> tables = parseSqlUtil.run(sqlState);
        for (Table table : tables) {
            Mapper mapper = new Mapper(table);
            module.appendSaveObj(new SaveObj("/",mapper.getName(),"java",mapper.buildEntityString()));
            BO bo = new BO(table);
            module.appendSaveObj(new SaveObj("/",bo.getName(),"java",bo.buildEntityString()));
            VO vo = new VO(table);
            module.appendSaveObj(new SaveObj("/",vo.getName(),"java",vo.buildEntityString()));
        }
        module.saveAllFiles();
    }


}
