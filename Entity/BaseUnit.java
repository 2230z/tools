package Entity;

import Utils.SqlUtils;

/**
 * 单例模式
 */
public class BaseUnit {
    public static BaseUnit baseUnit = new BaseUnit();
    // 私有化构造函数，避免外部生成实体
    private BaseUnit() {
        try{
            // 解析并赋值name
//            this.name = SqlUtils.parseTableName();
        } catch (Exception e) {
            System.err.println("表明解析错误");
        }
    }

    // 向外暴露公共获取对象方法
    public BaseUnit getInstance() { return baseUnit; }

    // 基础名称（由解析 SQL 语句得来）
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

}
