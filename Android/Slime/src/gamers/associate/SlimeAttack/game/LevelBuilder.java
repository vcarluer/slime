package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.ITimeAttackLevel;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelHome;

import java.util.ArrayList;
import java.util.HashMap;


public class LevelBuilder extends AbstractLevelBuilder {
	public static String LevelSelection = "LevelMenu";
	public static String LevelExtension =".slime";
	private static HashMap<String, LevelDefinition> levels;
	private static HashMap<String, String> levelsChain;
	private static LevelDefinition previousDef;
	
	public void init() {
		levels = new HashMap<String, LevelDefinition>();
		levelsChain = new HashMap<String, String>();
		add(new LevelHome());
		// Levels are queued in order of add calls
		// add(new LevelBeta());		
				
		// Bad menu is more level
		// add(new LevelDefinitionParser("Level0.slime", context));
		// add(new LevelDefinitionParser("LevelPoly.slime", context));
		// add(new LevelDefinitionParser("LevelSaw.slime", context));
		
		// add(new LevelDefinitionParser(LevelSelection + LevelExtension));
		
		// add(new LevelDefinitionParser("LevelTest.slime"));
		// add(new LevelDefinitionParser("Level1.slime"));
	}
	
	private void add(LevelDefinition levelDef) {
		if (levelDef != null) {			
			levels.put(levelDef.getId(), levelDef);
			if (!levelDef.isSpecial()) {
				if (previousDef != null) {				
					levelsChain.put(previousDef.getId(), levelDef.getId());				
				}
				
				previousDef = levelDef;
			}
		}
	}
	
	public String getNext(String levelName) {
		if (levelsChain.containsKey(levelName)) {
			return levelsChain.get(levelName);
		}
		else {
			return null;
		}
	}
	
	public void build(Level level, String levelName, GamePlay gamePlay) {
		LevelDefinition levelDef = levels.get(levelName);
		levelDef.setGamePlay(gamePlay);
		build(level, levelDef);
	}
	
	public void build(Level level, LevelDefinition levelDef) {
		if (levelDef != null) {
			levelDef.buildLevel(level);
			level.setLevelDefinition(levelDef);
			
			// For hard coded level, gameplay is added here and not in LevelDef
			if (levelDef.getGamePlay() != GamePlay.ManuallyDefined) {
				switch(levelDef.getGamePlay()) {
				case TimeAttack:
					TimeAttackGame taGame = TimeAttackGame.NewGame();
					level.addGamePlay(taGame);
					ITimeAttackLevel taLevel = (ITimeAttackLevel) levelDef;
					if (taLevel.getLevelTime() != 0) {
						taGame.setStartTime(taLevel.getLevelTime());
					}
									
					if (taLevel.getLevelCriticTime() != 0) {
						taGame.setCriticTime(taLevel.getLevelCriticTime());
					}
					break;
				case Survival:
					SurvivalGame sGame = SurvivalGame.NewGame();
					level.addGamePlay(sGame);
					break;
				default:
					level.addGamePlay(null);
					break;
				}
			}
		}
	}
	
	public ArrayList<LevelDefinition> getNormalLevels() {
		ArrayList<LevelDefinition> gameLevels = new ArrayList<LevelDefinition>();
		for(LevelDefinition levelDef : levels.values()) {
			if (!levelDef.isSpecial()) {
				gameLevels.add(levelDef);
			}
		}
		
		return gameLevels;
	}

	@Override
	public void rebuild(Level level, LevelDefinition levelDef) {
		this.build(level, levelDef);
	}

	@Override
	public LevelDefinition getNext(LevelDefinition levelDefinition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTutorial() {
		// TODO Auto-generated method stub
		return false;
	}
}
