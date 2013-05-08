package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.levels.LevelDefinitionParserPreBuild;

public class LevelInfoDefPreBuild extends LevelInfoDef {

	public LevelInfoDefPreBuild() {
	}

	@Override
	public void createItem(Level level) {
		this.width -= LevelDefinitionParserPreBuild.widthCorrection;
		this.height -= LevelDefinitionParserPreBuild.heightCorrection;
		this.x += LevelDefinitionParserPreBuild.xCorrection;
		this.y += LevelDefinitionParserPreBuild.yCorrection;
		super.createItem(level);
	}
}
