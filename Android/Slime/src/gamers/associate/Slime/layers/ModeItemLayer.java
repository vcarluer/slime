package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;

import org.cocos2d.nodes.CCDirector;


public abstract class ModeItemLayer extends CanvasItemLayer {	
	private static final float PADDINGBASE = 50f;
	protected float screenScaleX;
	protected float screenScaleY;	
	
	public ModeItemLayer() {
		super(PADDINGBASE, 2f, false, 0);		
	}
	
	@Override
	protected void defineLabelPosition() {
//		this.screenScaleX = (CCDirector.sharedDirector().winSize().width / 2f) / WIDTH;
		this.screenScaleY = (CCDirector.sharedDirector().winSize().height) / HEIGHT;
				
		this.labelY = HEIGHT * this.screenScaleY / 2f;
		this.labelX = this.width / 2 + this.getPadding(PADDINGBASE);		
	}

	@Override
	protected float getFontSize() {
		return SlimeFactory.FntSize;
	}
	
	
}
