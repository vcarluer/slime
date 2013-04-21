package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.achievements.AchievementStatistics;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelDefinitionParser;
import gamers.associate.SlimeAttack.levels.LevelHome;
import gamers.associate.SlimeAttack.levels.generator.LevelDefinitionGenerator;

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
	private boolean isTutorial;
	
	public LevelBuilderGenerator() {				
		this.levelparser.setLocalStorage(true);		
	}	
	
	private String getRandomFileName(int difficulty) {
		return defaultId + String.valueOf(difficulty) + fileExtension;
	}
		
	public void build(Level level, String id, GamePlay gamePlay)
	{
		SlimeFactory.Log.d(SlimeAttack.TAG, "build(Level level, String id, GamePlay gamePlay) start");
		// this one is only call for survival and home! Bad design powa. Juste ensure it here
		if (gamePlay == GamePlay.TimeAttack) return;

		SlimeFactory.PathFinder.reset();
		// always default id... Used for reset all
		this.levelDef.setId(defaultId);
		
		if (id != LevelHome.Id)
		{			
			this.levelDef.setGamePlay(gamePlay);
			level.setLevelDefinition(this.levelDef);			
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
			AchievementStatistics.isBoss = this.isBoss;
							
			this.complexity = this.computeComplexity();
			this.levelDef.setComplexity(this.complexity);
			if (this.isBoss) {
				this.levelDef.buildBossLevel(level);
			} else {
				this.levelDef.buildLevel(level);
			}
			
			AchievementStatistics.neededBonus = Level.currentLevel.getGamePlay().neededBonus();
									
			level.setTitle(TitleGenerator.generateNewTitle());
			this.levelDef.resetAndSave();
		}
		else
		{
			this.home.buildLevel(level);
			level.setLevelDefinition(this.home);
			level.addGamePlay(null);
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "build(Level level, String id, GamePlay gamePlay) start");
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
		SlimeFactory.Log.d(SlimeAttack.TAG, "build(Level level, LevelDefinition levelDefToLoad) start");
		if (levelDefToLoad.getGamePlay() == GamePlay.Survival) {
			this.build(level, levelDef.getId(), levelDef.getGamePlay());
		}
		
		if (levelDefToLoad.getGamePlay() == GamePlay.TimeAttack) {
			SlimeFactory.PathFinder.reset();
			this.isTutorial = this.isTut(levelDefToLoad);
			AchievementStatistics.isTuto = this.isTutorial;
			level.setLevelDefinition(levelDefToLoad);
			this.resetTotalStar();
			level.setTitle(TitleGenerator.generateNewTitle());
			
			this.gameInfo.forceLevel(levelDefToLoad.getDifficulty(), levelDefToLoad.getNumber());
			this.isBoss = levelDefToLoad.isBoss();
			AchievementStatistics.isBoss = this.isBoss;

			if (this.isTutorial || levelDefToLoad.isInvalidated() || !levelDefToLoad.buildLevel(level)) {
				levelDefToLoad.setInvalidated(false);
				this.levelDef.setId(levelDefToLoad.getId());
				this.levelDef.setGamePlay(levelDefToLoad.getGamePlay());
				
				if (this.isTutorial) {
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
				
				if ((!this.isTutorial || SlimeFactory.IsLevelSelectionOn) && levelDefToLoad instanceof LevelDefinitionParser) {
					LevelDefinitionParser parser = (LevelDefinitionParser) levelDefToLoad;					
					if (SlimeFactory.IsLevelSelectionOn) {
						parser.setLocalStorage(false);
					}
					parser.storeLevel(level);
				}
			}
			
			AchievementStatistics.neededBonus = Level.currentLevel.getGamePlay().neededBonus();
		}	
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "build(Level level, LevelDefinition levelDefToLoad) end");
	}
	
	@Override
	public void rebuild(Level level, LevelDefinition levelDef) {
		if (levelDef.getGamePlay() == GamePlay.TimeAttack) {
			this.build(level, levelDef);
		}
		
		if (levelDef.getGamePlay() == GamePlay.Survival) {			
			this.build(level, levelDef.getId(), levelDef.getGamePlay());			
		}
		
		level.getGamePlay().setTravelingDone(true);
	}
	
	// only time attack
	@Override
	public LevelDefinition getNext(LevelDefinition levelDefinition) {
		WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
		return world.getNext(levelDefinition);
	}

	@Override
	public boolean isTutorial() {
		return this.isTutorial;
	}
}