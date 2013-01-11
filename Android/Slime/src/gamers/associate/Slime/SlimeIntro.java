package gamers.associate.Slime;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
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
		
//		RelativeLayout layout = new RelativeLayout(this);
//		RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//		// para.gravity = Gravity.CENTER;
//		layout.setLayoutParams(para);		
//		VideoView videos= new VideoView(this);
//		ViewGroup.LayoutParams paraV = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//		videos.setLayoutParams(paraV);
//		layout.addView(videos);
        
        setContentView(R.layout.videointro);
        videos = (VideoView) this.findViewById(R.id.videointroview);
		
        // get bundle parameter here
//        Bundle introInfo = this.getIntent().getExtras();
//        int introChoice = introInfo.getInt(SlimeIntro.INTRO_CHOICE);
//        int resourceNum = 0;
//        switch(introChoice) {
//        	case LevelDifficulty.Normal:
//        		resourceNum = R.raw.bmoon;
//        		break;
//        	case LevelDifficulty.Hard:
//        		resourceNum = R.raw.cdisco;
//        		break;
//        	case LevelDifficulty.Extrem:
//        		resourceNum = R.raw.dhawaii;
//        		break;
//        	case LevelDifficulty.Easy:
//        	default:
//    			resourceNum	= R.raw.asombrero;
//    			break;
//        }
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
	
	private void end() {
		videos.stopPlayback();
		videos.clearFocus();
		finish();
	}
}
