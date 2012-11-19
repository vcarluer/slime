package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;

import java.io.BufferedWriter;
import java.io.IOException;

public class LevelDefinitionGenerator extends LevelDefinition {
	private int complexity;
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
