package cn.ffast.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @description:
 * @copyright:
 * @createTime: 2017/11/14 14:55
 * @author：dzy
 * @version：1.0
 */
public interface LogPoint {
    /**
     * 保存日志
     * @param joinPoint
     * @param methodName
     * @param operate
     */
    void saveLog(ProceedingJoinPoint joinPoint, String methodName, String operate);
}
