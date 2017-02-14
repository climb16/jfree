package com.jfree.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfree.registry.client.SynZookeeper;

/** zk 实现服务注册
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-23 13:38
 */
public class ZkServiceRegistry implements Registry {

    //同步zk操作类
    SynZookeeper synZookeeper = new SynZookeeper ();

    Logger logger = LoggerFactory.getLogger (getClass ());

    private static final String SEPARATOR = "/";

    //默认端口
    private static final String PORT = "80";
    
    private static String IP = "";
    
    /**
     * 服务注册中心节点
     */
    private String serviceRegistryRoot = "/service_registry_root";

    public String getServiceRegistryRoot() {
        return serviceRegistryRoot;
    }

    public void setServiceRegistryRoot(String serviceRegistryRoot) {
        this.serviceRegistryRoot = serviceRegistryRoot;
    }

    /**
     * 初始化服务根节点
     */
    public void init(){
        if (!synZookeeper.exists (this.serviceRegistryRoot)){
            synZookeeper.createServiceNode (this.serviceRegistryRoot, "服务注册中心");
            logger.info (serviceRegistryRoot + "init success!");
        }
    }

    @Override
    public void regist(String serviceName, String serviceAddress) {

        if (!synZookeeper.exists (this.serviceRegistryRoot + SEPARATOR + serviceName)) {
            synZookeeper.createServiceNode (this.serviceRegistryRoot + SEPARATOR + serviceName, "");
        }
        if (!synZookeeper.exists (this.serviceRegistryRoot + SEPARATOR + serviceName + SEPARATOR + serviceAddress)) {
            synZookeeper.createAddressNode (this.serviceRegistryRoot + SEPARATOR + serviceName + SEPARATOR + serviceAddress, "");
        }
    }
}