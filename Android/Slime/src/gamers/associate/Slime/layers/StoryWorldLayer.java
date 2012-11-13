package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.levels.LevelHome;

import java.util.HashMap;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCSlideInLTransition;
import org.cocos2d.transitions.CCSlideInRTransition;
import org.cocos2d.transitions.CCTransitionScene;

public class StoryWorldLayer extends CCLayer {
	private static final int fontSize = 60;
	private static final float transitionTime = 0.5f;
	private CCLabel title;
	private CCMenu menuToRight;
	private CCMenu menuToLeft;
	private CCSprite bkg;
	private CCMenu levels;	
	
	private int targetDiffLeft;
	private int targetDiffRight;	
	
	private static int paddingTitle = fontSize + 5;
	
	private static HashMap<Integer, CCScene> diffScenes = new HashMap<Integer, CCScene>();
	private CCMenu backMenu; 
	
	public static CCScene getScene(int difficulty) {
		CCScene scene = diffScenes.get(difficulty);
		if (scene == null) {
			scene = CCScene.node();			
			scene.addChild(new StoryWorldLayer(difficulty));
			diffScenes.put(difficulty, scene);
		}
				
		return scene;
	}	

	public StoryWorldLayer(int difficulty) {		
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
		
		this.setCurrentLevel(difficulty);
		
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
		Level currentLevel = Level.get(LevelHome.Id, true);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
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
	
	private void setCurrentLevel(int difficulty) {
		if (bkg != null) {
			this.removeChild(this.bkg, true);
		}
		
		int w = 480;
		int h = 360;
		String spritePath = LevelDifficulty.getSpriteBkgPath(difficulty);
		HomeLayer.addBkg(this, w, h, spritePath);
		boolean hasLeft = false;
		boolean hasRight = false;
		switch (difficulty) {
		default:
		case LevelDifficulty.Easy:			
			this.setTitle("To Mexico!");
			hasRight = true;
			break;
		case LevelDifficulty.Normal:
			hasLeft = true;
			hasRight = true;
			this.setTitle("To the Moon!");
			break;
		case LevelDifficulty.Hard:
			hasLeft = true;
			hasRight = true;
			this.setTitle("To the Disco!");
			break;
		case LevelDifficulty.Extrem:
			hasLeft = true;			
			this.setTitle("To Hawaii!");
			break;			
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
			this.targetDiffLeft = LevelDifficulty.getPreviousDifficulty(difficulty);
		}
		
		if (hasRight) {
			this.menuToRight.setVisible(true);
			this.menuToRight.setIsTouchEnabled(true);
			this.targetDiffRight = LevelDifficulty.getNextDifficulty(difficulty);
		}
		
		this.levels = CCMenu.menu();
		int cols = 5;
		int lvls = SlimeFactory.GameInfo.getLevelMax(difficulty);
		int row = (int) Math.ceil(lvls / cols);
		for(int i = 0; i < lvls; i++) {
			CCSprite spriteN = CCSprite.sprite("control-square-empty.png", true);
			CCSprite spriteS = CCSprite.sprite("control-square-empty.png", true);
			CCMenuItemSprite item = CCMenuItemSprite.item(spriteN, spriteS, this, "");
			item.setScale(133 / (CCDirector.sharedDirector().winSize().getWidth() / cols));
			this.levels.addChild(item);
		}
		
		int[] colRows = new int[row];
		for (int i = 0 ; i < row; i++) {
			colRows[i] = cols;
		}
		
		this.levels.alignItemsInColumns(colRows);
		this.levels.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, (CCDirector.sharedDirector().winSize().getHeight() / 2) - (MenuSprite.Height * PauseLayer.Scale));
		this.addChild(this.levels);
	}	
	
	private void setTitle(String title) {
		this.title.setString(title.toUpperCase());
	}
}
