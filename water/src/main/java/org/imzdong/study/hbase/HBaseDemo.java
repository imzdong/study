package org.imzdong.study.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.Iterator;

/**
 * 使用者会通过接口而不是具体类型来工作。在 HBase 1.0 中，从 ConnectionFactory 中获取一个 Connection 对象，
 * 然后根据需要从中获取 Table，Admin 和 RegionLocator 的实例。完成后，关闭获取的实例。最后，确保在退出之前清理 Connection 实例。
 * Connections 是重量级对象，
 * 但是线程安全，因此可以在应用程序中创建并保持一个实例。Table，Admin 和 RegionLocator 实例是轻量级的。
 * 它们可以随时创建，然后在使用后立即释放掉。有关新 HBase 1.0 API 的使用示例，请参阅Client Package Javadoc 说明。
 * https://hbase.apache.org/apidocs/org/apache/hadoop/hbase/client/ConnectionFactory.html
 * @author admin
 * @date 2021/4/9
 */
public class HBaseDemo {

    public static void main(String[] args) throws Exception{
        Configuration config = HBaseConfiguration.create();
        String hbaseHost = "127.0.0.1";
        String tableName = "table_test";
        // Until 2.x.y versions
        config.set("hbase.zookeeper.quorum", hbaseHost);
        //or Starting 3.0.0 version
        //config.set("hbase.masters", "localhost:1234");
        Connection connection = ConnectionFactory.createConnection(config);
        Table table = connection.getTable(TableName.valueOf(tableName));
        try {
            // Use the table as needed, for a single operation and a single thread
            //selfPut(table);
            ResultScanner scanner = table.getScanner("col_name".getBytes());
            Iterator<Result> iterator = scanner.iterator();
            while (iterator.hasNext()){
                Result next = iterator.next();
                System.out.println(new String(next.getRow()));
            }
        } finally {
            table.close();
            connection.close();
        }
    }

    private static void selfPut(Table table) throws IOException {
        String putOperate = "put_01";
        Put put = new Put(putOperate.getBytes());
        //liezu
        put.addColumn("col_name".getBytes(), "row1".getBytes(), "ddd".getBytes());
        table.put(put);
    }
}
