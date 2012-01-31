package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class BlocDefinitionHardCodedAll extends BlocDefinitionHardCoded
{
	@Override	
	public void buildLevel(Level paramLevel, int xOffset, int yOffset)
	  {
	    setOffset(xOffset, yOffset);
	    SlimeFactory.Platform.createBL(getX(150.0F), getY(150.0F), 100 , 32.0F);    
	  }
	@Override
	protected int getInitComplexity()
	  {
	    return 5;
	  }
	@Override
	  protected void initGenNodeInternal(LevelGenNode genNode)
	  {
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Top));
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Right));
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Bottom));
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Left));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Top));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Right));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Bottom));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Left));
	    genNode.setId("All");
	  }
}