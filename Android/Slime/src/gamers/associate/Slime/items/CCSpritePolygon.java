package gamers.associate.Slime.items;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccConfig;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.utils.BufferProvider;

import gamers.associate.Slime.CCSpriteRepeat;

public class CCSpritePolygon extends CCSpriteRepeat {
	protected FloatBuffer vertices;
	protected FloatBuffer texCoords;
	protected FloatBuffer colors;
	protected CGPoint[] baseArray;
	protected float texWidth;
	protected float texHeight;
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.CCSpriteRepeat#draw(javax.microedition.khronos.opengles.GL10)
	 */
	public CCSpritePolygon(CCSpriteFrame spriteFrame) {
		super(spriteFrame);
		this.polyInit();		
	}
	
	public CCSpritePolygon(String fileName) {
		super(fileName);
		this.polyInit();		
	}
	
	private void polyInit() {
		colors = BufferProvider.createFloatBuffer(4 * 4);
		// Atlas: Color
		colors.put(1.0f).put(1.0f).put(1.0f).put(1.0f);
		colors.put(1.0f).put(1.0f).put(1.0f).put(1.0f);
		colors.put(1.0f).put(1.0f).put(1.0f).put(1.0f);
		colors.put(1.0f).put(1.0f).put(1.0f).put(1.0f);
		colors.position(0);
	}
	
	public static CCSpritePolygon sprite(CCSpriteFrame spriteFrame) {
        return new CCSpritePolygon(spriteFrame);
    }
	
	public static CCSpritePolygon sprite(String fileName) {
        return new CCSpritePolygon(fileName);
    }
	
	@Override
	public void draw(GL10 gl) {		
		this.enableTextureRepeat(gl);
		
		boolean newBlend = false;
        if( blendFunc_.src != ccConfig.CC_BLEND_SRC || blendFunc_.dst != ccConfig.CC_BLEND_DST ) {
            newBlend = true;
            gl.glBlendFunc( blendFunc_.src, blendFunc_.dst );
        }
        
        // bug fix in case texture name = 0
        this.getTexture().checkName();
        // #define kQuadSize sizeof(quad_.bl)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.getTexture().name());

        // int offset = (int)&quad_;

        this.vertices.position(0);
        this.texCoords.position(0);
        
        /*float[] verticesArray = this.getVertexArray();
        float[] textArray = this.getTexCoordsArray();*/
        
        /*float textX = 50f / 128f;        
        float textY = 50f / 128f;
        this.vertices.position(0);
        this.texCoords.position(0);
        this.texCoords.clear();
        this.texCoords.put(0);
        this.texCoords.put(0);
        this.texCoords.put(0);
        this.texCoords.put(textY);
        this.texCoords.put(textX);
        this.texCoords.put(0);
        this.texCoords.put(textX);
        this.texCoords.put(textY);
        this.texCoords.position(0);*/

        /*float offsetX = 64;
        float offsetY = 64;
        
        this.vertices.clear();
        this.vertices.put(offsetX);
        this.vertices.put(offsetY + 50);
        this.vertices.put(0);
        
        this.vertices.put(offsetX);
        this.vertices.put(offsetY);
        this.vertices.put(0);
        
        this.vertices.put(offsetX + 50);
        this.vertices.put(offsetY + 50);
        this.vertices.put(0);
        
        this.vertices.put(offsetX + 50);
        this.vertices.put(offsetY);
        this.vertices.put(0);
        this.vertices.position(0);*/
        
        // vertex
        // int diff = offsetof( ccV3F_C4B_T2F, vertices);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0 , this.vertices);

        // color
        // diff = offsetof( ccV3F_C4B_T2F, colors);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors);

        // tex coords
        // diff = offsetof( ccV3F_C4B_T2F, texCoords);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texCoords);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.baseArray.length);

        if( newBlend )
            gl.glBlendFunc(ccConfig.CC_BLEND_SRC, ccConfig.CC_BLEND_DST);
	}
	
	public void setVertices(CGPoint[] vertexPoints) {
		//, float textureWidth, float textureHeight, CGPoint textureFPOffset
		this.baseArray = vertexPoints;
		//this.createTextureCoordinates(vertexPoints, textureWidth, textureHeight, textureFPOffset);
		this.createTextureCoordinates(vertexPoints);
		
		int len = vertexPoints.length;				
		
		float offsetX = this.getTexture().pixelsWide() / 2;
		float offsetY = this.getTexture().pixelsHigh() / 2;
		
		vertices  = BufferProvider.createFloatBuffer(len * 3);
		vertices.position(0);		
		for(CGPoint point : vertexPoints) {
			vertices.put(point.x + offsetX);
			vertices.put(point.y + offsetY);
			vertices.put(0);
		}
		
		vertices.position(0);				
	}
	
	public void createTextureCoordinates(CGPoint[] vertexPoints) {
		// , float textureWidth, float textureHeight, CGPoint textureFPOffset
		int len = vertexPoints.length;
		texCoords = BufferProvider.createFloatBuffer(len * 2);
						
		float originY = vertexPoints[0].y;
		
		texCoords.position(0);		
		for(CGPoint point : vertexPoints) {
			texCoords.put(point.x / this.getTexture().pixelsWide());
			texCoords.put((originY - point.y) / this.getTexture().pixelsHigh());			
		}
		
		texCoords.position(0);		
	}
	
	@Override
	public float[] getTexCoordsArray() {
    	float ret[] = new float[texCoords.limit()];
    	texCoords.get(ret, 0, texCoords.limit());    	
    	return ret;
    }
	
	@Override
	public float[] getVertexArray() {
    	float ret[] = new float[vertices.limit()];
    	vertices.get(ret, 0, vertices.limit());
    	return ret;
    }
}
