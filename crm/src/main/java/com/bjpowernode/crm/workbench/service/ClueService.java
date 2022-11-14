package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

/**
 * ClueService
 *
 * @author fj
 * @date 2022/9/4 22:09
 */
public interface ClueService {
    List<Clue> queryClueByConditionForPage(Map<String,Object> map);
    int queryCountOfClueByConditionForPage(Map<String,Object> map);
    //保存创建的市场活动
    int saveCreateClue(Clue clue);
    //修改市场活动线索
    int editClue(Clue clue);
    //根据id查找市场活动信息
    Clue queryClueById(String id);
    //删除市场活动线索
    int deleteClueByIds(String[] ids);
    Clue selectClueDetailById(String id);
    // 查询线索备注信息
    List<ClueRemark> queryClueRemarkById(String id);

    int addClueRemark(ClueRemark clueRemark);
}
