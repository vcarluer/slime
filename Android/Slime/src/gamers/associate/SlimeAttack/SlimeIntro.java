package gamers.associate.SlimeAttack;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.R.id;
import gamers.associate.SlimeAttack.R.layout;
import gamers.associate.SlimeAttack.R.raw;
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

public class SlimeIntro extends Activity {
	public static final String INTRO_CHOICE = "IntroChoice";
	private VideoView videos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        runVideo();
	}

	protected void runVideo() {
		setContentView(R.layout.videointro);
        videos = (VideoView) this.findViewById(R.id.videointroview);
        int resourceNum = R.raw.intro;
        
        String  str= "android.resource://gamers.associate.Slime/"+resourceNum;

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
	}
	
	@Override
	protected void onResume() {
		this.hideButtons();
		this.runVideo();
		super.onResume();
	}

	@Override
	protected void onStart() {
		this.hideButtons();
		super.onStart();
	}
	
	@SuppressLint("NewApi") private void hideButtons() {
		if (Build.VERSION.SDK_INT >= 14) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		}
	}

	private void end() {
		videos.stopPlayback();
		videos.clearFocus();
		finish();
	}
}
