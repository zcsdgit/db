package com.zcs.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Aspect
@Slf4j
@Component
public class SysCacheAspect {
	//@Pointcut("execution(*com.zcs.pj.sys.service..*.find*(..))")//find改成*就是万能配置
	@Pointcut("@annotation(com.zcs.pj.common.annotation.RequiredCache)")//被此注解修饰的方法在执行时会经过此aop
	public void doCachePointCut() {}
	@Around("doCachePointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		log.info("从cache中取数据");
		Object result = jp.proceed();
		log.info("将查询结果放到cache");
		
		return result;
	}
}
