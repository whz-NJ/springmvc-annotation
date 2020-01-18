package com.itguigu.service;

import org.springframework.stereotype.Service;

/**
 * @author whz
 * @create 2020-01-17 11:12
 * @desc TODO: add description here
 **/
@Service
public class HelloService {
    public String sayHello(String name) {
      return "hello " + name;
    }
}