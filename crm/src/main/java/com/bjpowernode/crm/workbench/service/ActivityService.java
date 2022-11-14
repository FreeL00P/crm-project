package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

/**
 * ActivityService
 *
 * @author fj
 * @date 2022/8/26 13:11
 */
public interface ActivityService {
    int saveCreateActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    int queryCountOfActivityByConditionForPage(Map<String,Object> map);
    int deleteActivityByIds(String[] ids);
    Activity queryActivityById(String id);
    int updateActivityById(Activity activity);
    List<Activity> queryAllActivity();
    List<Activity> queryActivityByIds(String[] ids);
    int saveActivityByList(List<Activity> activityList);
    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityRelationClueById(String id);
}
