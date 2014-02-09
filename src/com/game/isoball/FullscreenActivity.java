package com.game.isoball;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class FullscreenActivity extends Activity {
	private LinearLayout optionView;
	private ToggleButton optionsExpand;
	private ToggleButton optionsMusic;
	private ToggleButton optionsSound;
	private SharedPreferences sharedPreferences;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
        
        FrameLayout frameLayout = (FrameLayout)findViewById(android.R.id.content);
        
        optionView = (LinearLayout)getLayoutInflater().inflate(R.layout.options_layout, null);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        layoutParams.gravity = Gravity.BOTTOM;
        optionView.setLayoutParams(layoutParams);
        
        frameLayout.addView(optionView);
        optionView.setGravity(Gravity.BOTTOM);
        
        optionsExpand = (ToggleButton)optionView.findViewById(R.id.tb_options_expand);
        optionsMusic = (ToggleButton)optionView.findViewById(R.id.tb_options__music);
        optionsSound = (ToggleButton)optionView.findViewById(R.id.tb_options_sound);
        
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        
        optionsExpand.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					optionsMusic.setVisibility(View.VISIBLE);
					optionsSound.setVisibility(View.VISIBLE);
				} else {
					optionsMusic.setVisibility(View.GONE);
					optionsSound.setVisibility(View.GONE);
				}
			}
		});
        
        configureMusicOptions();
        configureSoundOptions();
    }
    
    private void configureMusicOptions() {
    	boolean musicOn = true;
    	final String musicSettingKey = getString(R.string.music_setting_key);
    	
    	musicOn = sharedPreferences.getBoolean(musicSettingKey, true);
    	optionsMusic.setChecked(musicOn);
    	
    	optionsMusic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sharedPreferences.edit().putBoolean(musicSettingKey, isChecked).commit();
				
			}
		});
    }
    
    private void configureSoundOptions() {
    	boolean soundOn = true;
    	final String soundSettingKey = getString(R.string.sound_setting_key);
    	
    	soundOn = sharedPreferences.getBoolean(soundSettingKey, true);
    	optionsSound.setChecked(soundOn);
    	
    	optionsSound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sharedPreferences.edit().putBoolean(soundSettingKey, isChecked).commit();
				
			}
		});
    }
}