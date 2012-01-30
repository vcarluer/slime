package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.GoalPortal;

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

	@Override
	protected void initClassHandled() {
		this.classHandled.add(GoalPortal.class);
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
		return Handled_Goal;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		
	}

}
