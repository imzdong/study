package org.imzdong.study.springbootmybatis.dao;

import org.imzdong.study.springbootmybatis.model.HealthLevel;

import java.util.List;

public interface HealthLevelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table health_level
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table health_level
     *
     * @mbg.generated
     */
    int insert(HealthLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table health_level
     *
     * @mbg.generated
     */
    HealthLevel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table health_level
     *
     * @mbg.generated
     */
    List<HealthLevel> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table health_level
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HealthLevel record);
}