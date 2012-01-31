package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.BumperAngle;

public class BumperAngleDef extends ItemDefinition {
	private static String Handled_Def = "Bumper_Angle";
		
	@Override
	public void createItem(Level level) {
		SlimeFactory.BumperAngle.createBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// NONE
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(BumperAngle.class);
	}

	@Override
	protected String writeNext(String line) {
		return line;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {		
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

}
