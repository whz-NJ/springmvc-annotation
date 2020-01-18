package com.itguigu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author whz
 * @create 2020-01-17 11:01
 * @desc TODO: add description here
 **/
//Spring根容器不扫描带有@Controller注解标注组件，它是扫描所有组件的缺省配置类
// useDefaultFilters 属性不用关闭（默认为true，打开）：扫描所有，
@ComponentScan(value="com.itguigu", excludeFilters = {
    @ComponentScan.Filter(type= FilterType.ANNOTATION, classes={ Controller.class})
})
public class RootConfig {

}