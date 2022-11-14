package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

/**
 * DicTypeService
 *
 * @author fj
 * @date 2022/9/9 20:01
 */
public interface DicTypeService {

    List<DicType> queryDicTypeByName(String name);
}
