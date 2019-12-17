package org.imzdong.study.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月17日, 0017 14:28
 */
@Aspect
@Component
public class LogAspect {

    private static final LocalVariableTableParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut(value = "@annotation(logAnnotation)")
    public void advice(LogAnnotation logAnnotation){}

    @AfterReturning(pointcut = "advice(logAnnotation)",returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint,LogAnnotation logAnnotation,Object returnObj){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNamesSign = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        System.out.println("after parameterNamesSign:"+parameterNamesSign);
        ParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();//new AspectJAdviceParameterNameDiscoverer("advice(logAnnotation)");
        String[] parameterNames = pnd.getParameterNames(method);
        System.out.println("after parameterNames:"+parameterNames);
    }
    @Around(value = "advice(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.最关键的一步:通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        System.out.println("around parameterNamesSign:"+parameterNames);
        ParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();//new AspectJAdviceParameterNameDiscoverer("advice(logAnnotation)");
        String[] parameterNamesM = pnd.getParameterNames(methodSignature.getMethod());
        System.out.println("around parameterNames:"+parameterNamesM);
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "error";
    }

}
