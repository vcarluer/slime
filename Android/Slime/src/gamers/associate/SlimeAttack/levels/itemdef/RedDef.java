package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.Red;

public class RedDef extends ItemDefinition {
	private static String Handled_Def = "Red";
	public static String Handled_DefMini = "MiniRed";	
	
	private int life;
	private boolean isBoss;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
		this.typesHandled.add(Handled_DefMini);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Red.class);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.isBoss = Boolean.parseBoolean(infos[start]);
		this.life = Integer.parseInt(infos[start + 1]);		
	}

	@Override
	public void createItem(Level level) {
		Red red = SlimeFactory.Red.createBL(this.getUName(), this.getX(), this.getY(), this.width, this.height, this.isBoss);
		if (this.isBoss) {
			red.setLife(Red.BossLife);
		} else {
			red.setLife(this.life);
		}
		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.isBoss));
		line = this.addValue(line, String.valueOf(this.life));
		
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

	@Override
	protected String getItemType(GameItem item) {
		Red red = (Red) item;
		if (red.isBoss()) {
			return Handled_Def;
		} else {
			return Handled_DefMini;
		}		
	}

	@Override
	protected void setValuesNext(GameItem item) {
		Red red = (Red)item;
		this.isBoss = red.isBoss();
		this.life = red.getLife();
	}

}
