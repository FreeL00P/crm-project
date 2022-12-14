package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Sun Sep 04 22:06:27 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Sun Sep 04 22:06:27 CST 2022
     */
    int insert(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Sun Sep 04 22:06:27 CST 2022
     */
    int insertSelective(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Sun Sep 04 22:06:27 CST 2022
     */
    Clue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Sun Sep 04 22:06:27 CST 2022
     */
    int updateByPrimaryKeySelective(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Sun Sep 04 22:06:27 CST 2022
     */
    int updateByPrimaryKey(Clue record);
    //查找所有的线索活动
    List<Clue> selectAllClue();
    //根据条件分页查询线索活动
    List<Clue> selectClueByConditionForPage(Map<String,Object> map);
    //查询每页总条数
     int selectCountOfClueByConditionForPage(Map<String,Object> map);
    //保存创建的线索市场活动
    int insertCreteClue(Clue clue);

    //根据id查找市场活动线索
    Clue selectClueById(String id);
    //修改市场活动线索
    int updateClue(Clue clue);
    //删除市场活动线索
    int deleteClueByIds(String[] ids);
    Clue selectClueDetailById(String id);

}