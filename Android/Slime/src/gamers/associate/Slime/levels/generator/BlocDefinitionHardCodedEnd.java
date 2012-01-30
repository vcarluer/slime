package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.GoalPortalFactory;
import gamers.associate.Slime.items.custom.PlatformFactory;

public class BlocDefinitionHardCodedEnd extends BlocDefinition
{
  public static BlocDirection EndDirection;

  public void buildLevel(Level paramLevel, int paramInt1, int paramInt2)
  {
    setOffset(paramInt1, paramInt2);
    SlimeFactory.Platform.createBL(getX(0.0F), getY(0.0F), BlocWidth / 2, 32.0F);
    SlimeFactory.GoalPortal.createBL(getX(100.0F), getY(100.0F), 32.0F, 35.0F);
  }

  protected int getComplexity()
  {
    return 5;
  }

  protected void initGenNodeInternal(LevelGenNode paramLevelGenNode)
  {
    paramLevelGenNode.setIsLevelEnd(true);
    paramLevelGenNode.addConnectorsEntry(paramLevelGenNode.getConnectorsFor(EndDirection));
  }
}

/* Location:           C:\projects\slimedecomp\slime-dex2jar.jar
 * Qualified Name:     gamers.associate.Slime.levels.generator.BlocDefinitionHardCodedEnd
 * JD-Core Version:    0.6.0
 */