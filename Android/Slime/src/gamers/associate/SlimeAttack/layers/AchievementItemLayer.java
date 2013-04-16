package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

public class AchievementItemLayer extends CCLayer {
	private CCLabel title;
	private CCMenu achievementMenu;
	private static final float sprWidth = 267f;
	private static final float sprHeight = 108f;
	private static final float targetHeight = 50f * SlimeFactory.getWidthRatio();
	private float height;
	private float scale;
	private float width;
	private float padding = 25f;
	private CGPoint pos;
	
	public AchievementItemLayer() {
		this.title = SlimeFactory.getLabel("Achievements", 42f * SlimeFactory.getWidthRatio());
		this.pos = CGPoint.make(CCDirector.sharedDirector().winSize().width / 2f, targetHeight / 2f);
		this.title.setPosition(this.pos);
		
		this.width = this.title.getContentSizeRef().width + padding * 2f;
		this.scale = this.width / sprWidth;
		this.height = sprHeight * this.scale;
		CCSprite spriteNorm = CCSprite.sprite("world-items/btn-achievements.png");
		CCSprite spriteSel = CCSprite.sprite("world-items/btn-achievements.png");
		CCMenuItemSprite menuItem = CCMenuItemSprite.item(spriteNorm, spriteSel, this, "selectAchievements");
		menuItem.setScale(this.scale);
		this.achievementMenu = CCMenu.menu(menuItem);
		this.achievementMenu.setPosition(this.pos.x, - this.height / 2f + targetHeight);
		
		this.addChild(this.achievementMenu);
		this.addChild(this.title);
	}
	
	@Override
	public void onEnter() {
		SlimeFactory.moveToZeroFromBottom(0.6f, this, targetHeight);
		super.onEnter();
	}

	public void selectAchievements(Object sender) {
		Sounds.playEffect(gamers.associate.SlimeAttack.R.raw.menuselect);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, AchievementsLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
