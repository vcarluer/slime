package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;
import java.util.ArrayList;

public class LevelBuilderGenerator implements ILevelBuilder
{
	public static String defaultId = "Random";
	private static String fileName = defaultId + ".slime"; 
	private int complexity;
	private LevelHome home = new LevelHome();
	private LevelDefinitionGenerator levelDef = new LevelDefinitionGenerator();
	private LevelDefinitionParser levelparser = new LevelDefinitionParser(fileName, true);
	private boolean firstBuild;
	
	public LevelBuilderGenerator() {
		this.levelparser.setLocalStorage(true);		
		this.firstBuild = true;
		this.levelDef.setBossComplexity(25);
		// always default id... Used for reset all
		this.levelDef.setId(defaultId);
	}
	
	public void build(Level level, String id)
	{
		if (id != LevelHome.Id)
		{			
			if (this.firstBuild) {
				this.complexity = this.levelDef.getComplexity();				
			}												
			
			if (this.firstBuild && this.levelparser.isStored() && !this.levelDef.isFinished()) {				
				this.levelparser.buildLevel(level);					
				level.setLevelDefinition(this.levelDef);
			} else {
				if (this.complexity > this.levelDef.getBossComplexity()) {
					this.complexity = 0;
				}

				this.complexity += 5;
				this.levelDef.setComplexity(this.complexity);
				this.levelDef.buildLevel(level);
				level.setLevelDefinition(this.levelDef);				
				this.levelparser.storeLevel(level);
				
				this.levelDef.resetAndSave();								
			}
			
			this.firstBuild = false;
		}
		else
		{
			this.home.buildLevel(level);
			level.setLevelDefinition(this.home);
			level.addGamePlay(null);
		}
	}
	
	public String getNext(String paramString)
	{
		return defaultId;
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
	
	public void resetAll() {
		this.levelDef.resetAllAndSave();
		this.levelparser.resetStorage();
		this.complexity = this.levelDef.getComplexity();
		Level.get(LevelBuilderGenerator.defaultId, true);
	}
	
	public LevelDefinitionParser getParser() {
		return this.levelparser;
	}
	
	public boolean hasBegun() {
		return this.levelparser.isStored();
	}
	
	public void start() {
		this.firstBuild = true;
		Level.get(LevelBuilderGenerator.defaultId, true);
	}
}