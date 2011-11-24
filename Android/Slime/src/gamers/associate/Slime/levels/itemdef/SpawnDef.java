package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class SpawnDef extends ItemDefinition {
	private static String Handled_Spawn = "Spawn";
	
	@Override
	public void createItem(Level level) {
		// todo: Create and use createBL
		level.setStartItem(SlimeFactory.Slimy.createJump(this.x + this.width / 2, this.y + this.height / 2, 1.0f));
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Spawn);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// NONE
	}

}
