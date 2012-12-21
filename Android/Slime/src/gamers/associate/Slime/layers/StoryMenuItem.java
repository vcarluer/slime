package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.RankFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

import android.view.MotionEvent;

public class StoryMenuItem extends CCLayer {
	public static final int SIZE = 165;
	private static final float scaleStar = 1.5f;
	private static final float yshift = - 35;
	private CCSprite backItem; 
	private CCLabel idItem;
	private CCSprite starItem;	
	
	private LevelDefinition levelDefintion;
	
	/** Creates a CCMenuItem with a target/selector */
    public static StoryMenuItem item(LevelDefinition levelDefinition) {
        return new StoryMenuItem(levelDefinition);
    }
	
	public StoryMenuItem(LevelDefinition levelDefinition) {
		this.levelDefintion = levelDefinition;
		
		this.backItem = CCSprite.sprite("control-square-empty.png", true);
		this.addChild(this.backItem);
		this.updateItem();
		this.setContentSize(SIZE, SIZE);
	}
	
	public void updateItem() {
		if (this.idItem != null) {
			this.removeChild(this.idItem, true);			
		}
		
		if (this.starItem != null) {
			this.removeChild(this.starItem, true);
		}
		
		if (this.levelDefintion != null) {
			this.idItem = SlimeFactory.getLabel(this.levelDefintion.getId());
			this.idItem.setPosition(0, 50 + yshift);											
			this.starItem = RankFactory.getSprite(this.levelDefintion.getRank());		
			this.starItem.setPosition(0,  yshift);
			this.starItem.setScale(scaleStar);
					
			this.addChild(this.idItem);
			this.addChild(this.starItem);
		}
	}
	
	public LevelDefinition getLevelDefinition() {
		return this.levelDefintion;
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		boolean inrectX = x < this.getPosition().x + SIZE / 2f && x > this.getPosition().x - SIZE / 2f;
		boolean inrectY = y < this.getPosition().y + SIZE / 2F && y > this.getPosition().y - SIZE / 2f;
		if (inrectX && inrectY) {
			this.select();
			return true;
		} else {
			return false;
		}
	}

	private void select() {
		Sounds.playEffect(R.raw.menuselect);			
		String levelName = String.valueOf(this.levelDefintion.getId());
		// levelName should be fixed here
		Level level = Level.get(levelName, true, GamePlay.TimeAttack);
		Sounds.pauseMusic();
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
}
