package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.EnergyBall;

public class EnergyBallDef extends ItemDefinition {
	public static String Handled_Def = "EnergyBall";
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(EnergyBall.class);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
	}

	@Override
	public void createItem(Level level) {
		SlimeFactory.EnergyBall.createBL("ball", x, y, width, height).setAngle(angle);
	}

	@Override
	protected String writeNext(String line) {
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
	}

}
