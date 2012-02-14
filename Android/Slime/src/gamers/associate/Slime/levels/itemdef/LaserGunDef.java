package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.LaserGun;

public class LaserGunDef extends ItemDefinition {
	private static String Handled_Def = "LaserGun";
	private String name;
	private String target;
	private boolean isOn;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.LaserGun.createBL(this.getX(), this.getY(), this.width, this.height, this.getIdPre() + this.name, this.getIdPre() + this.target, this.isOn).setAngle(this.angle);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.name = infos[start];
		this.target = infos[start + 1];
		this.isOn = Boolean.parseBoolean(infos[start + 2]);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(LaserGun.class);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.name));
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
		this.name = laser.getName();
		this.target = laser.getTarget();
		this.isOn = laser.getStartOn();
	}
}
