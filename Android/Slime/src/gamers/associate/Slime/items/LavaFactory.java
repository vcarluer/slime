package gamers.associate.Slime.items;

import java.io.IOException;
import java.io.InputStream;

import gamers.associate.Slime.CCSpriteRepeat;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.opengl.GLResourceHelper;
import org.cocos2d.types.CCTexParams;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LavaFactory extends GameItemPhysicFactory<Lava> {
	@Override
	public Lava create(float x, float y, float width, float height) {		
		Lava lava = this.instantiate(x, y, width, height);
		lava.setAnimationList(this.sharedAnimations);						
					
		lava.setSprite(null);
		if (lava != null) {
			CCSprite sprite = CCSpriteRepeat.sprite(Lava.texture1, true, width, height);			
			lava.setSprite(sprite);					
		}
		
		this.runFirstAnimations(lava);
		this.level.addItemToAdd(lava);		
		
		return lava;				
	}
	
	private static CCTexture2D createTextureFromFilePath(final String path) {
        
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
	
	@Override
	protected void createAnimList() {
		String keyFrame = Lava.texture1;
		CCTexture2D tex = createTextureFromFilePath(keyFrame);			
		CGRect rect = CGRect.make(0, 0, 0, 0);
        rect.size = tex.getContentSize();
        CCSpriteFrame frame = CCSpriteFrame.frame(tex, rect, CGPoint.zero());
        
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrame(frame, keyFrame);
		
		keyFrame = Lava.texture2;
		tex = createTextureFromFilePath(keyFrame);			
		rect = CGRect.make(0, 0, 0, 0);
        rect.size = tex.getContentSize();
        frame = CCSpriteFrame.frame(tex, rect, CGPoint.zero());
        
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrame(frame, keyFrame);
		
		this.createAnim(Lava.Anim_Init, 2, 0.5f);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Lava instantiate(float x, float y, float width, float height) {
		return new Lava(this.rootNode, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Lava item) {
		item.initAnimation();
	}
	
	/*@Override
	public Lava create(float x, float y, float width, float height) {		
		Lava lava = super.create(x, y, width, height);		
		if (lava != null) {
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Lava.texture));			
			lava.setSprite(sprite);
			CCTexParams texParams = new CCTexParams(GL10.GL_LINEAR, GL10.GL_LINEAR, GL10.GL_REPEAT, GL10.GL_REPEAT);
			lava.getSprite().getTexture().setTexParameters(texParams);
		}
		
		return lava;				
	}*/
}
