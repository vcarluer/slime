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
	
	public static CGPoint mid(CGRect rect) {
		return CGPoint.make(CGRect.midX(rect), CGRect.midY(rect));
	}
	
	public static void mid(CGRect rect, CGPoint target) {
		target.set(CGRect.midX(rect), CGRect.midY(rect));
	}
	
	public static void getScaledRect(CGRect selectionRect, float minW, float minH, float zoom, CGRect targetRect) {		
		float baseW = selectionRect.size.width ;
		float baseH = selectionRect.size.height;
		float scaledW = selectionRect.size.width / zoom; 
		float scaledH = selectionRect.size.height / zoom;
		if (scaledW < minW) {
			baseW = minW;
			scaledW = minW;
		}
		
		if (scaledH < minH) {
			baseH = minH;
			scaledH = minH;
		}
		
		float targX1 = selectionRect.origin.x - (scaledW - baseW) / 2;
		float targY1 = selectionRect.origin.y - (scaledH - baseH) / 2;
		
		targetRect.set(targX1, targY1, scaledW, scaledH);					
	}
}
