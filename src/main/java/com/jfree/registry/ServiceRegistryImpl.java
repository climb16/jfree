package com.jfree.registry;

import com.jfree.core.JfreeClassLoader;
import com.jfree.registry.adaper.DefaultRegistryAnnotationAdapter;
import com.jfree.registry.adaper.RegistryAnnotationAdapter;
import com.jfree.registry.annotation.RegistryBean;
import com.jfree.registry.annotation.RegistryService;
import com.jfree.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 服务注册实现类
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-20 16:16
 */
public class ServiceRegistryImpl implements ServiceRegistry {

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 服务接口层扫描路径，扫描此包下所有类进行服务注册
     */
    private String scanBasePackages;

    /**
     * 类加载容器
     */
    private JfreeClassLoader classLoader = JfreeClassLoader.getJfreeClassLoader();

    /**
     * 注解适配器
     */
    private RegistryAnnotationAdapter adapter;
    
    /**
     * 服务注册器
     */
    Registry registry;

    private static final String SEPARATOR = ",";

    public String getScanBasePackages() {
        return scanBasePackages;
    }

    public void setScanBasePackages(String scanBasePackages) {
        logger.debug("set registry basepackage propertise: " + scanBasePackages);
        this.scanBasePackages = scanBasePackages;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }    

    public RegistryAnnotationAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(RegistryAnnotationAdapter adapter) {
		if (adapter == null) {
			adapter = new DefaultRegistryAnnotationAdapter();
		}
		this.adapter = adapter;
	}

	/**
     * 初始化方法，
     * 扫描 scanBasePackage 包下，所有被 @RegistryService 注解的的服务类
     * 所有被 @RegistryMethod 注解的接口，注册到服务中心
     */
    @Override
    public void init(){
        if (StringUtil.isEmpty(this.scanBasePackages)) {
            logger.info("scanBasePackages: " + this.scanBasePackages);
            return;
        }
        String[] scanBasePackages = this.scanBasePackages.split(SEPARATOR);
        for (String scanBasePackage : scanBasePackages){
            //获取包下所有类
            Set<Class<?>> classes = classLoader.getClassSet(scanBasePackage);
            for (Class<?> clazz : classes){        
              //如果系类被RegistryService注释，再说明此类需要注册
                if(clazz.isAnnotationPresent(RegistryBean.class)){
                    logger.info("registry bean : " + clazz + " start register...");
                    //获取服务类下所有的方法
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods){
                        RegistryService registryService = method.getAnnotation(RegistryService.class);
                        if(registryService != null){
                            logger.info("service: " + method + " start register...");                     
                            ServiceModel serviceModel = adapter.getServiceModelAdaptrt(clazz, method);     
                            if(serviceModel != null){
                            	String serviceName = serviceModel.getServiceName();
                                String serviceAddress = serviceModel.getServiceAddress();
                                logger.debug("service: " + method + " [serviceName: " + serviceName + ", serviceAddress: " + serviceAddress + "]");
                                if (StringUtil.isEmpty(serviceName) || StringUtil.isEmpty(serviceAddress)) throw new NullPointerException("serviceName or serviceAddress is null");
                                else {
                                    registry.regist(serviceName, serviceAddress);
                                    logger.info("service: " + method + " register success!");
                                }
                            }                    
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 注销服务
     */
    @Override
    public void destroy(){}
}
