package gamers.associate.SlimeAttack.levels.generator.hardcoded;

import gamers.associate.SlimeAttack.game.IGamePlay;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.TimeAttackGame;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.levels.generator.BlocDirection;
import gamers.associate.SlimeAttack.levels.generator.LevelGenNode;

public class BlocDefinitionHardCodedStart extends BlocDefinitionHardCoded
{
	public static BlocDirection StartDirection;  

	@Override
	  public void buildLevel(Level level, int xOffset, int yOffset)
	  {
	    setOffset(xOffset, yOffset);
	    SlimeFactory.Platform.createBL(getX(0.0F), getY(0.0F), BlocWidth / 2, 32.0F);
	    Object localObject = SlimeFactory.Slimy.createJump("Hard", getX(100.0F), getY(100.0F), 1.0F);
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
	    genNode.addConnectorsExit(LevelGenNode.getConnectorsFor(StartDirection));
	    genNode.setId("Start" + String.valueOf(StartDirection));
	  }
}