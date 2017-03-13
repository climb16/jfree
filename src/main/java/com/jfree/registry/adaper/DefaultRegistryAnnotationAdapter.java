package com.jfree.registry.adaper;

import java.lang.reflect.Method;
import com.jfree.registry.HttpMethod;
import com.jfree.registry.ServiceModel;
import com.jfree.registry.annotation.RegistryService;

/**
 * 默认注解适配器
 * 默认使用 jfree web容器
 * @author xiao
 * @date 2017年2月5日 下午2:53:31
 */
public class DefaultRegistryAnnotationAdapter implements RegistryAnnotationAdapter {

	@Override
	public ServiceModel getServiceModelAdapter(Class<?> clazz, Method method) {
		ServiceModel serviceModel = new ServiceModel();
		RegistryService registryService = method.getAnnotation(RegistryService.class);
		if(registryService != null) {
			serviceModel.setServiceName(registryService.serviceName());
			serviceModel.setServiceAddress(registryService.serviceAddress());
			//
			serviceModel.setServiceMethod(HttpMethod.POST);
		}
		return serviceModel;
	}

}
