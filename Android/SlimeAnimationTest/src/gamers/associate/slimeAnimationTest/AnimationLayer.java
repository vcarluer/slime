package gamers.associate.slimeAnimationTest;

import java.util.ArrayList;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public class AnimationLayer  extends CCLayer {
		
	private CCSpriteSheet spriteSheet;		
	private CCSprite slime;		
	private int spriteCount;
	private float size = 32;
	
	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new AnimationLayer();
		scene.addChild(layer);
		
		return scene;
	}
	
	protected AnimationLayer() {
		super();
				
	}
	
	/*private UpdateCallback tickCallback = new UpdateCallback() {
		
		@Override
		public void update(float d) {
			tick(d);
		}
	};*/
	
	@Override
	public void onEnter() {
		super.onEnter();
		
		// start ticking (for physics simulation)
		//schedule(tickCallback);
		
		
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("labo.plist");
		this.spriteSheet = CCSpriteSheet.spriteSheet("labo.png");
		this.addChild(this.spriteSheet);
		
		this.createAnim("blueportal", 4);
		this.createAnim("burned-wait", 2);
		this.createAnim("burning", 5);
		this.createAnim("falling", 3);
		this.createAnim("landing-h", 3);
		this.createAnim("landing-v", 3);
		this.createAnim("redportal", 4);
		this.createAnim("wait-h", 5);
		this.createAnim("wait-v", 5);
	}
	
	private void createAnim(String animName, int frameCount) {
		ArrayList<CCSpriteFrame> animArray = new ArrayList<CCSpriteFrame>();
		for(int i = 0; i < frameCount; i++) {			
			String frameName = animName + "-" + String.valueOf(i + 1) + ".png";
			animArray.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName));		
		}
		
		CCAnimation animation = CCAnimation.animation(animName, 0.1f, animArray);
		CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.spriteFrameByName(animName + "-1.png"));
		// sprite.setContentSize(size, size);		
		float left = (this.spriteCount + 1) * size - size / 2;
		float top = CCDirector.sharedDirector().winSize().height - size / 2;
		
		sprite.setPosition(left, top);
		
		CCAnimate animate = CCAnimate.action(animation, false);
		CCAnimate reverse = animate.reverse();
		
		CCAction action = CCRepeatForever.action(CCSequence.actions(animate, reverse));		
		sprite.runAction(action);
		this.spriteSheet.addChild(sprite);
		this.spriteCount++;
	}

	@Override
	public void onExit() {
		super.onExit();
		
		// stop ticking (for physics simulation)			
		//unschedule(tickCallback);
	}
	
	public synchronized void tick(float delta) {
	}	
}
