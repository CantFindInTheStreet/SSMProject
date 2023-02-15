package com.cantfindinthestreet.dao.workbench;

import com.cantfindinthestreet.bean.workbench.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Thu Dec 15 11:32:55 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Thu Dec 15 11:32:55 CST 2022
     */
    int insert(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Thu Dec 15 11:32:55 CST 2022
     */
    int insertSelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Thu Dec 15 11:32:55 CST 2022
     */
    ClueRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Thu Dec 15 11:32:55 CST 2022
     */
    int updateByPrimaryKeySelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Thu Dec 15 11:32:55 CST 2022
     */
    int updateByPrimaryKey(ClueRemark record);

    List<ClueRemark> selectClueRemarkForDetailByClueId(String id);

    List<ClueRemark> selectClueRemarkByClueId(String id);

    int deleteClueRemarkByClueId(String id);
}
