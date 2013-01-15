package gamers.associate.Slime.layers;

import gamers.associate.Slime.IBackableLayer;
import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.achievements.Achievement;
import gamers.associate.Slime.items.custom.MenuSprite;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;

public class AchievementsLayer extends CCLayer  implements IScrollable, IBackableLayer {
	private static final float fontSize = 60;
	private static CCScene scene;
	private CCLabel title;
	private ScrollerLayer scroller;
	private CCNode achNode;
	private float minScroller;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new AchievementsLayer());
		}
		
		return scene;
	}
	
	protected AchievementsLayer() {
		this.achNode = CCNode.node();
		this.addChild(this.achNode);
		HomeLayer.addBkgSplash(this);
		this.addChild(HomeLayer.getBackButton(this, "goBack"), Level.zTop);
		
		this.title = SlimeFactory.getLabel("Achievements", fontSize);
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - PauseLayer.PaddingY - fontSize / 2f);
		this.addChild(this.title, Level.zTop);
		float colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorLight(200), CCDirector.sharedDirector().winSize().width, colorHeight);
		colorLayer.setPosition(0, CCDirector.sharedDirector().winSize().height - colorHeight);
		this.addChild(colorLayer, Level.zFront);
		
		this.scroller = new ScrollerLayer();
		this.scroller.setStoryLayer(this);
		this.scroller.setHandled(this.achNode);
		this.addChild(this.scroller, Level.zTop);
	}
	
	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}

	@Override
	public void toRight(Object sender) {
	}

	@Override
	public void toLeft(Object sender) {
	}

	@Override
	public void onEnter() {
		super.onEnter();
		int i = 0;
		int padding = 10;
		float rowSize = AchievementInfoLayer.rowSize + padding;
		this.minScroller = (CCDirector.sharedDirector().winSize().getHeight()) - (MenuSprite.Height * PauseLayer.Scale) - rowSize / 2;
		float achW = CCDirector.sharedDirector().winSize().width - padding * 2;
		for(Achievement ach : SlimeFactory.AchievementManager.getAchievementsList()) {
			AchievementInfoLayer achLayer = new AchievementInfoLayer(ach, achW);
			float yPos = - (i * rowSize + rowSize / 2f);
			achLayer.setPosition(- achW, yPos);
			CCDelayTime delay = CCDelayTime.action(0.5f);
			CGPoint target = CGPoint.make(padding, yPos);
			CCMoveTo moveTo = CCMoveTo.action((0.3f + (i % 4) * 2f) / 10f, target);
			CCSequence seq = CCSequence.actions(delay, moveTo);
			achLayer.runAction(seq);
			this.achNode.addChild(achLayer);
			i++;
		}
		
		this.achNode.setPosition(0, this.minScroller);
		
		float max = SlimeFactory.AchievementManager.getAchievementsCount() * rowSize  - rowSize / 2;
		if (max < 0) {
			max = 0;
		}
		
		this.scroller.setLimits(this.minScroller, max);
	}

	@Override
	public void onExit() {
		this.achNode.removeAllChildren(true);
		super.onExit();
	}

	@Override
	public void goBack() {
		this.goBack(this);
	}
}
