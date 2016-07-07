package com.jfree.ioc;

/**
 * beanFactory 接口，定义了beanFactory基本规范
 * @author xiao
 * @date 2016/06/13
 * @version 1.0.0
 */
public interface BeanFactory {

    /**
     * 根据name获取bean对象
     * @param name
     * @return
     */
    Object getBean(String name);

    /**
     * 根据类型获取bean
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> requiredType);

    /**
     * 获取指定name的bean类型
     * @param name
     * @return
     */
    Class<?> getType(String name);

}