package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelUtil;

public class LevelInfoDef extends ItemDefinition {
	private static String Handled_Size = "LevelInfo";

	@Override
	public void createItem(Level level) {
		level.setLevelSize(
				this.width,
				this.width * LevelUtil.getHeightRatio());
		
		LevelUtil.createGroundBox(level);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Size);
	}

	@Override
	protected void parseNext(String[] infos) {
		// NONE
	}
}
