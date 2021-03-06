package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.LaserGun;

public class LaserGunDef extends ItemDefinition {
	public static String Handled_Def = "LaserGun";
	private String target;
	private boolean isOn;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.LaserGun.createBL(this.getX(), this.getY(), this.width, this.height, this.getUName(), this.getUString(this.target), this.isOn).setAngle(this.angle);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.target = infos[start];
		this.isOn = Boolean.parseBoolean(infos[start + 1]);
		
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(LaserGun.class);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.target));
		line = this.addValue(line, String.valueOf(this.isOn));
		
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
		LaserGun laser = (LaserGun)item;
		this.target = laser.getTarget();
		this.isOn = laser.getStartOn();
	}
}
