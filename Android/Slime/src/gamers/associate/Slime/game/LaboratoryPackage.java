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

}
