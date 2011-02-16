package gamers.associate.Slime;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
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
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
		
		// show FPS
		// set false to disable FPS display, but don't delete fps_image.png!!
		CCDirector.sharedDirector().setDisplayFPS(true);
		
		// frames per second
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
		
		// this.scene = LevelFactory.GetLevel("Level1").getScene();		
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

        CCDirector.sharedDirector().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        CCDirector.sharedDirector().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        CCDirector.sharedDirector().end();
        //CCTextureCache.sharedTextureCache().removeAllTextures();
    }  
}