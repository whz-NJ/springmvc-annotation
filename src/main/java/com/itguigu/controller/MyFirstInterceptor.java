package com.itguigu.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author whz
 * @create 2020-01-17 15:15
 * @desc TODO: add description here
 **/
public class MyFirstInterceptor implements HandlerInterceptor {

  @Override public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception {
    System.out.println("preHandle..." + request.getRequestURI());
    return true; // 表示是否继续调用后续其他Interceptor处理
  }
  //目标方法执行正确以后执行
  @Override public void postHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler, ModelAndView modelAndView)
      throws Exception {
    System.out.println("postHandle...");
  }
  //页面响应后执行
  @Override public void afterCompletion(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    System.out.println("afterCompletion...");
  }
}