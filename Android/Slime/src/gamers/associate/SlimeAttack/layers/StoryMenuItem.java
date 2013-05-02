package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.Rank;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.RankFactory;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelDefinition;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class StoryMenuItem extends CCLayer {
	public static final int SIZE = 165;
	private static final float scaleStar = 1.5f; // 40 * 1.5 = 60
	private static final float targetSize = 60;
	private static final float yshift = - 35;
	
	private CCSprite backItem; 
	private CCLabel idItem;
	private CCSprite starItem;	
	private CCSprite marketItem;
	
	private LevelDefinition levelDefinition;
	private ScrollerLayer scroller;
	
	/** Creates a CCMenuItem with a target/selector */
    public static StoryMenuItem item(LevelDefinition levelDefinition, ScrollerLayer scroller) {
        return new StoryMenuItem(levelDefinition, scroller);
    }
	
	public StoryMenuItem(LevelDefinition levelDefinition, ScrollerLayer scroller) {
		this.scroller = scroller;
		this.levelDefinition = levelDefinition;
		
		this.backItem = CCSprite.sprite("control-square-empty.png", true);
		this.addChild(this.backItem);
		this.updateItem();
		this.setContentSize(SIZE, SIZE);
		this.setIsTouchEnabled(true);
	}
	
	public void defineNewScale(float s) {
		this.setScale(s);
		float baseX = this.getPositionRef().x;
		this.setPosition(- SIZE * this.getScale(), this.getPositionRef().y);
		this.setAnchorPoint(0, 0);
		CCDelayTime delay = CCDelayTime.action(0.4f + ((this.levelDefinition.getNumber() - 1) % StoryWorldLayer.COLS) * (2 / StoryWorldLayer.COLS));
		CCMoveTo actionTo = CCMoveTo.action(0.3f, CGPoint.make(baseX, this.getPositionRef().y));		
		
		CCSequence seq = CCSequence.actions(delay,  actionTo);
		this.runAction(seq);
	}

	public void updateItem() {
		if (this.idItem != null) {
			this.removeChild(this.idItem, true);			
		}
		
		if (this.starItem != null) {
			this.removeChild(this.starItem, true);
		}
		
		if (this.marketItem != null) {
			this.removeChild(this.marketItem, true);
		}
		
		if (this.levelDefinition != null) {
			this.idItem = SlimeFactory.getLabel(String.valueOf(this.levelDefinition.getNumber()), 32*SlimeFactory.getWidthRatio());
			this.idItem.setPosition(0, 50 + yshift);	
			if (SlimeFactory.LiteVersion && 
					this.levelDefinition.getNumber() > SlimeFactory.LiteStoryMaxLevel &&
					this.levelDefinition.getRank() != Rank.Lock) {
				this.marketItem = CCSprite.sprite("playstore.png");
				this.marketItem.setPosition(0,  yshift);
				this.marketItem.setScale(targetSize / SlimeFactory.LiteMarketSize);
				this.addChild(this.marketItem);
			} else {			
				this.starItem = RankFactory.getSprite(this.levelDefinition.getRank());		
				this.starItem.setPosition(0,  yshift);
				this.starItem.setScale(scaleStar);
				this.addChild(this.starItem);
			}
					
			this.addChild(this.idItem);
			
		}
	}
	
	public LevelDefinition getLevelDefinition() {
		return this.levelDefinition;
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (this.scroller == null || !this.scroller.hasMoved()) {
			float x = event.getX();
			float y = CCDirector.sharedDirector().winSize().getHeight() - event.getY();
			float parentX = this.getParent().getPositionRef().x;
			float parentY = this.getParent().getPositionRef().y;
			float realX = this.getPositionRef().x + parentX;
			float realY = this.getPositionRef().y + parentY;
			boolean inrectX = x < realX + SIZE / 2f && x > realX - SIZE / 2f;
			boolean inrectY = y < realY + SIZE / 2F && y > realY - SIZE / 2f;
			if (inrectX && inrectY) {
				this.select();
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}

	private void select() {		
		Sounds.stopMusic();
		if (this.levelDefinition.isUnlock()) {
			Sounds.playEffect(R.raw.menuselect);
			if (this.levelDefinition.getNumber() == 1) {
				SlimeFactory.ContextActivity.runIntro(this);
			} else {
				if (SlimeFactory.LiteVersion && this.levelDefinition.getNumber() > SlimeFactory.LiteStoryMaxLevel) {
					CCTransitionScene transition = CCFadeTransition.transition(0.5f, LiteGameOverLayer.getScene(SlimeFactory.LiteStory));
					CCDirector.sharedDirector().replaceScene(transition);
					return;
				}
				this.runLevel();
			}
		}
	}
	
	public void runLevel() {							
		Level level = Level.get(this.levelDefinition, true);
		Sounds.pauseMusic();
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
}
