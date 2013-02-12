package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.CircularSaw;

public class CircularSawDef extends ItemDefinition {
	public static String Handled_Def = "CircularSaw";
	
	private boolean isOn;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.CircularSaw.createBL(this.getX(), this.getY(), this.width, this.height, this.getUName(), this.isOn).setAngle(this.angle);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.isOn = Boolean.valueOf(infos[start]).booleanValue();		
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(CircularSaw.class);
		
	}

	@Override
	protected String writeNext(String line) {
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
		this.isOn = saw.getStartOn();		
	}
}
