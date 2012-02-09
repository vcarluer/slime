package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.items.base.GameItemPhysic;

public class Red extends GameItemPhysic {
	public static final float Default_Width = 153;
	public static final float Default_Height = 105f;
	
	protected static float Default_Body_Width = 153f;
	protected static float Default_Body_Height = 105f;

	public static String Anim_Bite = "bite";
	public static String Anim_Breaking = "breaking";
	public static String Anim_Contracting = "contracting";
	public static String Anim_Wait = "wait";
	
	public Red(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = spriteType.ANIM_SCALE;
		this.setNoStick(true);
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;			
		}				
		
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;
	}

	@Override
	protected void initBody() {
		// Physic body
				BodyDef bodyDef = new BodyDef();		
				bodyDef.type = BodyType.DynamicBody;
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
				Vector2 p1 = new Vector2(3 * bW / 14 - bW2, - bH2);
				Vector2 p2 = new Vector2(11 * bW / 14 - bW2, - bH2);
				Vector2 p3 = new Vector2(bW - bW2, 5 * bH / 10 - bH2);
				Vector2 p4 = new Vector2(12 * bW / 14 - bW2, 8 * bH / 10 - bH2);
				Vector2 p5 = new Vector2(8 * bW / 14 - bW2, 10 * bH / 10 - bH2);
				Vector2 p6 = new Vector2(3 * bW / 14 - bW2, 9 * bH / 10 - bH2);
				Vector2 p7 = new Vector2(bW / 14 - bW2, 6 *  bH / 10 - bH2);
				Vector2 p8 = new Vector2(bW / 14 - bW2, 3 *  bH / 10 - bH2);
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
					fixtureDef.density = 8.0f;
					fixtureDef.friction = 8.0f;
		    		fixtureDef.restitution = 0.1f;    		
		    		
		    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
		    		this.body.createFixture(fixtureDef);
		    	}
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return Anim_Wait;
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {					
		super.handleContact(item);
	}
	
	public void waitAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCDelayTime delay = CCDelayTime.action(2f);
		CCSequence seq = CCSequence.actions(animate, delay);
		CCRepeatForever repeat = CCRepeatForever.action(seq);
		this.sprite.runAction(repeat);	
	}
}
