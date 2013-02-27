package gamers.associate.SlimeAttack.game;

import java.util.List;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import gamers.associate.SlimeAttack.items.base.GameItem;

public class MoveInterpolateTrackAction extends CameraAction {

	private List<CGPoint> points;	
	private CGPoint currentPoint;
	private int currentIndex;
	private CGPoint pointAction;
	
	public MoveInterpolateTrackAction(CameraManager manager, float timeBetweenPoints, List<CGPoint> points) {
		super(manager, timeBetweenPoints);
		this.points = points;				
		this.currentIndex = 0;
		this.currentPoint = points.get(this.currentIndex);
		this.pointAction = CGPoint.zero();
	}

	@Override
	protected void actionInternal(float delta) {
		// Work directly with float for performance		
		CGRect camera = this.getManager().getVirtualCamera();
		float cameraX = camera.origin.x + camera.size.width / 2;
		float cameraY = camera.origin.y + camera.size.height / 2;
		float targetX = this.currentPoint.x;
		float targetY = this.currentPoint.y;
				
		// A borner...
		this.pointAction.x = cameraX + ((targetX - cameraX) * this.getInterpolation());
		this.pointAction.y = cameraY + ((targetY - cameraY) * this.getInterpolation());
		
		this.getManager().centerCameraOn(this.pointAction);		
	}

	@Override
	public boolean action(float delta) {
		float remainingTime = this.targetTimeAction - this.elapsedTimeAction;
		float compareDelta = delta;
		if (remainingTime < compareDelta) {
			compareDelta = remainingTime;
		}				
		
		this.interpolation = delta / remainingTime;
		
		this.actionInternal(compareDelta);
		
		this.elapsedTimeAction += delta;		
		if (elapsedTimeAction >= this.targetTimeAction) {
			if (this.currentIndex == this.points.size() - 1) {
				this.isEnded = true;
			} else {
				this.currentIndex++;
				this.currentPoint = this.points.get(this.currentIndex);
			}			
		}
		
		return this.isEnded;
	}
}
