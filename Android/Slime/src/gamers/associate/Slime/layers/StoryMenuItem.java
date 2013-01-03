package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.RankFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class StoryMenuItem extends CCLayer {
	public static final int SIZE = 165;
	private static final float scaleStar = 1.5f;
	private static final float yshift = - 35;
	private CCSprite backItem; 
	private CCLabel idItem;
	private CCSprite starItem;	
	
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
		float baseX = this.getPosition().x;
		this.setPosition(- SIZE * this.getScale(), this.getPosition().y);
		this.setAnchorPoint(0, 0);
		CCDelayTime delay = CCDelayTime.action(0.4f + ((this.levelDefinition.getNumber() - 1) % StoryWorldLayer.COLS) * (2 / StoryWorldLayer.COLS));
		CCMoveTo actionTo = CCMoveTo.action(0.3f, CGPoint.make(baseX, this.getPosition().y));		
		
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
		
		if (this.levelDefinition != null) {
			this.idItem = SlimeFactory.getLabel(String.valueOf(this.levelDefinition.getNumber()));
			this.idItem.setPosition(0, 50 + yshift);											
			this.starItem = RankFactory.getSprite(this.levelDefinition.getRank());		
			this.starItem.setPosition(0,  yshift);
			this.starItem.setScale(scaleStar);
					
			this.addChild(this.idItem);
			this.addChild(this.starItem);
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
			float parentX = this.getParent().getPosition().x;
			float parentY = this.getParent().getPosition().y;
			float realX = this.getPosition().x + parentX;
			float realY = this.getPosition().y + parentY;
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
		if (this.levelDefinition.isUnlock()) {
			Sounds.playEffect(R.raw.menuselect);
			if (this.levelDefinition.getNumber() == 1) {
				SlimeFactory.ContextActivity.runIntro(this);
			} else {
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
