package com.itguigu;

import com.itguigu.config.AppConfig;
import com.itguigu.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author whz
 * @create 2020-01-17 10:54
 * @desc Web容器启动时创建对象，调用方法来初始化IOC容器和前端控制器
 * 它会由 spring-web.jar 里 javax.servlet.ServletContainerInitializer 文件指定的
 * SpringServletContainerInitializer#onStartup() 拉起
 **/
public class MyWebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {
  //获取跟容器的配置类（类似Spring配置文件，之前通过 ServletContextListener，根据web.xml，创建父容器，这里通过代码实现）
  @Override protected Class<?>[] getRootConfigClasses() {
    return new Class[]{ RootConfig.class };
  }
  //获取Web容器的配置类（SpringMVC相关组件的配置类），子容器
  @Override protected Class<?>[] getServletConfigClasses() {
    return new Class[] { AppConfig.class };
  }
  //获取DispatcherServlet（前端控制器）的映射信息
  // 这里返回 / 表示拦截所有请求，包括我们的静态资源（js/png），但不包括 *.jsp 页面请求。
  // /* 会拦截 *.jsp 页面在内的所有请求，不要使用。jsp页面是tomcat的jsp引擎解析的，不是springMVC解析。
  @Override protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}