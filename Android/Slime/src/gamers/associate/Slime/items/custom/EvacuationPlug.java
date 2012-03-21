package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class EvacuationPlug extends GameItemPhysic {
	public static String Anim_Base = "tank-evacuation-plug";
	
	private static float Default_Width = 87f;
	private static float Default_Height = 40f;
	
	public EvacuationPlug(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.SINGLE_SCALE;
		this.zOrder = Level.zMid;
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height; 
		}
	}
	
	public static float getHeightFromWidth(float width) {
		return width * Default_Height / Default_Width;
	}

	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		bodyDef.type = BodyType.StaticBody;
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		
		// Define another box shape for our dynamic body.
		PolygonShape dynamicBox = new PolygonShape();
		// dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		float bW = this.bodyWidth / this.worldRatio;
		float bW2 = bW / 2;
		float bH = this.bodyHeight / this.worldRatio;
		float bH2 = bH / 2;
		//todo: correct vectors for plug (here based on circular saw)
		Vector2 p1 = new Vector2(bW / 11 - bW2, - bH2);
		Vector2 p2 = new Vector2(10 * bW / 11 - bW2, - bH2);
		Vector2 p3 = new Vector2(bW - bW2, 2 * bH / 5 - bH2);
		Vector2 p4 = new Vector2(bW - bW2, 4 * bH / 5 - bH2);
		Vector2 p5 = new Vector2(10 * bW / 11 - bW2, bH - bH2);
		Vector2 p6 = new Vector2(bW / 11 - bW2, bH - bH2);
		Vector2 p7 = new Vector2(- bW2, 4 *  bH / 5 - bH2);
		Vector2 p8 = new Vector2(- bW2, 2 *  bH / 5 - bH2);
		Vector2[] vertices = new Vector2[8];
		vertices[0] = p1;
		vertices[1] = p2;
		vertices[2] = p3;
		vertices[3] = p4;
		vertices[4] = p5;
		vertices[5] = p6;
		vertices[6] = p7;
		vertices[7] = p8;
		dynamicBox.set(vertices);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;	    		
			fixtureDef.density = 1.0f;
			fixtureDef.friction = 3.0f;
    		fixtureDef.restitution = 0f;    		
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
    	} 	
	}
	

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return Anim_Base;
	}
	
	public void remove() {
		this.destroyBodyOnly();
		CCFadeOut fade = CCFadeOut.action(1.0f);
		CCRotateBy rotate = CCRotateBy.action(0.1f, 30f);
		CCMoveBy move = CCMoveBy.action(1.0f, CGPoint.make(0, -75f));
		CCCallFunc call = CCCallFunc.action(this, "removeEnd");
		CCSequence seq = CCSequence.actions(move, call);
		
		this.sprite.runAction(fade);
		this.sprite.runAction(rotate);
		this.sprite.runAction(seq);		
	}
	
	public void removeEnd() {
		Level.currentLevel.addItemToRemove(this);
	}
}
