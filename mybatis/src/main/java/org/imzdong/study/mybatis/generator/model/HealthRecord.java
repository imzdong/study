package org.imzdong.study.mybatis.generator.model;

public class HealthRecord {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_record.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_record.level_id
     *
     * @mbg.generated
     */
    private Integer level_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_record.user_id
     *
     * @mbg.generated
     */
    private Integer user_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_record.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_record.id
     *
     * @return the value of health_record.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_record.id
     *
     * @param id the value for health_record.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_record.level_id
     *
     * @return the value of health_record.level_id
     *
     * @mbg.generated
     */
    public Integer getLevel_id() {
        return level_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_record.level_id
     *
     * @param level_id the value for health_record.level_id
     *
     * @mbg.generated
     */
    public void setLevel_id(Integer level_id) {
        this.level_id = level_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_record.user_id
     *
     * @return the value of health_record.user_id
     *
     * @mbg.generated
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_record.user_id
     *
     * @param user_id the value for health_record.user_id
     *
     * @mbg.generated
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_record.remark
     *
     * @return the value of health_record.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_record.remark
     *
     * @param remark the value for health_record.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}