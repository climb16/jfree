package com.jfree.ioc.holder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/6/27 13:51
 */
public interface BeanHolder {

    List<Bean> beanEnties = new ArrayList<>();

    Map<String, Bean> beanMap = new HashMap<>();

    Map<Class<?>, Bean> beanMapType = new HashMap<>();

}