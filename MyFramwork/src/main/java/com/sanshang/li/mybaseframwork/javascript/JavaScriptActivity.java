package com.sanshang.li.mybaseframwork.javascript;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.BaseActivity;
import com.sanshang.li.mybaseframwork.util.ToastUtils;

import java.math.MathContext;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JavaScriptActivity extends BaseActivity {

    @BindView(R.id.wv_java_script)
    WebView wvJavaScript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_script);
        ButterKnife.bind(this);


        //添加js调用接口类，通过Android这个字段 调用这个类的方法，记得在JsInterface里面的方法上加 @JavascriptInterface
        wvJavaScript.addJavascriptInterface(new JsInterface(),"Android");
    }

    class JsInterface {
        /**
         * 将会被js调用
         * JavascriptInterface 兼容高api
         *
         * @param id    视频id
         * @param path  视频路径
         * @param title 视频标题
         */
        @JavascriptInterface
        public void playVideo(int id, String path, String title) {

            if (!path.isEmpty()) {

                Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show();
                //调用播放器,注意这里调用系统自带播放器将会播放失败
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(path), "video/*");
                startActivity(intent);
            } else {
                Toast.makeText(mContext, "视频链接为空!", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 将可以被js调用。方法一定要public，不然调用不了
         * JavascriptInterface 兼容高的api
         */
        @JavascriptInterface
        public void showToast(){

            Toast.makeText(mContext, "Android代码弹出的Taost", Toast.LENGTH_SHORT).show();
        }
    }
}
