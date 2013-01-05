package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;

public class AchievementsLayer extends CCLayer  implements IScrollable {
	private static final float fontSize = 60;
	private static CCScene scene;
	private CCLabel title;
	private ScrollerLayer scroller;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new AchievementsLayer());
		}
		
		return scene;
	}
	
	protected AchievementsLayer() {
		HomeLayer.addBkgSplash(this);
		this.addChild(HomeLayer.getHomeMenuButton(this, "goHome"), Level.zTop);
		
		this.title = SlimeFactory.getLabel("Achievements", fontSize);
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - PauseLayer.PaddingY - fontSize / 2f);
		this.addChild(this.title, Level.zTop);
		float colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorLight(200), CCDirector.sharedDirector().winSize().width, colorHeight);
		colorLayer.setPosition(0, CCDirector.sharedDirector().winSize().height - colorHeight);
		this.addChild(colorLayer, Level.zFront);
		
		this.scroller = new ScrollerLayer();
		this.scroller.setStoryLayer(this);
		this.addChild(this.scroller, Level.zTop);
	}
	
	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}

	@Override
	public void toRight(Object sender) {
	}

	@Override
	public void toLeft(Object sender) {
	}
}
