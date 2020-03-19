package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.AsyncCallback;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class DeleteCallBack implements AsyncCallback.VoidCallback {

    @Override
    public void processResult(int rc, String path, Object ctx) {
        System.out.println("rc：" + rc);
        System.out.println("删除节点：" + path);
        System.out.println((String) ctx);
    }

}
