package gamers.associate.Slime.items.base;


import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccConfig;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.utils.BufferProvider;
import org.cocos2d.utils.FastFloatBuffer;

public class CCSpritePolygon extends CCSpriteRepeat {
	protected FastFloatBuffer vertices;
	protected FastFloatBuffer texCoords;
	protected FastFloatBuffer colors;
	protected CGPoint[] baseArray;
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.CCSpriteRepeat#draw(javax.microedition.khronos.opengles.GL10)
	 */
	public CCSpritePolygon(CCSpriteFrame spriteFrame) {
		super(spriteFrame);		
	}
	
	public CCSpritePolygon(String fileName, float textureWidth, float textureHeight) {
		super(fileName, textureWidth, textureHeight);					
	}
	
	public CCSpritePolygon(CCSpriteFrame spriteFrame, float textureWidth, float textureHeight) {
		super(spriteFrame, textureWidth, textureHeight);					
	}
	
	public static CCSpritePolygon sprite(CCSpriteFrame spriteFrame) {
        return new CCSpritePolygon(spriteFrame);
    }
	
	public static CCSpritePolygon sprite(String fileName, float textureWidth, float textureHeight) {
        return new CCSpritePolygon(fileName, textureWidth, textureHeight);
    }
	
	public static CCSpritePolygon sprite(CCSpriteFrame spriteFrame, float textureWidth, float textureHeight) {
        return new CCSpritePolygon(spriteFrame, textureWidth, textureHeight);
    }
	
	@Override
	public void draw(GL10 gl) {		
		boolean newBlend = false;
        if( blendFunc_.src != ccConfig.CC_BLEND_SRC || blendFunc_.dst != ccConfig.CC_BLEND_DST ) {
            newBlend = true;
            gl.glBlendFunc( blendFunc_.src, blendFunc_.dst );
        }

        // #define kQuadSize sizeof(quad_.bl)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.getTexture().name());

        this.vertices.position(0);
        this.texCoords.position(0);
        
        // vertex        
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0 , this.vertices.bytes);

        // color        
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors.bytes);

        // tex coords        
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texCoords.bytes);

        // gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.baseArray.length);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, this.baseArray.length);

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
		colors = BufferProvider.createFloatBuffer(len * 4);
		
		float originX = vertexPoints[0].x;
		float originY = vertexPoints[0].y;
		
		texCoords.position(0);		
		for(CGPoint point : vertexPoints) {
			texCoords.put((point.x - originX) / this.getTexture().pixelsWide());
			texCoords.put((originY - point.y) / this.getTexture().pixelsHigh());
			
			colors.put(1.0f).put(1.0f).put(1.0f).put(1.0f);
		}
		
		texCoords.position(0);						
		colors.position(0);
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
