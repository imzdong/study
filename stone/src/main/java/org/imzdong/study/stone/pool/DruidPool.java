package org.imzdong.study.stone.pool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/23
 */
public class DruidPool {

    private final static Logger logger = LoggerFactory.getLogger(DruidPool.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(DruidPool.class.getResourceAsStream("/druid.properties"));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            Connection connection = dataSource.getConnection();
            String sql = "select * from user;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                logger.info(resultSet.getString("id")+"-"+resultSet.getString("name"));
            }
        } catch (Exception e) {
            logger.error("数据库连接：",e);
        }


    }
}
