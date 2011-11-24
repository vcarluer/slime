package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class TargetDef extends ItemDefinition {
	private static String Handled_Def = "Target";
	private String name;
	@Override
	public void createItem(Level level) {
		SlimeFactory.Target.createBL(this.x, this.y, this.width, this.height, this.name);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.name = infos[start];
	}

}