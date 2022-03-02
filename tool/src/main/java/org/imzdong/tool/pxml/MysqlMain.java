package org.imzdong.tool.pxml;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

/**
 * @author admin
 * @since 2021/10/5 下午3:36
 */
public class MysqlMain {

    public static void main(String[] args) throws Exception {

        // 注册 JDBC 驱动
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        //"com.mysql.cj.jdbc.Driver"

        //192.144.237.217
        //jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
        String host = "192.144.237.217";
        String dbName = "test";
        String url = "jdbc:mysql://" + host + ":3306/" +
                dbName +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "tx";
        String passWord = "TigerRoot!77";

        Connection conn = DriverManager.getConnection(url, user, passWord);

        URL resource = DomParseXml.class.getClassLoader().getResource("");
        String path = resource.getPath();
        System.out.println(path);
        String xmlPath = path + "pxml/" + "data.xml";

        List<Order> orders = DomParseXml.orgDomParseXml(xmlPath);

        /**
         * 1、 题目要求： 要求输出 销售排名前10位的商品名称及对应总销售数量
         *
         * 结果输出示例：
         * 冰箱  29999
         * 电视  19999
         * 空调  9999
         */
        Map<String, List<Order>> nameMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getName));
        Map<String, Integer> nameSum = nameMap.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream().mapToInt(Order::getSaleNum).sum())
        );
        Map<String, Integer> result = nameSum.entrySet().stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e2,
                                LinkedHashMap::new)
                );
        System.out.println(result);

        //insert(conn, orders);


        // 完成后关闭
        conn.close();
    }


    private static void query(Connection conn) throws Exception{

        String sql = "select * from order";
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        List<Object> list = new ArrayList<>();

        while (rs.next()){
            String orderId = rs.getString("order_id");
            String productId = rs.getString("product_id");
            String name = rs.getString("name");
            String category = rs.getString("category");
            BigDecimal price = rs.getBigDecimal("price");
            Integer saleNum = rs.getInt("sale_num");
            Date saleDate = rs.getDate("sale_date");
            Order order = new Order();
            order.setOrderId(orderId);
            order.setProductId(productId);
            order.setName(name);
            order.setCategory(category);
            order.setPrice(price);
            order.setSaleNum(saleNum);
            order.setSaleDate(saleDate);
            list.add(order);
        }

        System.out.println(list);

        rs.close();
        stmt.close();

    }


    private static void insert(Connection conn, List<Order> list) throws Exception{

        //这里必须设置为false，我们手动批量提交
        conn.setAutoCommit(false);
        //这里需要注意，SQL语句的格式必须是预处理的这种，就是values(?,?,...,?)，否则批处理不起作用

        String sql = "INSERT INTO `order`" +
                "(order_id, product_id, name, category, price, sale_num, sale_date)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (Order order:list) {
            stmt.setString(1, order.getOrderId());
            stmt.setString(2, order.getProductId());
            stmt.setString(3, order.getName());
            stmt.setString(4, order.getCategory());
            stmt.setBigDecimal(5, order.getPrice());
            stmt.setInt(6, order.getSaleNum());
            stmt.setDate(7, new Date(order.getSaleDate().getTime()));
            //将要执行的SQL语句先添加进去，不执行
            stmt.addBatch();
        }
        stmt.executeBatch();
        conn.commit();
        stmt.close();

    }

}
