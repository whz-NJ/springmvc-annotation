package com.itguigu.config;

/**
 * @author whz
 * @create 2020-01-17 11:01
 * @desc TODO: add description here
 **/

import com.itguigu.controller.MyFirstInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

// SpringMVC容器，把默认的过滤规则useDefaultFilters禁用掉，这样只扫描 @Controller 注解对应组件，
// 必须把 useDefaultFilters 设置为true，否则 includeFilters（只扫描）参数就不能生效
// 参考 https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web.html#mvc-config-customize
// 对 WebMvcConfigurer 各个接口方法的说明，比如： configureDefaultServletHandling 和 spring-mvc.xml <mvc:default-servlet-handler/> 配置作用一样。
//  但 WebMvcConfigurer 接口方法太多了，我们一般不需要，可以用其子类：WebMvcConfigurerAdapter
// WebMvcConfigurerAdapter 把 WebMvcConfigurer 所有接口都实现了，只是是空方法，我们可以重写需要的方法即可。
@EnableWebMvc //作用和 spring-mvc.xml 里的 <mvc:annotation-driven> 配置一样

@ComponentScan(value="com.itguigu", includeFilters = {
    @ComponentScan.Filter(type= FilterType.ANNOTATION, classes={ Controller.class})}, useDefaultFilters = false)
public class AppConfig extends WebMvcConfigurerAdapter {

   // 定制视图解析器
  @Override public void configureViewResolvers(ViewResolverRegistry registry) {
    // super.configureViewResolvers(registry);
    // registry.jsp(); // 配置JSP视图解析器，这里不带参数，返回 jsp("/WEB-INF/", ".jsp")
                    // 表示从 WEB-INF 下找所有 *.jsp 文件，默认所有的jsp从 /WEB-INF 目录找
    registry.jsp("/WEB-INF/views/", "*.jsp");

  }
  // 静态资源访问（这样SpringMVC的 @RequestMapping 注解找不到的静态资源访问URL，都丢给tomcat
  // 作用和 spring-mvc.xml 的  <mvc:default-servlet-handler /> 配置一样
  @Override public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  //拦截器配置，参考 https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web.html#mvc-config-interceptors
  // 作用和 spring-mvc.xml 里的 mvc:interceptors 配置一样
  @Override public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**"); //拦截任意请求
  }
}