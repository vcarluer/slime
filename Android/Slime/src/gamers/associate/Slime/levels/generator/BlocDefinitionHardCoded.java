package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.SlimeFactory;


public abstract class BlocDefinitionHardCoded extends BlocDefinition {
	
	public BlocDefinitionHardCoded() {
		initGenNode();
	}
	
	private final void initGenNode()
	  {
	    LevelGenNode localLevelGenNode = new LevelGenNode();
	    localLevelGenNode.setComplexity(getInitComplexity());
	    initGenNodeInternal(localLevelGenNode);
	    setGenNode(localLevelGenNode);
	    SlimeFactory.LevelGenerator.addNode(localLevelGenNode);
	  }	  

	  protected abstract int getInitComplexity();
	  
	  protected abstract void initGenNodeInternal(LevelGenNode genNode);
}
