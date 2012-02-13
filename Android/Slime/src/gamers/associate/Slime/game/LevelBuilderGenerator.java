package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;

import java.util.ArrayList;

public class LevelBuilderGenerator implements ILevelBuilder
{
	private static int MinimumComplexity = 1;
	private static int AverageComplexityPerLevel = 1;
	public static String defaultId = "Random";
	private static String fileName = defaultId + ".slime"; 
	private int complexity;
	private LevelHome home = new LevelHome();
	private LevelDefinitionGenerator levelDef = new LevelDefinitionGenerator();
	private LevelDefinitionParser levelparser = new LevelDefinitionParser(fileName, true);
	private boolean firstBuild;	
	private GameInformation gameInfo;
	
	public LevelBuilderGenerator() {		
		this.gameInfo = SlimeFactory.GameInfo;
		this.levelparser.setLocalStorage(true);		
		this.firstBuild = true;
		// always default id... Used for reset all
		this.levelDef.setId(defaultId);
	}	
	
	public void build(Level level, String id)
	{
		if (id != LevelHome.Id)
		{
			if (this.firstBuild && this.levelparser.isStored() && !this.levelDef.isFinished()) {				
				this.levelparser.buildLevel(level);					
				level.setLevelDefinition(this.levelDef);
			} else {
				// this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor);
				this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorRectangle);
				this.gameInfo.levelUp();
				this.complexity = this.computeComplexity();
				this.levelDef.setComplexity(this.complexity);
				if (this.gameInfo.getLevelNum() == this.gameInfo.getLevelMax()) {
					this.levelDef.buildBossLevel(level);
				} else {
					this.levelDef.buildLevel(level);
				}

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
		this.complexity = this.computeComplexity();
	}
	
	private int computeComplexity() {
		return MinimumComplexity + this.gameInfo.getDifficulty() + AverageComplexityPerLevel * this.gameInfo.getDifficulty() * this.gameInfo.getLevelNum();
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
		this.gameInfo.resetDifficulty(this.gameInfo.getDifficulty());
		this.gameInfo.resetTotalScore();		
	}
	
	public void resetAllAndRun() {
		this.resetAll();
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