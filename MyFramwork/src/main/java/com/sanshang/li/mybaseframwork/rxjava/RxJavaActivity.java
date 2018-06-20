package com.sanshang.li.mybaseframwork.rxjava;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

public class RxJavaActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.btn_subscribe)
    Button mBtnSubscribe;
    @BindView(R.id.btn_consumer)
    Button mBtnConsumer;
    @BindView(R.id.btn_scheduler)
    Button mBtnScheduler;
    @BindView(R.id.btn_operator)
    Button mBtnOperator;
    @BindView(R.id.btn_filter)
    Button mBtnFilter;
    @BindView(R.id.btn_double_click)
    Button mBtnDoubleClick;
    @BindView(R.id.btn_combine)
    Button mBtnCombine;
    @BindView(R.id.btn_error_handle)
    Button mBtnErrorHandle;
    @BindView(R.id.btn_assist)
    Button mBtnAssist;
    @BindView(R.id.btn_condition)
    Button mBtnCondition;
    @BindView(R.id.btn_boolean)
    Button mBtnBoolean;
    @BindView(R.id.et_search)
    EditText mEtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        ButterKnife.bind(this);

        setTitleName("RxJava的简单使用");

        mBtnSubscribe.setOnClickListener(this);
        mBtnConsumer.setOnClickListener(this);
        mBtnScheduler.setOnClickListener(this);
        mBtnOperator.setOnClickListener(this);
        mBtnFilter.setOnClickListener(this);
        mBtnCombine.setOnClickListener(this);
        mBtnErrorHandle.setOnClickListener(this);
        mBtnAssist.setOnClickListener(this);
        mBtnCondition.setOnClickListener(this);
        mBtnBoolean.setOnClickListener(this);

        //使用RxBinding
        userRxBinding();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_subscribe :

                rxJava1();
                break;

            case R.id.btn_consumer :

                rxJava2();
                break;

            case R.id.btn_scheduler :

                rxJava3();
                break;

            case R.id.btn_operator :

                rxJava4();
                break;
            case R.id.btn_filter :

                rxJava5();
                break;
            case R.id.btn_combine :

                rxJava6();
                break;
            case R.id.btn_error_handle :

                rxJava7();
                break;
            case R.id.btn_assist :

                rxJava8();
                break;
            case R.id.btn_condition :

                rxJava9();
                break;
            case R.id.btn_boolean :

                rxJava10();
                break;
        }
    }

    private void rxJava10() {

        //all 不满足条件就终止发送
        Observable.just("1","2","3")
                .all(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {

                        if(TextUtils.equals(s,"2")) {

                            return false;
                        }

                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

                //也就只能判断数据是否发送完毕
                Log.d("--TAG--", "RxJavaActivity accept()" + aBoolean);
            }
        });

        //ToSortedList 排序 并一起发送
        Observable.just(2,5,8,11)
                .toSortedList()
                //自定义排序
