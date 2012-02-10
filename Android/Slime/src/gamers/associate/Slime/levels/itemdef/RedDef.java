package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Red;

public class RedDef extends ItemDefinition {
	private static String Handled_Def = "Red"; 
	
	private int life;
	private boolean isBoss;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
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
		Red red = SlimeFactory.Red.createBL(this.getX(), this.getY(), this.width, this.height);
		red.setBoss(true);
		red.setLife(3);
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
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		Red red = (Red)item;
		this.isBoss = red.isBoss();
		this.life = red.getLife();
	}

}
