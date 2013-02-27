package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.SlimyJump;

public class SpawnDef extends ItemDefinition {
	private static String Handled_Spawn = "Spawn";
	
	@Override
	public void createItem(Level level) {
		// todo: Create and use createBL. Change getIsBL too.
		SlimyJump slimy = SlimeFactory.Slimy.createJump(this.getUName(), this.getX() + this.width / 2, this.getY() + this.height / 2, 1.0f);
		level.setStartItem(slimy);		
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
