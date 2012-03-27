package gamers.associate.Slime.levels.generator.hardcoded;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.generator.BlocDefinition;
import gamers.associate.Slime.levels.generator.LevelGenNode;

public class BlocDefinitionFill extends BlocDefinitionHardCoded {

	private static final String IDFILL = "Fill";

	@Override
	protected int getInitComplexity() {		
		return 0;
	}

	@Override
	protected void initGenNodeInternal(LevelGenNode genNode) {
		genNode.setId(IDFILL);
	}

	@Override
	public void buildLevel(Level level, int xOffset, int yOffset) {
		setOffset(xOffset, yOffset);
		SlimeFactory.Platform.createWallBL("Fill", this.getX(0), this.getY(0), BlocDefinition.BlocWidth, BlocDefinition.BlocHeight);
	}
}
