package gamers.associate.Slime.levels;

import java.util.ArrayList;

import gamers.associate.Slime.game.Level;

public interface ILevelBuilder {
	void init();
	String getNext(String levelName);
	void build(Level level, String levelName, GamePlay gamePlay);
	ArrayList<LevelDefinition> getNormalLevels();
	void build(Level level, LevelDefinition levelDef);
	void rebuild(Level level, LevelDefinition levelDef);
	void start();
	int getTotalStar();
	void addStar();
	void resetTotalStar();
	boolean isBoss();
	void setFirstBuild(boolean firstBuild);
}
