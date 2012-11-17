package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SurvivalGame;
import gamers.associate.Slime.items.base.GameItem;

public class SurvivalDef extends ItemDefinition {
	public static String Handled_Survival = "Survival";
		
	@Override
	public void createItem(Level level) {
		if (level.getGamePlay() == null) {
			SurvivalGame sGame = SurvivalGame.NewGame();
			level.addGamePlay(sGame);
		}				
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Survival);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(SurvivalGame.class);
		
	}

	@Override
	protected String writeNext(String line) {
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Survival;
	}

	@Override
	protected void setValuesNext(GameItem item) {
	}

}
