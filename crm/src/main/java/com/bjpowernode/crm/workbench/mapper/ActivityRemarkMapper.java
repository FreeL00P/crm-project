package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import org.apache.poi.hssf.record.StyleRecord;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Sep 05 10:59:11 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Sep 05 10:59:11 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Sep 05 10:59:11 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Sep 05 10:59:11 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Sep 05 10:59:11 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Sep 05 10:59:11 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);
    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String activityId);
    //保存创建的市场活动备注
    int insertActivityRemark(ActivityRemark activityRemark);
    int deleteActivityRemark(String id);
    int updateActivityRemark(ActivityRemark activityRemark);
}