package gamers.associate.Slime.game;

import gamers.associate.Slime.layers.EndDifficultyGameLayer;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorTutorial;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

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
	private boolean firstBuild;		
	
	public LevelBuilderGenerator() {				
		this.levelparser.setLocalStorage(true);		
		this.firstBuild = true;
	}	
	
	private String getRandomFileName(int difficulty) {
		return defaultId + String.valueOf(difficulty) + fileExtension;
	}
	
	public void build(Level level, String id, GamePlay gamePlay)
	{
		// always default id... Used for reset all
		this.levelDef.setId(defaultId);
		
		if (id != LevelHome.Id)
		{			
			this.levelDef.setGamePlay(gamePlay);
			level.setLevelDefinition(this.levelDef);			
			
			if (this.levelDef.getGamePlay() == GamePlay.TimeAttack) {
				this.gameInfo.setLevel(Integer.valueOf(id));
			}
			
			this.isBoss = (this.gameInfo.getLevelNum() == this.gameInfo.getLevelMax());
			this.resetTotalStar();
			// Re-read pre-generated level
			if (!this.gameInfo.isSurvivalGameOver() && this.firstBuild && this.levelparser.isStored(this.getRandomFileName(this.gameInfo.getDifficulty())) && !this.levelDef.isFinished()) {				
				this.levelparser.setResourceName(this.getRandomFileName(this.gameInfo.getDifficulty()));
				this.levelparser.buildLevel(level);
				if (this.levelDef.getGamePlay() == GamePlay.TimeAttack && this.isTut()) {
					SlimeFactory.LevelGeneratorTutorial.setTitle();
				}

				level.setLevelDefinition(this.levelDef);
			} else {
				if (!isDebug) {
					this.gameInfo.levelUp();
				} else {
					this.gameInfo.forceLevel(forceDiff, forceLevel);					
				}
				
				this.levelparser.setResourceName(this.getRandomFileName(this.gameInfo.getDifficulty()));
				
				if (this.levelDef.getGamePlay() == GamePlay.TimeAttack) {
					if (this.isTut()) {
						this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorTutorial);
					} else {
						int lvl = SlimeFactory.GameInfo.getLevelNum();
						if (SlimeFactory.GameInfo.getDifficulty() == LevelDifficulty.Easy) {
							lvl -=  LevelGraphGeneratorTutorial.tutorialCount;
						}
						
						if (lvl % 4 == 0) {
							// this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorRectangle2);
							this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor3);
						} else {
							this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor3);
						}					
					}
				}
				
				if (this.levelDef.getGamePlay() == GamePlay.Survival) {
					this.gameInfo.setSurvivalGameOver(false);
					this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor3);
				}
				
				this.isBoss = (this.gameInfo.getLevelNum() == this.gameInfo.getLevelMax());
								
				this.complexity = this.computeComplexity();
				this.levelDef.setComplexity(this.complexity);
				if (this.isBoss) {
					this.levelDef.buildBossLevel(level);
				} else {
					this.levelDef.buildLevel(level);
				}
				
				if (this.levelDef.getGamePlay() == GamePlay.Survival) {
					this.levelparser.storeLevel(level);
				}
				
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
		String next = null;
		if (this.levelDef.getGamePlay() == GamePlay.Survival) {
			next = defaultId;
		}
		
		if (this.levelDef.getGamePlay() == GamePlay.TimeAttack) {
			if (this.gameInfo.getLevelNum() < this.gameInfo.getLevelMax()) {
				next = String.valueOf(this.gameInfo.getLevelNum());
			} else {
				this.gameInfo.endDifficulty();
				CCTransitionScene transition = CCFadeTransition.transition(0.5f, EndDifficultyGameLayer.getScene());
				CCDirector.sharedDirector().replaceScene(transition);
			}
		}
		
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
//		this.build(level, levelDef.getId(), levelDef.getGamePlay());
		if (!this.levelDef.buildLevel(level)) {
			this.levelDef.setId(levelDefToLoad.getId());
			this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorCorridor3);
			this.gameInfo.forceLevel(LevelDifficulty.Easy, levelDefToLoad.getNumber());
			this.isBoss = levelDefToLoad.isBoss();
			
			if (this.isBoss) {
				this.levelDef.buildBossLevel(level);
			} else {
				this.levelDef.buildLevel(level);
			}
		}
	}
	
	@Override
	public void rebuild(Level level, LevelDefinition levelDef) {		
		this.resetTotalStar();
		if (this.levelparser.isStored()) {
			this.levelparser.buildLevel(level);			
		} else {
			this.build(level, levelDef.getId(), levelDef.getGamePlay());			
		}
	}
	
	public void start() {
		this.firstBuild = true;
		Level.get(LevelBuilderGenerator.defaultId, true, this.levelDef.getGamePlay());		
	}
	
	public void setFirstBuild(boolean firstBuild) {
		this.firstBuild = firstBuild;
	}
}