package org.imzdong.study.stone.shardingsphere;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.imzdong.study.stone.pool.DruidPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ShardingSphere {

    private final static Logger logger = LoggerFactory.getLogger(ShardingSphere.class);

    public static void main(String[] args) throws Exception{
        //创建分片规则配置类
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //创建分表规则配置类
        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration("user", "shard${01..02}.user${0..1}");
        //shard${01..02}.user${0..1} 表示：shard01: user0，user1; shard02：user0，user1；
        //${begin..end} 表示的是一个从"begin"到"end"的范围区间，而多个 ${expression} 之间可以用"."符号进行连接，代表多个表达式数值之间的一种笛卡尔积关系。
        //创建分布式主键生成配置类
        Properties properties = new Properties();
        properties.setProperty("worker.id", "33");
        KeyGeneratorConfiguration keyGeneratorConfig = new KeyGeneratorConfiguration("SNOWFLAKE", "id", properties);
        shardingRuleConfig.setDefaultKeyGeneratorConfig(keyGeneratorConfig);
        shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfig);
        //根据性别分库，一共分为 2 个库
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("sex", "ds${sex % 2}"));
        //根据用户 ID 分表，一共分为 2 张表//user${id % 2}
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", null));
        //final Map<String, DataSource> dataSourceMap, final ShardingRuleConfiguration shardingRuleConfig, final Properties props
        DataSource realDataSource = ShardingDataSourceFactory.createDataSource(getMultipleDataSource(), shardingRuleConfig, new Properties());
        Connection connection = realDataSource.getConnection();
        String sql = "select * from user;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            logger.info(resultSet.getString("id")+"-"+resultSet.getString("name"));
        }
    }

    private static Map<String, DataSource> getMultipleDataSource(){
        Properties properties = new Properties();
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        try {
            properties.load(DruidPool.class.getResourceAsStream("/druid.properties"));
            DataSource shardO1 = DruidDataSourceFactory.createDataSource(properties);
            dataSourceMap.put("shard01",shardO1);
            DataSource shardO2 = DruidDataSourceFactory.createDataSource(properties);
            dataSourceMap.put("shardO2",shardO2);
        }catch (Exception e){
            logger.error("获取多连接池失败：",e);
        }
        return dataSourceMap;
    }


}
