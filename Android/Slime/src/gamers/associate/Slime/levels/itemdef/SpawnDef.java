package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.SlimyJump;

public class SpawnDef extends ItemDefinition {
	private static String Handled_Spawn = "Spawn";
	
	@Override
	public void createItem(Level level) {
		// todo: Create and use createBL. Change getIsBL too.
		level.setStartItem(SlimeFactory.Slimy.createJump(this.getX() + this.width / 2, this.getY() + this.height / 2, 1.0f));
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Spawn);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// NONE
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(SlimyJump.class);		
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
		return Handled_Spawn;
	}

	@Override
	protected void setValuesNext(GameItem item) {	
	}

}
