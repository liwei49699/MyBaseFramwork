package com.sanshang.li.mybaseframwork.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sanshang.li.mybaseframwork.R;

public class JniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        JniUtils utils = new JniUtils();
        String name = utils.getName();
        String string = utils.getString();
        
        Log.d("--TAG--" + "String:" + string, "JniActivity onCreate()" + "name=" + name);
    }
}
