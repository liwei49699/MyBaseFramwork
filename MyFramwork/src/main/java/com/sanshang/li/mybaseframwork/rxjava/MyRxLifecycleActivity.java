package com.sanshang.li.mybaseframwork.rxjava;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.RxBaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyRxLifecycleActivity extends RxBaseActivity implements OnClickListener {

    @BindView(R.id.btn_lifecycle)
    Button mBtnLifecycle;
    @BindView(R.id.btn_bus_bind)
    Button mBtnBusBind;
    @BindView(R.id.btn_bus_send)
    Button mBtnBusSend;
    @BindView(R.id.btn_third_bind)
    Button mBtnThirdBind;
    @BindView(R.id.btn_third_send)
    Button mBtnThirdSend;
    @BindView(R.id.btn_author_request)
    Button mBtnAuthorRequest;
    @BindView(R.id.btn_author_request_multi)
    Button mBtnAuthorRequestMulti;
    @BindView(R.id.btn_author_request_multi_all)
    Button mBtnAuthorRequestMultiAll;
    private RxPermissions mRxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rx_lifecycle);

        ButterKnife.bind(this);

        //三方注册
        RxBus.get().register(this);

        mBtnLifecycle.setOnClickListener(this);
        mBtnBusBind.setOnClickListener(this);
        mBtnBusSend.setOnClickListener(this);
        mBtnThirdBind.setOnClickListener(this);
        mBtnThirdSend.setOnClickListener(this);
        mBtnAuthorRequest.setOnClickListener(this);
        mBtnAuthorRequestMulti.setOnClickListener(this);
        mBtnAuthorRequestMultiAll.setOnClickListener(this);

        mRxPermissions = new RxPermissions(this); // where

        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d("--TAG3--", "MyRxLifecycleActivity onSubscribe()" + d.isDisposed());
                    }

                    @Override
                    public void onNext(Long s) {

                        Log.d("--TAG3--", "MyRxLifecycleActivity onNext()" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("--TAG3--", "MyRxLifecycleActivity onError()" + e);
                    }

                    @Override
                    public void onComplete() {

                        Log.d("--TAG3--", "MyRxLifecycleActivity onComplete()" + "完成");
                    }
                });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {

            case R.id.btn_lifecycle:

                myRxLifecycle();
                break;

            case R.id.btn_bus_bind:

                myRxBusBind();

                EventBus.getDefault().register(MyRxLifecycleActivity.this);

                break;

            case R.id.btn_bus_send:

                myRxBusSend();

                EventBus.getDefault().post(new RxBusEvent("123123"));
                break;

            case R.id.btn_third_bind:

                userThirdSend();
                break;

            case R.id.btn_third_send:

                userThirdSendTag();
                break;

            case R.id.btn_author_request:

                requestAuthor();
                break;

            case R.id.btn_author_request_multi:

                requestMultiAuthor();
                break;
            case R.id.btn_author_request_multi_all:

                requestMultiAuthorAll();
                break;
        }
    }

    private void requestMultiAuthorAll() {

        mRxPermissions.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                        if(permission.granted) {

                            Log.d("--TAG--", "MyRxLifecycleActivity accept()" + "全部允许" + permission.name);
                        } else if(permission.shouldShowRequestPermissionRationale) {

                            Log.d("--TAG--", "MyRxLifecycleActivity accept()" + "有拒绝" + permission.name);
                        } else {

                            Log.d("--TAG--", "MyRxLifecycleActivity accept()" + "不在访问" + permission.name);
                        }
                    }
                });
    }

    private void requestMultiAuthor() {

        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                        if(permission.granted) {
                         
                            Log.d("--TAG--", "MyRxLifecycleActivity accept()" + "允许" + permission.name);
                        } else if(permission.shouldShowRequestPermissionRationale) {

                            Log.d("--TAG--", "MyRxLifecycleActivity accept()" + "拒绝" + permission.name);
                        } else {
                            
                            Log.d("--TAG--", "MyRxLifecycleActivity accept()" + "不在访问" + permission.name);

                            boolean available = isIntentAvailable(MyRxLifecycleActivity.this, getAppDetailSettingIntent());
                            
                            if(available) {

                                startActivity(getAppDetailSettingIntent());
                            }
                        }
                    }
                });
    }


    //判断Intent是否有效
    public boolean isIntentAvailable(Context context, Intent intent) {

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        
        Log.d("--TAG--", "MyRxLifecycleActivity isIntentAvailable()" + list);
        return list.size() > 0;
    }

    //跳到权限请求界面
    private Intent getAppDetailSettingIntent() {

        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= 9) {

            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {

            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }

    private void requestAuthor() {

        mRxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                        Toast.makeText(MyRxLifecycleActivity.this, "" + aBoolean, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void userThirdSendTag() {

        List events = new ArrayList<>();

        events.add("1");
        events.add("2");
        events.add("3");

        RxBus.get().post(BusAction.EAT_TEST,events);

    }

    private void userThirdSend() {

        //手动发送
        RxBus.get().post(new RxBusEvent("12313213"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(RxBusEvent event) {

        Log.d("--TAG--", "MyRxLifecycleActivity getMessage()" + event);
    }

    /**
     * 发送事件
     */
    private void myRxBusSend() {

        MyRxBus.getInstance().post(new RxBusEvent("MyRxBus Info!!!"));
    }

    /**
     * 绑定事件
     */
    private void myRxBusBind() {

        MyRxBus.getInstance().toObservable(RxBusEvent.class)
                .compose(this.<RxBusEvent>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<RxBusEvent>() {
                    @Override
                    public void accept(RxBusEvent event) throws Exception {

                        Log.d("--TAG--", "MyRxLifecycleActivity accept()" + event);
                    }
                });
    }

    /**
     * RxJava的生命周期
     */
    private void myRxLifecycle() {

        //自动解除绑定时机 好像写在不是生命周期的方法不起作用 最好还是手动绑定
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d("--TAG1--", "MyRxLifecycleActivity onSubscribe()" + d.isDisposed());
                    }

                    @Override
                    public void onNext(Long s) {

                        Log.d("--TAG1--", "MyRxLifecycleActivity onNext()" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("--TAG1--", "MyRxLifecycleActivity onError()" + e);
                    }

                    @Override
                    public void onComplete() {

                        Log.d("--TAG1--", "MyRxLifecycleActivity onComplete()" + "完成");
                    }
                });

        //手动设定解除绑定时机
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        //是否已经解除绑定
                        Log.d("--TAG2--", "MyRxLifecycleActivity onSubscribe()" + d.isDisposed());
                    }

                    @Override
                    public void onNext(Long o) {

                        Log.d("--TAG2--", "MyRxLifecycleActivity onNext()" + o);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("--TAG2--", "MyRxLifecycleActivity onError()" + e);
                    }

                    @Override
                    public void onComplete() {

                        Log.d("--TAG2--", "MyRxLifecycleActivity onComplete()" + "完成");
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("--TAG--", "MyRxLifecycleActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("--TAG--", "MyRxLifecycleActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
        //三方注销
        RxBus.get().unregister(this);
    }

    static class  BusAction {

        public static final String EAT_MORE="eat_more";
        public static final String EAT_TEST="eat_test";
    }

    //消息的发送与接收是通过参数决定的 tag是一个字符串
    //接收消息
    @com.hwangjr.rxbus.annotation.Subscribe
    public void eat(RxBusEvent event) {

        Log.d("--TAG--", "MyRxLifecycleActivity eat()" + event);
    }

    //接收到tag的消息
//    @com.hwangjr.rxbus.annotation.Subscribe(thread = EventThread.IO, tags = {@Tag(BusAction.EAT_MORE)})
//    public void eatMore(List<RxBusEvent> foods) {
//        // purpose
//        Log.d("--TAG--", "MyRxLifecycleActivity eatMore()" + foods.size());
//    }

    //启动时生效
    //无需调用RxBus.get().post(this);
    @com.hwangjr.rxbus.annotation.Produce
    public RxBusEvent produceFood() {

        return new RxBusEvent("This is bread!");
    }

    //接收一个列表参数，并且限制这个接收线程是IO,标记为BusAction.EAT_MORE ,标记需要和发送者一致，线程不要需要
    @com.hwangjr.rxbus.annotation.Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.EAT_TEST)
            }
    )
    public void eatMore(ArrayList foods) {
        // purpose
        Toast.makeText(this,"eatMore",Toast.LENGTH_SHORT).show();
    }
    //    @Produce
//    public String produceFood() {
//        return "This is bread!";
//    }

    //带TAG需自动发送(测试不是)
    //也可手动发送
    /*@com.hwangjr.rxbus.annotation.Produce(
            thread = EventThread.IO,
            tags = {
                    @Tag(BusAction.EAT_TEST)
            }
    )
    public ArrayList produceMoreFood() {
        ArrayList list =new ArrayList<>();
        list.add("This");
        list.add("is");
        list.add("breads!");
        return list;
    }*/
}
