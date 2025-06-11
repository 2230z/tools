package target.Web.controller;

import Utils.StringUtil;
import base.api.CommonBuildMethods;
import base.entity.Project;

public class Controller implements CommonBuildMethods {

    // Entity类型
    private String entityType;
    // Entity实例
    private String entityName;
    // 项目模块名称
    private String projectModuleName;

    public Controller() {
        Project project = Project.getInstance();
        entityType = project.getCommonName();
        entityName = StringUtil.onlyFirstLower(entityType);
        projectModuleName = project.getProjectModuleName();
    }

    // AbcDefGhi -->  abc-def-ghi
    private String entityInApi() {
        return entityType.replaceAll("(?<=[a-z])(?=[A-Z])", "-")   // 插入符号 "-"
                         .toLowerCase();   // 转换为小写

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
        return String.format("@RestController(\"%1$s-%2$sController\")\n" +
                             "@RequestMapping(\"/api/%1$s/%4$s\") \n" +
                             "public class %2$sController extends BaseController { \n" +
                             "@Autowired \n" +
                             "private %2$sService %3$sService; \n" +
                             "%5$s \n" +
                             "}", projectModuleName,
                                  entityType,
                                  entityName,
                                  this.entityInApi(),
                                  this.createAllFunctions());
    }

    // 构造所有函数
    private String createAllFunctions() {
        return String.format("%s \n %s \n %s \n %s \n %s \n",
                this.createQueryByPage(), this.createQueryById(), this.createInsert(), this.createDelete(), this.createUpdate());
    }

    // 分页查询
    private String createQueryByPage() {
        return String.format("@PostMapping(\"/query/page\")\n" +
                            "public AjaxMessageEntity<PageResult<%1$sBO>> query%1$sByPage(@RequestBody %1$sVO %2$sVO) {\n" +
                                "AjaxMessageEntity<PageResult<%1$sBO>> ajaxMessageEntity = new AjaxMessageEntity();\n" +
                                "%1$sBO %2$sBO = BeanObjectCopyUtils.copyObject(new %1$sBO(), %2$sVO);\n" +
                                "PageInfo pageInfo = PageInfoUtil.copyObjectToPageInfo(%2$sVO);\n" +
                                "PageResult<%1$sBO> pageResult = %2$sService.query%1$sByPage(pageInfo, %2$sBO);\n" +
                                "ajaxMessageEntity.setData(pageResult);\n" +
                                "return ajaxMessageEntity;\n" +
                            "}", entityType, entityName);
    }

    // 根据 Id 查询详情
    private String createQueryById() {
        return String.format("@GetMapping(\"/query/-by-/{id}\")\n" +
                            "public AjaxMessageEntity<%1$sBO> query%1$sById(@PathVariable String id) {\n" +
                                "AjaxMessageEntity<%1$sBO> ajaxMessageEntity = new AjaxMessageEntity();\n" +
                                "%1$sBO %2$sBO = %2$sService.query%1$sById(id);\n" +
                                "ajaxMessageEntity.setData(%2$sBO);\n" +
                                "return ajaxMessageEntity;\n" +
                            "}", entityType, entityName);
    }

    // 插入
    private String createInsert() {
        return String.format("@PostMapping(\"/insert/save\")\n" +
                            "public AjaxMessageEntity<Integer> save%1$s(@RequestBody %1$sVO %2$sVO) {\n" +
                                "AjaxMessageEntity<Integer> ajaxMessageEntity = new AjaxMessageEntity();\n" +
                                "%1$sBO %2$sBO = (%1$sBO)BeanObjectCopyUtils.copyObject(new %1$sBO(), %2$sVO);\n" +
                                "int result = %2$sService.save%1$s(%2$sBO);\n" +
                                "ajaxMessageEntity.setCode(result == 0 ? GlobalConstant.QueueSendStatus.SUCCESS : GlobalConstant.QueueSendStatus.FAIL);\n" +
                                "ajaxMessageEntity.setData(result);\n" +
                                "return ajaxMessageEntity;\n" +
                            "}", entityType, entityName);
    }

    // 删除
    private String createDelete() {
        return String.format("@PostMapping(\"/delete\")\n" +
                            "public AjaxMessageEntity<Integer> delete%1$s(@RequestBody %1$sVO %2$sVO) {\n" +
                                "AjaxMessageEntity<Integer> ajaxMessageEntity = new AjaxMessageEntity();\n" +
                                "%1$sBO %2$sBO = BeanObjectCopyUtils.copyObject(new %1$sBO(), %2$sVO);\n" +
                                "int result = %2$sService.delete%1$s(%2$sBO);\n" +
                                "ajaxMessageEntity.setCode(result == 0 ? GlobalConstant.QueueSendStatus.SUCCESS : GlobalConstant.QueueSendStatus.FAIL);\n" +
                                "ajaxMessageEntity.setData(result);\n" +
                                "return ajaxMessageEntity;\n" +
                            "}", entityType, entityName);
    }

    // 更新
    private String createUpdate() {
        return String.format("@PostMapping(\"/update\")\n" +
                            "public AjaxMessageEntity<Integer> update%1$s(@RequestBody %1$sVO %2$sVO) {\n" +
                                "AjaxMessageEntity<Integer> ajaxMessageEntity = new AjaxMessageEntity();\n" +
                                "%1$sBO %2$sBO = BeanObjectCopyUtils.copyObject(new %1$sBO(), %2$sVO);\n" +
                                "int result = %2$sService.update%1$s(%2$sBO);\n" +
                                "ajaxMessageEntity.setCode(result == 0 ? GlobalConstant.QueueSendStatus.SUCCESS : GlobalConstant.QueueSendStatus.FAIL);\n" +
                                "ajaxMessageEntity.setData(result);\n" +
                                "return ajaxMessageEntity;\n" +
                            "}", entityType, entityName);
    }

}
