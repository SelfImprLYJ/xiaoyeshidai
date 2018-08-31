package com.xiaoyeshidai.jf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

//    private ImageView iv_spash_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        iv_spash_bg = findViewById(R.id.iv_spash_bg);

//        iv_spash_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                },2*1000);
//            }
//        });
    }
}
