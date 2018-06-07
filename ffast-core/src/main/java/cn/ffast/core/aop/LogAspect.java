package cn.ffast.core.aop;

import cn.ffast.core.annotations.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @description: 日志Aop
 * @copyright:
 * @createTime: 2017/11/14 14:13
 * @author：dzy
 * @version：1.0
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 日志切入点
     */
    @Resource
    private LogPoint logPoint;

    /**
     * 保存系统操作日志
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 调用出错
     */
    @Around(value = "@annotation(cn.ffast.core.annotations.Log)")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * 解析Log注解
         */
        Method currentMethod = currentMethod(joinPoint);
        Log log = currentMethod.getAnnotation(Log.class);
        Log classLog = joinPoint.getTarget().getClass().getAnnotation(Log.class);
        /**
         * 日志入库
         */
        String logStr = null;
        if (classLog != null) {
            logStr = classLog.value() + "-" + log.value();
        } else {
            logStr = log.value();
        }
        if (log != null && logPoint != null) {
            logPoint.saveLog(joinPoint, currentMethod.getName(), logStr);
        }

        /**
         * 方法执行
         */
        return joinPoint.proceed();
    }

    /**
     * 获取当前执行的方法
     *
     * @param joinPoint  连接点
     * @return 方法
     */
    private Method currentMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        return currentMethod;
    }

    public LogPoint getLogPoint() {
        return logPoint;
    }

    public void setLogPoint(LogPoint logPoint) {
        this.logPoint = logPoint;
    }

}