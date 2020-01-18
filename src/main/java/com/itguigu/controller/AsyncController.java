package com.itguigu.controller;

import com.itguigu.service.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author whz
 * @create 2020-01-17 17:09
 * @desc TODO: add description here
 **/
@Controller
public class AsyncController {
  @ResponseBody
  @RequestMapping("/createOrder")
  public DeferredResult<Object> creataOrder() {
    DeferredResult<Object> deferredResult = new DeferredResult<Object>(3000L,"createOrder timeout.");
    DeferredResultQueue.save(deferredResult); //请求保存
    return deferredResult;
  }
  @RequestMapping("/create")
  @ResponseBody
  public static String create() {
    // 创建订单
    String orderId = UUID.randomUUID().toString();
    DeferredResult<Object> deferredResult = DeferredResultQueue.get();
    deferredResult.setResult(orderId); // 将触发 /createOrder 请求返回给客户端
    return "success ===> " + orderId;
  }
  /**
   * 参考官网： https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web.html#mvc-ann-async-processing
   * 1. 控制器返回Callable
   * 2. SpringMVC 将异步处理：将Callable提交给TaskExecutor线程池（SpringMVC提供），使用隔离的线程执行
   * 3.DispatcherServlet 和所有的 Filter 退出 Servlet 容器线程 ，而响应依然保持打开（还未给浏览器响应）
   * 4. Callable返回结果，SpringMVC将请求重新派发给，恢复之前的处理
   * 5. 根据Callable返回结果，Spring MVC继续处理（接收步骤4来的请求-视图渲染...完整的流程）
   *    这从控制台Interceptor打印也可以看出（执行了两次 preHandle()）：
   *      preHandle.../springmvc_annotation_war_exploded/async01 //打印一下
   *      Main thread start: Thread[http-apr-8080-exec-1,5,main]==>1579252443125
   *      Main thread end: Thread[http-apr-8080-exec-1,5,main]==>1579252443128
   *      =============== 至此，DispatcherServlet以及所有Filter退出web容器线程 ==================
   *      =============== 等待Callable执行 =================
   *      Biz thread start: Thread[MvcAsync1,5,main]==>1579252443141
   *      Biz thread end: Thread[MvcAsync1,5,main]==>1579252443143
   *      =============== Callable执行完成 =================
   *      =============== 再次处理重新派发的Request请求，这里不用再执行目标Controller方法了，而是把Callable返回值作为结果======
   *      preHandle.../springmvc_annotation_war_exploded/async01 // 又打印一次，对应步骤5处理步骤4重新转发的请求
   *      postHandle...  // Callalbe的返回值作为目标方法的最终返回值，不再执行目标方法
   *      afterCompletion..
   *    从上面过程可以看出，在异步请求场景下，普通Interceptor无法拦截完成处理过程了
   *    异步专用拦截器：
   *      1）原生API的 javax.servlet.AsyncListener
   *      2）SpringMVC提供的异步拦截器：https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web.html#mvc-ann-async-interception
   *         实现 AsyncHandlerInterceptor 接口
   *
   */
  @ResponseBody
  @RequestMapping("/async01")
    public Callable<String> async01() {
    System.out.println("Main thread start: " + Thread.currentThread() + "==>" + System.currentTimeMillis());
    Callable<String> callable = new Callable<String>() {
        @Override public String call() throws Exception {
          System.out.println("Biz thread start: " + Thread.currentThread() + "==>" + System.currentTimeMillis());
          Thread.sleep(2);
          System.out.println("Biz thread end: " + Thread.currentThread() + "==>" + System.currentTimeMillis());
          return "Callable<String> async01()";
        }
      };
    System.out.println("Main thread end: " + Thread.currentThread() + "==>" + System.currentTimeMillis());
      return callable;
    }
}