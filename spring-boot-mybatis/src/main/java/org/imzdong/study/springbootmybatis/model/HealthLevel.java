package org.imzdong.study.springbootmybatis.model;

public class HealthLevel {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_level.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_level.level_name
     *
     * @mbg.generated
     */
    private String level_name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_level.id
     *
     * @return the value of health_level.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_level.id
     *
     * @param id the value for health_level.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_level.level_name
     *
     * @return the value of health_level.level_name
     *
     * @mbg.generated
     */
    public String getLevel_name() {
        return level_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_level.level_name
     *
     * @param level_name the value for health_level.level_name
     *
     * @mbg.generated
     */
    public void setLevel_name(String level_name) {
        this.level_name = level_name == null ? null : level_name.trim();
    }
}