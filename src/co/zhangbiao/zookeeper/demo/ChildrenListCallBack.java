package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.AsyncCallback;

import java.util.List;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class ChildrenListCallBack implements AsyncCallback.ChildrenCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {
        System.out.println("rc：" + rc);
        System.out.println("路径：" + path);
        System.out.println((String) ctx);
        children.forEach(System.out::println);
    }

}
