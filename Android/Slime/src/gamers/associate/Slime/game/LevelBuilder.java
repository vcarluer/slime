package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.ITimeAttackLevel;
import gamers.associate.Slime.levels.Level11;
import gamers.associate.Slime.levels.LevelBeta;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;
import gamers.associate.Slime.levels.LevelHome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;


public class LevelBuilder implements ILevelBuilder {
	private static HashMap<String, LevelDefinition> levels;
	private static HashMap<String, String> levelsChain;
	private static LevelDefinition previousDef;
	
	public void init() {
		levels = new HashMap<String, LevelDefinition>();
		levelsChain = new HashMap<String, String>();
		
		add(new LevelHome());
		// Levels are queued in order of add calls
		add(new LevelBeta());
		add(new Level11());
		
		Context context = SlimeFactory.ContextActivity;	
		add(new LevelDefinitionParser("test.slime", context));
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
	
	public void build(Level level, String levelName) {
		LevelDefinition levelDef = levels.get(levelName);
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
}
