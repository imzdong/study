package org.imzdong.study.spring.tx.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class TxDaoImpl implements TxDao{

    private JdbcTemplate jdbcTemplate;

    @Override
    public int updateAge(int id, int age) {
        String sql = "update tx set age = ? where  id = ?";
        return jdbcTemplate.update(sql,age,id);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
