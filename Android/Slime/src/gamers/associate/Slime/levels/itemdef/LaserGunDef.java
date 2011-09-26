package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class LaserGunDef extends ItemDefinition {
	private static String Handled_Def = "LaserGun";
	private String name;
	private String target;
	private boolean isOn;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.LaserGun.createBL(this.x, this.y, this.width, this.height, this.name, this.target, this.isOn).setAngle(this.angle);
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

}
