package com.zcs.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringShiroConfig {
	/**
	 * @Bean 描述的方法，返回值会交给spring管理
	 * @Bean 一般应用在第三方bean资源
	 */
	@Bean
	public SecurityManager newSecurityManager(@Autowired Realm realm ,@Autowired CacheManager cacheManager) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		sManager.setCacheManager(cacheManager);//将缓存对象注入给SecurityManager对象
		return sManager;
	}
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactory(@Autowired SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		//假如没有认证请求先访问此认证的url
		sfBean.setLoginUrl("/doLoginUI");
		//定义map指定请求过滤规则，lingkeHashmap有序
		LinkedHashMap<String, String> map=new LinkedHashMap<>();
		//静态资源允许匿名访问
		map.put("/bower_components/**", "anon");//anon表示允许匿名访问
		map.put("/build/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/user/doLogin", "anon");
		map.put("/doLogout", "logout");//自动查LoginUrl
		map.put("/**", "authc");//必须认证
		sfBean.setFilterChainDefinitionMap(map);
		return sfBean;
	}
	//*************************************************************************
	//授权设置
	//1.配置shiro中bean对象的生命周期管理
	@Bean("lifecycleBeanPostProcessor")//不起别名默认是方法名或类名首字母小写
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {// 配置bean对象的生命周期管理
		return new LifecycleBeanPostProcessor();
	}
	//配置代理
	//2.配置代理创建器对象(此对象负责为所有advisor类型的对象创建代理对象,底层AOP)
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {//通过此配置要为目标业务对象创建代理对象
		return new DefaultAdvisorAutoProxyCreator();
	}
	//授权属性的Advisor配置
	//advisor对象，此对象中的方法要检测你要执行的业务方法上是否有@RequiresPermissions注解，有此注解系统底层会调用
	//proxyCreator对象为方法所在类创建代理对象,然后通过代理对象实现权限控制(AOP)
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}
	//缓存设置
	@Bean("memoryConstrainedCacheManager")
	public MemoryConstrainedCacheManager newMemoryConstrainedCacheManager() {
		return new MemoryConstrainedCacheManager();
	}
}
