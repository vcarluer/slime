package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.LiquidSurface;

public class LiquidSurfaceDef extends ItemDefinition {
	private static String Handled_Def = "LiquidSurface";
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.LiquidSurface.createBL(this.getUName(), this.getX(), this.getY(), this.width, this.height);
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
		this.classHandled.add(LiquidSurface.class);
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
