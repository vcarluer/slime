package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.Sharer;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.ccColor3B;

import android.annotation.SuppressLint;
import android.view.MotionEvent;

@SuppressLint("DefaultLocale") public class TimeAttackGameOverLayer extends CCLayer implements IBackableLayer {
	private static CCScene scene;
	private CCLabel congratLabel;
	private CCLabel endLabel;
	private CCLabel gameOverLabel;
	private CCMenu backMenu;	
	private CCMenu shareMenu;
	private float yEndRelative;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new TimeAttackGameOverLayer());
		}
		
		return scene;
	}
	
	public TimeAttackGameOverLayer() {
		HomeLayer.addBkg(this, 800, 480, "game-over.png");
		this.backMenu = HomeLayer.getBackButton(this, "goBack");
		this.congratLabel = CCLabel.makeLabel("Congratulations!!!".toUpperCase(), "fonts/Slime.ttf", 42 * SlimeFactory.getWidthRatio());
		this.endLabel = CCLabel.makeLabel("World".toUpperCase(), "fonts/Slime.ttf", 28 * SlimeFactory.getWidthRatio());
		this.gameOverLabel = CCLabel.makeLabel("Game Over!".toUpperCase(), "fonts/Slime.ttf", 68 * SlimeFactory.getWidthRatio());
		this.gameOverLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 100 * SlimeFactory.getWidthRatio());
		this.congratLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY());
		this.yEndRelative = - (42 + 11) * SlimeFactory.getWidthRatio();
		this.endLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + this.yEndRelative);
		this.endLabel.setColor(SlimeFactory.ColorSlimeGold);
		
		this.addChild(this.gameOverLabel);
		this.addChild(this.congratLabel);
		this.addChild(this.endLabel);
		
		this.addChild(this.backMenu);
		this.setIsTouchEnabled(true);
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		//this.goBack();
		return true;
	}
	
	@Override
	public void onEnter() {
		SlimeFactory.playMenuMusic();
		this.endLabel.setString(("World " + SlimeFactory.PackageManager.getCurrentPackage().getName() + " finished").toUpperCase());
				
		this.shareMenu = HomeLayer.getNewShareButton(
				"World " + SlimeFactory.PackageManager.getCurrentPackage().getName() + " finished in story mode !"+  " " + Sharer.twitterTag, 
				1.0f, 0, this.yEndRelative - 28 * SlimeFactory.getWidthRatio() - (HomeLayer.shareSizeW * HomeLayer.getShareScale(1.0f)) / 2f);
		
		this.addChild(this.shareMenu);
		
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		if (this.shareMenu != null) {
			this.removeChild(this.shareMenu, true);
			this.shareMenu = null;
		}
		super.onExit();
	}

	public void goBack(Object sender) {
		goBack();
	}

	public void goBack() {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, CreditLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
