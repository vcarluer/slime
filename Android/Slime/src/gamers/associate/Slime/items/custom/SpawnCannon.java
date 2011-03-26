package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class SpawnCannon extends GameItemPhysic {
	public static String Anim_Base = "metal1";
	public static float Default_Width = 64f;
	public static float Default_Height = 64f;
	public static float Default_Powa = 1.5f;
	public static float Max_Impulse = 10f;
	public static int Max_Slimy = 3;
	private static long Increment_Time_Sec = 1;	
	private float spawnHeightShift = Slimy.Default_Height / 2;
	
	private CGPoint target;
	private Vector2 targetImpulse;
	private Vector2 worldImpulse;
	private boolean selected;
	private float powa;
	private CGRect cannonRect;
	private long selectStart;
	private int slimyCounter;
	private CGPoint lastGameTarget;
	private CGPoint spawnPoint;
	
	public SpawnCannon(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.SINGLE_SCALE;
		this.powa = Default_Powa;
		this.target = CGPoint.getZero();
		this.targetImpulse = new Vector2();
		this.worldImpulse = new Vector2();		
		
		if (width == 0 && this.height == 0) {
			this.bodyWidth = this.width = Default_Width;
			this.bodyHeight = this.height = Default_Height;			
		}
		
		this.spawnPoint = CGPoint.make(this.position.x, this.position.y + spawnHeightShift);
		this.cannonRect = CGRect.make(this.position.x - this.width / 2, this.position.y - this.height / 2, this.width, this.height);		
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
    		fixtureDef.isSensor = true;
    		fixtureDef.shape = staticBox;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 1.0f;
    		fixtureDef.restitution = 0f;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Level;
    		this.body.createFixture(fixtureDef);
    	}  
	}
	
	public Slimy spawnSlime(CGPoint gameTarget) {		
		this.computeTarget(gameTarget);
		return spawnSlimeToCurrentTarget();
	}
	
	public Slimy spawnSlimeToCurrentTarget() {
		SlimyGrow slimy = null;
		if (this.selected) {			
			CGPoint spawn = this.getSpawnPoint();
			slimy = SlimeFactory.Slimy.createGrow(spawn.x, spawn.y, 1);
			slimy.setTargetGrowDif(this.slimyCounter - 1);
			Vector2 pos = slimy.getBody().getPosition();			
			this.worldImpulse.x = (this.targetImpulse.x  / this.worldRatio);
			this.worldImpulse.y = (this.targetImpulse.y / this.worldRatio);
			if (this.worldImpulse.len() > (Max_Impulse)) {
				this.worldImpulse.nor().mul(Max_Impulse);
			}
			
			slimy.getBody().applyLinearImpulse(this.worldImpulse, pos);
		}
		
		return slimy;
	}
	
	public void select() {
		this.selected = true;
		this.slimyCounter = 1;
		this.selectStart = System.currentTimeMillis();
	}
	
	public void unselect() {
		if (this.selected) {
			this.selected = false;			
		}
	}
	
	public void selectionMove(CGPoint screenSelection) {
		if (this.selected) {
			CGPoint gameTarget = Level.currentLevel.getCameraManager().getGamePoint(screenSelection);
			this.computeTarget(gameTarget);
		}
	}
	
	protected void computeTarget(CGPoint gameTouch) {
		if (this.selected) {
			CGPoint spawnPoint =  this.getSpawnPoint();
			this.targetImpulse.x = (spawnPoint.x - gameTouch.x) * this.powa;
			this.targetImpulse.y = (spawnPoint.y - gameTouch.y) * this.powa;
			
			this.target.x = spawnPoint.x + this.targetImpulse.x;
			this.target.y = spawnPoint.y + this.targetImpulse.y;
			
			this.lastGameTarget = gameTouch;
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
			// CGSize s = CCDirector.sharedDirector().winSize();
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(2);
            gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
			CCDrawingPrimitives.ccDrawCircle(gl, this.getSpawnPoint(), 50, ccMacros.CC_DEGREES_TO_RADIANS(90), 50, true);
		}
	}
	
	private CGPoint getSpawnPoint() {
		return this.spawnPoint;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return SpawnCannon.Anim_Base;
	}

	public boolean trySelect(CGPoint lastTouchReference) {
		CGPoint gameTarget = Level.currentLevel.getCameraManager().getGamePoint(lastTouchReference);		
		if (this.isInCannon(gameTarget)) {			
			this.select();
			this.computeTarget(gameTarget);
		}
		
		return this.selected;
	}
	
	private boolean isInCannon(CGPoint gameTarget) {
		return CGRect.containsPoint(this.cannonRect, gameTarget);			
	}
		
	public boolean isSelected() {
		return this.selected;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#render(float)
	 */
	@Override
	public void render(float delta) {
		if (this.selected) {			
			long time = System.currentTimeMillis();
			if (this.isInCannon(this.lastGameTarget)) {
				if (time - this.selectStart > Increment_Time_Sec * 1000) {				
					if (this.slimyCounter < Max_Slimy) {
						this.slimyCounter++;
					}									
					
					this.selectStart = time;
				}
			}
			else {
				this.selectStart = time;
			}
		}
		
		super.render(delta);
	}
	
	public int getSlimyCounter() {
		return this.slimyCounter;
	}
}