//                .toSortedList(new Comparator<Integer>() {
//                                  @Override
//                                  public int compare(Integer o1, Integer o2) {
//
//                                      return 0;
//                                  }
//                              }
//                )
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + integers.size());
                    }
                });

        Observable.interval(1,TimeUnit.MILLISECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {

                        return "发送数据";
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + s);
            }
        });
    }

    private void rxJava9() {

        // ambArray 只发送一组数据
        Observable.ambArray(new ObservableSource<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {

                for (int i = 0; i < 10; i++) {

                    observer.onNext(i + "");
                }
            }
        }, new ObservableSource<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {

                for (int i = 10; i < 20; i++) {

                    observer.onNext(i + "");
                }
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + s);
            }
        });

        //defaultIfEmpty 没调用next 直接是complete 则会调用defaultIfEmpty
        Observable.just("")
                .defaultIfEmpty("数据为空")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + s);
                    }
                });
    }

    private void rxJava8() {

        //delay 延迟多长时间后发送
        //delay(,boolean) true 正常延迟 ,false 直接走异常 前面不执行
        Observable.just("1","2","A","4")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {

                        return Integer.parseInt(s);
                    }
                })
                .delay(2000,TimeUnit.MILLISECONDS,false)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + throwable.getMessage());
                    }
                });

        //do操作符的执行顺序 doOnSubscribe->doOnLifecycle->doOnNext->subscribe->doAfterNext-
        //                    ->doOnComplete->doAfterTerminate->doFinally
        Observable.just("12","34")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + "订阅时调用");
                    }
                })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + "到这儿可取消订阅");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                        Log.d("--TAG--", "RxJavaActivity run()" + "doOnLifecycle" );
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + "doOnNext:" + s);
                    }
                })
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + "doAfterNext:" + s);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {

                        Log.d("--TAG--", "RxJavaActivity run()" + "完成");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {

                        Log.d("--TAG--", "RxJavaActivity run()" + "doAfterTerminate:");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                        Log.d("--TAG--", "RxJavaActivity run()" + "doFinally:执行结束后调用");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + "收到消息:" + s);
                    }
                });

        //timeInterval 数据发送时间间隔
        //timestamp 同timeInterval 记录每次发送的时间戳
        Observable.just("1","2")
                .timeInterval()
                .subscribe(new Consumer<Timed<String>>() {
                    @Override
                    public void accept(Timed<String> timed) throws Exception {

                        long time = timed.time();

                        Log.d("--TAG--", "RxJavaActivity accept()" + time + "===" + timed.value());
                    }
                });
    }

    private void rxJava7() {

        //onErrorReturn 出错时 做一个转化发送之前的数据类型 且正常终止 不会走Error
        //onErrorResumeNext 重新发送一个Observable 不会走Error 单也不会走complete
        //onExceptionResumeNext 同onErrorResumeNext,不同的是 收到不是一个Exception也会拦截

        //retry(Predicate) 重新发射数据，true重新发送 false调用onError
        //retry(BiPredicate) 重新发射数据，true重新发送 false调用onError 有发送值
        //retry(int)    //最多让被观察者重新发射多少次
        //retryWhen 同retry 不同的是需重新发送Observable
        Observable.just("12","33","qw","34")
         .map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {

                return Integer.parseInt(s);
            }
        })
        .onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {

                return 0;
            }
        })
//        .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
//
//                Log.d("--TAG--", "RxJavaActivity apply()" + throwable.getMessage());
//                return new Observable<Integer>() {
//                    @Override
//                    protected void subscribeActual(Observer<? super Integer> observer) {
//
//                        observer.onNext(0);
//                    }
//                };
//            }
//        })
        /*.retry(new Predicate<Throwable>() {
            @Override
            public boolean test(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "retry错误: "+throwable.toString());

                //返回假就是不让重新发射数据了，调用观察者的onError就终止了。
                //返回真就是让被观察者重新发射请求
                return true;
            }
        })*/
        /*.retry(new BiPredicate<Integer, Throwable>() {
            @Override
            public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "retry错误: "+integer+" "+throwable.toString());

                //返回假就是不让重新发射数据了，调用观察者的onError就终止了。
                //返回真就是让被观察者重新发射请求
                return true;
            }
        })*/
