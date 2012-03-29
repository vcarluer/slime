package gamers.associate.Slime.game;

import static javax.microedition.khronos.opengles.GL10.GL_COLOR_ARRAY;
import static javax.microedition.khronos.opengles.GL10.GL_FLOAT;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_2D;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_COORD_ARRAY;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.utils.FastFloatBuffer;

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
		// float base = Math.round(millis);
		double base = Math.ceil((double)millis);
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 0 ) ; //arrondi � 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 0 ) ; 
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format(base);		
	}
	
	public static String getFormatTimeCritic(float millis) {
		// float base = Math.round(millis);
		double base = Math.ceil((double)millis);
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 0 ) ; //arrondi � 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 0 ) ; 
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format(base);		
	}
	
	/** draws a circle given the center, radius and number of segments. */
    public static void ccDrawCirclePlain(GL10 gl, CGPoint center, float r, float a,
            int segments) {

//        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * (segments + 2));
//        vbb.order(ByteOrder.nativeOrder());
//        FloatBuffer vertices = vbb.asFloatBuffer();
        FastFloatBuffer vertices = getVertices(2 * (segments + 2));

        int additionalSegment = 2;
        
        vertices.put(center.x);
        vertices.put(center.y);

        final float coef = 2.0f * (float) Math.PI / segments;
        for (int i = 0; i <= segments; i++) {
            float rads = i * coef;
            float j = (float) (r * Math.cos(rads + a) + center.x);
            float k = (float) (r * Math.sin(rads + a) + center.y);

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

        gl.glVertexPointer(2, GL_FLOAT, 0, vertices.bytes);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, segments + additionalSegment);

        // restore default state
        gl.glEnableClientState(GL_COLOR_ARRAY);
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL_TEXTURE_2D);	
    }
    
    private static FastFloatBuffer getVertices(int size) {
		if(tmpFloatBuf == null || tmpFloatBuf.capacity() < size) {
	        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * size);
	        vbb.order(ByteOrder.nativeOrder());
	        tmpFloatBuf = FastFloatBuffer.createBuffer(vbb);
		}
		tmpFloatBuf.rewind();
		return tmpFloatBuf;
	}
    
    private static FastFloatBuffer tmpFloatBuf;
}
