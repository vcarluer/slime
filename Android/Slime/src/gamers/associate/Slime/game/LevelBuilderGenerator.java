package gamers.associate.Slime.game;

import gamers.associate.Slime.game.achievements.AchievementStatistics;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;

import java.util.ArrayList;

public class LevelBuilderGenerator extends AbstractLevelBuilder
{	
	private static boolean isDebug =  SlimeFactory.IsForceDiffDebug;
	private static int forceDiff = SlimeFactory.ForceDiff;
	private static int forceLevel = SlimeFactory.ForceLevel;
	
	private static int MinimumComplexity = 1;
	private static int AverageComplexityPerLevel = 1;
	public static String defaultId = "Random";
	public static String fileExtension = ".slime";
	private String fileName = defaultId + fileExtension; 
	private int complexity;
	private LevelHome home = new LevelHome();
	private LevelDefinitionGenerator levelDef = new LevelDefinitionGenerator();
	private LevelDefinitionParser levelparser = new LevelDefinitionParser(fileName, true);		
	
	public LevelBuilderGenerator() {				
		this.levelparser.setLocalStorage(true);		
	}	
	
	private String getRandomFileName(int difficulty) {
		return defaultId + String.valueOf(difficulty) + fileExtension;
	}
		
	public void build(Level level, String id, GamePlay gamePlay)
	{
		// this one is only call for survival and home! Bad design powa. Juste ensure it here
		if (gamePlay == GamePlay.TimeAttack) return;

		// always default id... Used for reset all
		this.levelDef.setId(defaultId);
		
		if (id != LevelHome.Id)
		{			
			this.levelDef.setGamePlay(gamePlay);
			level.setLevelDefinition(this.levelDef);			
			
			this.isBoss = (this.gameInfo.getLevelNum() == this.gameInfo.getLevelMax());
			this.resetTotalStar();
			
			if (!isDebug) {
				if (SlimeFactory.GameInfo.getLevelNum() == 0) {
					SlimeFactory.GameInfo.setLevel(1);
				}
			} else {
				this.gameInfo.forceLevel(forceDiff, forceLevel);					
			}						
			
			this.levelparser.setResourceName(this.getRandomFileName(this.gameInfo.getDifficulty()));
						
			this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor3);
			
			this.isBoss = (this.gameInfo.getLevelNum() == this.gameInfo.getLevelMax());
			AchievementStatistics.isBoss = true;
							
			this.complexity = this.computeComplexity();
			this.levelDef.setComplexity(this.complexity);
			if (this.isBoss) {
				this.levelDef.buildBossLevel(level);
			} else {
				this.levelDef.buildLevel(level);
			}
									
			level.setTitle(TitleGenerator.generateNewTitle());
			this.levelDef.resetAndSave();
		}
		else
		{
			this.home.buildLevel(level);
			level.setLevelDefinition(this.home);
			level.addGamePlay(null);
		}
	}

	// only survival here
	public String getNext(String paramString)
	{
		String next = null;		
		next = defaultId;		
		
		return next;
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
	public void build(Level level, LevelDefinition levelDefToLoad) {
		if (levelDefToLoad.getGamePlay() == GamePlay.Survival) {
			this.build(level, levelDef.getId(), levelDef.getGamePlay());
		}
		
		if (levelDefToLoad.getGamePlay() == GamePlay.TimeAttack) {
			boolean isTuto = this.isTut(levelDefToLoad);
			AchievementStatistics.isTuto = true;
			level.setLevelDefinition(levelDefToLoad);
			this.resetTotalStar();
			level.setTitle(TitleGenerator.generateNewTitle());
			
			this.gameInfo.forceLevel(levelDefToLoad.getWorld().getDifficulty(levelDefToLoad.getNumber()), levelDefToLoad.getNumber());
			this.isBoss = levelDefToLoad.isBoss();
			AchievementStatistics.isBoss = true;

			if (isTuto || levelDefToLoad.isInvalidated() || !levelDefToLoad.buildLevel(level)) {
				levelDefToLoad.setInvalidated(false);
				this.levelDef.setId(levelDefToLoad.getId());
				this.levelDef.setGamePlay(levelDefToLoad.getGamePlay());
				
				if (isTuto) {
					this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorTutorial);	
					level.setTitle(null);
				} else {
					this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor3);
				}			
				
				if (this.isBoss) {
					this.levelDef.buildBossLevel(level);
				} else {
					this.levelDef.buildLevel(level);
				}
				
				if (levelDefToLoad instanceof LevelDefinitionParser) {
					LevelDefinitionParser parser = (LevelDefinitionParser) levelDefToLoad;					
					parser.storeLevel(level);
				}
			}
		}	
	}
	
	@Override
	public void rebuild(Level level, LevelDefinition levelDef) {
		if (levelDef.getGamePlay() == GamePlay.TimeAttack) {
			this.build(level, levelDef);
		}
		
		if (levelDef.getGamePlay() == GamePlay.Survival) {			
			this.build(level, levelDef.getId(), levelDef.getGamePlay());			
		}
	}
	
	// only time attack
	@Override
	public LevelDefinition getNext(LevelDefinition levelDefinition) {
		WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
		return world.getNext(levelDefinition);
	}
}