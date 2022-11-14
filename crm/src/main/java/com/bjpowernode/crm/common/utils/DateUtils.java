package com.bjpowernode.crm.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C), 2017-2022
 * <author>          <time>              <version>
 * 冯俊        2022/8/23 23:22    since 1.0.0
 */
public class DateUtils {
    public static String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return simpleDateFormat.format(date);
    }
}
