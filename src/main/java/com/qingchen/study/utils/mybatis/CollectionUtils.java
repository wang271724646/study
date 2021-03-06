package com.qingchen.study.utils.mybatis;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName CollectionUtils
 * @description:
 * @author: WangChen
 * @create: 2020-07-02 09:39
 **/
public class CollectionUtils {

    public CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
