package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class BumperAngleDef extends ItemDefinition {
	private static String Handled_Def = "Bumper_Angle";
		
	@Override
	public void createItem(Level level) {
		SlimeFactory.BumperAngle.createBL(this.x, this.y, this.width, this.height);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// NONE
	}

}
