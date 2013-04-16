package gamers.associate.SlimeAttack.layers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.LevelDifficulty;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.SlimySuccess;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelHome;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class CreditLayer extends CCLayer implements IBackableLayer {
	private static final String SLIMY = "Slimy";
	private static final float ScrollTime = 33f * 2f;
	private static final float CategoryFnt = 42f * SlimeFactory.SGSDensity;
	private static final float paddingCategory = 13f;
	private static final float NameFnt = 31f * SlimeFactory.SGSDensity;
	private static final float paddingName = 5f;
	private static final float paddingEndCategory = 30f;
	private static final float disclaimerFnt = 15f * SlimeFactory.SGSDensity;
	private static String vcr = "Vincent Carluer";
	private static String amz = "Antonio Munoz";
	private static String gcc = "Guillaume Clerc";
	private static String mbn = "Mihran Bodromian";
	private static String kmd = "Kevin MacLeod";
	private static CCScene scene;
	private List<CreditInfo> infos;
	private List<String> infosDisclaimer;
	private CGPoint endPos;
	private Random random;
	private int lastPick;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new CreditLayer());
		}
		
		return scene;
	}
	
	protected CreditLayer() {
		this.random = new Random();
		CCColorLayer colored = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 255));
		this.addChild(colored, Level.zUnder);
		
		this.setIsTouchEnabled(true);
		this.setCreditInfo();
		
	}

	@Override
	public void onEnter() {
		Sounds.stopMusic();
		SlimeFactory.playCreditMusic();
		this.setCreditText();
		this.autoScroll();
		super.onEnter();
	}

	private void autoScroll() {
		this.stopAllActions();
		this.setPosition(0, 0);
		CCMoveTo moveTo = CCMoveTo.action(ScrollTime, this.endPos);
		CCCallFunc	call = CCCallFunc.action(this, "goHome");
		CCSequence seq = CCSequence.actions(moveTo, call);
		this.runAction(seq);
	}

	@Override
	public void onExit() {
		super.onExit();
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		this.goHome();
		
		return true;
	}
	
	public void goHome() {
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	private void addInfo(String category, String... names) {
		CreditInfo info = new CreditInfo(category, names);
		this.infos.add(info);
	}
	
	private void setCreditText() {
		this.removeAllChildren(true);
		float currentY = - CategoryFnt / 2f;
		float currentX = CCDirector.sharedDirector().winSize().width / 2f;
		int idx = 0;
		for(CreditInfo info : this.infos) {
			CCLabel cat = SlimeFactory.getLabel(info.getCategory(), CategoryFnt);
			cat.setColor(SlimeFactory.ColorSlime);
			cat.setPosition(currentX, currentY);
			this.addChild(cat, Level.zFront);
			if (idx % 3 == 0) {
				this.addRandomAnimation(currentY);
			}
			
			currentY -= CategoryFnt / 2f + paddingCategory + NameFnt / 2f;
			int i = 0;
			for(String name : info.getNames()) {
				if (i > 0) {
					currentY -= NameFnt + paddingName;
				}
				
				CCLabel nameLabel = SlimeFactory.getLabel(name, NameFnt);
				nameLabel.setColor(SlimeFactory.ColorSlimeLight);
				nameLabel.setPosition(currentX, currentY);
				this.addChild(nameLabel, Level.zFront);
				i++;
			}
			
			currentY -= NameFnt / 2f + paddingEndCategory + CategoryFnt / 2f;
			idx++;
		}
		
		int i = 0;
		for(String info : this.infosDisclaimer) {
			if (i > 0) {
				currentY -= disclaimerFnt + paddingName;
			}
			
			CCLabel nameLabel = SlimeFactory.getLabel(info, disclaimerFnt);
			nameLabel.setColor(SlimeFactory.ColorSlimeLight);
			nameLabel.setPosition(currentX, currentY);
			this.addChild(nameLabel, Level.zFront);
			i++;
		}
		
		currentY -= disclaimerFnt / 2f + CCDirector.sharedDirector().winSize().height;
		this.endPos = CGPoint.make(0, - currentY);
	}

	
	private void addRandomAnimation(float currentY) {
		
		this.lastPick++;
		if (this.lastPick > 3) {
			this.lastPick = 0;
		}
		
		int diff = 0;
		switch (this.lastPick) {
			default:	
			case 0:
				diff = LevelDifficulty.Easy;
				break;
			case 1:
				diff = LevelDifficulty.Normal;
				break;
			case 2:
				diff = LevelDifficulty.Hard;
				break;
			case 3:
				diff = LevelDifficulty.Extrem;
				break;
		}
	
		CCSprite slime = SlimeFactory.SlimySuccess.getAnimatedSprite(SlimySuccess.getAnimationName(diff));
		float spriteScale = 1.5f * SlimeFactory.SGSDensity;
		slime.setScale(spriteScale);
		float x = random.nextInt((int) (CCDirector.sharedDirector().winSize().width / 4f - (slime.getContentSizeRef().width * spriteScale))) + (slime.getContentSizeRef().width * spriteScale) / 2f;
		int inv = random.nextInt(2);
		if (inv == 1) {
			x = CCDirector.sharedDirector().winSize().width - x;
		}
		
		slime.setPosition(x, currentY);
		this.addChild(slime, Level.zMid);
	}
	
	// Credits
	private void setCreditInfo() {
		this.infos = new ArrayList<CreditInfo>();
		this.infosDisclaimer = new ArrayList<String>();
		this.lastPick = -1;
		// Main
		this.addInfo("Game Design", vcr, amz, gcc);
		this.addInfo("Project Lead", vcr);
		this.addInfo("Art Director", gcc);
		this.addInfo("Lead Engineer", vcr);
		this.addInfo("Lead Tool engineer", amz);
		this.addInfo("Audio Lead", mbn);
		this.addInfo("Level Design", vcr, amz);
		this.addInfo("Lead Character", SLIMY);
		// specific
		// graph
		this.addInfo("Character Design", gcc);
		this.addInfo("Animation", gcc);
		this.addInfo("UI Design", vcr);
		this.addInfo("Cinematics Director", gcc);
		this.addInfo("Texture artist", gcc);
		// sound
		this.addInfo("Sound Design", mbn, amz);
		this.addInfo("Music Composer", mbn, kmd);
		this.addInfo("Audio Engineering", mbn);
		// market
		this.addInfo("Community Manager", amz);
		this.addInfo("Go to market", amz, vcr);
		this.addInfo("Producer", "None! we are indy");
		// other
		this.addInfo("Beta Test", this.getBetaTesters());
		this.addInfo("Thank you", "Our families", "cocos2d-android team", "libgdx team", "Google", "webomator", "You!");
		this.addInfo("Gamers Associate 2013", vcr, gcc, amz, mbn);
		this.addInfoDisclaimer("No green slime were harmed in the making of this game");
		this.addInfoDisclaimer("We can not speak about red slime under the Civil agreement 1337-42");
	}
	
	private void addInfoDisclaimer(String info) {
		this.infosDisclaimer.add(info);
	}

	private String[] getBetaTesters() {
		return new String[] { 
				"Raffi Leylekian", 
				"Mathieu Carluer", 
				"Jeremie Devauchelle",
				"Etienne Large",
				"Akentarun",
				"Kroustine la tartine masquee",
				"Fenrhir",
				"Paul Verveine",
				"PrinceGITS"
				};
	}

	@Override
	public void goBack() {
		this.goHome();
	}
}
