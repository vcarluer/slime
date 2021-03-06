package gamers.associate.SlimeAttack.levels.generator.hardcoded;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.custom.GoalPortal;
import gamers.associate.SlimeAttack.levels.generator.BlocDirection;
import gamers.associate.SlimeAttack.levels.generator.LevelGenNode;

public class BlocDefinitionHardCodedEnd extends BlocDefinitionHardCoded
{
  public static BlocDirection EndDirection;

  @Override
  public void buildLevel(Level level, int xOffset, int yOffset)
  {
    setOffset(xOffset, yOffset);
    SlimeFactory.Platform.createBL(getX(0.0F), getY(0.0F), BlocWidth / 2, 32.0F);
    SlimeFactory.GoalPortal.createBL(getX(100.0F), getY(100.0F), GoalPortal.Default_Width, GoalPortal.Default_Height);
  }

  @Override
  protected int getInitComplexity()
  {
    return 5;
  }

  @Override
  protected void initGenNodeInternal(LevelGenNode genNode)
  {
    genNode.setIsLevelEnd(true);
    genNode.addConnectorsEntry(LevelGenNode.getConnectorsFor(EndDirection));
    genNode.setId("End" + String.valueOf(EndDirection));
  }
}