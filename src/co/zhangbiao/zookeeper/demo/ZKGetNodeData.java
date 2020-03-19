package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ZKGetNodeData implements Watcher {

    private ZooKeeper zooKeeper;

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    private static Stat stat = new Stat();

    private static CountDownLatch countDown = new CountDownLatch(1);

    public ZKGetNodeData() {
    }

    public ZKGetNodeData(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, TIMEOUT, new ZKGetNodeData());
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

    @Override
    public void process(WatchedEvent event) {
        try {
            if (event.getType() == Event.EventType.NodeDataChanged) {
                ZKGetNodeData zkGetNodeData = new ZKGetNodeData(ZK_SERVER_PATH);
                byte[] result = zkGetNodeData.getZooKeeper().getData("/imooc", true, stat);
                System.out.println("更改后的值：" + new String(result));
                System.out.println("版本号变化Version：" + stat.getVersion());
                countDown.countDown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKGetNodeData zkGetNodeData = new ZKGetNodeData(ZK_SERVER_PATH);
        /**
         * 参数：
         * path：节点路径
         *
         * watch：true或false，注册一个watch事件
         *
         * stat：状态
         */
        byte[] result = zkGetNodeData.getZooKeeper().getData("/imooc", true, stat);
        System.out.println("当前值：" + new String(result));
        countDown.await();
    }
}
