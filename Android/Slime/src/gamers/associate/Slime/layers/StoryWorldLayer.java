package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Rank;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.WorldPackage;
import gamers.associate.Slime.items.custom.Button;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.items.custom.RankFactory;
import gamers.associate.Slime.levels.LevelDefinition;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCSlideInLTransition;
import org.cocos2d.transitions.CCSlideInRTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.ccColor4B;

import android.annotation.SuppressLint;
import android.util.FloatMath;
import android.util.SparseArray;

@SuppressLint("DefaultLocale") 
public class StoryWorldLayer extends CCLayer {
	public static final int COLS = 5;
	private static final int fontSize = 60;
	private static final float transitionTime = 0.5f;
	private CCLabel title;
	private CCMenu menuToRight;
	private CCMenu menuToLeft;
	private CCSprite bkg;
	private CCNode levels;
	private ScrollerLayer scroller;
	
	private int targetPageLeft;
	private int targetPageRight;	
	
	private static int paddingTitle = fontSize + 5;
	
	private static SparseArray<CCScene> diffScenes = new SparseArray<CCScene>();
	private CCMenu backMenu;
	
	private int currentPage;
	
	public static CCScene getScene(int page) {
		CCScene scene = diffScenes.get(page);
		if (scene == null) {
			scene = CCScene.node();			
			scene.addChild(new StoryWorldLayer(page));
			diffScenes.put(page, scene);
		}
				
		return scene;
	}	

