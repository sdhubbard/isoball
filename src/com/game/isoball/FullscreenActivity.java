package com.game.isoball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class FullscreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
        
        FrameLayout frameLayout = (FrameLayout)findViewById(android.R.id.content);
        LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.options_layout, null);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        layoutParams.gravity = Gravity.BOTTOM;
        view.setLayoutParams(layoutParams);
        
        frameLayout.addView(view);
        view.setGravity(Gravity.BOTTOM);
        
    }
}