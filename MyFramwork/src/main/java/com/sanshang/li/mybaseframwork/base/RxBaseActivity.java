package com.sanshang.li.mybaseframwork.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class RxBaseActivity extends RxAppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        userRxLifecyle();
    }

    private void userRxLifecyle() {

        // Android组件库，里面定义了例如RxAppCompatActivity、RxFragment之类的Android组件
        // 内部引用了基础库和Android库，如果使用此库则无需再重复引用
        // compile 'com.trello.rxlifecycle2:rxlifecycle-components:2.1.0'

        //使用compose(bindToLifecycle())方法绑定Activity的生命周期，
        // 在onStart方法中绑定，在onStop方法被调用后就会解除绑定，以此类推。
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe();

        //指定在onDestroy方法被调用时取消订阅
        //将compose方法放在subscribeOn方法之前，如果在被观察者中执行了阻塞方法
        //比如Thread.sleep()，取消订阅后该阻塞方法不会被中断
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe();
    }
}
