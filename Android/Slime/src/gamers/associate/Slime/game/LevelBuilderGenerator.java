package gamers.associate.Slime.game;

import gamers.associate.Slime.layers.EndDifficultyGameLayer;
import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.LevelDefinitionGenerator;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

public class LevelBuilderGenerator implements ILevelBuilder
{
	private static boolean isDebug =  false;
	private static int forceDiff = 8;
	private static int forceLevel = 80;
	
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
				this.levelDef.setLevelGenerator(SlimeFactory.LevelGeneratorRectangle2);
				if (!isDebug) {
					this.gameInfo.levelUp();
				} else {
					this.gameInfo.forceLevel(forceDiff, forceLevel);
				}
				
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
		String next = null;
		if (this.gameInfo.getLevelNum() < this.gameInfo.getLevelMax()) {
			next = defaultId;
		} else {
			this.gameInfo.endDifficulty();
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, EndDifficultyGameLayer.getScene());
			CCDirector.sharedDirector().replaceScene(transition);
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