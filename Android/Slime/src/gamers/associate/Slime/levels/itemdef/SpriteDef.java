package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.custom.SpriteAction;

public class SpriteDef extends ItemDefinition {
	private static String Handled_Def = "Sprite";
	private String plist;
	private String frame;
	private int count;
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
	public void createItem(Level level) {
		if (actionCode != SpriteAction.noActionReserved) {
			SpriteAction action = new SpriteAction(
					this.actionCode, this.actionValue, this.actionValue2, this.actionTime, this.inverse, this.repeat, 
					this.originalDelay, this.resetPosition, this.delayBefore);
			GameItem item = SlimeFactory.Sprite.createBL(this.getX(), this.getY(), this.width, this.height, this.plist, this.frame, this.count, action);
			item.setAngle(this.angle);
			if (this.frame.equals("gestures_tap")) {
				level.setHelpItem(item);
			}			
		} else {
			SlimeFactory.Sprite.createBL(this.getX(), this.getY(), this.width, this.height, this.plist, this.frame, this.count).setAngle(this.angle);
		}		
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.plist = infos[start];
		this.frame = infos[start+1];
		this.count = Integer.parseInt(infos[start+2]);
		if ((infos.length - 1) > (start + 2)) {
			this.actionCode = Integer.valueOf(infos[start+3]);
			this.actionValue = Float.valueOf(infos[start+4]);
			this.actionValue2 = Float.valueOf(ZeroIfNone(infos[start+5]));
			this.actionTime = Float.valueOf(infos[start+6]);
			this.inverse = Boolean.valueOf(infos[start+7]);
			this.repeat = Boolean.valueOf(infos[start+8]);
			this.originalDelay = Float.valueOf(infos[start+9]);
			this.resetPosition = Boolean.valueOf(infos[start+10]);
			this.delayBefore = Float.valueOf(infos[start+11]);
		} else {
			this.actionCode = SpriteAction.noActionReserved;
			this.actionValue = 0;
			this.actionValue2 = 0;
			this.actionTime = 0;
			this.inverse = false;
			this.repeat = false;
			this.originalDelay = 0;
			this.resetPosition = false;
			this.delayBefore = 0;
		}
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(GameItemCocos.class);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.plist);
		line = this.addValue(line, this.frame);
		line = this.addValue(line, String.valueOf(this.count));
		if (this.actionCode != SpriteAction.noActionReserved) {
			line = this.addValue(line, String.valueOf(this.actionCode));
			line = this.addValue(line, String.valueOf(this.actionValue));
			line = this.addValue(line, String.valueOf(this.actionValue2));
			line = this.addValue(line, String.valueOf(this.actionTime));
			line = this.addValue(line, String.valueOf(this.inverse));
			line = this.addValue(line, String.valueOf(this.repeat));
			line = this.addValue(line, String.valueOf(this.originalDelay));
			line = this.addValue(line, String.valueOf(this.resetPosition));
			line = this.addValue(line, String.valueOf(this.delayBefore));
		}

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
		GameItemCocos cocos = (GameItemCocos)item;
		this.plist = cocos.getpList();
		this.frame = cocos.getFrameName();
		this.count = cocos.getFrameCount();
		this.actionCode = SpriteAction.noActionReserved;
		this.actionValue = 0;
		this.actionValue2 = 0;
		this.actionTime = 0;
		this.inverse = false;
		this.repeat = false;
		this.originalDelay = 0;
		this.resetPosition = false;
		this.delayBefore = 0;
		if (cocos.getSpriteAction() != null) {
			if (cocos.getSpriteAction().getActionCode() != SpriteAction.noActionReserved) {
				this.actionCode = cocos.getSpriteAction().getActionCode();
				this.actionValue = cocos.getSpriteAction().getActionValue();
				this.actionValue2 = cocos.getSpriteAction().getActionValue2();
				this.actionTime = cocos.getSpriteAction().getActionTime();
				this.inverse = cocos.getSpriteAction().getInverse();
				this.repeat = cocos.getSpriteAction().getRepeat();
				this.originalDelay = cocos.getSpriteAction().getOriginalDelay();
				this.resetPosition = cocos.getSpriteAction().getResetPosition();
				this.delayBefore = cocos.getSpriteAction().getDelayBefore();
			}			
		}
	}
}
