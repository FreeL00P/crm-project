package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2017-2022
 * <author>          <time>              <version>
 * 冯俊        2022/8/23 21:21    since 1.0.0
 */
public interface UserService {
    User queryUserByLoginActAndPwd(Map<String,Object> map);//根据用户名和密码查询数据
    //查询所有用户信息
    List<User> queryAllUser();
}
