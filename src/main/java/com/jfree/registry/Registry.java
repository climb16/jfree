package com.jfree.registry;

/** 服务注册器
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-23 16:05
 */
public interface Registry {

    /**
     * 注册
     * @param serviceName
     * @param serviceAddress
     */
    void regist(String serviceName, String serviceAddress);

}
