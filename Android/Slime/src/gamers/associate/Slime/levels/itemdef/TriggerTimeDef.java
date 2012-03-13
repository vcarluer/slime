package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.TriggerTime;

public class TriggerTimeDef extends ItemDefinition {
	private static String Handled_Def = "Trigger_Time";
	private String target;
	private float interval;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(TriggerTime.class);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.target = infos[start];
		this.interval = Float.valueOf(infos[start+1]).floatValue();
	}

	@Override
	public void createItem(Level level) {
		SlimeFactory.TriggerTime.create(this.getIdPre() + this.target, this.interval);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.target);
		line = this.addValue(line, String.valueOf(this.interval));
		return line;
	}

	@Override
	protected boolean getIsBL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		TriggerTime trigger = (TriggerTime) item;
		this.target = trigger.getTarget();
		this.interval = trigger.getInterval();
	}

}
