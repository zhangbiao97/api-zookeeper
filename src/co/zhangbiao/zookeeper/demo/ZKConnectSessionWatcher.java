package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ZKConnectSessionWatcher implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZKConnectSessionWatcher.class);

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    public static void main(String[] args) {
        try {
            ZooKeeper zk = new ZooKeeper(ZK_SERVER_PATH, TIMEOUT, new ZKConnectSessionWatcher());
            long sessionId = zk.getSessionId();
            String ssid = "0x" + Long.toHexString(sessionId);
            System.out.println(ssid);
            byte[] sessionPasswd = zk.getSessionPasswd();
            logger.info("客户端开始连接ZooKeeper服务器......");
            logger.info("连接状态：{}", zk.getState());
            Thread.sleep(1000);
            logger.info("连接状态：{}", zk.getState());
            Thread.sleep(200);
            //开始会话重连
            logger.info("开始会话重连......");
            ZooKeeper zkSession = new ZooKeeper(ZK_SERVER_PATH, TIMEOUT, new ZKConnectSessionWatcher(), sessionId,
                    sessionPasswd);
            logger.info("重新连接状态zkSession：{}", zkSession.getState());
            Thread.sleep(1000);
            logger.info("重新连接状态zkSession：{}", zkSession.getState());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("接收到watch通知：{}", event);
    }
}
