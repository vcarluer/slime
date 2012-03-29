package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.TeslaCoil;

public class TeslaCoilDef extends ItemDefinition {
	public static String Handled_Def = "TeslaCoil"; 
	private boolean startOn;
	private float strikeDistance;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(TeslaCoil.class);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.startOn = Boolean.valueOf(infos[start]).booleanValue();
		this.strikeDistance = Float.valueOf(infos[start+1]).floatValue();
	}

	@Override
	public void createItem(Level level) {
		float strike = TeslaCoil.Default_StrikeDistance;
		if (this.strikeDistance > 0) {
			strike = this.strikeDistance;
		}
		
		SlimeFactory.TeslaCoil.createBL(this.getX(), this.getY(), this.width, this.height, this.getUName(), this.startOn, strike).setAngle(this.angle);
	}

	@Override
protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.startOn));
		line = this.addValue(line, String.valueOf(this.strikeDistance));
		
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
		TeslaCoil tesla = (TeslaCoil) item;
		this.startOn = tesla.isStartOn();
		this.strikeDistance = tesla.getStrikeDistance();
	}

}
