package com.zcs.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcs.pj.common.annotation.RequiredLog;
import com.zcs.pj.common.utils.IPUtils;
import com.zcs.pj.sys.dao.SysLogDao;
import com.zcs.pj.sys.entity.SysLog;

import lombok.extern.slf4j.Slf4j;
/**
 * @Aspect 描述的类为一个切面类对象
 * 1)切入点 (切入扩展功能的点)
 * 2)通知(扩展功能)
 */
@Aspect
@Service
@Slf4j
//@Order(2)	//此注解控制配置类的加载顺序，数值越低优先级越高	
/**当切面1调用切面2时，如果切面1有事务，而切面2没有事务，并且transaction是readonly=true，
 * 	此时如果切面2存在写操作，那句会报错，因为事务是只读的无法执行写操作，所以此时执行顺序很重要，这时候就需要order注解	
 * 	注意：其中涉及到事务的传播特性，及没有事务的调用有事务的会创建一个新事务然后原先的事务取消，而有事务的调用没有事务的那没有事务的会参与到有事务的那里
 */
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	/**
	 * @Pointcut 注解用于定义切入点
	 * bean表达式为切入点表达式
	 * bean表达式内部指定的bean对象中所有方法为切入点(进行功能扩展的点)
	 */
	@Pointcut("bean(sysUserServiceImpl)")
	public void logPointCut() {}
	/**
	 * @Around 描述的方法为环绕通知，用于功能
	 * @param jp 连接点(封装要执行的方法信息)
	 * @return 目标方法的执行结果
	 * @throws Throwable 
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		try {
			long t1=System.currentTimeMillis();
		log.info("start"+t1);
		Object result = jp.proceed();//调用下一个切面方法或目标方法
		long t2=System.currentTimeMillis();
		log.info("after"+t2);
		saveObject(jp,(t2-t1));
		return result;
		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	private void saveObject(ProceedingJoinPoint jp,long time) throws JsonProcessingException, NoSuchMethodException, SecurityException {
		//1.获取用户行为信息
		//1.1获取目标对象类型
		Class<?> targetCls = jp.getTarget().getClass();
		//1.2获取目标对象方法
		//1.2.1获取方法签名对象,此对象封装了方法的相关信息
		MethodSignature ms=(MethodSignature) jp.getSignature();
		String methodName=targetCls.getName()+"."+ms.getName();
		Object[] args = jp.getArgs();
		String params="[]";
		if(args.length>0) {
			//jackson的序列化的方式：new ObjectMapper()//构建jackson中的对象转换器//writeValueAsString(args)-->将对象转换为json格式的字符串(序列化)
			params=new ObjectMapper().writeValueAsString(args);
		}
		//1.2.3获取操作名
		String operation="operation";
		//Method method=ms.getMethod();//获取的是接口方法，应该要实现类的方法
		Method targetMethod=targetCls.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
		RequiredLog rLog=targetMethod.getDeclaredAnnotation(RequiredLog.class);
		if(rLog!=null) {
			 operation=rLog.value();
		}
		//2.封装用户行为信息
		SysLog log=new SysLog();
		log.setIp(IPUtils.getIpAddr());
		log.setUsername("admin");
		log.setMethod(methodName);//目标类全名+方法名
		log.setParams(params);//方法实际参数
		log.setOperation(operation);//添加、修改、删除
		log.setTime(time);
		log.setCreatedTime(new Date());
		//3.将用户行为信息存储到数据库
		sysLogDao.insertObject(log);
		
	}
}
