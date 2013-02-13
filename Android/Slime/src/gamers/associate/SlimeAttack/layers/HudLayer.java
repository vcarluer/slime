package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.IGameItemHandler;
import gamers.associate.SlimeAttack.game.IGamePlay;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.game.Util;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.base.SpriteSheetFactory;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;
import gamers.associate.SlimeAttack.items.custom.Star;
import gamers.associate.SlimeAttack.items.custom.StarCounter;
import gamers.associate.SlimeAttack.items.custom.StarCounterFactory;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelDefinitionParser;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale") public class HudLayer extends CCLayer implements IGameItemHandler {
	private static final String TIME_S_UP = "TIME'S UP";
	private static final int countPaddingX = 130;
	private static final float scoreTakenPadding = 10f;
	private static final float TextHeight = 60f;
	private static final float PaddingHCount = TextHeight/2f;
	private static final float PaddingLeftStar = 25f;
	private static final int starCountHShift = 35;
	private static final float starCountBarHeight = 30f;
	private static final float starCountBarWidthMax = 200f;

	private static String Count_Text = "Hud";
	
	private CCBitmapFontAtlas countLabel;	
	private float countX;
	
	private CCMenu menu;
	
	private CCBitmapFontAtlas title;
	private CCBitmapFontAtlas scoreTaken;
	
	private CGPoint tmp = CGPoint.zero();
	
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
	
	private CCMenu selectLevelMenu;
	private CCMenu rebuildLevelMenu;
	
	private boolean hideCount;
	
	private CCLabel timesup;
	
	public HudLayer() {
		
		this.gameItems = new ArrayList<GameItem>();
		this.gameItemsToAdd = new ArrayList<GameItem>();
		this.gameItemstoRemove = new ArrayList<GameItem>();
		this.starCounters = new ArrayList<StarCounter>();		
		
		this.menu = HomeLayer.getPauseButton(this, "goPause");
		this.addChild(this.menu);
		
		this.countLabel = getMenuLabel(Count_Text);		
		this.countLabel.setAnchorPoint(0, 0);
		this.addChild(this.countLabel);
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - 15, 
				CCDirector.sharedDirector().winSize().getHeight() - countPaddingX));
		
		this.scoreTaken = getMenuLabel("   ", TextHeight, SlimeFactory.ColorSlime);
		this.scoreTaken.setAnchorPoint(0, 0);
		this.addChild(this.scoreTaken);
				
		this.hideSlimyCount();
		
		this.title = getMenuLabel(" ", 45f, SlimeFactory.ColorSlimeBorder);
		this.title.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() / 2));
		this.addChild(this.title);		
		
		this.starsTaken = new ArrayList<CCSprite>();
		this.starsToAdd = new ArrayList<CCSprite>();
		this.starsToDelete = new ArrayList<CCSprite>();
		this.starX = CCDirector.sharedDirector().winSize().getWidth() / 2 - Star.Reference_Width - PaddingLeftStar;
		this.starY = CCDirector.sharedDirector().winSize().getHeight() - (starCountHShift + Star.Reference_Height / 2);
				
		if (SlimeFactory.IsLevelSelectionOn && SlimeFactory.IsLevelSelectionShowButtons) {
			float recordX = CCDirector.sharedDirector().winSize().getWidth() / 2f - ((MenuSprite.Width * PauseLayer.Scale) + PauseLayer.PaddingX) / 2 ;
			float recordY = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY) / 2;;
			float rebuildX = recordX - ((MenuSprite.Width * PauseLayer.Scale) + PauseLayer.PaddingX);
			this.selectLevelMenu = HomeLayer.getMenuButton("control-empty.png", recordX, recordY, this, "recordLvl");
			this.rebuildLevelMenu = HomeLayer.getMenuButton("control-restart.png", rebuildX, recordY, this, "rebuildLvl");
			this.addChild(this.selectLevelMenu);
			this.addChild(this.rebuildLevelMenu);
		}
		
		this.timesup = SlimeFactory.getLabel(TIME_S_UP, 63f);
		this.timesup.setColor(SlimeFactory.ColorSlime);
		this.timesup.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2f, CCDirector.sharedDirector().winSize().getHeight() / 2);
		this.addChild(this.timesup);
		this.timesup.setVisible(false);
	}	
	
	private static CCBitmapFontAtlas getMenuLabel(String text) {
		return getMenuLabel(text, TextHeight);
	}
	
	private static CCBitmapFontAtlas getMenuLabel(String text, float size) {
		return getMenuLabel(text, size, ccColor3B.ccWHITE);
	}
	
	private static CCBitmapFontAtlas getMenuLabel(String text, float size, ccColor3B color) {
		CCBitmapFontAtlas label =  CCBitmapFontAtlas.bitmapFontAtlas("0123456789", "SlimeFont.fnt");
		label.setScale(size / SlimeFactory.FntSize);
		label.setColor(color);
		return label;
	}
	
	@Override
	public void onEnter() {		
		super.onEnter();				
		this.starSprite = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);			
		this.starSprite.setPosition(this.starX, this.starY);
		this.starSprite.setAnchorPoint(0, 0f);
		this.addChild(this.starSprite);
		CCFadeOut fadeOut = CCFadeOut.action(0);
		this.timesup.runAction(fadeOut);
		// this.setStarsCount();
	}
	
	private void setStarsCount() {
		boolean activate = false;
		if (SlimeFactory.LevelBuilder.getTotalStar() > 0) {
			IGamePlay gp = Level.currentLevel.getGamePlay();
			if (gp != null) {				
				float xPosBase = CCDirector.sharedDirector().winSize().getWidth() / 2;
				float yPos = CCDirector.sharedDirector().winSize().getHeight() - starCountHShift;
				float w = starCountBarHeight * StarCounter.Default_Width / StarCounter.Default_Height * SlimeFactory.SGSDensity;
				float totalW = w * SlimeFactory.LevelBuilder.getTotalStar();
				if (totalW > starCountBarWidthMax) {
					w = starCountBarWidthMax / SlimeFactory.LevelBuilder.getTotalStar();
					totalW = w * SlimeFactory.LevelBuilder.getTotalStar();
				}
				
				for(int i = 0; i < SlimeFactory.LevelBuilder.getTotalStar(); i++) {					
					float xPos = xPosBase + (i * w);
					SlimeFactory.StarCounter.create(xPos, yPos, w, starCountBarHeight, i + 1, gp.neededBonus(), SlimeFactory.LevelBuilder.getTotalStar());
				}
				
				this.scoreTaken.setPosition(xPosBase - (w / 2f) + totalW  + scoreTakenPadding, CCDirector.sharedDirector().winSize().getHeight() - (starCountHShift + PaddingHCount));				
				activate = true;
			}			
		}
		
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
		this.currentTitle = null;
	}
	
	public void gameBegin() {
		this.title.stopAllActions();
		this.title.setVisible(false);
		this.currentTitle = null;
	}

	@Override
	public void onExit() {
		this.currentTitle = null;
		if (this.starSprite != null) {
			this.removeChild(this.starSprite, true);
			this.starSprite = null;
		}
		super.onExit();
	}
	
	public void goPause(Object sender) {
		Sounds.playEffect(R.raw.menuselect);		
		Level.currentLevel.pause();		
		SlimeFactory.ContextActivity.showAndNextAd();
	}
	
	public void setSlimyCount(int count) {
		if (!this.isHideCount()) {
			this.countLabel.setVisible(true);		
			this.countLabel.setString((Count_Text + String.valueOf(count)).toUpperCase());
		}
	}
	
	public void setHudText(String text) {
		if (!this.isHideCount()) {
			this.countLabel.setVisible(true);
			this.countLabel.setString(text.toUpperCase());
		}
	}
	
	public void setHudStartText(String text) {
		this.countLabel.setString(text.toUpperCase());
		this.countX = CCDirector.sharedDirector().winSize().getWidth() - PauseLayer.PaddingX - countPaddingX; // this.countLabel.getContentSize().width
		this.countLabel.setPosition(
				CGPoint.ccp(this.countX, 
				CCDirector.sharedDirector().winSize().getHeight() - (starCountHShift + PaddingHCount)));
	}
	
	public void hideHudText() {
		if (this.countLabel != null) {
			this.countLabel.setVisible(false);
		}
		
		if (this.scoreTaken != null) {
			this.scoreTaken.setVisible(false);
		}
	}
	
	public CCBitmapFontAtlas getLabel() {
		return this.countLabel;
	}
	
	public void hideSlimyCount() {
		this.countLabel.setVisible(false);
		this.scoreTaken.setVisible(false);
	}
	
	public CCMenu getMenu() {
		return this.menu;
	}
	
	private String currentTitle;
	
	public void setTitle(String titleText) {
		if (titleText == null) {
			this.currentTitle = null;
		} else {
			if (this.currentTitle == null) {
				if (Level.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.TimeAttack) {
					this.currentTitle = String.valueOf(Level.currentLevel.getLevelDefinition().getNumber()) + ". " + titleText;
				} else {
					this.currentTitle = String.valueOf(SlimeFactory.GameInfo.getLevelNum()) + ". " + titleText;
				}
				
				this.title.stopAllActions();
				this.title.setVisible(true);
				this.title.setString(this.currentTitle.toUpperCase());
				// double padding
				float dPadding = 250f;
				float scaleRatio = CCDirector.sharedDirector().winSize().width / (this.title.getContentSize().width + dPadding);
				this.title.setScale(scaleRatio);
				this.title.setPosition(
						CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
						CCDirector.sharedDirector().winSize().getHeight() / 2));
				this.title.setOpacity(255);
				float moveDistance =  CCDirector.sharedDirector().winSize().getWidth() / 2 - (this.title.getContentSize().width * scaleRatio) / 2f;
				float time = 3f;
				CCDelayTime delay = CCDelayTime.action(time);
				CCCallFunc call = CCCallFunc.action(this, "fadeTitle");
				CCSequence seq = CCSequence.actions(delay, call);
				this.title.runAction(seq);
				tmp.set(-moveDistance, 0);
				CCMoveBy move = CCMoveBy.action(time, tmp);
				this.title.runAction(move);
			}
		}
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
				
				if (this.starCounters.size() > this.counterIdx) {
					StarCounter currentCounter = this.starCounters.get(this.counterIdx);
					currentCounter.takeStar();
					this.counterIdx++;
					
					IGamePlay gp = Level.currentLevel.getGamePlay();
					// Extra bonus anim
					if (this.counterIdx == this.starCounters.size()) {							
						for(StarCounter counter: this.starCounters) {
							if (counter != currentCounter) {
								counter.animSup();
							}							
						}
						
						if (!this.isHideCount()) {
							if (gp != null) {
								this.scoreTaken.setString("+" + Util.getFormatTime(gp.getExtraBonusPoints()));
							}
						}
						
					} else {
						if (!this.isHideCount()) {
							if (gp != null) {
								this.scoreTaken.setString("+" + Util.getFormatTime(gp.getNormalBonusPoints()));
							}						
						}
					}
					
					if (!this.isHideCount()) {
						this.scoreTaken.setVisible(true);
						this.scoreTaken.stopAllActions();
						CCFadeIn fi = CCFadeIn.action(0.1f);
						CCDelayTime delay = CCDelayTime.action(1.0f);
						CCFadeOut fade = CCFadeOut.action(0.5f);
						CCSequence seq = CCSequence.actions(fi, delay, fade);
						this.scoreTaken.runAction(seq);
					}
				}
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
				this.starsTaken.add(star);
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
		SpriteSheetFactory.attach(StarCounterFactory.CONTROL_STARS, this);
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
		
		for(CCSprite sprite : this.starsToAdd) {
			sprite.cleanup();
		}
		
		for(CCSprite sprite : this.starsToDelete) {
			sprite.cleanup();
			this.removeChild(sprite, true);
		}
		
		for(CCSprite sprite : this.starsTaken) {
			sprite.cleanup();
			this.removeChild(sprite, true);
		}
		
		this.starsToAdd.clear();
		this.starsToDelete.clear();
		this.starsTaken.clear();
		
		this.starCounters.clear();
		this.counterIdx = 0;
		
		this.scoreTaken.cleanup();
		
		this.hideTimesUp();
	}		

	public boolean isHideCount() {
		return hideCount;
	}

	public void setHideCount(boolean hideCount) {
		this.hideCount = hideCount;
	}
	
	public void rebuildLvl(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.getLevelDefinition().setInvalidated(true);
		Level.currentLevel.reload();
	}
	
	public void recordLvl(Object sender) {
		LevelDefinition levelDef = Level.currentLevel.getLevelDefinition();
		if (levelDef instanceof LevelDefinitionParser) {
			LevelDefinitionParser parser = (LevelDefinitionParser) levelDef;
			parser.dumpLevel();
		}
		
		Level.currentLevel.goNext();
	}
	
	public void timesup() {
		this.timesup.setVisible(true);
		CCFadeIn fadeIn = CCFadeIn.action(1.0f);
		CCDelayTime delay = CCDelayTime.action(1.0f);
		CCFadeOut fadeOut = CCFadeOut.action(0.3f);
		CCSequence seq = CCSequence.actions(fadeIn, delay, fadeOut);
		this.timesup.runAction(seq);
	}
	
	public void hideTimesUp() {
		this.timesup.stopAllActions();
		this.timesup.setVisible(false);
	}
}
