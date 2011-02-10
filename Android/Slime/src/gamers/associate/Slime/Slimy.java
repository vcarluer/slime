package gamers.associate.Slime;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
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
	
	protected Boolean isLanded;	
	protected CCAction waitAction;
	
	public Slimy(CCNode node, float x, float y, World world, float worldRatio) {		
		super(node, x, y, world, worldRatio);
		
		this.width = 26f;
		this.height = 24f;
		this.bodyWidth = 16f;
		this.bodyHeight = 23f;
		// this.scale = 1.5f;
		this.isLanded = false;
		
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
		dynamicBox.setAsBox((this.bodyWidth * this.scale) / this.worldRatio / 2, (this.bodyHeight * this.scale) / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 0.3f;
    		fixtureDef.restitution = 0.3f;
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
		
		CCAction action = CCRepeatForever.action(CCSequence.actions(animate, reverse, delay));
		this.waitAction = action;
		this.sprite.runAction(action);
	}
	
	public void fall() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Falling), false);
		CCAnimate reverse = animate.reverse();
		
		CCAction action = CCRepeatForever.action(CCSequence.actions(animate, reverse));		
		this.currentAction = action;
		this.sprite.runAction(this.currentAction);
	}
	
	public void land() {
		if (!this.isLanded && this.sprite != null) {
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
	
	@Override
	public void contact(Object with) {		
		this.land();
	}
	
	public void win() {
		if (this.currentAction != null) {
			this.sprite.stopAction(this.currentAction);
		}
		
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Burning), false);
		this.currentAction = animate;
		this.sprite.stopAction(this.waitAction);
		this.sprite.runAction(this.currentAction);
	}
}
