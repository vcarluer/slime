package gamers.associate.Slime.game;

public class MexicoPackage extends WorldPackage {

	private static final String MEXICO = "Mexico";
	private static final String BKG_MEXICO_POSTCARD_PNG = "bkg/mexico-postcard.png";

	public MexicoPackage() {
		this.setLock(true);
	}

	@Override
	protected void defineBackgroundPath() {
		this.setBackgroundPath(BKG_MEXICO_POSTCARD_PNG);
	}

	@Override
	protected void defineName() {
		this.setName(MEXICO);
	}

	@Override
	protected void defineLevelCount() {
		this.setLevelCount(0);
	}

	@Override
	protected int getDifficulty(int lvlNumber) {
		return LevelDifficulty.Easy;
	}

}
