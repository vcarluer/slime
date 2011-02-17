package gamers.associate.Slime;

import org.cocos2d.layers.CCLayer;

import android.view.MotionEvent;

public class HomeLayer extends CCLayer {
	private static HomeLayer layer;	
	
	public static HomeLayer get() {
		if (layer == null) {
			layer = new HomeLayer();
		}
		
		return layer;
	}
	
	protected HomeLayer() {
		super();
		
		this.setIsTouchEnabled(true);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// TODO: Go to level selection		
		return super.ccTouchesEnded(event);
	}		
}
