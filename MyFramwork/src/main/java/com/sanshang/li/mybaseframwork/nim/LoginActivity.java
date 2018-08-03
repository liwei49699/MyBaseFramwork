package com.sanshang.li.mybaseframwork.nim;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.sanshang.li.mybaseframwork.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_open_database)
    TextView mTvOpenDatabase;
    @BindView(R.id.tv_request_permission)
    TextView mTvRequestPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doLogin();
            }
        });

        mTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doLogout();
            }
        });

        mTvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StatusCode status = NIMClient.getStatus();
                switch (status) {
                    //未定义
                    case  INVALID:
                
                        break;
                    //未登录/登录失败
                    case  UNLOGIN:

                        break;
                    //网络连接已断开
                    case  NET_BROKEN:

                        break;
                    //正在连接服务器
                    case  CONNECTING:

                        break;
                    //正在登录中
                    case  LOGINING:

                        break;
                    //正在同步数据
                    case  SYNCING:

                        break;
                    //已成功登录
                    case  LOGINED:

                        break;
                    //被其他端的登录踢掉
                    case  KICKOUT:

                        break;
                    //被同时在线的其他端主动踢掉
                    case  KICK_BY_OTHER_CLIENT:

                        break;
                    //被服务器禁止登录
                    case  FORBIDDEN:

                        break;
                    //客户端版本错误
                    case  VER_ERROR:

                        break;
                    //用户名或密码错误
                    case  PWD_ERROR:

                        break;
                }
                
                mTvState.append(status + "");
            }
        });

        mTvOpenDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean res = NIMClient.getService(AuthService.class).openLocalCache("123456");
                
                Toast.makeText(LoginActivity.this, "打开数据库：" + res, Toast.LENGTH_SHORT).show();
            }
        });

        mTvRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //申请权限
                LoginActivityPermissionsDispatcher.getMultiWithPermissionCheck(LoginActivity.this);

            }
        });

        //检测多客户端状态检测
        registerMulClient();
        //移动端和PC端互踢
        registerClientState();
        //在线状态，重连
        onlineStateAndRetry();
        //登陆成功后同步数据
        loginSynData();

    }

    //获取多个权限
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    //获取了相应的权限之后就会执行这个方法
    public void getMulti() {

        Toast.makeText(this, "getMulti", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
        //给用户解释要请求什么权限，为什么需要此权限
    void showRationale(final PermissionRequest request) {

        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要WRITE_EXTERNAL_STORAGE和RECORD_AUDIO权限，下一步将继续请求权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})//一旦用户拒绝了
    public void multiDenied() {

        Toast.makeText(this, "已拒绝一个或以上权限", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})//用户选择的不再询问
    public void multiNeverAsk() {

        Toast.makeText(this, "已拒绝一个或以上权限，并不再询问", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void loginSynData() {

        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
            @Override
            public void onEvent(LoginSyncStatus status) {

                if (status == LoginSyncStatus.BEGIN_SYNC) {
                    
                    Log.d("--TAG--", "LoginActivity onEvent()" + "开始同步");
                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                    
                    Log.d("--TAG--", "LoginActivity onEvent()" + "同步完成");
                }
            }
        }, true);
    }

    private void onlineStateAndRetry() {

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode> () {
                    public void onEvent(StatusCode status) {

                        if (status.wontAutoLogin()) {
                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                        }

                        int value = status.getValue();

                        boolean b = status.shouldReLogin();

                        boolean b1 = status.wontAutoLoginForever();

                        Log.d("--TAG--", "LoginActivity onEvent()" + status.getValue());
                    }
                }, true);
    }

    private void registerClientState() {

//        NIMClient.getService(AuthService.class).kickOtherClient().setCallback(new RequestCallback<Void>() {
//            @Override
//            public void onSuccess(Void param) {
//                // 踢出其他端成功
//                Log.d("--TAG--", "LoginActivity onSuccess()" + "踢出其他端成功");
//            }
//
//            @Override
//            public void onFailed(int code) {
//                // 踢出其他端失败，返回失败code
//                Log.d("--TAG--", "LoginActivity onSuccess()" + "踢出其他端失败，返回失败code");
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//                // 踢出其他端错误
//                Log.d("--TAG--", "LoginActivity onSuccess()" + "踢出其他端错误");
//
//            }
//        });
    }

    private void registerMulClient() {

        Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
            @Override
            public void onEvent(List<OnlineClient> onlineClients) {

                if (onlineClients == null || onlineClients.size() == 0) {
                    return;
                }

                OnlineClient client = onlineClients.get(0);
                switch (client.getClientType()) {
                    case ClientType.Windows:
                        // PC端
                        Log.d("--TAG--", "LoginActivity onEvent()" + "PC端");
                        break;
                    case ClientType.MAC:
                        // MAC端
                        Log.d("--TAG--", "LoginActivity onEvent()" + "MAC端");

                        break;
                    case ClientType.Web:
                        // Web端
                        Log.d("--TAG--", "LoginActivity onEvent()" + "Web端");

                        break;
                    case ClientType.iOS:
                        // IOS端
                        Log.d("--TAG--", "LoginActivity onEvent()" + "IOS端");

                        break;
                    case ClientType.Android:
                        // Android端
                        Log.d("--TAG--", "LoginActivity onEvent()" + "Android端");

                        break;
                    default:
                        break;
                }

                NIMClient.getService(AuthService.class).kickOtherClient(client).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        // 踢出其他端成功
                        Log.d("--TAG--", "LoginActivity onSuccess()" + "踢出其他端成功");
                    }

                    @Override
                    public void onFailed(int code) {
                        // 踢出其他端失败，返回失败code
                        Log.d("--TAG--", "LoginActivity onSuccess()" + "踢出其他端失败，返回失败code");
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // 踢出其他端错误
                        Log.d("--TAG--", "LoginActivity onSuccess()" + "踢出其他端错误");

                    }
                });
            }
        };

        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, true);
    }

    private void doLogout() {

        NIMClient.getService(AuthService.class).logout();
    }

    public void doLogin() {

        // config...
        //账号
        String account = "123456";
        final String password = "123456";
        LoginInfo info = new LoginInfo(account,password);
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {

                        Log.d("--TAG--", "LoginActivity onSuccess()" + password);

                    }

                    @Override
                    public void onFailed(int code) {

                        Log.d("--TAG--", "LoginActivity onFailed()" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                        Log.d("--TAG--", "LoginActivity onException()" + exception);
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };

        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
