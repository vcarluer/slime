package gamers.associate.Slime.game;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class Util {
	public static void draw(GL10 gl, CGRect rect, float width, float r, float g, float b, float a) {
		// Drawing selection rectangle
		gl.glDisable(GL10.GL_LINE_SMOOTH);
		gl.glLineWidth(width);
		gl.glColor4f(r, g, b, a);            
		
		float x1 = rect.origin.x;
		float y1 = rect.origin.y;
		float x2 = rect.origin.x + CGRect.width(rect);
		float y2 = rect.origin.y + CGRect.height(rect);
		
		CGPoint vertices[] = {
				rect.origin, 
				CGPoint.ccp(x2, y1),
				CGPoint.ccp(x2, y2),
				CGPoint.ccp(x1, y2)
				};
        CCDrawingPrimitives.ccDrawPoly(gl, vertices, 4, true);
	}
}
