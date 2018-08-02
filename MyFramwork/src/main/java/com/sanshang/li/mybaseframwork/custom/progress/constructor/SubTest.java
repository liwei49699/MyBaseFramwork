package com.sanshang.li.mybaseframwork.custom.progress.constructor;

/**
 * Created by li on 2018/7/23.
 * WeChat 18571658038
 * author LiWei
 */

public class SubTest extends Test {

    /**
     * 默认的构造被重写私有化了，
     * 必须实现一个可继承的构造器
     * @param className
     */
    public SubTest(String className) {
        super(className);
    }
}
