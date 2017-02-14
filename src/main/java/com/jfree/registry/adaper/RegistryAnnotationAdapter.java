package com.jfree.registry.adaper;

import java.lang.reflect.Method;
import com.jfree.registry.ServiceModel;

/**
 * 注解适配器
 * @author xiao
 * @date 2017年2月5日 上午11:46:40
 */
public interface RegistryAnnotationAdapter {

	/**
	 * 适配服务模型
	 * @return
	 */
	ServiceModel getServiceModelAdaptrt(Class<?> clazz, Method method);
}
