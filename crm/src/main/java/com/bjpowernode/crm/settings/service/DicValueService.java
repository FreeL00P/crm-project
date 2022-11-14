package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * DicValueService
 *
 * @author fj
 * @date 2022/9/9 19:48
 */
public interface DicValueService {
    List<DicValue> queryDicValuesByTypeCode(String  typeCode);
}
