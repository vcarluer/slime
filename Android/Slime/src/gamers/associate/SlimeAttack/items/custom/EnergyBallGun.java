package gamers.associate.SlimeAttack.items.custom;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.types.CGPoint;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.ITrigerable;

import com.badlogic.gdx.physics.box2d.World;

public class EnergyBallGun extends LaserGun {
	public static float Default_BallSpeed = 50f;
	public static float Default_WaitTime = 2f;
	
	private List<ITrigerable> targets;
	
	private float ballSpeed; // px per sec
	private float waitTime;
	
	private float nextShoot;
	
	public EnergyBallGun(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.ballSpeed = Default_BallSpeed;
		this.waitTime = Default_WaitTime;
	}

	private void createTargetList()	 {
		if (this.targets == null) {
			this.targets = new ArrayList<ITrigerable>();
			if (this.getTarget() != null && this.getTarget() != "") {
				this.targets = Level.currentLevel.getTrigerables(this.getTarget());
			}
		}		
	}
	
	@Override
	public void render(float delta) {
		this.createTargetList();
		if (this.nextShoot > 0) {
			this.nextShoot -= delta;
		}
		
		if (this.isOn()) {
			if (this.nextShoot <= 0) {
				for(ITrigerable target : this.targets) {
					CGPoint sourcePoint = this.getSourcePoint();
					EnergyBall ball = SlimeFactory.EnergyBall.create(sourcePoint.x, sourcePoint.y);
					ball.moveTo(target.getPosition(), this.ballSpeed);												
				}
				
				this.nextShoot = this.waitTime;
			}
		}
	}	

	public float getBallSpeed() {
		return ballSpeed;
	}

	public void setBallSpeed(float ballSpeed) {
		if (ballSpeed > 0) {
			this.ballSpeed = ballSpeed;
		} else {
			this.ballSpeed = Default_BallSpeed;
		}
		
	}

	public float getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(float waitTime) {
		if (waitTime > 0) {
			this.waitTime = waitTime;
		} else {
			this.waitTime = Default_WaitTime;
		}
		
	}
}
