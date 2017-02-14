package com.jfree.registry;

/**
 * 服务注册接口，定义服务的注册
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-20 14:06
 */
public interface ServiceRegistry {

	/**
     * 初始化方法，
     */
	void init();

	/**
     * 注销服务
     */
    void destroy();

}