package gamers.associate.Slime.game;


public class LaboratoryPackage extends WorldPackage {
	private static final String BKG_MOON_POSTCARD_PNG = "bkg/moon-postcard.png";
	private static final String LABORATORY = "Laboratory";
	private static final int levelCount = 80;

	@Override
	protected void defineName() {
		this.setName(LABORATORY);
	}

	@Override
	protected void defineLevelCount() {
		this.setLevelCount(levelCount);
	}

	@Override
	protected void defineBackgroundPath() {
		this.setBackgroundPath(BKG_MOON_POSTCARD_PNG);
	}

	@Override
	protected int getDifficulty(int lvlNumber) {
		int diff = (int) Math.ceil(4 * lvlNumber / levelCount);
		switch(diff) {
			default:
			case 1:
				return LevelDifficulty.Easy;
			case 2:
				return LevelDifficulty.Normal;
			case 3:
				return LevelDifficulty.Hard;
			case 4:
				return LevelDifficulty.Extrem;
		}
	}

}
