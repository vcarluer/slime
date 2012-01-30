package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;

public class LevelDefinitionGenerator extends LevelDefinition {
	private int complexity;
	
	public LevelDefinitionGenerator() {
		this.gamePlay = GamePlay.ManuallyDefined;
	}
	
	@Override
	protected boolean getNoStore() {
		return true;
	}

	@Override
	public void buildLevel(Level level) {
		SlimeFactory.LevelGenerator.generate(this.getComplexity(), BlocDirection.Left);
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

}
