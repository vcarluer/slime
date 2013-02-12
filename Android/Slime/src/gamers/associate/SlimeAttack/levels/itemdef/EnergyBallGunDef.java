package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.EnergyBallGun;

public class EnergyBallGunDef extends ItemDefinition {
	public static String Handled_Def = "EnergyBallGun";
	private String target;
	private boolean isOn;
	private float ballSpeed;
	private float waitTime;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.EnergyBallGun.createBL(this.getX(), this.getY(), this.width, this.height, this.getUName(), this.getUString(this.target), this.isOn, this.ballSpeed, this.waitTime).setAngle(this.angle);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.target = infos[start];
		this.isOn = Boolean.parseBoolean(infos[start + 1]);
		this.ballSpeed = Float.parseFloat(infos[start + 2]);
		this.waitTime = Float.parseFloat(infos[start + 3]);
		
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(EnergyBallGun.class);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.target));
		line = this.addValue(line, String.valueOf(this.isOn));
		line = this.addValue(line, String.valueOf(this.ballSpeed));
		line = this.addValue(line, String.valueOf(this.waitTime));
		
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
		EnergyBallGun gun = (EnergyBallGun)item;
		this.target = gun.getTarget();
		this.isOn = gun.getStartOn();
		this.ballSpeed = gun.getBallSpeed();
		this.waitTime = gun.getWaitTime();
	}
}
