package gamers.associate.SlimeAttack.levels.generator.hardcoded;

import gamers.associate.SlimeAttack.levels.generator.BlocDefinition;
import gamers.associate.SlimeAttack.levels.generator.LevelGenNode;


public abstract class BlocDefinitionHardCoded extends BlocDefinition {
	
	public BlocDefinitionHardCoded() {
		this(null, true);
	}
	
	public BlocDefinitionHardCoded(String resourceName, boolean noUserInfoStore) {
		super(resourceName, noUserInfoStore);
		initGenNode();
	}	
	
	private final void initGenNode()
	  {
	    LevelGenNode localLevelGenNode = new LevelGenNode();
	    localLevelGenNode.setComplexity(getInitComplexity());
	    initGenNodeInternal(localLevelGenNode);
	    setGenNode(localLevelGenNode);
	    // SlimeFactory.LevelGeneratorCorridor.addNode(localLevelGenNode);
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
