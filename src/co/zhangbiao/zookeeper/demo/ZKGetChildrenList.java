package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ZKGetChildrenList implements Watcher {

    private ZooKeeper zooKeeper;

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    private static CountDownLatch countDown = new CountDownLatch(1);

    public ZKGetChildrenList() {
    }

    public ZKGetChildrenList(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, TIMEOUT, new ZKGetChildrenList());
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
        ZKGetChildrenList zkGetChildrenList = new ZKGetChildrenList(ZK_SERVER_PATH);
        //同步获取子节点列表
        /*List<String> childrenList = zkGetChildrenList.getZooKeeper().getChildren("/imooc", true);
        childrenList.forEach(System.out::println);*/
        //异步获取子节点列表
        /*String ctx = "{'childrenList':'success'}";
        zkGetChildrenList.getZooKeeper().getChildren("/imooc", true, new ChildrenListCallBack(), ctx);*/
        //异步获取子节点列表方式2
        String ctx = "{'childrenList':'success'}";
        zkGetChildrenList.getZooKeeper().getChildren("/imooc", true,
                (cr, path, ctx2, children, stat) -> {
                    System.out.println("cr：" + cr);
                    System.out.println("path：" + path);
                    System.out.println((String) ctx2);
                    children.forEach(System.out::println);
                    System.out.println(stat.getVersion());
                }, ctx);
        countDown.await();
    }

    @Override
    public void process(WatchedEvent event) {
        try {
            if (event.getType() == Event.EventType.NodeChildrenChanged) {
                System.out.println("NodeChildrenChanged");
                ZKGetChildrenList zkGetChildrenList = new ZKGetChildrenList(ZK_SERVER_PATH);
                //同步获取子节点列表
                List<String> childrenList = zkGetChildrenList.getZooKeeper().getChildren(event.getPath(), false);
                childrenList.forEach(System.out::println);
                countDown.countDown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
}
