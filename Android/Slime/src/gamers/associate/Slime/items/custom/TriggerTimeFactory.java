package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemFactory;

public class TriggerTimeFactory extends GameItemFactory<TriggerTime> {
	private String target;
	private float interval;
	
	@Override
	protected TriggerTime instantiate(float x, float y, float width,
			float height) {
		return new TriggerTime(x, y, width, height, this.target, this.interval);
	}
	
	public TriggerTime create(String name, String target, float interval) {
		this.target = target;
		this.interval = interval;
		return this.create();
	}
}
