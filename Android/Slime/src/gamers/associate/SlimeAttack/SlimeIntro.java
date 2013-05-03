package gamers.associate.SlimeAttack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;
import gamers.associate.SlimeAttackLite.R;

public class SlimeIntro extends Activity {
	public static final String INTRO_CHOICE = "IntroChoice";
	private VideoView videos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        
	        runVideo();
		} catch(Exception ex) {
		}
	}

	protected void runVideo() {
		try	{
			setContentView(R.layout.videointro);
	        videos = (VideoView) this.findViewById(R.id.videointroview);
	        int resourceNum = R.raw.introlab;
	        
	        String  str= "android.resource://gamers.associate.SlimeAttack/"+resourceNum;
	
	        Uri uri=Uri.parse(str);
	        
			videos.setVideoURI(uri);
			
			videos.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {			
					end();
				}
			});
			
			videos.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					end();
					return true;
				}
			});
					
			videos.start();
		} catch(Exception ex) {
		}
	}
	
	@Override
	protected void onResume() {
		try	{
			this.hideButtons();
			this.runVideo();
			super.onResume();
		} catch(Exception ex) {
		}		
	}

	@Override
	protected void onStart() {
		try	{
			this.hideButtons();
			super.onStart();
		} catch(Exception ex) {
		}
	}
	
	@SuppressLint("NewApi") private void hideButtons() {
		try	{
			if (Build.VERSION.SDK_INT >= 14) {
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
			}
		} catch(Exception ex) {
		}
	}

	private void end() {
		try {
			videos.stopPlayback();
			videos.clearFocus();
			finish();
		} catch(Exception ex) {
		}
	}
}
