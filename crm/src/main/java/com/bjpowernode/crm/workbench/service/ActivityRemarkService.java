package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * ActivityRemark
 *
 * @author fj
 * @date 2022/9/5 11:11
 */
public interface ActivityRemarkService {
    List<com.bjpowernode.crm.workbench.domain.ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    int saveCreateActivityRemark(ActivityRemark activityRemark);
    int removeActivityRemark(String id);
    int editActivityRemark(ActivityRemark activityRemark);
}
