package target.Impl.dao.impl;

import Utils.StringUtil;
import base.api.CommonBuildMethods;
import base.entity.Project;

public class DaoImpl implements CommonBuildMethods {
    // 命名空间
    private final String nameSpace;
    // Entity类型
    private String entityType;
    // Entity实例
    private String entityName;
    // 项目模块名称
    private String projectModuleName;

    public DaoImpl(String nameSpace) {
        this.nameSpace = nameSpace;

        Project project = Project.getInstance();
        entityType = project.getCommonName();
        entityName = StringUtil.onlyFirstLower(entityType);
        projectModuleName = project.getProjectModuleName();
    }

    @Override
    public String buildPackageName() {
        return "";
    }

    @Override
    public String buildImportStatement() {
        return "";
    }

    @Override
    public String buildEntityStatement() {
        return String.format("@Repository(\"%1$s-%2$sDaoImpl\")\n" +
                            "public class %2$sDaoImpl implements %2$sDao { \n" +
                                "private static final String NAMESPACE = \"%3$s.\";" +
                                "@Autowired \n" +
                                "private SqlSessionTemplate sqlSessionTemplate; \n" +
                                "%4$s \n" +
                            "}",
                                 projectModuleName,
                                 entityType,
                                 nameSpace,
                                 this.createAllFunctions());
    }

    // 分页查询
    private String createQueryByPage() {
        return String.format("@Override \n" +
                            "public PageResult<%1$s> query%1$sByPage (%1$s %2$s, PageInfo pageInfo) { \n" +
                                "PageRowBounds pageRowBounds = new PageRowBounds(pageInfo);\n" +
                                "List<%1$s> list = sqlSessionTemplate.selectList(NAMESPACE + \"query%1$sByPage\", %2$s, pageRowBounds);\n" +
                                "return new PageResult<>(pageRowBounds.getTotal(), list);" +
                            "} \n", entityType, entityName);
    }

    // 根据 ID 查询详情
    private String createQueryById() {
        return String.format("@Override \n" +
                             "public %s query%sById (String id) { \n" +
                                "return this.sqlSessionTemplate.selectOne(NAMESPACE + \"query%sById\", id);" +
                            "} \n", entityType);
    }

    // 插入
    private String createInsert() {
        return String.format("@Override \n" +
                            "public int insert%1$s (%1$s %2$s) { \n" +
                                "return this.sqlSessionTemplate.insert(NAMESPACE + \"insert%1$s\", %2$s);" +
                            "} \n", entityType, entityName);
    }

    // 删除
    private String createDelete() {
        return String.format("@Override \n" +
                            "public int delete%1$s (%1$s %2$s) { \n" +
                                "return this.sqlSessionTemplate.delete(NAMESPACE + \"delete%1$s\", %2$s);" +
                            "} \n", entityType, entityName);
    }

    // 更新
    private String createUpdate() {
        return String.format("@Override \n" +
                            "public int update%1$s (%1$s %2$s) { \n" +
                                "return this.sqlSessionTemplate.update(NAMESPACE + \"update%1$s\", %2$s);" +
                            "} \n", entityType, entityName);
    }

    private String createAllFunctions() {
        return String.format("%s \n %s \n %s \n %s \n %s \n",
                this.createQueryByPage(), this.createQueryById(), this.createInsert(), this.createDelete(), this.createUpdate());
    }

}
