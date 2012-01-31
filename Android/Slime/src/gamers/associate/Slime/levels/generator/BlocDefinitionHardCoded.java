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
	  
	  protected float getX(float x)
	  {
	    return getX(x, this.currentXOffset);
	  }

	  protected float getX(float x, float xOffset)
	  {
	    return x + xOffset * BlocWidth;
	  }

	  protected float getY(float y)
	  {
	    return getY(y, this.currentYOffset);
	  }

	  protected float getY(float y, float yOffset)
	  {
	    return y + yOffset * BlocHeight;
	  }
}
