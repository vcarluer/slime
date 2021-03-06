package gamers.associate.SlimeAttack;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.base.SpriteSheetFactory;
import gamers.associate.SlimeAttack.layers.CreditLayer;
import gamers.associate.SlimeAttack.layers.GALogoLayer;
import gamers.associate.SlimeAttack.layers.IBackableLayer;
import gamers.associate.SlimeAttack.layers.StoryMenuItem;
import gamers.associate.SlimeAttack.levels.LevelHome;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;

/**
 * @uml.dependency   supplier="gamers.associate.Slime.LevelLayer"
 */
public class SlimeAttack extends Activity {
	private static final String SGN_AMZ = "800F2BBA8B5811D40BB140402CAF9D63";
	private static final String SGS_VCR = "703A6FB6180B55E158105A7D9481857A";
	private static final String SGS_CMZ = "AB84E64BAD5ED6B0B7CBD7AC5F37461C";
	private static final String AdMobPublisherId = "a14f5f7a390a6a4";
	private static final boolean AdTest = true;
	private static final boolean AdOn = false;

	public static final int ACTIVITY_INTRO = 0;
	
	public static final String TAG = "Slime";	

	public static final int SHOW_AD = 0;
    public static final int HIDE_AD = 1;
    public static final int NEXT_AD = 2;
    public static final int SHOW_NEXT_AD = 3;
	
	static {
        System.loadLibrary("gdx");
	}		
	
	private CCGLSurfaceView mGLSurfaceView;
	private CCScene scene;
	private AudioManager audio;
	private boolean startLevel;
	
//	private AdView adView;	
	
	private Handler mHandler;
	private StoryMenuItem storyMenuItemAfterIntro;
	private static boolean needMusicResume;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SlimeFactory.Log.d(SlimeAttack.TAG, "Slime.onCreate");
        super.onCreate(savedInstanceState);
     // set the window status, no tile, full screen and don't sleep
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                
        mGLSurfaceView = new CCGLSurfaceView(this);        
        setContentView(R.layout.main);
        RelativeLayout layout = (RelativeLayout) this.findViewById(R.id.mainLayout);        
        layout.addView(this.mGLSurfaceView);
        
        if (AdOn) {
        	adAdView(layout);
        }        
        
//        SlimeFactory.Log.d(Slime.TAG, "mGLSurfaceView isHardwareAccelerated(): " + mGLSurfaceView.isHardwareAccelerated());
//        SlimeFactory.Log.d(Slime.TAG, "adView isHardwareAccelerated(): " + adView.isHardwareAccelerated());
        //SlimeFactory.Log.d(Slime.TAG, "layout isHardwareAccelerated(): " + layout.isHardwareAccelerated());
        
        SlimeFactory.ContextActivity = this;
        SlimeFactory.init();        
        
     // attach the OpenGL to a window
        // View glView = findViewById(R.id.glsurface);
		CCDirector.sharedDirector().attachInView(mGLSurfaceView);
		
		// no effect here because devise orientation is controlled by manifest
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		
		// show FPS
		// set false to disable FPS display, but don't delete fps_image.png!!		
		CCDirector.sharedDirector().setDisplayFPS(Level.DebugMode);
		
		// frames per second
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
		
		SlimeFactory.setDensity(this.getResources().getDisplayMetrics().density);
        SlimeFactory.Log.d(SlimeAttack.TAG, "Density: " + String.valueOf(SlimeFactory.Density));
		
