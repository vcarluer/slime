package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;

import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.SpriteType;

public class StarCounter extends GameItemCocos {
	public static String Anim_Start_Empty = "control-stars-start-empty";
	public static String Anim_Start_Green = "control-stars-start-green";
	public static String Anim_Start_Gold = "control-stars-start-yellow";
	
	public static String Anim_Cell_Empty = "control-stars-cell-empty";
	public static String Anim_Cell_Green = "control-stars-cell-green";
	public static String Anim_Cell_Gold = "control-stars-cell-yellow";
	public static String Anim_Cell_Target_Empty = "control-stars-cell-target-empty";
	public static String Anim_Cell_Target_Green = "control-stars-cell-target-green";
	
	public static String Anim_End_Empty = "control-stars-end-empty";
	public static String Anim_End_Green = "control-stars-end-green";
	public static String Anim_End_Gold = "control-stars-end-yellow";
	public static String Anim_End_Target_Empty = "control-stars-end-target-empty";
	public static String Anim_End_Target_Green = "control-stars-end-target-green";
	
	public static float Default_Width = 17f;
	public static float Default_Height = 21f;
	
	private StarCounterType counterType;
	
	public StarCounter(float x, float y, float width, float height, StarCounterType counterType) {
		super(x, y, width, height);
		
		this.spriteType = SpriteType.SINGLE_SCALE;		
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height; 
		}		
		
		this.counterType = counterType;
	}

	@Override
	protected String getReferenceAnimationName() {
		switch (this.counterType) {
		case Cell_Gold:
		case Cell_Green:
			return Anim_Cell_Empty;
		case Cell_Target:
			return Anim_Cell_Target_Empty;
		case End_Gold:
		case End_Green:
			return Anim_End_Empty;
		case End_Target:
			return Anim_End_Target_Empty;
		case Start_Gold:
		case Start_Green:
			return Anim_Start_Empty;
		default:
			return Anim_Cell_Empty;
		}
	}
	
	public void takeStar() {
		if (this.sprite != null) {
			String anim = "";
			switch (this.counterType) {
			case Cell_Gold:
				anim = Anim_Cell_Gold;
				break;
			case Cell_Green:
				anim = Anim_Cell_Green;
				break;
			case Cell_Target:
				anim = Anim_Cell_Target_Green;
				break;
			case End_Gold:
				anim = Anim_End_Gold;
				break;
			case End_Green:
				anim = Anim_End_Green;
				break;
			case End_Target:
				anim = Anim_End_Target_Green;
				break;
			case Start_Gold:
				anim = Anim_Start_Gold;
				break;
			case Start_Green:
				anim = Anim_Start_Green;
				break;
			default:
				anim = Anim_Cell_Green;
				break;
			}			
						
			this.animate(anim);
		}				
	}
	
	public void resetStar() {
		String anim = this.getReferenceAnimationName();
		this.animate(anim);
	}
	
	private void animate(String anim) {
		this.sprite.stopAllActions();
		CCAnimate animate = CCAnimate.action(this.animationList.get(anim), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
	}
}
