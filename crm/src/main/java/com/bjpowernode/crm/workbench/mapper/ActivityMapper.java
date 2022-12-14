package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Aug 26 13:00:49 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Aug 26 13:00:49 CST 2022
     */
    int insert(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Aug 26 13:00:49 CST 2022
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Aug 26 13:00:49 CST 2022
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Aug 26 13:00:49 CST 2022
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Aug 26 13:00:49 CST 2022
     */
    int updateByPrimaryKey(Activity record);

    int insertActivity(Activity activity);

    /**
     * 根据条件分页查询Activity
     * @param map
     * @return
     */
    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);
    int selectCountOfActivityByCondition(Map<String,Object> map);
    int deleteActivityByIds(String[] ids);
    Activity selectActivityById(String id);
    int updateActivityById(Activity activity);
    List<Activity> selectAllActivity();
    List<Activity> selectActivityByIds(String[] ids);
    int insertActivityByList(List<Activity> activityList);
    Activity selectActivityForDetailById(String id);

    List<Activity> selectClueRelationActivityByClueId(String id);

}