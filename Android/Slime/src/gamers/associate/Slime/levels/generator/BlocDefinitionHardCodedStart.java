package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.IGamePlay;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.items.base.GameItem;

public class BlocDefinitionHardCodedStart extends BlocDefinitionHardCoded
{
	public static BlocDirection StartDirection;  

	@Override
	  public void buildLevel(Level level, int xOffset, int yOffset)
	  {
	    setOffset(xOffset, yOffset);
	    SlimeFactory.Platform.createBL(getX(0.0F), getY(0.0F), BlocWidth / 2, 32.0F);
	    Object localObject = SlimeFactory.Slimy.createJump(getX(100.0F), getY(100.0F), 1.0F);
	    Level.currentLevel.setStartItem((GameItem)localObject);
	    localObject = TimeAttackGame.NewGame();
	    level.addGamePlay((IGamePlay)localObject);
	    ((TimeAttackGame)localObject).setStartTime(20);
	    ((TimeAttackGame)localObject).setCriticTime(5);
	    level.setMaxDimension(Level.ManualDimension);
	  }

	@Override
	  protected int getInitComplexity()
	  {
	    return 5;
	  }

	@Override
	  protected void initGenNodeInternal(LevelGenNode genNode)
	  {
	    genNode.setIsLevelStart(true);
	    genNode.addConnectorsExit(genNode.getConnectorsFor(StartDirection));
	    genNode.setId("Start" + String.valueOf(StartDirection));
	  }
}