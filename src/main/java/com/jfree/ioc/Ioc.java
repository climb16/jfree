package com.jfree.ioc;

/**
 * Ioc 基层接口，定义ioc实现的基本规范
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/6/24 16:11
 */
public interface Ioc {

    /**
     * ioc容器初始化
     */
    void init();

    /**
     * 容器销毁
     */
    void destroyed();
}