package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;

public class LaboratoryPackage extends WorldPackage {

	private static final String LABORATORY = "Laboratory";
	private static final String LVLSEP = "-";
	private static final int levelCount = 80;

	@Override
	protected void createLevelList() {
		for(int i = 0; i < LaboratoryPackage.levelCount; i++) {
			LevelDefinition definition = new LevelDefinitionParser(this.getResourceName(i));
		}
	}

	private String getResourceName(int i) {
		return this.getName() + LVLSEP + String.valueOf(i) + SlimeFactory.slimeFileExt;
	}

	@Override
	protected void defineName() {
		this.setName(LABORATORY);
	}

}
