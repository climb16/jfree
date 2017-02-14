package com.jfree.registry.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2017-1-20 14:11
 */
public class ZookeeperClient implements Watcher {

    private static final String CONNECTION_STR = "101.200.131.11:2181";
    private static final int SESSION_TIMEOUT = 10000;

    private static CountDownLatch latch = new CountDownLatch(1);

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 创建 zookeeper session
     * @return
     */
    public ZooKeeper create(){
        try {
            //发送链接申请
            ZooKeeper zk = new ZooKeeper(CONNECTION_STR, SESSION_TIMEOUT, this);
            //等待zookeeper session 创建
            latch.await();
            return zk;
        } catch (IOException e) {
            logger.debug("zookeeper create fail... ", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) latch.countDown();
    }
}