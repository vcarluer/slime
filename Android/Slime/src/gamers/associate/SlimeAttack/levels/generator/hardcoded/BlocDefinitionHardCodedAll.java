package gamers.associate.SlimeAttack.levels.generator.hardcoded;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.levels.generator.BlocDirection;
import gamers.associate.SlimeAttack.levels.generator.LevelGenNode;

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
	    genNode.addConnectorsEntry(LevelGenNode.getConnectorsFor(BlocDirection.Top));
	    genNode.addConnectorsEntry(LevelGenNode.getConnectorsFor(BlocDirection.Right));
	    genNode.addConnectorsEntry(LevelGenNode.getConnectorsFor(BlocDirection.Bottom));
	    genNode.addConnectorsEntry(LevelGenNode.getConnectorsFor(BlocDirection.Left));
	    genNode.addConnectorsExit(LevelGenNode.getConnectorsFor(BlocDirection.Top));
	    genNode.addConnectorsExit(LevelGenNode.getConnectorsFor(BlocDirection.Right));
	    genNode.addConnectorsExit(LevelGenNode.getConnectorsFor(BlocDirection.Bottom));
	    genNode.addConnectorsExit(LevelGenNode.getConnectorsFor(BlocDirection.Left));
	    genNode.setId("All");
	  }
}