package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Button;

public class ButtonDef extends ItemDefinition {
	private static String Handled_Def = "Button";
	
	private String target;
	private float resetTime;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.Button.createBL(this.getX(), this.getY(), this.width, this.height, this.target, this.resetTime).setAngle(this.angle);
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

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Button.class);		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.target));
		line = this.addValue(line, String.valueOf(this.resetTime));
		
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
		Button but = (Button)item;
		this.target = but.getTarget();
		this.resetTime = but.getResetTime();
	}
}
