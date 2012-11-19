package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Target;

public class TargetDef extends ItemDefinition {
	private static String Handled_Def = "Target";
	@Override
	public void createItem(Level level) {
		SlimeFactory.Target.createBL(this.getUName(), this.getX(), this.getY(), this.width, this.height);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Target.class);		
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
