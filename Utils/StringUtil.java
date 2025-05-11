package Utils;

public class StringUtil {

    public static Boolean isNotBlank(String str) {
        return !(str == null || str.isEmpty() || str.trim().isEmpty());
    }

    /**
     * 首字母大写，其余小写
     * @param str
     * @return
     */
    public static String FirstUpperLeftLower(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * 将下划线命名转为驼峰命名
     * business_no --> businessNo
     * @param column 数据库列名
     * @return 转换后的驼峰命名字符串
     */
    public static String toCamelString(String column) {
        StringBuilder result = new StringBuilder();
        String[] parts = column.split("_");
        for(String part : parts) {
            if(result.isEmpty()) {
                result.append(part.toLowerCase());
            } else {
                result.append(FirstUpperLeftLower(part));
            }
        }
        return result.toString();
    }


    /**
     * 根据表明生成实体类名称
     * T_BASE_TABLE_NAME --> TableName
     * @param tableName
     * @return
     */
    public static String getEntityName(String tableName) {
        StringBuilder builder = new StringBuilder();
        String[] parts = tableName.split("_");
        if(parts.length > 0) {
            for(int i=2; i<parts.length; i++) {
                String part = parts[i];
                builder.append(FirstUpperLeftLower(part));
            }
        }
        return builder.toString();
    }


}
