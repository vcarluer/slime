package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.EnergyBall;

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
		SlimeFactory.EnergyBall.createBL(this.getUName(), x, y, width, height).setAngle(angle);
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
