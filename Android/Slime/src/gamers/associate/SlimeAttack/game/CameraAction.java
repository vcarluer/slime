package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.items.base.GameItem;

public abstract class CameraAction {
	private GameItem targetAction;
	private float targetValue;
	private float elapsedTimeAction;
	private float targetTimeAction;	
	private CameraManager cameraManager;
	private float interpolation;
	private boolean isEnded;
	
	public CameraAction(CameraManager manager, float time) {		
		this.cameraManager = manager;
		this.targetTimeAction = time;		
	}
	
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
			this.isEnded = true;
		}
		
		return this.isEnded;
	}
	
	protected abstract void actionInternal(float delta);
	
	protected void setTargetAction(GameItem target) {
		this.targetAction = target;
	}
	
	protected GameItem getTargetAction() {
		return this.targetAction;
	}
	
	protected void setTargetValue(float target) {
		this.targetValue = target;
	}
	
	protected float getTargetValue() {
		return this.targetValue;
	}
	
	protected CameraManager getManager() {
		return this.cameraManager;
	}
	
	protected float getInterpolation() {
		return this.interpolation;
	}
	
	public boolean getEnded() {
		return this.isEnded;
	}
}
