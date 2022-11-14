package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.common.contants.Contants;
import com.bjpowernode.crm.common.domain.ReturnObject;
import com.bjpowernode.crm.common.utils.DateUtils;
import com.bjpowernode.crm.common.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * clueController
 *
 * @author fj
 * @date 2022/9/4 21:48
 */
@Controller
public class ClueController {
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueService clueService;
    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        //传递用户列表到前台请求域
        List<User> users = userService.queryAllUser();
        request.setAttribute("users",users);
        //传递数据字典到前台
        //称呼
        List<DicValue> appellations = dicValueService.queryDicValuesByTypeCode(Contants.DIC_TYPE_CODE_APPELLATION);
        //线索状态
        List<DicValue> clueStates = dicValueService.queryDicValuesByTypeCode(Contants.DIC_TYPE_CODE_CLUESTATE);
        //线索来源
        List<DicValue> sources = dicValueService.queryDicValuesByTypeCode(Contants.DIC_TYPE_CODE_SOURCE);
        //保存到请求域
        request.setAttribute("appellations",appellations);
        request.setAttribute("sources",sources);
        request.setAttribute("clueStates",clueStates);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/queryClueByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String fullname,String company,String phone,
                                                                String source,String owner,String mphone,String state,
                                                                int  pageNo, int pageSize){
        //封装参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("owner",owner);
        map.put("company",company);
        map.put("phone",phone);
        map.put("pageNo",pageNo);
        map.put("source",source);
        map.put("mphone",mphone);
        map.put("state",state);
        map.put("beginNo",(pageNo - 1)*pageSize);
        map.put("pageSize",pageSize);
        //调用Service方法查询数据
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows= clueService.queryCountOfClueByConditionForPage(map);
        //根据查询结果生成响应信息
        HashMap<String , Object> resultMap    = new HashMap<>();
        resultMap.put("clueList",clueList);
        resultMap.put("totalRows",totalRows);
        return resultMap;
    }

    //保存创建的市场活动
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session){
        //封装参数
        clue.setId(UUIDUtils.getUUid());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        clue.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法 保存clue（创建的线索对象）
            int ret = clueService.saveCreateClue(clue);
            if (ret > 0) {//插入成功执行
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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
    //根据id查找市场活动线索信息
    @RequestMapping("/workbench/clue/queryClueById.do")
    public @ResponseBody Clue  queryClueById(String id){
        //调用service层方法
        return clueService.queryClueById(id);
    }
    //修改市场活动线索
    @RequestMapping("/workbench/clue/editClue.do")
    public @ResponseBody Object editClue(Clue clue,HttpSession session){
        //封装参数
        clue.setEditTime(DateUtils.formatDateTime(new Date()));
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        clue.setEditBy(user.getId());
        //调用service层方法
        int ret = clueService.editClue(clue);
        ReturnObject retObj = new ReturnObject();
        try{
            if (ret>0){
                retObj.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("系统繁忙...");
            }
        }catch (Exception e) {
            retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObj.setMessage("系统繁忙...");
        }
        return retObj;
    }

    //根据id删除市场活动线索
    @RequestMapping("/workbench/clue/deleteClueByIds.do")
    public @ResponseBody  Object deleteClueById(String[] ids){
        //调用service层方法
        int ret = clueService.deleteClueByIds(ids);
        ReturnObject retObj = new ReturnObject();
        try{
            if (ret>0){
                retObj.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("系统繁忙...");
            }
        }catch (Exception e) {
            retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObj.setMessage(e.getMessage());
        }
        return retObj;
    }

    @RequestMapping("workbench/clue/detailClueByIds.do")
    public String detailClueByIds(String id,HttpServletRequest request){
        //调用service层
        Clue clue = clueService.selectClueDetailById(id);
        List<ClueRemark> clueRemarkList =clueService.queryClueRemarkById(id);
        List<Activity> relationActivityList = activityService.queryActivityRelationClueById(id);
        //保存到请求域
        //线索
        request.setAttribute("clue",clue);
        //线索备注
        request.setAttribute("clueRemarkList",clueRemarkList);
        //线索关联的市场活动
        request.setAttribute("relationActivityList",relationActivityList);
        return "workbench/clue/detail";
    }

    //添加线索备注
    public @ResponseBody Object addClueRemark(ClueRemark clueRemark,HttpSession session){

        //封装参数
        clueRemark.setId(UUIDUtils.getUUid());
        clueRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        clueRemark.setCreateBy(user.getId());
        //调用service层方法
        int ret = clueService.addClueRemark(clueRemark);
        ReturnObject retObj = new ReturnObject();
        try{
            if (ret>0){
                retObj.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("系统繁忙...");
            }
        }catch (Exception e) {
            retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObj.setMessage("系统繁忙...");
        }
        return retObj;

    }
}
