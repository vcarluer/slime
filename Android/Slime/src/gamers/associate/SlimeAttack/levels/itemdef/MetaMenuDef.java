package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.items.base.GameItem;

public class MetaMenuDef extends ItemDefinition {
	private static String Handled_Def = "MetaMenu";	
	
	@Override
	public void createItem(Level level) {
		level.setPhysicDisabled(true);
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
		// NONE for now
		
	}

	@Override
	protected String writeNext(String line) {
		return null;
	}

	@Override
	protected boolean getIsBL() {
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return null;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		
	}	
}
