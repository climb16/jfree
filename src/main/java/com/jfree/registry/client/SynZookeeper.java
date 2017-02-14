package com.jfree.registry.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-20 17:59
 */
public class SynZookeeper {

    Logger log = LoggerFactory.getLogger(SynZookeeper.class);

    //zc
    ZookeeperClient zc = new ZookeeperClient();

    ZooKeeper zk = zc.create();

    /**
     * 创建服务节点
     * @param registryPath 节点路径
     * @param data 节点内容
     * @return
     */
    public boolean createServiceNode(String registryPath, String data){
       return this.createNode (registryPath, data, CreateMode.PERSISTENT);
    }

    /**
     * 创建地址节点
     * @param registryPath 节点路径
     * @param data 节点内容
     * @return
     */
    public boolean createAddressNode(String registryPath, String data){
        return this.createNode (registryPath, data, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * 创建节点
     * @param registryPath 节点路径
     * @param data 节点内容
     * @return
     */
    private boolean createNode(String registryPath, String data, CreateMode createMode){
        try {
            log.debug ("createNode [registryPath" + registryPath + ", data + " + data + " ]");
            String rc = zk.create(registryPath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
            log.debug("zk create result : " + rc);
        } catch (KeeperException e) {
            log.error ("createNode", e);
            return false;
        } catch (InterruptedException e) {
            log.error ("createNode", e);
            return false;
        }
        return true;
    }

    /**
     * 判断节点时候存在
     * @param registryName
     * @return
     */
    public boolean exists(String registryName){
        try {
            log.debug ("exists [registryName" + registryName + "]");
            Stat stat = zk.exists (registryName, null);
            if (stat != null){
                log.debug ("exists: " + stat.toString ());
                return true;
            }
        } catch (KeeperException e) {
            log.error ("exists", e);
        } catch (InterruptedException e) {
            log.error ("exists", e);
        }
        return false;
    }
}
