package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;

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

}
