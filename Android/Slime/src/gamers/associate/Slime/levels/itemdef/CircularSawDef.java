package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.CircularSaw;

public class CircularSawDef extends ItemDefinition {
	private static String Handled_Def = "CircularSaw";
	
	private boolean isOn;
	private String name;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.CircularSaw.createBL(this.getX(), this.getY(), this.width, this.height, this.getIdPre() + this.name, this.isOn).setAngle(this.angle);
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

	@Override
	protected void initClassHandled() {
		this.classHandled.add(CircularSaw.class);
		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.name);
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
		CircularSaw saw = (CircularSaw)item;
		this.name = saw.getName();
		this.isOn = saw.getStartOn();		
	}
}
