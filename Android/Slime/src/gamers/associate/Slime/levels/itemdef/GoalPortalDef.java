package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class GoalPortalDef extends ItemDefinition {
	private static String Handled_Goal = "GoalPortal";
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.GoalPortal.createBL(this.x, this.y, this.width, this.height);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Goal);	
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// NONE	
	}

}
