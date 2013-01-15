package gamers.associate.Slime.layers;

import java.util.ArrayList;
import java.util.List;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class CreditLayer extends CCLayer {
	private static final float CategoryFnt = 42f;
	private static final float paddingCategory = 10f;
	private static final float NameFnt = 31f;
	private static final float paddingName = 5f;
	private static final float paddingEndCategory = 20f;
	private static String vcr = "Vincent Carluer";
	private static String amz = "Antonio Munoz";
	private static String gcc = "Guillaume Clerc";
	private static String mbn = "Mihran Bodromian";
	private static CCScene scene;
	
	private String creditText;
	private List<CreditInfo> infos;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new CreditLayer());
		}
		
		return scene;
	}
	
	protected CreditLayer() {
		CCColorLayer colored = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 255));
		this.addChild(colored, Level.zUnder);
		
		this.setIsTouchEnabled(true);
		this.setCreditInfo();
		this.setCreditText();
	}

	@Override
	public void onEnter() {
		Sounds.stopMusic();
		Sounds.playMusic(R.raw.slimy_credits, true);
		super.onEnter();
	}

	@Override
	public void onExit() {
		super.onExit();
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
		
		return true;
	}
	
	private void setCreditInfo() {
		this.infos = new ArrayList<CreditInfo>();
		this.addInfo("Game design", vcr, amz, gcc);
		
	}
	
	private void addInfo(String category, String... names) {
		CreditInfo info = new CreditInfo(category, names);
		this.infos.add(info);
	}
	
	private void setCreditText() {
		float currentY = - CategoryFnt / 2f + CCDirector.sharedDirector().winSize().height / 2f;
		float currentX = CCDirector.sharedDirector().winSize().width / 2f;
		for(CreditInfo info : this.infos) {
			CCLabel cat = SlimeFactory.getLabel(info.getCategory(), CategoryFnt);
			cat.setColor(SlimeFactory.ColorSlime);
			cat.setPosition(currentX, currentY);
			this.addChild(cat);
			
			currentY -= CategoryFnt / 2f + paddingCategory + NameFnt / 2f;
			int i = 0;
			for(String name : info.getNames()) {
				if (i > 0) {
					currentY -= NameFnt + paddingName;
				}
				
				CCLabel nameLabel = SlimeFactory.getLabel(name, NameFnt);
				nameLabel.setColor(SlimeFactory.ColorSlimeLight);
				nameLabel.setPosition(currentX, currentY);
				this.addChild(nameLabel);
				i++;
			}
			
			currentY -= NameFnt / 2f + paddingEndCategory - CategoryFnt / 2f;
		}
	}
}