		// First scene after start
		// Needed, not overriden by ccdirector resume?		
		this.scene = GALogoLayer.scene();
		
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		// Make the Scene active		
		CCDirector.sharedDirector().runWithScene(this.scene);
    }

	protected void adAdView(RelativeLayout layout) {
//		// Create an ad.
//        adView = new AdView(this, AdSize.BANNER, AdMobPublisherId);
//        FrameLayout.LayoutParams adsParams =new FrameLayout.LayoutParams(
//        		FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);        
//        adView.setLayoutParams(adsParams);
//        adView.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL); 
//        // Add the AdView to the view hierarchy. The view will have no size
//        // until the ad is loaded.
//        layout.addView(adView);
//        
//        this.mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//	            	switch(msg.what)
//	    	        {
//	    	        case SHOW_AD:	        
//	    	            showAdInternal();
//	    	            break;
//	    	        case HIDE_AD:
//	    	            hideAdInternal();
//	    	            break;
//	    	        case NEXT_AD:
//	    	        	nextAdInternal();
//	    	        	break;
//	    	        case SHOW_NEXT_AD:
//	    	        	showAndNextAdInternal();
//	    	        	break;
//	    	        default:
//	    	            break;
//	    	        }
//			    }
//        };
//
//        this.hideAdInternal();
	}    
    
    private void showAdInternal() {    	
//    	this.adView.setVisibility(View.VISIBLE);    	
    }
    
    private void hideAdInternal() {    	
//    	this.adView.setVisibility(View.GONE);    	
    }
    
    private void nextAdInternal() {
//    	// Create an ad request.
//        AdRequest adRequest = new AdRequest();
//        if (AdTest) {
//        	adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
//        	adRequest.addTestDevice(SGS_VCR);
//        	adRequest.addTestDevice(SGN_AMZ);
//        	adRequest.addTestDevice(SGS_CMZ);
//        }
//        // Fill out ad request.       
//
//        // Start loading the ad in the background.        
//        this.adView.loadAd(adRequest);
    }
    
    private void showAndNextAdInternal() {
    	this.showAdInternal();
    	this.nextAdInternal();    	
    }
    
    public void showAd() {    	
    	this.ad(SHOW_AD);
    }
    
    public void hideAd() {
    	this.ad(HIDE_AD);
    }
    
    public void nextAd() {
    	this.ad(NEXT_AD);
    }
    
    public void showAndNextAd() {
    	this.ad(SHOW_NEXT_AD);
    }
    
    private void ad(int actionCode) {
    	if (SlimeAttack.AdOn) {
    		Message msg = new Message();
        	msg.what = actionCode;        
            mHandler.sendMessage(msg);
    	}    	
    }
    
    @Override
    public void onStart() {
    	SlimeFactory.Log.d(SlimeAttack.TAG, "Slime.onStart");
    	this.hideButtons();
        super.onStart(); 
    }

    @Override
    public void onPause() {
    	SlimeFactory.Log.d(SlimeAttack.TAG, "Slime.onPause");
        super.onPause();

        needMusicResume = Sounds.isMusicPlaying;
    	Sounds.stopMusic();
        
        if (Level.currentLevel != null) {
        	Level.currentLevel.setPause(true);
        }                
        
        Sounds.stopEffect(R.raw.victory);
        CCDirector.sharedDirector().onPause();
    }

    @Override
    public void onResume() {
    	SlimeFactory.Log.d(SlimeAttack.TAG, "Slime.onResume");
        super.onResume();

        CCDirector.sharedDirector().onResume();  
        this.hideButtons();
        
        if (this.startLevel) {
        	this.startLevel = false;
        	if (this.storyMenuItemAfterIntro != null) {
        		this.storyMenuItemAfterIntro.runLevel();
        		this.storyMenuItemAfterIntro = null;
        	}
        } else {
        	
        	if (!Sounds.isMute() && needMusicResume && Level.currentLevel != null && (!Level.currentLevel.getActivated() || Level.currentLevel.getCurrentLevelName() == LevelHome.Id)) {
        		CCScene scene = CCDirector.sharedDirector().getRunningScene();
        		if (scene != null && scene.getChildren().size() > 0 && (scene.getChildren().get(0) instanceof CreditLayer)) {
        			SlimeFactory.playCreditMusic();
        		} else {
        			SlimeFactory.playMenuMusic();
        		}
        	}
        	
        	if (Level.currentLevel != null) {
            	// No automatic unpause
            	if (Level.currentLevel.getCurrentLevelName() == LevelHome.Id) {
            		Level.currentLevel.setPause(false);
            	} else {
            		// to put the hud back in pause
            		Level.currentLevel.enablePauseLayer();
            	}
            }
        }
    }

    @Override
    public void onDestroy() {
    	SlimeFactory.Log.d(SlimeAttack.TAG, "Slime.onDestroy");
        super.onDestroy();
                
        CCDirector.sharedDirector().end();
        SpriteSheetFactory.destroy();
        SlimeFactory.detachAll();
        SlimeFactory.destroyAll();        
        
        if (Level.currentLevel != null) {
        	Level.currentLevel.resetLevel();
        }
        
        Level.isInit = false;
        
        Sounds.destroy();
        // Destroy here, world and game items?
        
     // Destroy the AdView.
        if (AdOn) {
//        	adView.destroy();
        }
               
        // No more needed if rotation works:
        /*SpriteSheetFactory.destroy();
        SlimeFactory.destroyAll();*/
        //CCTextureCache.sharedTextureCache().removeAllTextures();
    }
    
    // Called on rotation. 
    // Does not call onDestroy anymore due to android:configChanges="keyboardHidden|orientation" in manifest
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {			
			return true;			
	    }
		
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			 audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI); 
			return true;
		}
	    
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) { 
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			return true;
	    }		   
		
		return super.onKeyDown(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Level.currentLevel != null && Level.currentLevel.getActivated() && (Level.currentLevel.getCurrentLevelName() == LevelHome.Id)) {
				this.finish();
				return true;
			}			
			
			CCScene scene = CCDirector.sharedDirector().getRunningScene();
			
			if (scene != null && scene.getChildren() != null && scene.getChildren().size() > 0) {
				// Must always be the first layer
				CCNode firstNode = scene.getChildren().get(0);
				if (firstNode instanceof IBackableLayer) {
					IBackableLayer backable = (IBackableLayer) firstNode;
					backable.goBack();
				}
						
			}
			/*if (LevelSelection.get() != null && LevelSelection.get().getActivated()) {
				Sounds.playEffect(R.raw.menuselect);
				LevelSelection.get().goBack();
			}*/
	    }				
		
		return super.onKeyUp(keyCode, event);
	}
	
	public void runIntro(StoryMenuItem menuItem) {
		Sounds.stopMusic();
		this.storyMenuItemAfterIntro = menuItem;
		Intent i = new Intent(this, SlimeIntro.class);
		int currentDifficulty = SlimeFactory.GameInfo.getDifficulty();
		i.putExtra(SlimeIntro.INTRO_CHOICE, currentDifficulty);
		this.startActivityForResult(i, ACTIVITY_INTRO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		this.startLevel = true;					
	}
	
	@SuppressLint("NewApi") private void hideButtons() {
		if (Build.VERSION.SDK_INT >= 14) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		}
	}

	public int getCurrentMusicVolume() {
		AudioManager audMgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		return audMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	}
	
	public void buyGame() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String marketId = "gamers.associate.SlimeAttack";		
		intent.setData(Uri.parse("market://details?id=" + marketId));
		startActivity(intent);
	}
}