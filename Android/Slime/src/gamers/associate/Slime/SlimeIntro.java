package gamers.associate.Slime;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class SlimeIntro extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		this.setContentView(R.layout.main);
//		SurfaceView videoSurface = (SurfaceView) findViewById(R.id.videosurface);
//	    SurfaceHolder holder = videoSurface.getHolder();
//		MediaPlayer mp = MediaPlayer.create(this, R.raw.intro);
//		mp.setDisplay(holder);
//		mp.setOnCompletionListener(new OnCompletionListener() {
//			
//			@Override
//			public void onCompletion(MediaPlayer mp) {
//				mp.release();
//			}
//		});
//		
//		mp.start();
		
		VideoView videos= new VideoView(this);
		
		int a= R.raw.intro; //
        String  str= "android.resource://gamers.associate.Slime/"+a;

        Uri uri=Uri.parse(str);
        
		videos.setVideoURI(uri);
		videos.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				finish();
			}
		});
		
		setContentView(videos);
        videos.start();
	}
}
