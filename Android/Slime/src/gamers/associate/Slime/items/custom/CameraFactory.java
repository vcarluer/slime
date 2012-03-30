package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;

public class CameraFactory extends GameItemPhysicFactory<Camera> {

	private String targetName;
	private boolean startOn;
	private float fov;
	private float viewdistance;
	private float rotateTime;
	private float rotateAngle;

	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Camera.Anim_Wait, 1);
		this.createAnim(Camera.Anim_Wait, 1);		
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Camera instantiate(float x, float y, float width, float height) {
		return new Camera(x, y, width, height, this.world, this.worldRatio, this.targetName, this.startOn, this.fov, this.viewdistance, this.rotateTime, this.rotateAngle);
	}

	@Override
	protected void runFirstAnimations(Camera item) {
		item.initState();
	}
	
	public Camera create(String name, float x, float y, float width, float height, String targetName, boolean startOn, float fov, float viewDistance, float rotateTime, float rotateAngle) {
		this.targetName = targetName;
		this.startOn = startOn;
		this.fov = fov;
		this.viewdistance = viewDistance;
		this.rotateTime = rotateTime;
		this.rotateAngle = rotateAngle;
		return this.create(name, x, y, width, height);
	}
	
	public Camera createBL(String name, float x, float y, float width, float height, String targetName, boolean startOn, float fov, float viewDistance, float rotateTime, float rotateAngle) {
		return this.create(name, x + width / 2f, y + height / 2f, width, height, targetName, startOn, fov, viewDistance, rotateTime, rotateAngle);
	}
}
