package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.ITrigerable;

public class TriggerTime extends GameItem {

	private static final int IntervalNotComputed = -1;
	private String target;
	private float interval;
	private float deltaSum;
	private float intervalNormalize;

	public TriggerTime(float x, float y, float width, float height, String target, float interval) {
		super(x, y, width, height);
		this.target = target;
		this.interval = interval;
		this.deltaSum = 0f;
		this.intervalNormalize = IntervalNotComputed;
	}

	@Override
	public void render(float delta) {		
		super.render(delta);
		if (this.target != null) {
			this.deltaSum += delta;
			if (this.deltaSum > this.getIntervalNormalize()) {
				for (ITrigerable trigerable : Level.currentLevel.getTrigerables(this.target)) {
					trigerable.trigger(this, "");
				}
				
				this.deltaSum = 0f;
			}
		}		
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public float getInterval() {
		return this.interval;
	}
	
	private float getIntervalNormalize() {
		if (this.intervalNormalize == IntervalNotComputed) {
			this.intervalNormalize = this.interval * Level.currentLevel.getNormalTimeRatio();
		}
		
		return this.intervalNormalize;
	}
}
