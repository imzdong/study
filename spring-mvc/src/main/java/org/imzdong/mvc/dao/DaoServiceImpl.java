package org.imzdong.mvc.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author zhoud
 * @see
 * @since 2021/2/21 12:07
 */
public class DaoServiceImpl implements DaoService{

    private JdbcTemplate jdbcTemplate;

    @Override
    public int update() {
        String sql = "update tx set age = ? where  id = ?";
        int num = jdbcTemplate.update(sql,23,1);
        return num;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
