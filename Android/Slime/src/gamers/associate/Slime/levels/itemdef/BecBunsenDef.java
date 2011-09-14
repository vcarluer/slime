package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class BecBunsenDef extends ItemDefinition {
	private static String Handled_BecBunsen = "BecBunsen";
	
	private boolean isOn;
	private float delay;
	private String name;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.BecBunsen.createBL(this.x, this.y, this.width, this.height, this.name, this.delay, this.isOn);		
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_BecBunsen);		
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.isOn = Boolean.valueOf(infos[start]).booleanValue();
		this.delay = Float.valueOf(infos[start + 1]).floatValue();
		this.name = infos[start + 2];
	}
}
