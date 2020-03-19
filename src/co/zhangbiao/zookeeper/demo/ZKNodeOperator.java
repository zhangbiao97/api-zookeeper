package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ZKNodeOperator implements Watcher {

    private ZooKeeper zooKeeper;

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    public ZKNodeOperator() {
    }

    public ZKNodeOperator(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, TIMEOUT, new ZKNodeOperator());
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

    /**
     * 创建zk节点
     *
     * @param path
     * @param data
     * @param acls
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls) {
        String result = "";
        try {
            /**
             * 同步或异步创建节点，都不支持子节点的递归创建，异步有一个callback函数
             * 参数：
             * path：创建的路径
             *
             * data：存储数据的byte[]
             *
             * acl：控制权限策略
             *      Ids.OPEN_ACL_UNSAFE -> world:anyone:cdrwa
             *      CREATOR_ALL_ACL -> auth:user:password:cdrwa
             *
             * createMode：节点类型，是一个枚举
             *      PERSISTENT：持久节点
             *      PERSISTENT_SEQUENTIAL：持久顺序节点
             *      EPHEMERAL：临时节点
             *      EPHEMERAL_SEQUENTIAL：临时顺序节点
             */
            String ctx = "{'create':'success'}";
            //result = zooKeeper.create(path, data, acls, CreateMode.EPHEMERAL);
            zooKeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(),
                    ctx);
            System.out.println("创建节点：\t" + result + "\t成功...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    @Override
    public void process(WatchedEvent event) {

    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKNodeOperator zkNodeOperator = new ZKNodeOperator(ZK_SERVER_PATH);
        //创建节点
        /*zkNodeOperator.createZKNode("/testNode", "testNode".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE);*/
        //修改节点数据
        /*Stat stat = zkNodeOperator.getZooKeeper().setData("/testNode", "xyz".getBytes(), 0);
        System.out.println(stat.getVersion());*/
        /*String ctx = "{'update':'success'}";
        zkNodeOperator.getZooKeeper().setData("/testNode", "abc".getBytes(), 1, new UpdateCallBack()
                , ctx);
        Thread.sleep(2000);*/
        //删除节点
        String ctx = "{'delete':'success'}";
        zkNodeOperator.getZooKeeper().delete("/testNode", 2, new DeleteCallBack(), ctx);
        Thread.sleep(2000);
    }

}
