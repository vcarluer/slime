package gamers.associate.SlimeAttack.game;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import gamers.associate.SlimeAttack.items.base.GameItem;

public class MoveInterpolateAction extends CameraAction {

	private CGPoint pointAction;	
	private boolean followAtEnd;
	
	public MoveInterpolateAction(CameraManager manager, float time, GameItem target, boolean followAtEnd) {
		super(manager, time);
		this.pointAction = CGPoint.zero();		
		this.setTargetAction(target);
		this.followAtEnd = followAtEnd;
	}

	@Override
	protected void actionInternal(float delta) {
		// Work directly with float for performance		
		CGRect camera = this.getManager().getVirtualCamera();
		float cameraX = camera.origin.x + camera.size.width / 2;
		float cameraY = camera.origin.y + camera.size.height / 2;
		float targetX = this.getTargetAction().getPosition().x;
		float targetY = this.getTargetAction().getPosition().y;
				
		// A borner...
		this.pointAction.x = cameraX + ((targetX - cameraX) * this.getInterpolation());
		this.pointAction.y = cameraY + ((targetY - cameraY) * this.getInterpolation());
		
		this.getManager().centerCameraOn(this.pointAction);		
	}

	@Override
	protected void handleEnding() {
		super.handleEnding();
		
		if(this.followAtEnd && Level.currentLevel.getStartItem() != null) {
			Level.currentLevel.getCameraManager().follow(Level.currentLevel.getStartItem());
		}
	}
}
