package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.BecBunsen;

public class BecBunsenDef extends ItemDefinition {
	private static String Handled_BecBunsen = "BecBunsen";	
	
	private boolean isOn;
	private float delay;
	private String name;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.BecBunsen.createBL(this.getX(), this.getY(), this.width, this.height, this.getIdPre() + this.name, this.delay, this.isOn).setAngle(this.angle);		
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

	@Override
	protected void initClassHandled() {
		this.classHandled.add(BecBunsen.class);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.isOn));
		line = this.addValue(line, String.valueOf(this.delay));
		line = this.addValue(line, String.valueOf(this.name));
		
		return line;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_BecBunsen;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		BecBunsen bec = (BecBunsen)item;
		
		this.isOn = bec.getStartOn();
		this.delay = bec.getAnimDelay();
		this.name = bec.getName();		
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}
}
