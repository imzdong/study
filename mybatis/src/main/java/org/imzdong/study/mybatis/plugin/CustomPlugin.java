package org.imzdong.study.mybatis.plugin;

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
 * @description: 自定义插件
 * mybatis插件（准确的说应该是around拦截器，因为接口名是interceptor，而且invocation.proceed要自己调用，配置中叫插件）功能非常强大，可以让我们无侵入式的对SQL的执行进行干涉，从SQL语句重写、参数注入、结果集返回等每个主要环节，典型的包括权限控制检查与注入、只读库映射、K/V翻译、动态改写SQL。
 *
 * MyBatis默认支持对4大对象（Executor，StatementHandler，ParameterHandler，ResultSetHandler）上的方法执行拦截，具体支持的方法为：
 *
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)，主要用于sql重写。
 * ParameterHandler (getParameterObject, setParameters)，用于参数处理。
 * ResultSetHandler (handleResultSets, handleOutputParameters)，用于结果集二次处理。
 * StatementHandler (prepare, parameterize, batch, update, query)，用于jdbc层的控制。
 * 大多数情况下仅在Executor做插件比如SQL重写、结果集脱敏，ResultSetHandler和StatementHandler仅在高级场景中使用，而且某些场景中非常有价值。
 * @author: Winter
 * @time: 2020年8月6日, 0006 13:43
 */
@Intercepts({
        @Signature(type = Executor.class,
                method = "query",
                args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class CustomPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
