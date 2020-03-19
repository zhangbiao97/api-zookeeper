package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.AsyncCallback;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class CreateCallBack implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("创建节点：" + path);
        System.out.println((String) ctx);
    }
}
