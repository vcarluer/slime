package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;

import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.items.base.GameItemCocos;

public class SlimySuccess extends GameItemCocos {
	public static String Anim_Afro = "afro";
	public static String Anim_Cosmonaut = "cosmonaut";
	public static String Anim_Hawaiian = "hawaiian";
	public static String Anim_Sombrero = "sombrero";
	public final static int STYLE_AFRO = 0;
	public final static int STYLE_COSMONAUT = 1;
	public final static int STYLE_HAWAIIAN = 2;
	public final static int STYLE_SOMBRERO = 3;
	
	private CCAction currentAction;
	
	public SlimySuccess(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void success(int slimeStyle) {
		String animName = getAnimationNameByStyle(slimeStyle);
		
		CCAnimate animate = CCAnimate.action(this.animationList.get(animName), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		
		if (this.currentAction != null) {
			this.sprite.stopAction(this.currentAction);
		}
		this.currentAction = repeat;
		this.sprite.runAction(this.currentAction);
	}
	
	private static String getAnimationNameByStyle(int slimeStyle) {
		String animName = "";		
		switch(slimeStyle) {
		case STYLE_AFRO:
			animName = Anim_Afro;
			break;
		case STYLE_COSMONAUT:
			animName = Anim_Cosmonaut;
			break;
		case STYLE_HAWAIIAN:
			animName = Anim_Hawaiian;
			break;
		case STYLE_SOMBRERO:
		default:
			animName = Anim_Sombrero;
			break;
		}
		
		return animName;
	}
	
	private static int getStyleByDifficulty(int difficulty) {
		int style = 0;
		switch (difficulty) {
		case LevelDifficulty.Normal:
			style = STYLE_COSMONAUT;
			break;
		case LevelDifficulty.Hard:
			style = STYLE_AFRO;
			break;
		case LevelDifficulty.Extrem:
			style = STYLE_HAWAIIAN;
			break;
		case LevelDifficulty.Easy:
		default:
			style = STYLE_SOMBRERO;
			break;
		}
		
		return style;
	}
	
	public static String getAnimationName(int difficulty) {
		return getAnimationNameByStyle(getStyleByDifficulty(difficulty));
	}

	@Override
	protected String getReferenceAnimationName() {
		return Anim_Sombrero;
	}
}
