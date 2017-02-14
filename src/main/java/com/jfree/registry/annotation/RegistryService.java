package com.jfree.registry.annotation;

import java.lang.annotation.*;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-20 16:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegistryService {

    /**
     * 服务名称
     * @return
     */
    String serviceName();
    
    /**
     * 服务地址
     * @return
     */
    String serviceAddress() default "";
}
