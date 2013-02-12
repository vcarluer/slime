package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemCocos;
import gamers.associate.SlimeAttack.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;

public class StarCounter extends GameItemCocos {
	public static String Anim_OneCell_Target_Empty = "control-stars-onecell-empty";
	public static String Anim_OneCell_Target_Green = "control-stars-onecell-green";
	
	public static String Anim_Start_Empty = "control-stars-start-empty";
	public static String Anim_Start_Green = "control-stars-start-green";
	public static String Anim_Start_Gold = "control-stars-start-yellow";
	public static String Anim_Start_Target_Empty = "control-stars-start-target-empty";
	public static String Anim_Start_Target_Green = "control-stars-start-target-green";
	
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
	
	public static float Default_Width_OneCell = 27f;
	public static float Default_Height_OneCell = 21f;
	
	private StarCounterType counterType;
	
	public StarCounter(float x, float y, float width, float height, StarCounterType counterType) {
		super(x, y, width, height);
		
		this.counterType = counterType;
		this.spriteType = SpriteType.SINGLE_SCALE;		
		
		if (width == 0 && height == 0) {
			if (this.counterType == StarCounterType.OneCell_Target) {
				
			} else {
				this.width = Default_Width_OneCell;
				this.height = Default_Height_OneCell;
			}			
		}						
	}
	
	public static float getOneCellWidthRatio() {
		return Default_Width_OneCell / Default_Width;
	}
	
	public static float getOneCellHeightRation() {
		return Default_Height_OneCell / Default_Height;
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
		case Start_Target:
			return Anim_Start_Target_Empty;
		case OneCell_Target:
			return Anim_OneCell_Target_Empty;
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
			case Start_Target:
				anim = Anim_Start_Target_Green;
				break;
			case OneCell_Target:
				anim = Anim_OneCell_Target_Green;
				break;
			default:
				anim = Anim_Cell_Green;
				break;
			}			
						
			this.animate(anim);
			this.animSup();
		}				
	}
	
	public void animSup() {
		CCScaleBy sb = CCScaleBy.action(0.2f, 1.3f);		
		CCSequence seq = CCSequence.actions(sb, sb.reverse());
		this.sprite.runAction(seq);
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
