package org.imzdong.study.mybatis.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @description:
 * @author: Winter
 * @time: 2020年8月31日, 0031 18:33
 */
public class JdbcDemo {

    public static void main(String[] args) throws Exception{
        String url = "jdbc:mysql://127.0.0.1:3306/world?serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from user where id = ?");
        preparedStatement.setInt(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String result01 = resultSet.getString(1);
            String result02 = resultSet.getString(2);
            System.out.println(String.format("id:%s; name:%s",result01,result02));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
