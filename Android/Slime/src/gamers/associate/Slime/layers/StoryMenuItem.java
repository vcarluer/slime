package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.RankFactory;
import gamers.associate.Slime.levels.LevelDefinition;

import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

public class StoryMenuItem extends CCMenuItem {
	public static final int SIZE = 165;
	private CCSprite backItem; 
	private CCLabel idItem;
	private CCSprite starItem;
	private CCLabel scoreItem;
	
	private LevelDefinition levelDefintion;
	
	/** Creates a CCMenuItem with a target/selector */
    public static StoryMenuItem item(Object target, String selector, LevelDefinition levelDefinition) {
        return new StoryMenuItem(target, selector, levelDefinition);
    }
	
	public StoryMenuItem(Object target, String selector, LevelDefinition levelDefinition) {
		super(target, selector);
		this.levelDefintion = levelDefinition;
		
		this.backItem = CCSprite.sprite("control-square-screen.png", true);
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
		
		if (this.scoreItem != null) {
			this.removeChild(this.scoreItem, true);
		}		
		
		if (this.levelDefintion != null) {
			this.idItem = SlimeFactory.getLabel(this.levelDefintion.getId());
			int score = this.levelDefintion.getMaxScore();
			if (score > 0) {
				this.scoreItem = SlimeFactory.getLabel(String.valueOf(score));				
			} else {
				this.scoreItem = SlimeFactory.getLabel(" ");
			}
			
			this.scoreItem.setScale(0.3f);
			this.scoreItem.setPosition(0, -50);
			this.starItem = RankFactory.getSprite(this.levelDefintion.getRank());
			
			this.idItem.setPosition(0, 50);
					
			this.addChild(this.idItem);			
			this.addChild(this.scoreItem);
			this.addChild(this.starItem);
		}
	}
	
	public LevelDefinition getLevelDefinition() {
		return this.levelDefintion;
	}
}
