package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ThumbnailManager;
import gamers.associate.Slime.items.base.GameItemCocosFactory;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.TextureAnimation;

public class ThumbnailFactory extends GameItemCocosFactory<Thumbnail> {

	@Override
	protected void createAnimList() {		
		TextureAnimation.createFramesFromFiles(Thumbnail.Thumbnail_back, 1);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Thumbnail instantiate(float x, float y, float width, float height) {
		return new Thumbnail(x, y, width, height);
	}

	@Override
	protected void runFirstAnimations(Thumbnail item) {		
	}
	
	public Thumbnail create(float x, float y, ISelectable target) {				
		Thumbnail tn = this.create(x, y, ThumbnailManager.Thumbnail_Margin_Width, ThumbnailManager.Thumbnail_Margin_Height);
		tn.setTarget(target);
		return tn;
	}
}
