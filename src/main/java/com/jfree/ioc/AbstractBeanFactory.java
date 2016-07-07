package com.jfree.ioc;

import com.jfree.ioc.holder.Bean;
import com.jfree.ioc.holder.BeanHolder;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/6/27 13:23
 */
public abstract class AbstractBeanFactory implements BeanFactory, BeanHolder {

    @Override
    public Object getBean(String name) {
        Bean bean = beanMap.get(name);
        return bean != null ? bean.getInstance() : null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        Bean bean = beanMapType.get(requiredType);
        return bean != null ? (T) bean.getInstance() : null;
    }

    @Override
    public Class<?> getType(String name) {
        Bean bean = beanMap.get(name);
        return bean != null ? bean.getBeanType() : null;
    }
}