//                .retry(3)    //最多让被观察者重新发射数据3次
//                .retry(3, new Predicate<Throwable>() {
//                    @Override
//                    public boolean test(@NonNull Throwable throwable) throws Exception {
//
//                        Log.d("--TAG--", "RxJavaActivity test()" + throwable.toString());
//                        //最多让被观察者重新发射数据3次，但是这里返回值可以进行处理
//                        //返回假就是不让重新发射数据了，调用观察者的onError就终止了。
//                        //返回真就是让被观察者重新发射请求
//                        return true;
//                    }
//                })
         .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

                Log.d("--TAG--", "RxJavaActivity run()" + "完成");
            }
        });
    }

    private void userRxBinding() {

        //结合RxBinding搜索时间过滤
        RxTextView.textChangeEvents(mEtSearch)
                .debounce(300,TimeUnit.MILLISECONDS)
                .flatMap(new Function<TextViewTextChangeEvent, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(TextViewTextChangeEvent event) throws Exception {

                        return Observable.just(event.text().toString());
                    }
                }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {

                if(TextUtils.isEmpty(s)) {

                    return false;
                } else {

                    return true;
                }
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()--结果:" + s);
            }
        });

        //throttleFirst 多长时间发射一次
        RxView.clicks(mBtnDoubleClick)
                .throttleFirst(1000,TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        Toast.makeText(RxJavaActivity.this, "一秒内只能点一次" + o, Toast.LENGTH_SHORT).show();
                        Log.d("--TAG--", "RxJavaActivity accept()" + "一秒内只能点一次" + o.getClass().getName());
                    }
                });

    }

    /**
     * 组合操作符
     */
    private void rxJava6() {

        //startWith 插入到第一个位置 先发送
        Observable.just("1",2,'3')
                .startWith("a")
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {

                        Log.d(Character.class + "--TAG--" + serializable.getClass(), "RxJavaActivity accept()" + serializable + "==" + serializable.getClass().getName());
                    }
                });

        //merge 合并 总和不会去除重复
        final Observable<String> observable1 = Observable.just("1", "2", "3");
        Observable<String> observable2 = Observable.just("1", "2", "c");
        Observable.merge(observable1,observable2)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + s);
                    }
                });

        //combineLatest 结合 1.最后最新的数据 2.每一个数据
        Observable<String> observableA = Observable.just("1", "2", "3");
        Observable<String> observableB = Observable.just("4", "4", "6","7");
        Observable.combineLatest(observableA, observableB, new BiFunction<String, String, Integer>() {
            @Override
            public Integer apply(String s, String s2) throws Exception {

                return Integer.parseInt(s + s2);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + integer);
            }
            //添加异常响应 异常后中断后面的发送
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + throwable.getMessage());
            }
        });

        //zip 同combineLatest 不同:一一对应,两两结合 14 2t 36 多出的数据将不会执行

        Observable.combineLatest(new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {

                observer.onNext("2131");
            }
        }, new ObservableSource<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {

                observer.onNext("asdad");
            }
        }, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {

                return s + s2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + s);
            }
        });

    }

    /**
     * 过滤操作符
     */
    private void rxJava5() {

        //double 两次数据发送时间间隔大于发送
        //filter 单个条件过滤
        //take 发送多少数据
        //注意顺序
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                for (int i = 0; i < 100; i++) {

                    emitter.onNext(i);

                    Thread.sleep(i * 100);

                    Log.d("--TAG--", "RxJavaActivity accept()" + Thread.currentThread().getName());

                }
            }
        }).subscribeOn(Schedulers.io())
                .take(4)   //发射前面四个数据
                .debounce(500,TimeUnit.MILLISECONDS)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {

                        if(integer < 10) {

                            return false;
                        } else {

                            return true;
                        }
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                        Log.d("--TAG--", "RxJavaActivity accept()" + integer + Thread.currentThread().getName());
                        //5 6 7 8 9 10.....
                    }
                });
    }

    /**
     * 操作符及其变换
     */
    private void rxJava4() {

        //map 简单变换
        Observable.just("12313")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {

                        if(!TextUtils.isEmpty(s)) {

                            return Integer.parseInt(s);
                        }
                        //魔法数字标识为空 避免异常
                        return 0;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + "变换为Integer:" + integer);
            }
        });

        //flatMap 将发射的数据 转化为Observable
        Observable.just("2123141","535435")
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(final String s) throws Exception {

                        return new ObservableSource<Integer>() {
                            @Override
                            public void subscribe(Observer<? super Integer> observer) {

                                int i = Integer.parseInt(s);
                                observer.onNext(i);
                            }
                        };
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<MyRxJava>>() {
                    @Override
                    public ObservableSource<MyRxJava> apply(final Integer integer) throws Exception {

                        return Observable.create(new ObservableOnSubscribe<MyRxJava>() {
                            @Override
                            public void subscribe(ObservableEmitter<MyRxJava> emitter) throws Exception {

                                MyRxJava rxJava = new MyRxJava(integer, String.valueOf(integer + 1));
                                emitter.onNext(rxJava);
                            }
                        });
                    }
                })
                .subscribe(new Consumer<MyRxJava>() {
            @Override
            public void accept(MyRxJava rxJava) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + rxJava);
            }
        });

        //buffer 缓存操作符
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                        for (int i = 0; i < 100; i++) {

                            emitter.onNext(i);
                         }
                    }
                    //5毫秒
                 }).buffer(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {

                        for (Integer integer : integers) {

                            Log.d("--TAG--", "RxJavaActivity accept()" + integer + Calendar.SECOND);
                        }
                    }
                });
    }

    /**
     * 线程调度
     */
    private void rxJava3() {

        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                Log.d("--TAG--", "RxJavaActivity subscribe()" + Thread.currentThread().getName());

                emitter.onNext("Thread");
            }
        });

        //创建消费者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + s + Thread.currentThread().getName());
            }
        };

        //消费者 事件发送之前执行
        Consumer<Disposable> beforeConsumer = new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + disposable.isDisposed() +
                        Thread.currentThread().getName());
            }
        };

        observable
                //被观察者发生线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(beforeConsumer)
                //之前线程
                .subscribeOn(AndroidSchedulers.mainThread())
                //观察者线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 拆分消费者 Consumer
     */
    private void rxJava2() {

        Observable<String> observable = Observable.just("消息1", "消息2");

        //Consumer 消费者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + s);
            }
        };

        Consumer<Throwable> errorConsumer = new Consumer<Throwable>() {

            @Override
            public void accept(Throwable throwable) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + throwable.getMessage());
            }
        };

        Action action = new Action() {

            @Override
            public void run() throws Exception {

                Log.d("--TAG--", "RxJavaActivity run()" + "完成");
            }
        };

        observable.subscribe(consumer, errorConsumer, action, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                Log.d("--TAG--", "RxJavaActivity accept()" + "第4个参数");
            }
        });
    }

    /**
     * 简单的Observer Observable实现订阅关系
     */
    private void rxJava1() {

        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                if (d != null) {

                    //输出为null
                    Log.d("--TAG--", "RxJavaActivity onSubscribe()" + d.toString());
                }

                boolean disposed = d.isDisposed();
                if (disposed) {

                    Log.d("--TAG--", "RxJavaActivity onSubscribe()" + "没有移除");
                } else {

                    Log.d("--TAG--", "RxJavaActivity onSubscribe()" + "已被移除");
                }
            }

            @Override
            public void onNext(String s) {

                Log.d("--TAG--", "RxJavaActivity onNext()" + s);
            }

            @Override
            public void onError(Throwable e) {

                Log.d("--TAG--", "RxJavaActivity onError()" + e.getMessage());
            }

            @Override
            public void onComplete() {

                Log.d("--TAG--", "RxJavaActivity onComplete()");
            }
        };

        //创建订阅者 同Observer RxJava2没使用
        final Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

                Log.d("--TAG--", "RxJavaActivity onSubscribe()" + s);
            }

            @Override
            public void onNext(String s) {

                Log.d("--TAG--", "RxJavaActivity onNext()" + s);
            }

            @Override
            public void onError(Throwable t) {

                Log.d("--TAG--", "RxJavaActivity onError()" + t.getMessage());
            }

            @Override
            public void onComplete() {

                Log.d("--TAG--", "RxJavaActivity onComplete()");
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("emitter");
                emitter.onNext("emitter1");
                emitter.onComplete();
                emitter.onNext("emitter1");
            }
        });

        observable.subscribe(observer);
    }


}
