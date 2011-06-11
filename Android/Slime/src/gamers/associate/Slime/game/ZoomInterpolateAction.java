package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.GameItem;

public class ZoomInterpolateAction extends CameraAction {

	public ZoomInterpolateAction(CameraManager manager, float time, GameItem target, float targetZoom) {
		super(manager, time);
		this.setTargetAction(target);
		this.setTargetValue(targetZoom);
	}

	@Override
	protected void actionInternal(float delta) {		
		float zoomBy = (this.getTargetValue() - this.getManager().getCurrentZoom()) * this.getInterpolation();
		this.getManager().zoomCameraCenterBy(zoomBy);
	}

}
