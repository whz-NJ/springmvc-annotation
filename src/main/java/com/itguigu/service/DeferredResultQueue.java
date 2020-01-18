package com.itguigu.service;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author whz
 * @create 2020-01-17 18:36
 * @desc TODO: add description here
 **/
public class DeferredResultQueue {
    private static Queue<DeferredResult<Object>> queue = new ConcurrentLinkedDeque<DeferredResult<Object>>();

    public static void save(DeferredResult<Object> deferredResult) {
      queue.add(deferredResult);
    }
  public static DeferredResult<Object> get() {
      return queue.poll();
  }
}