package gamers.associate.SlimeAttack.levels;

import java.util.ArrayList;

import gamers.associate.SlimeAttack.game.Level;

public interface ILevelBuilder {
	void init();
	String getNext(String levelName);
	void build(Level level, String levelName, GamePlay gamePlay);
	ArrayList<LevelDefinition> getNormalLevels();
	void build(Level level, LevelDefinition levelDef);
	void rebuild(Level level, LevelDefinition levelDef);
	int getTotalStar();
	void addStar();
	void resetTotalStar();
	boolean isBoss();
	LevelDefinition getNext(LevelDefinition levelDefinition);
}
