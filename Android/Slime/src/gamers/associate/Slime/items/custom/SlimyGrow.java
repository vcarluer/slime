package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;

import java.util.ArrayList;

import org.cocos2d.actions.interval.CCScaleTo;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class SlimyGrow extends Slimy{
	public static int Max_Grow = 3;
	
	protected int counter;
	protected boolean isEaten;
	protected int targetGrowDif;
	
	public SlimyGrow(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		counter = 1;
	}

	/**
	 * @return the counter
	 */
	public int getCounter() {
		return this.counter;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	@Override
	protected void contactInternal(ContactInfo item) {
		if (item.getContactWith() instanceof SlimyGrow) {
			if (!isEaten) {				
				SlimyGrow kSlimy = (SlimyGrow)item.getContactWith();
				if (this.counter >= kSlimy.getCounter()) {					
					this.eat(kSlimy);
				}
			}			
		}
		else {
			this.land();
		}
	}
	
	public void eat(SlimyGrow slimy) {
		if (!slimy.getIsEaten()) {
			int maxEat = Max_Grow - this.counter;
			int eatCount = slimy.getCounter();
			if (eatCount > maxEat) {
				eatCount = maxEat;
			}
			
			this.targetGrowDif = eatCount;
			slimy.eatIt(eatCount);
		}
	}
	
	public void eatIt(int count) {
		this.targetGrowDif = - count;				
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.custom.Slimy#render(float)
	 */
	@Override
	public void render(float delta) {
		if (targetGrowDif != 0) {
			this.counter += this.targetGrowDif;
			this.targetGrowDif = 0;
			if (this.counter > 0) {
				float scaleValue = (float) Math.sqrt(this.counter); 
				this.scale(scaleValue);
			}
		}
		
		if (this.counter <= 0) {
			this.isEaten = true;
			this.kill();
			Level.currentLevel.addItemToRemove(this);
		}
					
		super.render(delta);
	}
	
	public boolean getIsEaten() {
		return this.isEaten;
	}
	
	public void setTargetGrowDif(int grow) {
		this.targetGrowDif = grow;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.custom.Slimy#kill()
	 */
	@Override
	protected void kill() {		
		this.scale(1);		
		super.kill();
	}
	
	protected void scale(float scaleValue) {
		CCScaleTo scale = CCScaleTo.action(0.5f, scaleValue);
		this.sprite.runAction(scale);
		this.scaleBodyTo(scaleValue);
	}
	
	protected void scaleBodyTo(float scale) {
		this.width = Default_Width * scale;
		this.height = Default_Height * scale;
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;
		
		ArrayList<Fixture> fixtureToRemove = new ArrayList<Fixture>();
		for(Fixture def : this.body.getFixtureList()) {
			fixtureToRemove.add(def);
		}
		
		for(Fixture fixture : fixtureToRemove) {
			this.body.destroyFixture(fixture);
		}
		
		synchronized (world) {
			this.createFixture();
		}	
	}
}
