package gamers.associate.Slime.items.custom;


import gamers.associate.Slime.R;
import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GoalPortal extends GameItemPhysic implements ISelectable {
	public static String Anim_Goal_Portal = "tank-evacuation-whirlwind";
	private static String thumbnail = "tank-evacuation-01.png"; // "teleporter-red-portal-04.png";
	
	public static float Default_Width = 32f;
	public static float Default_Height = 75f;
	private CCSprite thumbnailSprite;
	
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
		//this.sprite.setVisible(false);
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
		SlimeFactory.Log.d(Slime.TAG, "GoalPortal.handleContact(...) start");
		super.handleContact(item);
		
		if (this.isActive && item.getContactWith() instanceof Slimy) {
			Slimy slimy = (Slimy) item.getContactWith();
			if (slimy.isAlive()) {
				Level.currentLevel.detachSlimies();
				slimy.win();							
				slimy.destroyBody();			
				
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
		
		SlimeFactory.Log.d(Slime.TAG, "GoalPortal.handleContact(...) end");
	}
	
	private boolean nextStepActivate;
	
	// to not activate during contacts
	public void setActiveNextStep() {
		this.nextStepActivate = true;
	}
	
	@Override
	public void render(float delta) {
		if (nextStepActivate) {
			this.nextStepActivate = false;
			this.setActive(true);
		}
		
		super.render(delta);
	}

	@Override
	public void setActive(boolean active) {
		this.sprite.setVisible(active);
		super.setActive(active);
	}

	public void endAnimDone() {
		Level.currentLevel.showEndLevel();
	}
	
	private CCFiniteTimeAction getAnimatePortalEnterReference() {		
		return CCRotateBy.action(1.5f, 180f);				
	}
	
	private void applyOtherPortalEnterAction(GameItemCocos item) {		
		CCScaleTo scaleUp = CCScaleTo.action(1.0f, 1.5f);
		// CCDelayTime delay = CCDelayTime.action(2.0f);
		CCScaleTo scaleDown = CCScaleTo.action(0.5f, 0.0f);
		CCSequence action1 = CCSequence.actions(scaleUp, scaleDown);
		CCDelayTime delay2 = CCDelayTime.action(1.0f);				
		CCFadeOut fade = CCFadeOut.action(0.5f);		
		CCSequence action2 = CCSequence.actions(delay2, fade);		
		CCMoveTo moveIn = CCMoveTo.action(0.5f, CGPoint.ccp(this.getPosition().x + this.width / 2, this.getPosition().y));
		CCSequence action3 = CCSequence.actions(delay2, moveIn);
		
		item.getSprite().runAction(action1);
		item.getSprite().runAction(action2);
		item.getSprite().runAction(action3);
	}

	@Override
	public boolean trySelect(CGPoint gameReference) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSelect(CGPoint gameReference) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void selectionMove(CGPoint gameReference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectionStop(CGPoint gameReference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select(CGPoint gameReference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CGRect getSelectionRect() {
		return CGRect.make(this.position.x, this.position.y, Default_Height, Default_Height);
	}

	public CCSprite getThumbail() {		
		if (this.thumbnailSprite == null) {
			this.thumbnailSprite = CCSprite.sprite(thumbnail, true);				
		}
		
		this.thumbnailSprite.setRotation(this.sprite.getRotation());
		this.thumbnailSprite.setScale(Thumbnail.Reference_Width / (107f + Thumbnail.Reference_Inside_Margin));
		return this.thumbnailSprite;			
	}
		
	public CCNode getRootNode() {
		// return this.rootNode;
		return SpriteSheetFactory.getSpriteSheet("tank");
	}

	@Override
	public boolean simpleSelect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isThumbnailAwaysOn() {
		return true;
	}

	@Override
	public boolean isThumbnailActive() {
		return true;
	}
}
