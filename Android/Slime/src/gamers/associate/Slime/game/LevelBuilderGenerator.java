package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;
import java.util.ArrayList;

public class LevelBuilderGenerator implements ILevelBuilder
{
	private static String fileName = "Random.slime"; 
	private int complexity;
	private LevelHome home = new LevelHome();
	private LevelDefinitionGenerator levelDef = new LevelDefinitionGenerator();
	private LevelDefinitionParser levelparser = new LevelDefinitionParser(fileName);
	
	public LevelBuilderGenerator() {
		this.levelparser.setLocalStorage(true);
	}
	
	public void build(Level paramLevel, String id)
	{
		if (id != LevelHome.Id)
		{
		  this.levelDef.setId(id);
		  this.levelDef.setComplexity(this.complexity);
		  this.levelDef.buildLevel(paramLevel);
		  paramLevel.setLevelDefinition(this.levelDef);
		  this.levelparser.storeLevel(paramLevel);
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
	
	@Override
	public void build(Level level, LevelDefinition levelDef) {
		levelDef.buildLevel(level);
	}
	
	@Override
	public void rebuild(Level level, LevelDefinition levelDef) {
		if (this.levelparser.isStored()) {
			this.levelparser.buildLevel(level);
		} else {
			this.build(level, levelDef.getId());
		}
	}
}