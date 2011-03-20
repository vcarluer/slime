package gamers.associate.Slime.items.base;

import org.cocos2d.nodes.CCSpriteSheet;

public class SpriteSheetCacheInfo {
	public static int Included_For_Attach = 0;
	public static int Excluded_For_Attach = 1;
	
	private String name;
	private CCSpriteSheet spriteSheet;
	private int zOrder;
	private int attachType;
	
	public SpriteSheetCacheInfo(String name, CCSpriteSheet spriteSheet, int zOrder, int attachType) {
		this.name = name;
		this.spriteSheet = spriteSheet;
		this.zOrder = zOrder;
		this.attachType = attachType;
	}
	
	/**
	 * @return the spriteSheet
	 */
	public CCSpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	/**
	 * @param spriteSheet the spriteSheet to set
	 */
	public void setSpriteSheet(CCSpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	/**
	 * @return the zOrder
	 */
	public int getZOrder() {
		return zOrder;
	}
	/**
	 * @param zOrder the zOrder to set
	 */
	public void setZOrder(int zOrder) {
		this.zOrder = zOrder;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the attachType
	 */
	public int getAttachType() {
		return attachType;
	}
	/**
	 * @param attachType the attachType to set
	 */
	public void setAttachType(int attachType) {
		this.attachType = attachType;
	}
}
