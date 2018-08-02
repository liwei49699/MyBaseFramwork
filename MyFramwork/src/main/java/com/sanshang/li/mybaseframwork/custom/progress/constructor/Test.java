package com.sanshang.li.mybaseframwork.custom.progress.constructor;

/**
 * Created by li on 2018/7/23.
 * WeChat 18571658038
 * author LiWei
 */

public class Test {

    /**
     * 构造器私有
     * 子类不能继承私有构造器
     * 必须重写可继承的构造器
     * @param i
     */
    private Test(Integer i){
    }

    /**
     * 同一个包内可访问
     */
    Test(float name) {
    }

    /**
     * 都可以访问
     * @param className
     */
    public Test(String className){

    }
}
