package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.EvacuationPlug;
import gamers.associate.SlimeAttack.items.custom.GoalPortal;
import gamers.associate.SlimeAttack.levels.GamePlay;

public class GoalPortalDef extends ItemDefinition {
	private static String Handled_Goal = "GoalPortal";
	
	@Override
	public void createItem(Level level) {
		GoalPortal goal = SlimeFactory.GoalPortal.createBL(this.getUName(), this.getX(), this.getY(), this.width, this.height);
		goal.setAngle(this.angle);
		level.setGoal(goal);
		
		if (!(level.getLevelDefinition().getGamePlay() == GamePlay.TimeAttack && SlimeFactory.PackageManager.getCurrentPackage().getOrder() == 1 && SlimeFactory.GameInfo.getLevelNum() == 1)) {
			float evacuationHeight = EvacuationPlug.getHeightFromWidth(this.width);
			EvacuationPlug plug = SlimeFactory.EvacuationPlug.create(this.getUName(), goal.getPosition().x, goal.getPosition().y, this.width, evacuationHeight);
			plug.setAngle(this.angle);
			level.setPlug(plug);
		}
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
