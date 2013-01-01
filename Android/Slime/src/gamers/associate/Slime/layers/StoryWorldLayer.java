package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.Rank;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.WorldPackage;
import gamers.associate.Slime.items.custom.Button;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.items.custom.RankFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
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

import android.annotation.SuppressLint;
import android.util.FloatMath;
import android.util.SparseArray;

@SuppressLint("DefaultLocale") 
public class StoryWorldLayer extends CCLayer {
	private static final int fontSize = 60;
	private static final float transitionTime = 0.5f;
	private CCLabel title;
	private CCMenu menuToRight;
	private CCMenu menuToLeft;
	private CCSprite bkg;
	private CCNode levels;
	private ScrollerLayer scroller;
	
	private int targetDiffLeft;
	private int targetDiffRight;	
	
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
		this.addChild(backMenu);
		
		this.title = CCLabel.makeLabel("World".toUpperCase(), "fonts/Slime.ttf", fontSize);
		this.title.setColor(SlimeFactory.ColorSlime);
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - paddingTitle);
		this.addChild(this.title);
		
		CCSprite spriteN = CCSprite.sprite("arrowl.png");
		CCSprite spriteS = CCSprite.sprite("arrowl.png");
		CCMenuItemSprite leftArrow = CCMenuItemSprite.item(spriteN, spriteS, this, "toLeft");
		leftArrow.setScale(PauseLayer.Scale);
		leftArrow.setPosition((- CCDirector.sharedDirector().winSize().getWidth() / 2) + ((PauseLayer.arrowWidth * PauseLayer.Scale) / 2), 0);
		this.menuToLeft = CCMenu.menu(leftArrow);		
		this.addChild(this.menuToLeft);		
		
		CCSprite spriteRN = CCSprite.sprite("arrow.png");
		CCSprite spriteRS = CCSprite.sprite("arrow.png");
		CCMenuItemSprite rightArrow = CCMenuItemSprite.item(spriteRN, spriteRS, this, "toRight");
		rightArrow.setScale(PauseLayer.Scale);
		rightArrow.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2 - (PauseLayer.arrowWidth * PauseLayer.Scale) / 2, 0);
		this.menuToRight = CCMenu.menu(rightArrow);		
		this.addChild(this.menuToRight);
		
		this.scroller = new ScrollerLayer();
		this.addChild(this.scroller, Level.zTop);
		
		// Not needed? background enough...
		this.title.setVisible(false);		
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
		CCTransitionScene transition = CCSlideInLTransition.transition(transitionTime, StoryWorldLayer.getScene(this.targetDiffLeft));
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	public void toRight(Object sender) {
		CCTransitionScene transition = CCSlideInRTransition.transition(transitionTime, StoryWorldLayer.getScene(this.targetDiffRight));
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	private void setCurrentLevel(int page) {
		if (bkg != null) {
			this.removeChild(this.bkg, true);
		}
		
		if (this.levels != null) {
			this.removeChild(this.levels, true);
			this.levels.removeAllChildren(true);
		}
		
		WorldPackage world = SlimeFactory.PackageManager.getPackage(page);
		SlimeFactory.PackageManager.setCurrentPackage(world);
		
		int w = 480;
		int h = 360;
		String spritePath = world.getBackgroundPath();
		HomeLayer.addBkg(this, w, h, spritePath);
		boolean hasLeft = false;
		boolean hasRight = false;
		this.setTitle(world.getName());
		if (page > 1) {
			hasLeft = true;
		}
		
		if (page < SlimeFactory.PackageManager.getPackageCount()) {
			hasRight = true;
		}
		
		this.targetDiffLeft = 0;
		this.targetDiffRight = 0;
		this.menuToLeft.setVisible(false);
		this.menuToLeft.setIsTouchEnabled(false);
		this.menuToRight.setVisible(false);
		this.menuToRight.setIsTouchEnabled(false);
		
		if (hasLeft) {
			this.menuToLeft.setVisible(true);
			this.menuToLeft.setIsTouchEnabled(true);
			this.targetDiffLeft = page - 1;
		}
		
		if (hasRight) {
			this.menuToRight.setVisible(true);
			this.menuToRight.setIsTouchEnabled(true);
			this.targetDiffRight = page + 1;
		}
		
		this.levels = CCNode.node();
		// this.levels.setAnchorPoint(0, 1);
		this.scroller.setHandled(this.levels);
		int cols = 5;
		int lvls = world.getLevelCount();
		int row = (int) FloatMath.ceil(lvls / cols);
		
		float width = CCDirector.sharedDirector().winSize().getWidth() - (PauseLayer.arrowWidth + (MenuSprite.Width * PauseLayer.Scale));
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
		for(LevelDefinition levelDefinition : world.getLevels()) {
			StoryMenuItem item = StoryMenuItem.item(levelDefinition);			
			
			int colItem = i % cols;
			int rowItem = (int) FloatMath.floor(i / cols);
			float x = colItem * colSize + colSize / 2f;
			float y = - (rowItem * rowSize + rowSize / 2f);
			item.setPosition(x, y);
			item.setScale(itemScale);			
			
			this.levels.addChild(item);
			i++;
		}
		
		this.levels.setPosition(MenuSprite.Width * PauseLayer.Scale, min);
		this.addChild(this.levels);
	}		

	private void setTitle(String title) {
		this.title.setString(title.toUpperCase());
	}

}