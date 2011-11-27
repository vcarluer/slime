package gamers.associate.Slime.items.custom;


import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GoalPortal extends GameItemPhysic {
	public static String Anim_Goal_Portal = "redportal";
	
	private static float Default_Width = 32f;
	private static float Default_Height = 10f;
	
	public GoalPortal(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.ANIM_SCALE;
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;
		}
		
		this.bodyWidth = this.width;
		this.bodyHeight = this.height;
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
		PolygonShape contactBox = new PolygonShape();
		contactBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);    		
    		this.body.setUserData(this);    		
    		Fixture fix = this.body.createFixture(contactBox, 1.0f);
    		fix.setSensor(true);
    	}		
	}
	
	public void createPortal() {
		CCAction animate = CCRepeatForever.action(CCAnimate.action(this.animationList.get(Anim_Goal_Portal), false));				
		this.sprite.runAction(animate);		
	}

	@Override
	protected String getReferenceAnimationName() {
		return GoalPortal.Anim_Goal_Portal;		
	}
	
//	@Override
//	protected void initSprite() {						
//		this.setSprite(GameItemCocos.createSpriteFromFirstFrame(Anim_Goal_Portal));
//	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {		
		super.handleContact(item);
		
		if (item.getContactWith() instanceof Slimy) {
			Slimy slimy = (Slimy) item.getContactWith();
			if (slimy.isAlive()) {
				slimy.win();
				slimy.destroyBodyOnly();			
				
				if (Level.currentLevel.win(false)) {
					CCCallFunc callback = CCCallFunc.action(this, "endAnimDone");
					CCSequence sequence = CCSequence.actions(this.getAnimatePortalEnterReference(), callback);
					slimy.getSprite().runAction(sequence);
					Sounds.playEffect(R.raw.portalgoal);
				}
				else {
					slimy.getSprite().runAction(this.getAnimatePortalEnterReference());
				}
				
				this.applyOtherPortalEnterAction(slimy);
			}
		}				
	}
	
	public void endAnimDone() {
		Level.currentLevel.showEndLevel();
	}
	
	private CCFiniteTimeAction getAnimatePortalEnterReference() {		
		return CCRotateBy.action(2.5f, 2160);		
	}
	
	private void applyOtherPortalEnterAction(GameItemCocos item) {
		CCScaleTo scaleUp = CCScaleTo.action(1.0f, 3.0f);
		CCDelayTime delay = CCDelayTime.action(1.0f);
		CCScaleTo scaleDown = CCScaleTo.action(0.5f, 0.0f);
		CCSequence action1 = CCSequence.actions(scaleUp, delay, scaleDown);
		CCDelayTime delay2 = CCDelayTime.action(2.0f);				
		CCFadeOut fade = CCFadeOut.action(0.5f);
		CCSequence action2 = CCSequence.actions(delay2, fade);
		
		
		item.getSprite().runAction(action1);
		item.getSprite().runAction(action2);
	}
}
