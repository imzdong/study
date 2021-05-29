package org.imzdong.study.mybatis.simple;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * query(MappedStatement ms, Object parameter, RowBounds rowBounds,
 * ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql)
 */

@Intercepts({@Signature(
  type= Executor.class,
  method = "query",
  /*args = {MappedStatement.class,Object.class, RowBounds.class,
          ResultHandler.class, CacheKey.class, BoundSql.class}*/
  args = {MappedStatement.class,Object.class, RowBounds.class,
          ResultHandler.class}
          )})
public class ExamplePlugin implements Interceptor {

  private Properties properties = new Properties();
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    // implement pre processing if need
    Object returnObject = invocation.proceed();
    // implement post processing if need
    return returnObject;
  }
  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}