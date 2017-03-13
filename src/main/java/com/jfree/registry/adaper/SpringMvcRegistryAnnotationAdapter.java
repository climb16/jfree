package com.jfree.registry.adaper;

import java.lang.reflect.Method;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jfree.registry.HttpMethod;
import com.jfree.registry.ServiceModel;
import com.jfree.registry.annotation.RegistryService;

/**
 * spring mvc
 * 
 * @author xiao
 * @date 2017年2月5日 下午3:11:28
 */
public class SpringMvcRegistryAnnotationAdapter implements RegistryAnnotationAdapter {

	@Override
	public ServiceModel getServiceModelAdapter(Class<?> clazz, Method method) {
		ServiceModel serviceModel = new ServiceModel();
		RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
		if(requestMapping != null) this.setServiceModel(serviceModel, requestMapping);
		requestMapping = method.getAnnotation(RequestMapping.class);
		if(requestMapping != null) this.setServiceModel(serviceModel, requestMapping);
		RegistryService registryService = method.getAnnotation(RegistryService.class);
		if(registryService != null) serviceModel.setServiceName(registryService.serviceName());
		return serviceModel;
	}

	/**
	 * 设置 service model
	 * 
	 * @param serviceModel
	 * @param requestMapping
	 */
	private void setServiceModel(ServiceModel serviceModel, RequestMapping requestMapping) {
		RequestMethod[] httpMethods = requestMapping.method();
		if (httpMethods != null && httpMethods.length > 0) {
			serviceModel.setServiceMethod(HttpMethod.valueOf(httpMethods[0].name()));
		}
		String[] strs = requestMapping.value();
		if (strs != null && strs.length > 0) {
			serviceModel.setServiceAddress(serviceModel.getServiceAddress() + strs[0]);
		}
	}
}
