package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.common.contants.Contants;
import com.bjpowernode.crm.common.domain.ReturnObject;
import com.bjpowernode.crm.common.utils.DateUtils;
import com.bjpowernode.crm.common.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * ActivityRemarkController
 *
 * @author fj
 * @date 2022/9/6 12:01
 */
@Controller
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        //封装参数
        activityRemark.setId(UUIDUtils.getUUid());
        activityRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Contants.EDIT_EDIT_FLAG_NO);
        try {
            int ret= activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("保存失败");
            }
        }catch (Exception e) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody Object deleteActivityRemarkById(String id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret= activityRemarkService.removeActivityRemark(id);
            if (ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("删除失败");
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("删除失败");
            }
        }catch (Exception e) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage(e.getMessage());
        }
        return returnObject;
    }
    @RequestMapping("/workbench/activity/updateActivityRemark.do")
    public @ResponseBody Object updateActivityRemark(ActivityRemark activityRemark,HttpSession session){
        //封装参数
        activityRemark.setEditTime(DateUtils.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Contants.EDIT_EDIT_FLAG_YES);
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法 更新修改的activity
            int ret = activityRemarkService.editActivityRemark(activityRemark);
            if (ret > 0) {//成功执行
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙");
        }
        return returnObject;
    }

}
