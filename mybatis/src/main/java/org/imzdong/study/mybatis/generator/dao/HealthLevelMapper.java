package org.imzdong.study.mybatis.generator.dao;

import java.util.List;
import org.imzdong.study.mybatis.generator.model.HealthLevel;

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