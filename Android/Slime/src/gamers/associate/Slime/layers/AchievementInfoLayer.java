package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.achievements.Achievement;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

public class AchievementInfoLayer extends CCLayer {
	private static final float titleSize = 32;
	private static final float descriptionSize = 22;
	private static final float padding = 5;
	private static final float extPadding = 5f;
	public static final float rowSize = titleSize + descriptionSize + padding + extPadding * 2;
	private Achievement info;
	private CCSprite unlockSprite;
	private CCLabel title;
	private CCLabel description;
	private float width;
	private float height;
	
	public AchievementInfoLayer(Achievement info, float w) {
		this.info = info;
		this.width = w;
		this.height = rowSize;
		
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorBorder(200), this.width, this.height);
		this.addChild(colorLayer);
		
		this.title = SlimeFactory.getLabel(info.getName(), titleSize);
		this.title.setPosition(this.width / 2f, this.height / 2f + (titleSize + padding) / 2f);
		this.addChild(this.title);
		this.description = SlimeFactory.getLabel(info.getDescription(), descriptionSize);
		this.description.setPosition(this.width / 2f, this.height / 2f - (descriptionSize + padding) / 2f);
		this.addChild(this.description);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		
		String spriteName = "";
		if (info.isAchieved()) {
			spriteName = "world-items/star-gold.png";
		} else {
			spriteName = "world-items/star-lock.png";
		}
		
		this.unlockSprite = CCSprite.sprite(spriteName);
		this.unlockSprite.setScale(rowSize / this.unlockSprite.getContentSize().height);
		this.unlockSprite.setPosition((this.unlockSprite.getContentSize().width * this.unlockSprite.getScaleX()) / 2f, 
				(this.unlockSprite.getContentSize().height * this.unlockSprite.getScaleY()) / 2f);
		this.addChild(this.unlockSprite);
		
		this.title.setPosition((this.unlockSprite.getContentSize().width * this.unlockSprite.getScaleX()) + padding + this.title.getContentSize().width / 2f, 
				this.height / 2f + titleSize / 2f);
		
		this.description.setPosition((this.unlockSprite.getContentSize().width * this.unlockSprite.getScaleX()) + padding + this.description.getContentSize().width / 2f, 
				this.height / 2f - descriptionSize / 2f - padding);
	}

	@Override
	public void onExit() {
		this.removeChild(this.unlockSprite, true);
		super.onExit();
	}
}
