package gamers.associate.Slime;

import static javax.microedition.khronos.opengles.GL10.GL_REPEAT;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_2D;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_WRAP_S;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_WRAP_T;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCSprite;

public class CCSpriteRepeat extends CCSprite {
    
    public static CCSpriteRepeat sprite(String filename) {
        return new CCSpriteRepeat(filename);
    }
    
    protected CCSpriteRepeat(String fileName) {
    	super(fileName);    	
    }
		
	@Override
	public void draw(GL10 gl) {
		gl.glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        gl.glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		super.draw(gl);
	}	
}