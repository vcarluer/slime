package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.items.base.GameItem;

public class TimeAttackDef extends ItemDefinition {
	private static String Handled_TimeAttack = "TimeAttack";
	
	private int levelTime;
	private int criticTime;
	
	@Override
	public void createItem(Level level) {
		TimeAttackGame taGame = TimeAttackGame.NewGame();
		level.addGamePlay(taGame);
		taGame.setStartTime(this.levelTime);
		taGame.setCriticTime(this.criticTime);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_TimeAttack);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.levelTime = Integer.valueOf(infos[start]).intValue();
		if (infos.length > 5) {
			this.criticTime = Integer.valueOf(infos[start + 1]).intValue();
		}
		else {
			this.criticTime = 0;
		}
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(TimeAttackGame.class);
		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.levelTime));
		line = this.addValue(line, String.valueOf(this.criticTime));
		
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_TimeAttack;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		TimeAttackGame game = (TimeAttackGame)item;
		this.levelTime = (int) game.getStartTime();
		this.criticTime = (int) game.getCriticTime();
	}

}
