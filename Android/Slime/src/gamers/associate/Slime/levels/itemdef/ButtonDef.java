package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class ButtonDef extends ItemDefinition {
	private static String Handled_Def = "Button";
	
	private String target;
	private float resetTime;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.Button.createBL(this.x, this.y, this.width, this.height, this.target, this.resetTime);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.target = infos[start];
		this.resetTime = Float.valueOf(infos[start + 1]).floatValue();
	}

}
