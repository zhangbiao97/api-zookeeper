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
public class ZKConnect implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZKConnect.class);

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    public static void main(String[] args) {
        /**
         * 客户端和zk服务端连接是一个异步的过程
         * 当连接成功后，客户端会收到一个watch通知
         *
         * 参数：
         * connectString：连接服务器的ip字符串，比如：“10.11.12.106:2181”
         * 可以是一个ip，也可以是多个ip，一个ip代表单机，多个ip代表集群
         *
         * sessionTimeout：超时时间，心跳接收不到了，那就超时
         *
         * watcher：通知事件，如果有对应的事件触发，则会收到一个通知；如果不需要，那就设置为null
         *
         * sessionId：会话的ID
         *
         * sessionPasswd：会话密码，当会话丢失后，可以依据sessionId和sessionPasswd重新获取会话
         *
         * canBeReadOnly：可读，当这个物理机节点断开后，还是可以读到数据的，只是不能写，此时数据被读取到的
         * 可能是旧数据，此处建议设置为false，不推荐使用！
         *
         */
        try {
            ZooKeeper zk = new ZooKeeper(ZK_SERVER_PATH, TIMEOUT, new ZKConnect());
            logger.info("客户端开始连接ZooKeeper服务器......");
            logger.info("连接状态：{}", zk.getState());
            Thread.sleep(2000);
            logger.info("连接状态：{}", zk.getState());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("客户端连接失败！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("接收到watch通知：{}", event);
    }
}
