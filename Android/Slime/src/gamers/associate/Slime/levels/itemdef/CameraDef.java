package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Camera;

public class CameraDef extends ItemDefinition {
	public static String Handled_Def = "Camera";
	private String targetName;
	private boolean startOn;
	private float fov;
	private float viewDistance;
	private float rotateTime;
	private float rotateAngle;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Camera.class);	
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.targetName = infos[start];
		this.startOn = Boolean.parseBoolean(infos[start + 1]);
		this.fov = Float.parseFloat(infos[start + 2]);
		this.viewDistance = Float.parseFloat(infos[start + 3]);
		this.rotateTime = Float.parseFloat(infos[start + 4]);
		this.rotateAngle = Float.parseFloat(infos[start + 5]);
	}

	@Override
	public void createItem(Level level) {
		// setAngle after cameraMove error?
		SlimeFactory.Camera.createBL(this.getUName(), this.getX(), this.getY(), this.width, this.height, this.getUString(this.targetName), this.startOn, this.fov, this.viewDistance, this.rotateTime, this.rotateAngle).setAngle(this.angle);			
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.targetName);
		line = this.addValue(line, String.valueOf(this.startOn));
		line = this.addValue(line, String.valueOf(this.fov));
		line = this.addValue(line, String.valueOf(this.viewDistance));
		line = this.addValue(line, String.valueOf(this.rotateTime));
		line = this.addValue(line, String.valueOf(this.rotateAngle));
		
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		Camera cam = (Camera) item; 
		this.targetName = cam.getTargetName();
		this.startOn = cam.isStartOn();
		this.fov = cam.getFov();
		this.viewDistance = cam.getViewDistance();
		this.rotateTime = cam.getRotateTime();
		this.rotateAngle = cam.getRotateAngle();
	}

}
