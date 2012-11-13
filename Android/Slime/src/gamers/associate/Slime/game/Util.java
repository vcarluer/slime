package gamers.associate.Slime.game;

import static javax.microedition.khronos.opengles.GL10.GL_COLOR_ARRAY;
import static javax.microedition.khronos.opengles.GL10.GL_FLOAT;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_2D;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_COORD_ARRAY;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.utils.CCFormatter;

import android.util.FloatMath;

public class Util {
	public static void draw(GL10 gl, CGRect rect, float width, float r,
			float g, float b, float a) {
		// Drawing selection rectangle
		gl.glDisable(GL10.GL_LINE_SMOOTH);
		gl.glLineWidth(width);
		gl.glColor4f(r, g, b, a);

		float x1 = rect.origin.x;
		float y1 = rect.origin.y;
		float x2 = rect.origin.x + CGRect.width(rect);
		float y2 = rect.origin.y + CGRect.height(rect);

		CGPoint vertices[] = { rect.origin, CGPoint.ccp(x2, y1),
				CGPoint.ccp(x2, y2), CGPoint.ccp(x1, y2) };
		CCDrawingPrimitives.ccDrawPoly(gl, vertices, 4, true);
	}

	public static CGPoint mid(CGRect rect) {
		return CGPoint.make(CGRect.midX(rect), CGRect.midY(rect));
	}

	public static void mid(CGRect rect, CGPoint target) {
		target.set(CGRect.midX(rect), CGRect.midY(rect));
	}

	public static void getScaledRect(CGRect selectionRect, float minW,
			float minH, float zoom, CGRect targetRect) {
		float baseW = selectionRect.size.width;
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

	public static boolean isNumeric(String s) {
		try {
			Float.parseFloat(s);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}
	
	public static String getFormatTime(float millis) {
		return CCFormatter.format("%d", (int)FloatMath.ceil(millis));
	}
	
	public static String getFormatTime(int millis) {
		return CCFormatter.format("%d", millis);
	}
	
	public static String getFormatTimeCritic(float millis) {
		return CCFormatter.format("%2.2f", millis);
	}
	
	/** draws a circle given the center, radius and number of segments. */
    public static void ccDrawCirclePlain(GL10 gl, CGPoint center, float r, float a,
            int segments) {

//        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * (segments + 2));
//        vbb.order(ByteOrder.nativeOrder());
//        FloatBuffer vertices = vbb.asFloatBuffer();
        FloatBuffer vertices = getVertices(2 * (segments + 2));

        int additionalSegment = 2;
        
        vertices.put(center.x);
        vertices.put(center.y);

        final float coef = 2.0f * (float) Math.PI / segments;
        for (int i = 0; i <= segments; i++) {
            float rads = i * coef;
            float j = (float) (r * FloatMath.cos(rads + a) + center.x);
            float k = (float) (r * FloatMath.sin(rads + a) + center.y);

            vertices.put(j);
            vertices.put(k);
        }        
        
        vertices.position(0);

        // Default GL states: GL_TEXTURE_2D, GL_VERTEX_ARRAY, GL_COLOR_ARRAY, GL_TEXTURE_COORD_ARRAY
        // Needed states: GL_VERTEX_ARRAY, 
        // Unneeded states: GL_TEXTURE_2D, GL_TEXTURE_COORD_ARRAY, GL_COLOR_ARRAY	
        gl.glDisable(GL_TEXTURE_2D);
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL_COLOR_ARRAY);

        gl.glVertexPointer(2, GL_FLOAT, 0, vertices);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, segments + additionalSegment);

        // restore default state
        gl.glEnableClientState(GL_COLOR_ARRAY);
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL_TEXTURE_2D);	
    }
    
    public static void ccDrawTrianglePlain(GL10 gl, CGPoint v0, CGPoint v1, CGPoint v2) {

//        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * (segments + 2));
//        vbb.order(ByteOrder.nativeOrder());
//        FloatBuffer vertices = vbb.asFloatBuffer();
        int count = 3;
    	FloatBuffer vertices = getVertices(2 * (count));
        
        vertices.put(v0.x);
        vertices.put(v0.y);
        vertices.put(v1.x);
        vertices.put(v1.y);
        vertices.put(v2.x);
        vertices.put(v2.y);        
        
        vertices.position(0);

        // Default GL states: GL_TEXTURE_2D, GL_VERTEX_ARRAY, GL_COLOR_ARRAY, GL_TEXTURE_COORD_ARRAY
        // Needed states: GL_VERTEX_ARRAY, 
        // Unneeded states: GL_TEXTURE_2D, GL_TEXTURE_COORD_ARRAY, GL_COLOR_ARRAY	
        gl.glDisable(GL_TEXTURE_2D);
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL_COLOR_ARRAY);

        gl.glVertexPointer(2, GL_FLOAT, 0, vertices);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, count);

        // restore default state
        gl.glEnableClientState(GL_COLOR_ARRAY);
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL_TEXTURE_2D);	
    }
    
    private static FloatBuffer getVertices(int size) {
		if(tmpFloatBuf == null || tmpFloatBuf.capacity() < size) {
	        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * size);
	        vbb.order(ByteOrder.nativeOrder());
	        tmpFloatBuf = vbb.asFloatBuffer();
		}
		tmpFloatBuf.rewind();
		return tmpFloatBuf;
	}
    
    private static FloatBuffer tmpFloatBuf;
    
    public static float det(CGPoint u, CGPoint v) {
    	// det(u v) = u.v = uxvy-uyvx
    	return u.x*v.y - u.y*v.x;
    }
    
    // http://mathworld.wolfram.com/TriangleInterior.html
    public static float solveA(CGPoint v0, CGPoint v1, CGPoint v2, CGPoint v) {
    	return (det(v, v2) - det(v0, v2)) / (det(v1, v2));    	
    }
    
    public static float solveB(CGPoint v0, CGPoint v1, CGPoint v2, CGPoint v) {
    	return -1 * ((det(v, v1) - det(v0, v1)) / det(v1, v2));
    }
    
    /** Is point in triangle 
     @param v0 start
     @param v1 vector1
     @param v2 vector2
     @param v point to test
     **/
    public static boolean inTriangle(CGPoint v0, CGPoint v1, CGPoint v2, CGPoint v) {
    	float a = solveA(v0, v1, v2, v);
    	float b = solveB(v0, v1, v2, v);
    	return a > 0 && b > 0 && a + b < 1;
    }
}
