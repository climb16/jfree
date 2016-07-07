package com.jfree.ioc.holder;

import java.util.Map;
import java.util.Objects;

/**
 * bean entity
 * @author xiao
 * @datetime 2016/6/24 16:03
 */
public class Bean {

    private String beanId;
    private Class<?> beanType;
    private String beanClass;
    private Object instance;
    private String initMethod;
    private String destroyedMethod;
    private Map<String, Object> propertise;

    public Bean() {
    }

    public Bean(String beanId, String beanClass, String initMethod, String destroyedMethod) {
        this.beanId = beanId;
        this.beanClass = beanClass;
        this.initMethod = initMethod;
        this.destroyedMethod = destroyedMethod;
    }

    public Bean(String beanId, Class<?> beanType, String beanClass, Objects instance) {
        this.beanId = beanId;
        this.beanType = beanType;
        this.beanClass = beanClass;
        this.instance = instance;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getDestroyedMethod() {
        return destroyedMethod;
    }

    public void setDestroyedMethod(String destroyedMethod) {
        this.destroyedMethod = destroyedMethod;
    }

    public Map<String, Object> getPropertise() {
        return propertise;
    }

    public void setPropertise(Map<String, Object> propertise) {
        this.propertise = propertise;
    }

    public void setProperty(String name, Object value){
        this.propertise.put(name, value);
    }
}