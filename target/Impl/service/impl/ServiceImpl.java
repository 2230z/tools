package target.Impl.service.impl;

import Utils.StringUtil;
import base.api.CommonBuildMethods;
import base.entity.Project;

public class ServiceImpl implements CommonBuildMethods {
    // Entity类型
    private String entityType;
    // Entity实例
    private String entityName;
    // 项目模块名称
    private String projectModuleName;

    public ServiceImpl() {
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
        return String.format("@Service(\"%1$s-%2$sService\") \n" +
                            "public class %2$sServiceImpl implements %2$sService { \n" +
                                "private static final Logger LOGGER = LoggerFactory.getLogger(%2$sService.class); \n" +
                                "@Autowired \n" +
                                "private %2$s %3$s; \n" +
                                "%4$s \n" +
                            "}", projectModuleName,
                                 entityType,
                                 entityName,
                                 this.createAllFunctions());
    }

    // 构造所有函数
    private String createAllFunctions() {
        return String.format("%s \n %s \n %s \n %s \n %s \n",
                this.createQueryByPage(), this.createQueryById(), this.createInsert(), this.createDelete(), this.createUpdate());
    }

    // 分页查询
    private String createQueryByPage() {
        return String.format("@Override\n" +
                            "public PageResult<%1$sBO> query%1$sByPage(PageInfo pageInfo, %1$sBO %2$sBO) {\n" +
                            "   PageResult<%1$sBO> pageResult = new PageResult<>();\n" +
                            "   try {\n" +
                            "       %1$s %2$s = BeanObjectCopyUtils.copyObject(new %1$s(), %2$sBO);\n" +
                            "       PageResult<%1$s> %2$sResults = %2$sDao.query%1$sByPage(pageInfo, %2$s);\n" +
                            "       List<%1$s> %2$sList = %2$sResults.getRows();\n" +
                            "       List<%1$sBO> %2$sBOList = new ArrayList<>();\n" +
                            "       if (%2$sList != null) {\n" +
                            "           %2$sBOList = BeanObjectCopyUtils.copyListObjToListObj(%1$sBO.class, %2$sList);\n" +
                            "       }\n" +
                            "        pageResult.setTotal(%2$sResults.getTotal());\n" +
                            "        pageResult.setRows(%2$sBOList);\n" +
                            "   } catch (ISSServiceException ex) {\n" +
                            "        LOGGER.error(\"分页查询异常\" + JSON.toJSONString(%2$sBO), ex);\n" +
                            "        throw ex;\n" +
                            "   } catch (Exception ex) {\n" +
                            "        LOGGER.error(\"分页查询异常\" + JSON.toJSONString(%2$sBO) , ex);\n" +
                            "        throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "           GlobalExceptionEnums.ERROR.getCode(),\n" +
                            "           GlobalExceptionEnums.ERROR.getName(), ex);\n" +
                            "   }\n" +
                            "   return pageResult;\n" +
                            "}", entityType, entityName);
    }

    // 根据 Id 查询详情
    private String createQueryById() {
        return String.format("@Override\n" +
                            "public %1$sBO query%1$sById(String id) {\n" +
                                "%1$sBO %2$sBO = new %1$sBO();\n" +
                                "try{\n" +
                                    "%1$s %2$s = %2$sDao.query%1$sById(id);\n" +
                                    "if(%2$s == null){\n" +
                                        "LOGGER.error(\"单据不存在\");\n" +
                                        "throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                                                "TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(),\n" +
                                                "TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName(),\n" +
                                                "new String[]{\"单据不存在\"}) \n" +
                                    "}\n" +
                                    "%2$sBO = BeanObjectCopyUtils.copyObject(new %1$sBO(), %2$s);\n" +
                                    "} catch (ISSServiceException ex) {\n" +
                                            "LOGGER.error(\"根据id[\" + id +\"]查询详情异常:\", ex);\n" +
                                            "throw ex;\n" +
                                    "} catch ( Exception ex) {\n" +
                                            "LOGGER.error(\"根据id[\" + id +\"]查询详情异常:\", ex);\n" +
                                            "throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                                                "GlobalExceptionEnums.ERROR.getCode(),\n" +
                                                "GlobalExceptionEnums.ERROR.getName(), ex);\n" +
                                    "}\n" +
                                "return financialSubscribeApplyBO;\n" +
                            "}", entityType, entityName);
    }

    // 插入
    private String createInsert() {
        return String.format("@Override\n" +
                            "public int save%1$s(%1$sBO %2$sBO) {\n" +
                            "   int res = 0;\n" +
                            "   try {\n" +
                            "       DataValidator.validate(%2$sBO);\n" +
                            "       if(%2$sBO.getApprovalStatus() == null) {\n" +
                            "           // 审批状态未已保存\n" +
                            "           %2$sBO.setApprovalStatus(TrustConstant.TrustLoanSubApplyApprovalStatus.SAVE);\n" +
                            "       }\n" +
                            "       SystemPropertyUtil.setPropertiesOnCreate(%2$sBO); \n" +
                            "       %1$s %2$s = BeanObjectCopyUtils.copyObject(new %1$s(), %2$sBO);\n" +
                            "       res = %2$sDao.insert%1$s(%2$s);\n" +
                            "       if (res == 0) {\n" +
                            "           LOGGER.error(\"单据新增失败\");\n" +
                            "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(), \n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName(),\n" +
                            "                       new String[]{\"单据新增失败\"}) \n" +
                            "       }\n" +
                            "   } catch (ISSServiceException ex) {\n" +
                            "       LOGGER.error(\"单据保存接口异常\", ex);\n" +
                            "       throw ex;\n" +
                            "   } catch (Exception ex) {\n" +
                            "       LOGGER.error(\"单据保存接口异常\", ex);\n" +
                            "       throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "               GlobalExceptionEnums.ERROR.getCode(),\n" +
                            "               GlobalExceptionEnums.ERROR.getName(), ex);\n" +
                            "   }\n" +
                            "   return res;\n" +
                            "}",entityType, entityName);
    }

