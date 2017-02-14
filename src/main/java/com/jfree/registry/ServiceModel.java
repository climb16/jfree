package com.jfree.registry;

/**
 * 服务模型
 * @author xiao
 * @date 2017年2月5日 下午2:32:15
 */
public class ServiceModel {
	
	/**
	 * 服务名称
	 */
	private String serviceName;

	/**
	 * 服务地址
	 */
	private String serviceAddress;
	
	/**
	 * 请求方法
	 */
	private HttpMethod serviceMethod;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public HttpMethod getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(HttpMethod serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
}