	public StoryWorldLayer(int page) {
		this.currentPage = page;
		backMenu = HomeLayer.getBackButton(this, "goBack");
		this.addChild(backMenu, Level.zTop);
		
		this.title = CCLabel.makeLabel("World".toUpperCase(), "fonts/Slime.ttf", fontSize);
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - PauseLayer.PaddingY - fontSize / 2f);
		this.addChild(this.title, Level.zTop);
		float colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
//		ccColor4B.ccc4(255, 255, 255, 200)
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorLight(200), CCDirector.sharedDirector().winSize().width, colorHeight);
		colorLayer.setPosition(0, CCDirector.sharedDirector().winSize().height - colorHeight);
		this.addChild(colorLayer, Level.zFront);
		
		CCSprite spriteN = CCSprite.sprite("arrowl.png");
		CCSprite spriteS = CCSprite.sprite("arrowl.png");
		CCMenuItemSprite leftArrow = CCMenuItemSprite.item(spriteN, spriteS, this, "toLeft");
		leftArrow.setPosition((- CCDirector.sharedDirector().winSize().getWidth() / 2) + (PauseLayer.arrowWidth  / 2), 0);
		this.menuToLeft = CCMenu.menu(leftArrow);		
		this.addChild(this.menuToLeft);		
		
		CCSprite spriteRN = CCSprite.sprite("arrow.png");
		CCSprite spriteRS = CCSprite.sprite("arrow.png");
		CCMenuItemSprite rightArrow = CCMenuItemSprite.item(spriteRN, spriteRS, this, "toRight");
		rightArrow.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2 - PauseLayer.arrowWidth / 2, 0);
		this.menuToRight = CCMenu.menu(rightArrow);		
		this.addChild(this.menuToRight);
		
		this.scroller = new ScrollerLayer();
		this.scroller.setStoryLayer(this);
		this.addChild(this.scroller, Level.zTop);
	}
	
	@Override
	public void onEnter() {
		this.backMenu.setVisible(false);
		this.backMenu.setIsTouchEnabled(false);
		CCDelayTime delay = CCDelayTime.action(transitionTime);
		CCCallFunc showBack = CCCallFunc.action(this, "showBackMenu");
		CCSequence seq = CCSequence.actions(delay, showBack);
		this.backMenu.runAction(seq);
		this.setCurrentLevel(this.currentPage);
		super.onEnter();
	}
	
	public void showBackMenu() {
		this.backMenu.setVisible(true);
		this.backMenu.setIsTouchEnabled(true);
	}

	@Override
	public void onExit() {
		this.backMenu.stopAllActions();
		this.backMenu.setVisible(false);
		this.backMenu.setIsTouchEnabled(false);
		super.onExit();
	}

	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	public void toLeft(Object sender) {
		if (this.menuToLeft.isTouchEnabled()) {
			CCTransitionScene transition = CCSlideInLTransition.transition(transitionTime, StoryWorldLayer.getScene(this.targetPageLeft));
			CCDirector.sharedDirector().replaceScene(transition);
		}
	}
	
	public void toRight(Object sender) {
		if (this.menuToRight.isTouchEnabled()) {
			CCTransitionScene transition = CCSlideInRTransition.transition(transitionTime, StoryWorldLayer.getScene(this.targetPageRight));
			CCDirector.sharedDirector().replaceScene(transition);
		}
	}
	
	private void setCurrentLevel(int page) {
		if (bkg != null) {
			this.removeChild(this.bkg, true);
		}
		
		if (this.levels != null) {
			this.levels.removeAllChildren(true);
			this.removeChild(this.levels, true);
		}
		
		WorldPackage world = SlimeFactory.PackageManager.getPackage(page);
		SlimeFactory.PackageManager.setCurrentPackage(world);
		
		int w = 480;
		int h = 360;
		String spritePath = world.getBackgroundPath();
		this.bkg = HomeLayer.addBkg(this, w, h, spritePath);
		boolean hasLeft = false;
		boolean hasRight = false;
		this.setTitle(world.getName());
		if (page > 1) {
			hasLeft = true;
		}
		
		if (page < SlimeFactory.PackageManager.getPackageCount()) {
			hasRight = true;
		}
		
		this.targetPageLeft = 0;
		this.targetPageRight = 0;
		this.menuToLeft.setVisible(false);
		this.menuToLeft.setIsTouchEnabled(false);
		this.menuToRight.setVisible(false);
		this.menuToRight.setIsTouchEnabled(false);
		
		if (hasLeft) {
			this.menuToLeft.setVisible(true);
			this.menuToLeft.setIsTouchEnabled(true);
			this.targetPageLeft = page - 1;
		}
		
		if (hasRight) {
			this.menuToRight.setVisible(true);
			this.menuToRight.setIsTouchEnabled(true);
			this.targetPageRight = page + 1;
		}
		
		if (!world.isLock()) {
			this.levels = CCNode.node();
			// this.levels.setAnchorPoint(0, 1);
			this.scroller.setHandled(this.levels);
			int cols = COLS;
			int lvls = world.getLevelCount();
			int row = (int) FloatMath.ceil(lvls / cols);
			
			float width = CCDirector.sharedDirector().winSize().getWidth() - PauseLayer.arrowWidth * 2;
			float margeOut = 11;		
			int colSize = (int) (width / cols);
			int rowSize = colSize;
			float targetItemSize = StoryMenuItem.SIZE + margeOut * 2;
			float itemScale = colSize / targetItemSize;				
			float min = (CCDirector.sharedDirector().winSize().getHeight()) - (MenuSprite.Height * PauseLayer.Scale);
			float deltascreen = row * rowSize - min;  
			if (deltascreen < 0) {
				deltascreen = 0;
			}
			float max = min + deltascreen;
			
			this.scroller.setLimits(min, max);
			
			int i = 0;
			float currentLevel = min;
			for(LevelDefinition levelDefinition : world.getLevels()) {
				StoryMenuItem item = StoryMenuItem.item(levelDefinition, this.scroller);			
				
				int colItem = i % cols;
				int rowItem = (int) FloatMath.floor(i / cols);
				float x = colItem * colSize + colSize / 2f;
				if (levelDefinition.isUnlock()) {
					currentLevel = min + (rowItem * rowSize);
				}
				
				float y = - (rowItem * rowSize + rowSize / 2f);
				
				item.setPosition(x, y);
				item.defineNewScale(itemScale);			
				
				this.levels.addChild(item);
				i++;
			}
			
			this.levels.setPosition(PauseLayer.arrowWidth, currentLevel);
			this.addChild(this.levels);
		} else {
			if (this.lockWorld != null) {
				this.lockWorld.stopAllActions();
				this.removeChild(this.lockWorld, true);
			}
			
			this.lockWorld = RankFactory.getSprite(Rank.Lock);
			this.lockWorld.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() / 2);
			this.lockWorld.setScale(3.0f);
			CCScaleTo scaleTo1 = CCScaleTo.action(0.5f, 5.0f);
			CCScaleTo scaleTo2 = CCScaleTo.action(0.5f, 3.0f);
			CCSequence seq = CCSequence.actions(scaleTo1, scaleTo2);
			CCRepeatForever repeat = CCRepeatForever.action(seq);
			this.lockWorld.runAction(repeat);
			this.addChild(this.lockWorld);
		}
	}		
	
	private CCSprite lockWorld;
	
	private void setTitle(String title) {
		this.title.setString(title.toUpperCase());
	}

}