    // 删除
    private String createDelete() {
        return String.format("@Override\n" +
                            "public int delete%1$s(%1$sBO %2$sBO) {\n" +
                            "   int res = 0;\n" +
                            "   try {\n" +
                            "       %1$s validApply = %2$sDao.query%1$sById(%2$sBO.getId());\n" +
                            "       if(validApply == null){\n" +
                            "           LOGGER.error(\"单据不存在\");\n" +
                            "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(), \n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName(),\n" +
                            "                       new String[]{\"单据不存在\"}) \n" +
                            "       }\n" +
                            "       // 只有保存状态（没有发起）的数据可以删除\n" +
                            "       if (validApply.getApprovalStatus() != TrustConstant.TrustLoanSubApplyApprovalStatus.SAVE) {\n" +
                            "           LOGGER.error(\"当前审批状态不可以删除\");\n" +
                            "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(), \n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName(),\n" +
                            "                       new String[]{\"当前审批状态不可以删除\"}) \n" +
                            "       }\n" +
                            "       %1$s deleteObj = BeanObjectCopyUtils.copyObject(new %1$s(), %2$sBO);\n" +
                            "       res = %2$sDao.delete%1$s(deleteObj);\n" +
                            "       if (res == 0) {\n" +
                            "           LOGGER.error(\"删除失败\");\n" +
                            "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(), \n" +
                            "                       TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName(),\n" +
                            "                       new String[]{\"删除失败\"}) \n" +
                            "       }\n" +
                            "   } catch (ISSServiceException ex) {\n" +
                            "       LOGGER.error(\"删除异常\"+JSON.toJSONString(%2$sBO), ex);\n" +
                            "       throw ex;\n" +
                            "   } catch (Exception ex) {\n" +
                            "       LOGGER.error(\"删除异常\"+JSON.toJSONString(%2$sBO), ex);\n" +
                            "       throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                            "                    GlobalExceptionEnums.ERROR.getCode(),\n" +
                            "                    GlobalExceptionEnums.ERROR.getName(), ex);\n" +
                            "   }\n" +
                            "   return res;\n" +
                            "}", entityType, entityName);
    }

    // 更新
    private String createUpdate() {
        return String.format("@Override\n" +
                "public int update%1$s(%1$sBO %2$sBO) {\n" +
                "   int res = 1;\n" +
                "   try {\n" +
                "       %1$s validApply = %2$sDao.query%1$sById(%2$sBO.getId());\n" +
                "       if(validApply == null){\n" +
                "           LOGGER.error(\"单据不存在\");\n" +
                "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                "                   TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(),\n" +
                "                   TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName());\n" +
                "                   new String[]{\"单据不存在\"}) \n" +
                "       }\n" +
                "       // 审批状态为保存、审批退回时可以修改\n" +
                "       // 业务状态为已驳回时可以修改\n" +
                "       if(validApply.getApprovalStatus() != TrustConstant.TrustLoanSubApplyApprovalStatus.SAVE &&\n" +
                "           validApply.getApprovalStatus() != TrustConstant.TrustLoanSubApplyApprovalStatus.APPROVAL_REFUSE &&\n" +
                "           validApply.getBusinessStatus() != TrustConstant.TrustDeliveryApplyBusinessStatus.REFUSED) {\n" +
                "           LOGGER.error(\"当前审批状态不可以修改\");\n" +
                "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                "                   TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(),\n" +
                "                   TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName());\n" +
                "                   new String[]{\"当前审批状态不可以修改\"}) \n" +
                "       }\n" +
                "       res = %2$sDao.update%1$s(updateObj);\n" +
                "       if(res == 0){\n" +
                "           LOGGER.error(\"单据更新失败\");\n" +
                "           throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                "                   TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getCode(),\n" +
                "                   TrustExceptionCodeEnum.SYSTEM_COMMON_ERROR.getName());\n" +
                "                   new String[]{\"单据更新失败\"}) \n" +
                "       }\n" +
                "   } catch (ISSServiceException ex) {\n" +
                "       LOGGER.error(\"单据更新异常\"+JSON.toJSONString(%2$sBO), ex);\n" +
                "       throw ex;\n" +
                "   } catch (Exception ex) {\n" +
                "       LOGGER.error(\"单据更新异常\"+JSON.toJSONString(%2$sBO), ex);\n" +
                "       throw ISSExceptionFactory.wrapException(ISSServiceException.class,\n" +
                "               GlobalExceptionEnums.ERROR.getCode(),\n" +
                "               GlobalExceptionEnums.ERROR.getName(), ex);\n" +
                "   }\n" +
                "   return res;\n" +
                "}", entityType, entityName);
    }

}
