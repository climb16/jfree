package com.jfree.registry.annotation;

import java.lang.annotation.*;

/**
 * 服务注册，只有被此注解类注解的类才能被注册中心注册
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-20 16:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegistryBean {
}
