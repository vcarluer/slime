package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class BlocDefinitionHardCodedBoss extends BlocDefinitionHardCoded
{
	@Override
	public void buildLevel(Level paramLevel, int xOffset, int yOffset)
	  {
	    setOffset(xOffset, yOffset);
	    SlimeFactory.Platform.createBL(getX(200.0F), getY(200.0F), 100 , 32.0F);
	    SlimeFactory.Platform.createBL(getX(200.0F), getY(275.0F), 100 , 32.0F);
	    SlimeFactory.Platform.createBL(getX(200.0F), getY(232.0F), 68 , 32.0F).setAngle(-90f);
	    SlimeFactory.GoalPortal.createBL(getX(100.0F), getY(100.0F), 32.0F, 35.0F);
	  }
	@Override
	  protected int getInitComplexity()
	  {
	    return 5;
	  }

	@Override
	  protected void initGenNodeInternal(LevelGenNode genNode)
	  {
	    genNode.setBoss(true);
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Top));
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Right));
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Bottom));
	    genNode.addConnectorsEntry(genNode.getConnectorsFor(BlocDirection.Left));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Top));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Right));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Bottom));
	    genNode.addConnectorsExit(genNode.getConnectorsFor(BlocDirection.Left));
	    genNode.setId("Boss!");
	  }
}