package com.bjpowernode.crm.common.utils;

import java.util.UUID;

/**
 * UUIDUtils
 *
 * @author fj
 * @date 2022/8/26 14:03
 */
public class UUIDUtils {
    public static String getUUid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
