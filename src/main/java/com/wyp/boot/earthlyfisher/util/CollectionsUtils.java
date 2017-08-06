package com.wyp.boot.earthlyfisher.util;

import java.util.Collection;

/**
 * Created by earthlyfisher on 2017/4/25.
 */
public class CollectionsUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public <T> T getInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
