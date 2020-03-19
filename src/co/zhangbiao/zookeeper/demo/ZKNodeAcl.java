package co.zhangbiao.zookeeper.demo;

import co.zhangbiao.zookeeper.utils.ACLUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ZKNodeAcl implements Watcher {

    private ZooKeeper zooKeeper;

    private static final Logger logger = LoggerFactory.getLogger(ZKNodeExist.class);

    private static final String ZK_SERVER_PATH = "10.11.12.106:2181";

    private static final Integer TIMEOUT = 5000;

    private static CountDownLatch countDown = new CountDownLatch(1);

    public ZKNodeAcl() {
    }

    public ZKNodeAcl(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, TIMEOUT, new ZKNodeAcl());
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

    public static void main(String[] args) throws Exception {
        ZKNodeAcl zkNodeAcl = new ZKNodeAcl(ZK_SERVER_PATH);
        /*String result = zkNodeAcl.getZooKeeper().create("/aclimooc", "aclimooc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println("result：" + result);*/
        //自定义用户权限
        /*ArrayList<ACL> acls = new ArrayList<>();
        Id zhangbiao1 = new Id("digest", ACLUtils.getDigestUserPwd("zhangbiao1:zhangbiao"));
        Id zhangbiao2 = new Id("digest", ACLUtils.getDigestUserPwd("zhangbiao2:zhangbiao"));
        acls.add(new ACL(ZooDefs.Perms.ALL, zhangbiao1));
        acls.add(new ACL(ZooDefs.Perms.CREATE, zhangbiao2));
        acls.add(new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.DELETE, zhangbiao2));
        String result = zkNodeAcl.getZooKeeper().create("/aclimooc/digest", "digest".getBytes(), acls, CreateMode.PERSISTENT);
        logger.info("创建节点：{}成功", result);*/
        //注册过的用户必须通过addAuthInfo才能操作节点，参考命令行 addauth
        /*zkNodeAcl.getZooKeeper().addAuthInfo("digest", "zhangbiao2:zhangbiao".getBytes());
        Stat stat = zkNodeAcl.getZooKeeper().setData("/aclimooc/digest", "new-digest".getBytes(), 0);
        logger.info("stat：{}", stat);*/
        // ip的方式
        /*ArrayList<ACL> acls = new ArrayList<>();
        Id ip = new Id("ip", "10.11.12.108");
        acls.add(new ACL(ZooDefs.Perms.ALL, ip));
        String result = zkNodeAcl.getZooKeeper().create("/aclimooc/ip", "ip".getBytes(), acls, CreateMode.PERSISTENT);
        logger.info("创建节点：{}成功", result);*/
        Stat stat = zkNodeAcl.getZooKeeper().setData("/aclimooc/ip", "123".getBytes(), 1);
        logger.info("stat：", stat);
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
}
