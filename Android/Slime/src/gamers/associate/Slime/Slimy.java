package gamers.associate.Slime;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Slimy extends GameItemPhysic {
	
	public static String Anim_Burned_Wait = "burned-wait";
	public static String Anim_Burning = "burning";
	public static String Anim_Falling = "falling";
	public static String Anim_Landing_H = "landing-h";
	public static String Anim_Landing_V = "landing-v";
	public static String Anim_Wait_H = "wait-h";
	public static String Anim_Wait_V = "wait-v";
	
	public static float Default_Width = 24f;
	public static float Default_Height = 26f;
	private static float Default_Body_Width = 16f;
	private static float Default_Body_Height = 23f;
	
	protected Boolean isLanded;	
	protected CCAction waitAction;
	protected Boolean isBurned;
	
	public Slimy(CCNode node, float x, float y, float width, float height, World world, float worldRatio) {		
		super(node, x, y, width, height, world, worldRatio);
		
		if (width == 0 && this.height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;
			this.transformTexture();
		}	
		
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;

		this.isLanded = false;
		this.isBurned = false;
		
		this.initBody();
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
		dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 0.3f;
    		fixtureDef.restitution = 0.1f;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
    	}  
	}
	
	public void fadeIn() {
		CCFadeIn fade = CCFadeIn.action(0.5f);
		this.sprite.runAction(fade);
	}
	
	public void waitAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait_V), false);
		CCDelayTime delay = CCDelayTime.action(3f);
		CCAnimate reverse = animate.reverse();
		
		this.waitAction = CCRepeatForever.action(CCSequence.actions(animate, reverse, delay));		 
		this.sprite.runAction(this.waitAction);
	}
	
	public void fall() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Falling), false);
		CCAnimate reverse = animate.reverse();
		
		CCAction action = CCRepeatForever.action(CCSequence.actions(animate, reverse));		
		this.currentAction = action;
		this.sprite.runAction(this.currentAction);
	}
	
	public void land() {
		if (!this.isLanded && !this.isBurned && this.sprite != null) {
			if (this.currentAction != null) {
				this.sprite.stopAction(this.currentAction);
			}
			
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Landing_V), false);
			this.currentAction = animate;
			this.sprite.runAction(this.currentAction);
			this.isLanded = true;
		}
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);		
	}
	
	public void win() {
		this.burn();
	}
	
	public void burn() {
		if (!this.isBurned) {
			if (this.currentAction != null) {				
				this.sprite.stopAction(this.currentAction);				
			}
			
			if (this.waitAction != null) {				
				this.sprite.stopAction(this.waitAction);
			}
					
			CCAnimate animateBlink = CCAnimate.action(this.animationList.get(Anim_Burned_Wait), false);
			CCDelayTime delay = CCDelayTime.action(3f);
			CCSequence blinkSeq = CCSequence.actions(animateBlink, animateBlink.reverse(), delay);
			this.waitAction = CCRepeatForever.action(blinkSeq);		
			this.sprite.runAction(this.waitAction);		
			
			CCAnimate animBurn = CCAnimate.action(this.animationList.get(Anim_Burning), false);
			this.currentAction = animBurn;		
			this.sprite.runAction(this.currentAction);
			
			Filter filter = new Filter();
			
			filter.categoryBits = GameItemPhysic.Category_OutGame;
			filter.maskBits = GameItemPhysic.Category_Static;					
			filter.groupIndex = -1;
			for(Fixture fix : this.body.getFixtureList()) {
				// Change fixture shape here?
				fix.setFilterData(filter);
				fix.setRestitution(0f);
				fix.setFriction(1.0f);
				fix.setDensity(10f);
				this.body.resetMassData();
			}
			
			this.isBurned = true;
		}
	}
	
	@Override
	protected CCAnimation getReferenceAnimation() {
		return this.animationList.get(Slimy.Anim_Wait_V);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItemPhysic#handleContact(gamers.associate.Slime.GameItemPhysic)
	 */
	@Override
	protected void handleContact(GameItemPhysic item) {		
		super.handleContact(item);
		this.land();
	}
}
