package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.Slimy;
import gamers.associate.Slime.items.custom.Star;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class EndLevelLayer extends CCLayer {			
	private CCSprite slime;
	private CCSprite star;
	private CCLabel scoreLabel;
	private CCMenu menu;	
	private CCMenuItemSprite nextMenu;
	private CCMenuItemSprite restartMenu;
	private CCMenuItemSprite homeMenu;
	
	public EndLevelLayer() {						
		this.scoreLabel = getMenuLabel("0");
		this.scoreLabel.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		CCSprite nextSprite = CCSprite.sprite("control-fastforward.png", true);
		this.nextMenu = CCMenuItemSprite.item(nextSprite, nextSprite, this, "goNext");
		
		CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		this.restartMenu = CCMenuItemSprite.item(restartSprite, restartSprite, this, "goRestart");
		this.restartMenu.setScale(0.5f);
		
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		this.homeMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		this.homeMenu.setScale(0.5f);
		
		this.menu = CCMenu.menu(this.homeMenu, this.nextMenu, this.restartMenu);
		this.menu.alignItemsHorizontally(50);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 - 100
				));				

		this.addChild(this.scoreLabel);
		this.addChild(this.menu);		
	}
	
	public void goNext(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.goNext();
	}
	
	public void goRestart(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.reload();
	}

	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.goHome();
	}
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {		
		super.onEnter();		
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onExit()
	 */
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		super.onExit();
	}
	
	public void setScore(String text) {
		this.scoreLabel.setString(text.toUpperCase());
		
		float starPadding = -10f;
		float starX = this.scoreLabel.getPosition().x - this.scoreLabel.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
		this.star.setPosition(CGPoint.make(
				starX,
				this.star.getPosition().y
				));
	}
	
	public void setScore(int score) {
		String text =String.valueOf(score);
		this.setScore(text);
	}		
		
	public void enable() {
		CCMenu currentMenu = null;
		Boolean hasNext = Level.currentLevel.hasNext();
		/*if (hasNext) {
			currentMenu = this.menu;
		}
		else {
			currentMenu = this.menuEndPack;
		}*/
		currentMenu = this.menu;
		
		this.setIsTouchEnabled(true);
		currentMenu.setIsTouchEnabled(true);
		currentMenu.setVisible(true);
		if (this.slime != null) {
			this.slime.setVisible(true);
		}
		
		if (this.star != null) {
			this.star.setVisible(true);
		}
		
		this.scoreLabel.setVisible(true);
		this.setVisible(true);

//		CCAction action = CCFadeIn.action(1.0f);
//		this.menu.runAction(action);
	}
	
	public void disable() {
		this.setIsTouchEnabled(false);
		if (this.slime != null) {
			this.slime.setVisible(false);
		}
		
		if (this.star != null) {
			this.star.setVisible(false);
		}
		
		this.scoreLabel.setVisible(false);
		this.menu.setIsTouchEnabled(false);
		this.menu.setVisible(false);
		this.setVisible(false);
	}
	
	private static CCLabel getMenuLabel(String text) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 60.0f);
	}
	
	public void setNextEnabled(boolean value) {
		this.nextMenu.setIsEnabled(value);
		this.nextMenu.setVisible(value);
	}	
	
	public void setHomeEnabled(boolean value) {
		this.homeMenu.setIsEnabled(value);
		this.homeMenu.setVisible(value);
	}
	
	public void setVictory(int score) {
		this.initStar();
		this.setScore(score);
		this.initSlime(Slimy.Anim_Success, 0f);
	}
	
	public void setLose() {
		this.initStar();
		this.setScore(0);
		this.initSlime(Slimy.Anim_LastDeath, 2f);
	}
	
	private void initStar() {
		if (this.star == null) {
			this.star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);
			this.addChild(this.star);
		}
				
		this.star.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));					
	}
	
	private void initSlime(String animation, float waitTime) {
		if (this.slime == null) {
			this.slime = SlimeFactory.Slimy.getAnimatedSprite(animation, waitTime);
			this.slime.setScale(2f);
			this.addChild(this.slime);
		} else {
			this.slime.stopAllActions();
			this.slime.runAction(SlimeFactory.Slimy.getAnimation(animation, waitTime));
		}				
		
		this.slime.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 + 100
				));
	}
}