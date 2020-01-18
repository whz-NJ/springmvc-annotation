package com.itguigu.controller;

import com.itguigu.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author whz
 * @create 2020-01-17 11:10
 * @desc TODO: add description here
 **/
@Controller
public class HelloController {
     @Autowired HelloService helloService;
    @ResponseBody // 直接写出去到客户端（而不是一个视图名）
    @RequestMapping("/hello")
    public String hello() {
      WebApplicationContext webAc = ContextLoader
          .getCurrentWebApplicationContext();
      ServletContext servletContext = webAc.getServletContext();
      String realPath = servletContext.getRealPath("/");
      System.out.println("realPath="+realPath); // C:\Code3\springmvc-annotation\webapp\WEB-INF\classes\artifacts\springmvc_annotation_war_exploded\

      String hello = helloService.sayHello("tomcat..");
      return hello;
    }
  @RequestMapping("/suc")
    public String success() {
    WebApplicationContext webAc = ContextLoader.getCurrentWebApplicationContext();
    ServletContext servletContext = webAc.getServletContext();
    String realPath = servletContext.getRealPath("/");
    System.out.println("realPath2="+realPath);
    return "success"; // 会与视图解析器配置的路径/后缀进行拼接，找到对应的  /WEB-INF/views/success.jsp
    }
}