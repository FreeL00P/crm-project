package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.mapper.ClueRemarkMapper;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClueServiceImpl
 *
 * @author fj
 * @date 2022/9/4 22:09
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    public List<Clue> queryClueByConditionForPage(Map<String, Object> map){
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryCountOfClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectCountOfClueByConditionForPage(map);
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertCreteClue(clue);
    }

    @Override
    public int editClue(Clue clue) {
        return clueMapper.updateClue(clue);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int deleteClueByIds(String[] ids) {
        return clueMapper.deleteClueByIds(ids);
    }

    @Override
    public Clue selectClueDetailById(String id) {
        return clueMapper.selectClueDetailById(id);
    }
    //根据线索id查询备注
    @Override
    public List<ClueRemark> queryClueRemarkById(String id) {
        return clueRemarkMapper.selectClueRemarkById(id);
    }

    @Override
    public int addClueRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.addRemarkByClue(clueRemark);
    }
}
