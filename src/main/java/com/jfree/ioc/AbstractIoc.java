package com.jfree.ioc;

import com.jfree.core.config.JfreeConfigReader;
import com.jfree.ioc.holder.Bean;
import com.jfree.ioc.holder.BeanHolder;
import com.jfree.util.FileUtil;
import com.jfree.util.StringUtil;
import com.jfree.web.ServletContextResource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * Ioc 默认实现
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/6/24 16:11
 */
public abstract class AbstractIoc implements Ioc, BeanHolder {

    Logger logger = LogManager.getLogger(AbstractIoc.class);

    abstract void setReader();

    protected JfreeConfigReader reader;


    @Override
    public void init() {
        setReader();
        //加载容器
        reader.init();
        //初始化容器
        for (int i = 0; i < beanEnties.size(); i++){
            Bean bean = beanEnties.get(i);
            logger.debug("bean[id= " + bean.getBeanId() + ",\t class= " + bean.getBeanClass() + ",\t " +
                    "initMethod= " + bean.getInitMethod() + ",\t destroyedMethod= " + bean.getDestroyedMethod() + "] loading...");
            try {
                //反射获取生成bean实例
                Class<?> clazz = Class.forName(bean.getBeanClass());
                Object obj = clazz.newInstance();
                bean.setBeanType(clazz);
                bean.setInstance(obj);
                beanMap.put(bean.getBeanId(), bean);
                beanMapType.put(bean.getBeanType(), bean);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                beanEnties.remove(i);
                logger.error("bean[id= " + bean.getBeanId() + ", class= " + bean.getBeanClass() + "] load faild..." ,  e);
            }
        }

        //基本参数注入, 调用初始化方法
        for (Bean bean : beanEnties){
            logger.debug("bean[id= " + bean.getBeanId() + ",\t class= " + bean.getBeanClass() + ",\t " +
                    "initMethod= " + bean.getInitMethod() + ",\t destroyedMethod= " + bean.getDestroyedMethod() + "] loading...");try {
                //基本参数注入
                Iterator<Map.Entry<String, Object>> it = bean.getPropertise().entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String, Object> entity = it.next();
                    Class<?> clazz = bean.getBeanType();
                    PropertyDescriptor pd = new PropertyDescriptor(
                            entity.getKey(), clazz);
                    pd.getWriteMethod().invoke(bean.getInstance(), new Object[]{entity.getValue()});
                }
                //调用初始化方法
                if (!StringUtil.isEmpty(bean.getInitMethod())){
                    Method method = bean.getBeanType().getMethod(bean.getInitMethod());
                    method.invoke(bean.getInstance(), new Object[0]);
                }
            } catch (IllegalAccessException |  IntrospectionException
                    | NoSuchMethodException | InvocationTargetException e) {
                logger.error("bean[id= " + bean.getBeanId() + ", class= " + bean.getBeanClass() + "] init[DI、INIT] faild..." ,  e);
            }
        }
    }

    @Override
    public void destroyed() {
        for (int i = 0; i < beanEnties.size(); i++){
            Bean bean = beanEnties.get(i);
            logger.debug("bean[id= " + bean.getBeanId() + ",\t class= " + bean.getBeanClass() + ",\t " +
                    "initMethod= " + bean.getInitMethod() + ",\t destroyedMethod= " + bean.getDestroyedMethod() + "] destroyed...");
            try {
                if (!StringUtil.isEmpty(bean.getDestroyedMethod())){
                    Method method = bean.getBeanType().getMethod(bean.getDestroyedMethod());
                    method.invoke(bean.getInstance(), new Object[0]);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                logger.error("bean[id= " + bean.getBeanId() + ", class= " + bean.getBeanClass() + "] destroyed faild..." ,  e);
            }
        }
    }
}