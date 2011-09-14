package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class CircularSawDef extends ItemDefinition {
	private static String Handled_Def = "CircularSaw";
	
	private boolean isOn;
	private String name;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.CircularSaw.createBL(this.x, this.y, this.width, this.height, this.name, this.isOn);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.name = infos[start];
		this.isOn = Boolean.valueOf(infos[start + 1]).booleanValue();		
	}

}
