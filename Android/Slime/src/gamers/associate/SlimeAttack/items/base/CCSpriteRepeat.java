package gamers.associate.SlimeAttack.items.base;

import static javax.microedition.khronos.opengles.GL10.GL_REPEAT;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

// 3 possible calls to private void setTextureRect(float x, float y, float w, float h, float sw, float sh, boolean rotated)
// - public void setTextureRect(CGRect rect, Boolean rotated)
// - public void setDisplayFrame(CCSpriteFrame frame)
// - in constructor init
// => Overided to create proper vertexes and texture rectangle
//
// 3 overrided constructors created, create more if needed
public class CCSpriteRepeat extends CCSprite {
    protected float width;
    protected float height;
	
    /*public static CCSpriteRepeat sprite(String filename) {
        return new CCSpriteRepeat(filename);
    }*/
    
    public static CCSpriteRepeat sprite(String filename, float width, float height) {
        return new CCSpriteRepeat(filename, width, height);
    }
    
    public static CCSpriteRepeat sprite(String spriteFrameName, boolean isFrame, float width, float height) {
        return new CCSpriteRepeat(spriteFrameName, isFrame, width, height);
    }
    
    public static CCSpriteRepeat sprite(CCSpriteFrame spriteFrame, float width, float height) {
        return new CCSpriteRepeat(spriteFrame, width, height);
    }
    
    public CCSpriteRepeat(CCSpriteFrame spriteFrame, float width, float height) {
    	super(spriteFrame);
    	this.initObj(width, height);
    }
    
    public CCSpriteRepeat(String spriteFrameName, boolean isFrame, float width, float height) {
    	super(spriteFrameName, isFrame);
    	this.initObj(width, height);
    }
    
    public CCSpriteRepeat(String fileName, float width, float height) {    	
    	super(fileName);    	
    	this.initObj(width, height);
    }
    
    public CCSpriteRepeat() {
    	super();
    }
    
    public CCSpriteRepeat(CCSpriteFrame spriteFrame) {
    	super(spriteFrame);
    }
    
    protected void initObj(float width, float height) {
    	this.width = width;
    	this.height = height;
    	// Trigget call to public void setTextureRect(CGRect rect, Boolean rotated)
    	this.setTextureRect(this.getTextureRect());
    }
    
	@Override
	public void setTextureRect(CGRect rect, Boolean rotated) {		
		if (this.width != 0 && this.height != 0) {
			rect.size = CGSize.make(this.width, this.height);
			super.setTextureRect(rect, rotated);
		}
		else {
			super.setTextureRect(rect, rotated);
		}		
	}
	
	@Override
	public void setDisplayFrame(CCSpriteFrame frame) {
		super.setDisplayFrame(frame);
		if (this.width != 0 && this.height != 0) {
			this.setTextureRect(this.getTextureRect());
		}
	}
	
	@Override
	public void setTexture(CCTexture2D texture) {		
		if (texture != null) {
			texture.setTexParameters(GL10.GL_LINEAR,GL10.GL_LINEAR,GL_REPEAT,GL_REPEAT);
		}
		
		super.setTexture(texture);
		
	}
}