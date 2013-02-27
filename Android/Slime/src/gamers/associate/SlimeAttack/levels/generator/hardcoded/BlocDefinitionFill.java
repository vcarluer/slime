package gamers.associate.SlimeAttack.levels.generator.hardcoded;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.levels.generator.BlocDefinition;
import gamers.associate.SlimeAttack.levels.generator.LevelGenNode;

public class BlocDefinitionFill extends BlocDefinitionHardCoded {

	public static final String IDFILL = "Fill";

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
