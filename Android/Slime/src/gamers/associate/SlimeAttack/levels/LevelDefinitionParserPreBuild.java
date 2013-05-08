package gamers.associate.SlimeAttack.levels;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.levels.itemdef.ItemDefinition;
import gamers.associate.SlimeAttack.levels.itemdef.LevelInfoDef;
import gamers.associate.SlimeAttack.levels.itemdef.LevelInfoDefPreBuild;

public class LevelDefinitionParserPreBuild extends LevelDefinitionParser {
	public static float widthCorrection = 1280;
	public static float heightCorrection = 736;
	public static float xCorrection = widthCorrection / 2f;
	public static float yCorrection = heightCorrection / 2f;
	
	public LevelDefinitionParserPreBuild() {
	}

	public LevelDefinitionParserPreBuild(String resourceName,
			boolean noUserInfoStore) {
		super(resourceName, noUserInfoStore);
	}

	public LevelDefinitionParserPreBuild(String resourceName) {
		super(resourceName);
	}

	@Override
	protected void defineIgnoredItemsNames() {
		this.ignoredItemsNames.add(LevelUtil.GroundBoxGlass);
		super.defineIgnoredItemsNames();
	}

	@Override
	protected LevelInfoDef getLevelInfoDef() {
		return new LevelInfoDefPreBuild();
	}

	@Override
	public boolean buildLevel(Level level) {
		boolean value = super.buildLevel(level);
		LevelUtil.createGroundBoxGlass(level);
		return value;
	}
}
