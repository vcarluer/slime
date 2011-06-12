package gamers.associate.Slime;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.layers.GALogoLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * @uml.dependency   supplier="gamers.associate.Slime.LevelLayer"
 */
public class Slime extends Activity {
	static {
        System.loadLibrary("gdx");
	}		
	
	private CCGLSurfaceView mGLSurfaceView;
	private CCScene scene;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // set the window status, no tile, full screen and don't sleep
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        mGLSurfaceView = new CCGLSurfaceView(this);
        
        setContentView(mGLSurfaceView);
        
     // attach the OpenGL to a window
		CCDirector.sharedDirector().attachInView(mGLSurfaceView);
		
		// no effect here because devise orientation is controlled by manifest
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		
		// show FPS
		// set false to disable FPS display, but don't delete fps_image.png!!
		CCDirector.sharedDirector().setDisplayFPS(true);
		
		// frames per second
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
		
		// First scene after start
		// Needed, not overriden by ccdirector resume?		
		this.scene = GALogoLayer.scene();
					
		// Make the Scene active		
		CCDirector.sharedDirector().runWithScene(this.scene);
    }
    
    @Override
    public void onStart() {
        super.onStart(); 
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Level.currentLevel != null) {
        	Level.currentLevel.setPause(true);
        }
        
        CCDirector.sharedDirector().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        CCDirector.sharedDirector().onResume();
        if (Level.currentLevel != null) {
        	Level.currentLevel.setPause(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
                
        CCDirector.sharedDirector().end();
        SpriteSheetFactory.destroy();
        SlimeFactory.detachAll();
        SlimeFactory.destroyAll();
        
        if (Level.currentLevel != null) {
        	Level.currentLevel.resetLevel();
        }
        
        Level.isInit = false;
        
        // Destroy here, world and game items?
        
        // No more needed if rotation works:
        /*SpriteSheetFactory.destroy();
        SlimeFactory.destroyAll();*/
        //CCTextureCache.sharedTextureCache().removeAllTextures();
    }
    
    // Called on rotation. 
    // Does not call onDestroy anymore due to android:configChanges="keyboardHidden|orientation" in manifest
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		
		/*this.scene = SlimeLoadingLayer.scene();
		CCDirector.sharedDirector().replaceScene(this.scene);*/
		//Level.currentLevel.getCameraManager().setCameraView();
		
		// Reinit camera view based on screen size
		// Not needed, called in Level.get
		/* if (Level.currentLevel != null) {
			Level.currentLevel.getCameraManager().setCameraView();
		}*/
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        // moveTaskToBack(true);
	        return true;
	    }	    
		return super.onKeyDown(keyCode, event);
	}          
}