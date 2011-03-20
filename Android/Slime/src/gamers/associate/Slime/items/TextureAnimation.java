package gamers.associate.Slime.items;

import java.io.IOException;
import java.io.InputStream;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.opengl.GLResourceHelper;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TextureAnimation {
	// Create directly animation with animName + png
	/*public static void createFramesFromFiles(String animName) {
		createFramesFromFiles(animName, 1, true);
	}*/
	
	public static void createFramesFromFiles(String animName, int frameCount) {
		for (int i = 0; i < frameCount; i++) {
			String keyFrame = keyName(animName, i + 1);
			CCTexture2D tex = createTextureFromFilePath(keyFrame);			
			CGRect rect = CGRect.make(0, 0, 0, 0);
	        rect.size = tex.getContentSize();
	        CCSpriteFrame frame = CCSpriteFrame.frame(tex, rect, CGPoint.zero());
	        
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrame(frame, keyFrame);
		}
	}
	
	/*public static void createFramesFromFiles(String animName, int frameCount, boolean directName) {
		if (frameCount == 1 && directName) {
			String keyFrame = animName + ".png";
			CCTexture2D tex = createTextureFromFilePath(keyFrame);			
			CGRect rect = CGRect.make(0, 0, 0, 0);
	        rect.size = tex.getContentSize();
	        CCSpriteFrame frame = CCSpriteFrame.frame(tex, rect, CGPoint.zero());
	        
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrame(frame, keyFrame);
		}
		else {
			for (int i = 0; i < frameCount; i++) {
				String keyFrame = keyName(animName, i + 1);
				CCTexture2D tex = createTextureFromFilePath(keyFrame);			
				CGRect rect = CGRect.make(0, 0, 0, 0);
		        rect.size = tex.getContentSize();
		        CCSpriteFrame frame = CCSpriteFrame.frame(tex, rect, CGPoint.zero());
		        
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrame(frame, keyFrame);
			}
		}
	}*/
	
	public static String keyName(String animName, int frameNumber) {
		return animName + "-" + String.valueOf(frameNumber) + ".png";
	}
	
	public static CCTexture2D createTextureFromFilePath(final String path) {
        
    	final CCTexture2D tex = new CCTexture2D();
        tex.setLoader(new GLResourceHelper.GLResourceLoader() {
			
			@Override
			public void load() {
	            try {
		        	InputStream is = CCDirector.sharedDirector().getActivity().getAssets().open(path);
		            Bitmap bmp = BitmapFactory.decodeStream(is);
					is.close();
					tex.initWithImage(bmp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        return tex;
    }
}
