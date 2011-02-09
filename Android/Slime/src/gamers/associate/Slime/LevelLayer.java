package gamers.associate.Slime;

import java.util.Iterator;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.config.ccMacros;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.LevelFactory"
 */
public class LevelLayer  extends CCLayer {
	
	/**
	 * @uml.property  name="level"
	 * @uml.associationEnd  
	 */
	private Level level;	
	
	public static CCScene Scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new LevelLayer());
		return scene;
	}
	
	protected LevelLayer() {
		super();
		this.setIsTouchEnabled(true);
		this.init();
	}
	
	private void init() {
		this.level = LevelFactory.GetLevel("Level1", this);
	}
	
	private UpdateCallback tickCallback = new UpdateCallback() {
		
		@Override
		public void update(float d) {
			tick(d);
		}
	};
	
	@Override
	public void onEnter() {
		super.onEnter();
		
		// start ticking (for physics simulation)
		schedule(tickCallback);
	}

	@Override
	public void onExit() {
		super.onExit();
		
		// stop ticking (for physics simulation)			
		unschedule(tickCallback);
	}
	
	public synchronized void tick(float delta) {
    	this.level.tick(delta);
    }
	
	 @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        this.level.SpawnSlime();
        return CCTouchDispatcher.kEventHandled;
    }
}
