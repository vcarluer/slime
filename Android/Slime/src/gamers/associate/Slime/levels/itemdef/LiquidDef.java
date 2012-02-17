package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Liquid;

public class LiquidDef extends ItemDefinition {
	private static String Handled_Def = "Liquid";
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.Liquid.createBL(this.getX(), this.getY(), this.width, this.height);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);	
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// None
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Liquid.class);
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
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {		
	}
}
