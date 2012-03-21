package gamers.associate.Slime.layers;

import java.util.ArrayList;
import java.util.List;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.IGameItemHandler;
import gamers.associate.Slime.game.IGamePlay;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelSelection;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.TitleGenerator;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.items.custom.Star;
import gamers.associate.Slime.items.custom.StarCounter;
import gamers.associate.Slime.items.custom.StarFactory;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

public class HudLayer extends CCLayer implements IGameItemHandler {
	private static String Count_Text = "Hud";
	
	private CCLabel countLabel;
	
	private CCMenu menu;
	
	private CCLabel title;
	
	private CGPoint tmp = CGPoint.zero();
	
	private CCLabel starLabel;
	private CCSprite starSprite;
	
	private List<CCSprite> starsToAdd;
	private List<CCSprite> starsToDelete;
	private List<CCSprite> starsTaken;
	private float starX;
	private float starY;
	
	private Object sync = new Object();
	
	private List<GameItem> gameItems;
	private List<GameItem> gameItemsToAdd;
	private List<GameItem> gameItemstoRemove;
	
	private List<StarCounter> starCounters;
	private int counterIdx;
	
	public HudLayer() {
		
		this.gameItems = new ArrayList<GameItem>();
		this.gameItemsToAdd = new ArrayList<GameItem>();
		this.gameItemstoRemove = new ArrayList<GameItem>();
		this.starCounters = new ArrayList<StarCounter>();
		float pauseScale = PauseLayer.Scale;
		CCSprite pauseSprite = CCSprite.sprite("control-pause.png", true);
		CCMenuItemSprite pauseMenu = CCMenuItemSprite.item(pauseSprite, pauseSprite, this, "goPause");
		pauseMenu.setScale(pauseScale);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * pauseScale) + PauseLayer.PaddingX) / 2 ;
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * pauseScale) + PauseLayer.PaddingY) / 2;
		pauseMenu.setPosition(CGPoint.make(left, top));
		
		
		this.menu = CCMenu.menu(pauseMenu);		
		this.addChild(this.menu);
		
		this.countLabel = getMenuLabel(Count_Text);		
		this.countLabel.setAnchorPoint(0, 0);
		this.addChild(this.countLabel);
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - 15, 
				CCDirector.sharedDirector().winSize().getHeight() - 65));
				
		this.hideSlimyCount();
		
		this.title = getMenuLabel(" ", 45f, SlimeFactory.ColorSlimeBorder);
		this.title.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() / 2));
		this.addChild(this.title);
		
		this.starLabel = getMenuLabel("0 / 0");
		this.starLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() - 65));
		this.starLabel.setColor(SlimeFactory.ColorSlime);
		this.starLabel.setAnchorPoint(0, 0f);
		this.addChild(starLabel);				
		
		this.starsTaken = new ArrayList<CCSprite>();
		this.starsToAdd = new ArrayList<CCSprite>();
		this.starsToDelete = new ArrayList<CCSprite>();
		this.starX = CCDirector.sharedDirector().winSize().getWidth() / 2 - Star.Reference_Width - 25f;
		this.starY = CCDirector.sharedDirector().winSize().getHeight() - 55;		
	}	
	
	private static CCLabel getMenuLabel(String text) {
		return getMenuLabel(text, 60f);
	}
	
	private static CCLabel getMenuLabel(String text, float size) {
		return getMenuLabel(text, size, ccColor3B.ccWHITE);
	}
	
	private static CCLabel getMenuLabel(String text, float size, ccColor3B color) {
		CCLabel label =  CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", size);
		label.setColor(color);
		return label;
	}
	
	@Override
	public void onEnter() {		
		super.onEnter();
		this.setTitle(TitleGenerator.generateNewTitle());
		this.setStarsCount();
		
		this.starSprite = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);			
		this.starSprite.setPosition(this.starX, this.starY);
		this.starSprite.setAnchorPoint(0, 0f);
		this.addChild(this.starSprite);				
	}
	
	private void setStarsCount() {
		boolean activate = false;
		if (SlimeFactory.LevelBuilder.getTotalStar() > 0) {
			IGamePlay gp = Level.currentLevel.getGamePlay();
			if (gp != null) {
				String txt = String.valueOf(gp.bonusCount()) + " / " + String.valueOf(gp.neededBonus());
				this.starLabel.setString(txt);
				this.starLabel.setPosition(
						CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
						CCDirector.sharedDirector().winSize().getHeight() - 65));
				
				activate = true;
			}			
		}
		
		this.starLabel.setVisible(activate);
		this.setStarSprite(activate);
	}	
	
	private void setStarSprite(boolean activate) {
		if (this.starSprite != null) {			
			this.starSprite.setVisible(activate);			
		}
	}
	
	public void upudateStarsCount() {
		this.setStarsCount();
	}
	
	public void fadeTitle() {
		CCFadeOut fade = CCFadeOut.action(1f);
		this.title.runAction(fade);
	}
	
	public void gameBegin() {
		this.title.stopAllActions();
		this.title.setVisible(false);
	}

	@Override
	public void onExit() {
		super.onExit();
	}

	public void goBack(Object sender) {
		Level currentLevel = Level.get(LevelHome.Id, true);							
		CCTransitionScene transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
		Sounds.playMusic(R.raw.menumusic, true);
	}
	
	public void goPause(Object sender) {
		Sounds.playEffect(R.raw.menuselect);		
		Level.currentLevel.pause();		
		SlimeFactory.ContextActivity.showAndNextAd();
	}
	
	public void setSlimyCount(int count) {
		this.countLabel.setVisible(true);
		this.countLabel.setString((Count_Text + String.valueOf(count)).toUpperCase());
	}
	
	public void setHudText(String text) {
		this.countLabel.setVisible(true);
		this.countLabel.setString(text.toUpperCase());		
	}
	
	public void setHudStartText(String text) {
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - PauseLayer.PaddingX - this.countLabel.getContentSize().width, 
				CCDirector.sharedDirector().winSize().getHeight() - 65));		
		this.countLabel.setString(text.toUpperCase());		
	}
	
	public void hideHudText() {
		if (this.countLabel != null) {
			this.countLabel.setVisible(false);
		}
	}
	
	public CCLabel getLabel() {
		return this.countLabel;
	}
	
	public void hideSlimyCount() {
		this.countLabel.setVisible(false);
	}
	
	public CCMenu getMenu() {
		return this.menu;
	}
	
	public void setTitle(String title) {
		this.title.stopAllActions();
		this.title.setVisible(true);
		this.title.setString(title.toUpperCase());
		// double padding
		float dPadding = 250f;
		float scaleRatio = CCDirector.sharedDirector().winSize().width / (this.title.getContentSize().width + dPadding);
		this.title.setScale(scaleRatio);
		this.title.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() / 2));
		this.title.setOpacity(255);
		float moveDistance = 75f;
		float time = 3f;
		CCDelayTime delay = CCDelayTime.action(time);
		CCCallFunc call = CCCallFunc.action(this, "fadeTitle");
		CCSequence seq = CCSequence.actions(delay, call);
		this.title.runAction(seq);
		tmp.set(-moveDistance, 0);
		CCMoveBy move = CCMoveBy.action(time, tmp);
		this.title.runAction(move);
	}
	
	public void starTaken(float x, float y) {
		CCSprite star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait, Star.BaseFrameName); // Anim_Fade
		star.setPosition(x, y);
		star.setScale(1 / Level.currentLevel.getCameraManager().getCurrentZoom());
		synchronized(this.sync) {
			this.starsToAdd.add(star);
		}		
	}		
	
	public void endMoveStar() {
		synchronized(this.sync) {
			if (this.starsTaken.size() > 0) {
				this.starsToDelete.add(this.starsTaken.get(0));
				this.starsTaken.remove(0);
			}
			
			if (this.starCounters.size() > this.counterIdx) {
				this.starCounters.get(this.counterIdx).takeStar();
				this.counterIdx++;
			}
		}		
	}

	public void starTaken(CGPoint screenPos) {
		this.starTaken(screenPos.x, screenPos.y);
	}
	
	public void render(float delta) {
		
		// Star animation management
		synchronized(this.sync) {
			for(CCSprite star : this.starsToAdd) {
				this.addChild(star);
				CCMoveTo moveTo = CCMoveTo.action(Star.AnimDelay, CGPoint.make(this.starX + Star.Reference_Width / 2f, this.starY + Star.Reference_Height / 2f));
				CCCallFunc endMove = CCCallFunc.action(this, "endMoveStar");		
				CCSequence seq = CCSequence.actions(moveTo, endMove);
				CCScaleTo scale = CCScaleTo.action(Star.AnimDelay - 0.1f, 1.0f);		
				star.runAction(seq);
				star.runAction(scale);
			}
			
			this.starsToAdd.clear();
			
			for(CCSprite star : this.starsToDelete) {
				this.removeChild(star, true);
			}
			
			this.starsToDelete.clear();
		}		
		
		synchronized(this.sync) {
			for(GameItem item : this.gameItemsToAdd) {
				this.gameItems.add(item);
				if (item instanceof StarCounter) {
					StarCounter counter = (StarCounter) item;
					this.starCounters.add(counter);
				}
			}
			
			this.gameItemsToAdd.clear();
			
			for(GameItem item : this.gameItemstoRemove) {
				this.gameItems.remove(item);
				if (item instanceof StarCounter) {
					StarCounter counter = (StarCounter) item;
					this.starCounters.remove(counter);
				}				
			}
			
			this.gameItemstoRemove.clear();
		}
	}

	@Override
	public void addItemToAdd(GameItem item) {
		synchronized(this.sync) {
			this.gameItemsToAdd.add(item);
		}
	}

	@Override
	public void addItemToRemove(GameItem item) {
		synchronized(this.sync) {
			this.gameItemstoRemove.add(item);
		}		
	}

	@Override
	public void attachToFactory() {
		SlimeFactory.StarCounter.attach(this, this);
	}

	@Override
	public void resetLevel() {
		for(GameItem item : this.gameItems) {
			item.destroy();
		}
		
		for(GameItem item : this.gameItemsToAdd) {
			item.destroy();
		}
		
		for(GameItem item : this.gameItemstoRemove) {
			item.destroy();
		}
		
		this.gameItems.clear();
		this.gameItemsToAdd.clear();
		this.gameItemstoRemove.clear();
		
		this.starCounters.clear();
		this.counterIdx = 0;
	}
}
