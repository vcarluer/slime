package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.IGamePlay;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.PlatformFactory;
import gamers.associate.Slime.items.custom.SlimyFactory;

public class BlocDefinitionHardCodedStart extends BlocDefinition
{
  public static BlocDirection StartDirection;  

  public void buildLevel(Level paramLevel, int paramInt1, int paramInt2)
  {
    setOffset(paramInt1, paramInt2);
    SlimeFactory.Platform.createBL(getX(0.0F), getY(0.0F), BlocWidth / 2, 32.0F);
    Object localObject = SlimeFactory.Slimy.createJump(getX(100.0F), getY(100.0F), 1.0F);
    Level.currentLevel.setStartItem((GameItem)localObject);
    localObject = TimeAttackGame.NewGame();
    paramLevel.addGamePlay((IGamePlay)localObject);
    ((TimeAttackGame)localObject).setStartTime(20);
    ((TimeAttackGame)localObject).setCriticTime(5);
  }

  protected int getComplexity()
  {
    return 5;
  }

  protected void initGenNodeInternal(LevelGenNode paramLevelGenNode)
  {
    paramLevelGenNode.setIsLevelStart(true);
    paramLevelGenNode.addConnectorsExit(paramLevelGenNode.getConnectorsFor(StartDirection));
  }
}

/* Location:           C:\projects\slimedecomp\slime-dex2jar.jar
 * Qualified Name:     gamers.associate.Slime.levels.generator.BlocDefinitionHardCodedStart
 * JD-Core Version:    0.6.0
 */