package gamers.associate.Slime.levels.generator;

import java.io.BufferedWriter;
import java.io.IOException;

import android.util.Log;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;

public class LevelDefinitionGenerator extends LevelDefinition {
	private int complexity;
	
	public LevelDefinitionGenerator() {
		this.gamePlay = GamePlay.ManuallyDefined;
	}
	
	@Override
	protected boolean getNoStore() {
		return false;
	}

	@Override
	public void buildLevel(Level level) {
		SlimeFactory.LevelGenerator.generate(this.getComplexity(), BlocDirection.Left);
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
		Log.d(Slime.TAG, "Complexity set: " + this.complexity);
	}

	@Override
	protected void storeUserInfoNext(BufferedWriter buffWriter) throws IOException {
		super.storeUserInfoNext(buffWriter);
		try {	
			buffWriter.write(String.valueOf(this.complexity));
			buffWriter.newLine();
		} catch (IOException e) {
			Log.e(Slime.TAG, "ERROR during write of complexity " + String.valueOf(this.complexity));
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
	
	
}
