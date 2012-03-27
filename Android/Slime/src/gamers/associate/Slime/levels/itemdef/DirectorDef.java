package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Director;
import gamers.associate.Slime.items.custom.SpriteAction;

public class DirectorDef extends ItemDefinition {
	public static String Handled_Def = "Director";
	private String target;
	private int actionCode;
	private float actionValue;
	private float actionValue2;
	private float actionTime;
	private boolean inverse;
	private boolean repeat;	
	private float originalDelay;
	private boolean resetPosition;
	private float delayBefore;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Director.class);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.target = infos[start];
		this.actionCode = Integer.valueOf(infos[start+1]);
		this.actionValue = Float.valueOf(infos[start+2]);
		this.actionValue2 = Float.valueOf(ZeroIfNone(infos[start+3]));
		this.actionTime = Float.valueOf(infos[start+4]);
		this.inverse = Boolean.valueOf(infos[start+5]);
		this.repeat = Boolean.valueOf(infos[start+6]);
		this.originalDelay = Float.valueOf(infos[start+7]);
		this.resetPosition = Boolean.valueOf(infos[start+8]);
		this.delayBefore = Float.valueOf(infos[start+9]);
	}

	@Override
	public void createItem(Level level) {
		SpriteAction action = new SpriteAction(
				this.actionCode, this.actionValue, this.actionValue2, this.actionTime, this.inverse, this.repeat, 
				this.originalDelay, this.resetPosition, this.delayBefore);
		SlimeFactory.Director.create(this.getUName(), this.getUString(this.target), action);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.target);
		line = this.addValue(line, String.valueOf(this.actionCode));
		line = this.addValue(line, String.valueOf(this.actionValue));
		line = this.addValue(line, String.valueOf(this.actionValue2));
		line = this.addValue(line, String.valueOf(this.actionTime));
		line = this.addValue(line, String.valueOf(this.inverse));
		line = this.addValue(line, String.valueOf(this.repeat));
		line = this.addValue(line, String.valueOf(this.originalDelay));
		line = this.addValue(line, String.valueOf(this.resetPosition));
		line = this.addValue(line, String.valueOf(this.delayBefore));

		return line;
	}

	@Override
	protected boolean getIsBL() {
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		Director director = (Director) item;
		this.target = director.getTarget();
		this.actionCode = director.getAction().getActionCode();
		this.actionValue = director.getAction().getActionValue();
		this.actionValue2 = director.getAction().getActionValue2();
		this.actionTime = director.getAction().getActionTime();
		this.inverse = director.getAction().getInverse();
		this.repeat = director.getAction().getRepeat();
		this.originalDelay = director.getAction().getOriginalDelay();
		this.resetPosition = director.getAction().getResetPosition();
		this.delayBefore = director.getAction().getDelayBefore();
	}
}
