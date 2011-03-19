package gamers.associate.Slime.items;

import gamers.associate.Slime.Level;
import gamers.associate.Slime.SlimeFactory;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class SpawnCannon extends GameItemPhysic {
	public static String texture = "metal1.png";
	public static float Default_Width = 32f;
	public static float Default_Height = 32f;
	private float spawnHeightShift = Slimy.Default_Height / 2;
	
	private CGPoint target;
	private boolean selected;
	
	public SpawnCannon(CCNode node, float x, float y, float width, float height,
			World world, float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);
		this.textureMode = TextureMode.REPEAT;
		
		if (width == 0 && this.height == 0) {
			this.bodyWidth = this.width = Default_Width;
			this.bodyHeight = this.height = Default_Height;
			this.transformTexture();
		}				
		
		// Todo: remove this
		this.select();
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
		this.target = target;
		return spawnSlimeToCurrentTarget();
	}
	
	public Slimy spawnSlimeToCurrentTarget() {
		CGPoint spawn = this.getSpawnPoint();
		Slimy slimy = SlimeFactory.Slimy.create(spawn.x, spawn.y, 1.0f);
		Vector2 pos = slimy.getBody().getPosition();
		Vector2 impulse = new Vector2();
		impulse.x = this.target.x  / this.worldRatio - pos.x;
		impulse.y = this.target.y / this.worldRatio - pos.y ;
		if (impulse.len() > 10f) {
			impulse.nor().mul(10f);
		}
		
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
	
	public void selectionMove(CGPoint screenSelection) {
		if (this.selected) {
			CGPoint gameTarget = Level.currentLevel.getCameraManager().getGamePoint(screenSelection);
			this.target = CGPoint.make(gameTarget.x, gameTarget.y);
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {		
		super.draw(gl);
		if (this.selected && this.target != null) {
			/*gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(2.0f);*/
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);				        
			CGPoint spawnPoint =  this.getSpawnPoint();
			CCDrawingPrimitives.ccDrawLine(gl, spawnPoint, this.target);
			CGSize s = CCDirector.sharedDirector().winSize();
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(2);
            gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
			CCDrawingPrimitives.ccDrawCircle(gl, this.getSpawnPoint(), 50, ccMacros.CC_DEGREES_TO_RADIANS(90), 50, true);
		}
	}
	
	private CGPoint getSpawnPoint() {
		return CGPoint.make(this.getPosition().x, this.getPosition().y + spawnHeightShift);
	}		
}
