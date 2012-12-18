package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Rank;

import org.cocos2d.nodes.CCSprite;

public class RankFactory {
	private static final String WORLD_ITEMS_STAR_LOCK_PNG = "world-items/star-lock.png";
	private static final String WORLD_ITEMS_STAR_INACTIVE_PNG = "world-items/star-inactive.png";
	private static final String WORLD_ITEMS_STAR_BRONZE_PNG = "world-items/star-bronze.png";
	private static final String WORLD_ITEMS_STAR_SILVER_PNG = "world-items/star-silver.png";
	private static final String WORLD_ITEMS_STAR_GOLD_PNG = "world-items/star-gold.png";

	public static CCSprite getSprite(Rank rank) {
		String pngAsset;
		switch (rank) {
		default:
		case Lock:
			pngAsset = WORLD_ITEMS_STAR_LOCK_PNG;
			break;
		case None:
			pngAsset = WORLD_ITEMS_STAR_INACTIVE_PNG;
			break;
		case Bronze:
			pngAsset = WORLD_ITEMS_STAR_BRONZE_PNG;
			break;
		case Silver:
			pngAsset = WORLD_ITEMS_STAR_SILVER_PNG;
			break;
		case Gold:
			pngAsset = WORLD_ITEMS_STAR_GOLD_PNG;
			break;			
		}
		
		return CCSprite.sprite(pngAsset);		
	}
}
