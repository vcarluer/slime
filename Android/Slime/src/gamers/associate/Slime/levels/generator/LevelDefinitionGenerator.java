package gamers.associate.Slime.levels.generator;

import java.io.BufferedWriter;
import java.io.IOException;

import org.cocos2d.nodes.CCDirector;

import android.util.Log;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;

public class LevelDefinitionGenerator extends LevelDefinition {
	private static int DefaultBoss = 20;
	private int complexity;
	private int bossComplexity = DefaultBoss;
	private LevelGraphGeneratorBase currentGenerator;
	
	public LevelDefinitionGenerator() {
		this.gamePlay = GamePlay.ManuallyDefined;
	}
	
	@Override
	protected boolean getNoStore() {
		return false;
	}
	
	public void setLevelGenerator(LevelGraphGeneratorBase generator) {
		this.currentGenerator = generator;
	}

	@Override
	public void buildLevel(Level level) {
		if (this.currentGenerator != null) {
			this.currentGenerator.generate(this.getComplexity(), BlocDirection.Left, this.getGamePlay());
			this.postBuild(level);
		}
	}
	
	private void postBuild(Level level) {
		float w = CCDirector.sharedDirector().winSize().width / 2;
		float h = CCDirector.sharedDirector().winSize().height / 2;
		
		// Complete level here
		
	}

	public void buildBossLevel(Level level) {
		if (this.currentGenerator != null) {
			this.currentGenerator.generate(this.getComplexity(), BlocDirection.Left, true, this.getGamePlay());
			this.postBuild(level);
		}
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
		SlimeFactory.Log.d(Slime.TAG, "Complexity set: " + this.complexity);
	}

	@Override
	protected void storeUserInfoNext(BufferedWriter buffWriter) throws IOException {
		super.storeUserInfoNext(buffWriter);
		try {	
			buffWriter.write(String.valueOf(this.complexity));
			buffWriter.newLine();
		} catch (IOException e) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR during write of complexity " + String.valueOf(this.complexity));
			throw e;
		}
	}

	@Override
	protected void loadUserInfoNext(String line, int idx) {
		super.loadUserInfoNext(line, idx);
		if (idx == 5) {
			this.complexity = Integer.valueOf(line).intValue();
		}
	}

	@Override
	protected void resetUserInfoNext() {
		// No reset of complexity! except reset of game.
	}

	@Override
	protected void resetAllNext() {		
		super.resetAllNext();
		this.complexity = 0;
	}
}
