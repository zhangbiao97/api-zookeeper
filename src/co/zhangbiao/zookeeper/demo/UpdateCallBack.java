package co.zhangbiao.zookeeper.demo;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * Create By ZhangBiao
 * 2020/3/19
 */
public class UpdateCallBack implements AsyncCallback.StatCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println("rc：" + rc);
        System.out.println("修改成功：" + path);
        System.out.println((String) ctx);
        System.out.println("stat：" + stat.getVersion());
    }

}
