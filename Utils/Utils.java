package Utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * list 不为 null 且不为空
     * @param list 目标列表
     */
    public static List<?> getNotNullAndEmptyList(List<?> list) {
        return (list == null || list.isEmpty()) ? new ArrayList<>() : list;
    }
}
