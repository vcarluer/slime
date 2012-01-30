package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;
import java.util.ArrayList;

public class LevelBuilderGenerator
  implements ILevelBuilder
{
  private int complexity;
  private LevelHome home = new LevelHome();
  private LevelDefinitionGenerator levelDef = new LevelDefinitionGenerator();

  public void build(Level paramLevel, String paramString)
  {
    if (paramString != LevelHome.Id)
    {
      this.levelDef.setId(paramString);
      this.levelDef.setComplexity(this.complexity);
      this.levelDef.buildLevel(paramLevel);
      paramLevel.setLevelDefinition(this.levelDef);
    }
    else
    {
      this.home.buildLevel(paramLevel);
      paramLevel.setLevelDefinition(this.home);
      paramLevel.addGamePlay(null);
    }
  }

  public String getNext(String paramString)
  {
    return "Random";
  }

  public ArrayList<LevelDefinition> getNormalLevels()
  {
    return null;
  }

  public void init()
  {
    this.complexity = 5;
  }
}

/* Location:           C:\projects\slimedecomp\slime-dex2jar.jar
 * Qualified Name:     gamers.associate.Slime.game.LevelBuilderGenerator
 * JD-Core Version:    0.6.0
 */