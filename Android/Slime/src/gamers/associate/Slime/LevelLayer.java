package gamers.associate.Slime;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;

import android.view.MotionEvent;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.LevelFactory"
 */
public class LevelLayer extends CCLayer {
	
	/**
	 * @uml.property  name="level"
	 * @uml.associationEnd  
	 */
	private Level level;
	
	public LevelLayer(Level level) {
		super();
		this.level = level;
		this.setIsTouchEnabled(true);
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
	 
	 // Test
	 /*public void resetWon() {
		 this.level.resetWon();
	 }*/
}
