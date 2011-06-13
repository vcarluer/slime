package gamers.associate.Slime.game;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import gamers.associate.Slime.items.base.GameItem;

public class MoveInterpolateAction extends CameraAction {

	private CGPoint pointAction;	
	
	public MoveInterpolateAction(CameraManager manager, float time, GameItem target) {
		super(manager, time);
		this.pointAction = CGPoint.zero();		
		this.setTargetAction(target);
	}

	@Override
	protected void actionInternal(float delta) {
		// Work directly with float for performance		
		CGRect camera = this.getManager().getVirtualCamera();
		float cameraX = camera.origin.x + camera.size.width / 2;
		float cameraY = camera.origin.y + camera.size.height / 2;
		float targetX = this.getTargetAction().getPosition().x;
		float targetY = this.getTargetAction().getPosition().y;
				
		this.pointAction.x = cameraX + ((targetX - cameraX) * this.getInterpolation());
		this.pointAction.y = cameraY + ((targetY - cameraY) * this.getInterpolation());
		
		this.getManager().centerCameraOn(this.pointAction);		
	}

}