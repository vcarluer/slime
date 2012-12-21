package gamers.associate.Slime.layers;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class ScrollerLayer extends CCLayer {
	private CCNode handled;
	private boolean hasMoved;
	private CGPoint tmpPoint;
	private float lastDelta;
	
	public ScrollerLayer() {
		this.setIsTouchEnabled(true);
		this.tmpPoint = CGPoint.zero();
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (this.handled != null) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				if (event.getHistorySize() > 0) {
					this.lastDelta = event.getY() - event.getHistoricalY(0);
					this.tmpPoint.x = getHandled().getPosition().x;
					this.tmpPoint.y = getHandled().getPosition().y - this.lastDelta;					
					getHandled().stopAllActions();					
					CCMoveTo moveTo = CCMoveTo.action(0, this.tmpPoint);
					getHandled().runAction(moveTo);
					this.hasMoved = true;
				}			
			}			    

			return true;
		}
		
		return false;
	}

	public CCNode getHandled() {
		return handled;
	}

	public void setHandled(CCNode handled) {
		this.handled = handled;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (this.hasMoved) {
			this.hasMoved = false;
			this.tmpPoint.x = 0;
			this.tmpPoint.y = - this.lastDelta / 2f;
			CCMoveBy moveBy = CCMoveBy.action(0.2f, this.tmpPoint);
			getHandled().stopAllActions();
			getHandled().runAction(moveBy);
			return true;
		} else {
			return false;
		}
		
	}
}
