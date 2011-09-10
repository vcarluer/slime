package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class SpawnDef extends ItemDefinition {
	private static String Handled_Spawn = "Spawn";
	
	@Override
	public void createItem(Level level) {
		level.setStartItem(SlimeFactory.Slimy.createJump(this.x, this.y, 1.0f));
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Spawn);
	}

	@Override
	protected void parseNext(String[] infos) {
		// NONE
	}

}
