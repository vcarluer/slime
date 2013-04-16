package gamers.associate.SlimeAttack.game;

import java.util.List;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class MoveInterpolateTrackAction extends CameraAction {

	private List<CGPoint> points;	
	private CGPoint currentPoint;
	private int currentIndex;
	private CGPoint pointAction;
	private boolean resumeAtEnd;
	
	public MoveInterpolateTrackAction(CameraManager manager, float timeBetweenPoints, List<CGPoint> points, boolean resumeAtEnd) {
		super(manager, timeBetweenPoints);
		this.points = points;				
		this.currentIndex = 0;
		this.currentPoint = points.get(this.currentIndex);
		this.pointAction = CGPoint.zero();
		this.getManager().centerCameraOn(this.currentPoint);
		if (this.points.size() > 1) {
			this.currentIndex++;
		}
		
		this.currentPoint = points.get(this.currentIndex);
		
		this.resumeAtEnd = resumeAtEnd;
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
				if (this.resumeAtEnd) {
					Level.currentLevel.resume();
					if(Level.currentLevel.getStartItem() != null) {
						Level.currentLevel.getCameraManager().follow(Level.currentLevel.getStartItem());
					}
				}
				
			} else {
				this.currentIndex++;
				this.currentPoint = this.points.get(this.currentIndex);
				this.elapsedTimeAction = 0;
			}			
		}
		
		return this.isEnded;
	}
}
