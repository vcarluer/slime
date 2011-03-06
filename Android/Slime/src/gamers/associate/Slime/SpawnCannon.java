package gamers.associate.Slime;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.utils.Util7;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class SpawnCannon extends GameItemPhysic {
	public static String texture = "metal.png";
	public static float Default_Width = 32f;
	public static float Default_Height = 32f;
	
	public SpawnCannon(CCNode node, float x, float y, float width, float height,
			World world, float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);
		
		if (width == 0 && this.height == 0) {
			this.bodyWidth = this.width = Default_Width;
			this.bodyHeight = this.height = Default_Height;
			this.transformTexture();
		}				
		
		// Todo: remove this
		this.select();
		
		this.initBody();
	}
	
	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		
		// Define another box shape for our dynamic body.
		PolygonShape staticBox = new PolygonShape();
		staticBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = staticBox;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 1.0f;
    		fixtureDef.restitution = 0f;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Static;
    		this.body.createFixture(fixtureDef);
    	}  
	}
	
	public Slimy spawnSlime(CGPoint target) {
		Slimy slimy = SlimeFactory.Slimy.create(this.position.x, this.position.y + 40, 1.0f);		
		Vector2 targetVec = new Vector2(target.x, target.y);
		Vector2 pos = slimy.getBody().getPosition();
		Vector2 impulse = targetVec.sub(pos).mul(1 / this.worldRatio);
		//Vector2 impulse = new Vector2(targetVec.x - pos.x, targetVec.y - pos.y);
		slimy.getBody().applyLinearImpulse(impulse, pos);
		return slimy;
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void unselect() {
		this.selected = false;
		this.target = null;
	}
	
	public void selectionMove(CGPoint selection) {
		if (this.selected) {
			this.target = CGPoint.make(selection.x, selection.y);
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {		
		super.draw(gl);
		if (this.selected && this.target != null) {
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
	        CCDrawingPrimitives.ccDrawLine(gl, this.getPosition(), this.target);	       
		}
	}
	
	private CGPoint target;
	private boolean selected;
	
	/*static void drawDashedLine(GL10 gl, CGPoint origin, CGPoint destination, float dashLength) {
		float dx = destination.x - origin.x;
		float dy = destination.y - origin.y;
		float dist = sqrtf(dx * dx + dy * dy);
		float x = dx / dist * dashLength;
		float y = dy / dist * dashLength;
		
		CGPoint p1 = origin;
		int segments = (int)(dist / dashLength);
		int lines = (int)((float)segments / 2.0);

		CGPoint[] vertices = new CGPoint[segments];
		for(int i = 0; i < lines; i++)
		{
			vertices[i*2] = p1;
			p1 = CGPoint.make(p1.x + x, p1.y + y);
			vertices[i*2+1] = p1;
			p1 = CGPoint.make(p1.x + x, p1.y + y);
		}
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
		gl.glDrawArrays(GL10.GL_LINES, 0, segments);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		free(vertices);
	}*/

}
