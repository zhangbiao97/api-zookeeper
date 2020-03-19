package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ZKNodeExist implements Watcher {

    private ZooKeeper zooKeeper;

    private static final Logger logger = LoggerFactory.getLogger(ZKNodeExist.class);

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    private static CountDownLatch countDown = new CountDownLatch(1);

    public ZKNodeExist() {
    }

    public ZKNodeExist(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, TIMEOUT, new ZKNodeExist());
        } catch (IOException e) {
            e.printStackTrace();
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKNodeExist zkNodeExist = new ZKNodeExist(ZK_SERVER_PATH);
        String path = "/imooc-back";
        Stat stat = zkNodeExist.getZooKeeper().exists(path, true);
        if (stat != null) {
            logger.info("查询的节点{}版本号为：{}", path, stat.getVersion());
        } else {
            logger.error("节点{}不存在", path);
        }
        countDown.await();
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeCreated) {
            logger.info("节点创建");
            countDown.countDown();
        }
        if (event.getType() == Event.EventType.NodeDataChanged) {
            logger.info("节点数据已改变");
            countDown.countDown();
        }
        if (event.getType() == Event.EventType.NodeDeleted) {
            logger.info("节点删除");
            countDown.countDown();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
}
