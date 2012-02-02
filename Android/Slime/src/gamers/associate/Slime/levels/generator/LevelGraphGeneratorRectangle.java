package gamers.associate.Slime.levels.generator;


public class LevelGraphGeneratorRectangle extends LevelGraphGeneratorBase {
	private static final String BlocsAssetsBase = "blocsRectangle";
	
	@Override
	protected void generateInternal(int maxComplexity,
			BlocDirection constrained, boolean isBoss) {
		// For now only to right
		// pull by line
		
	}

	@Override
	public String getAssetsBase() {
		return BlocsAssetsBase;
	}
}
