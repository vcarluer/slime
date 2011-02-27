package gamers.associate.Slime;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;

public class LevelSelection {
	private static LevelSelection levelSelection;
	
	private CCScene scene;	
	private CCLayer selectionLayer;
	private CCLayer backgroundLayer;
	
	public static LevelSelection get()
	{
		if (levelSelection == null) {
			levelSelection = new LevelSelection();
		}
		
		return levelSelection;
	}
	
	protected LevelSelection() {
		this.scene = CCScene.node();
		this.selectionLayer = new LevelSelectionLayer();
		this.scene.addChild(this.selectionLayer);
	}
	
	protected CCScene getScene()
	{
		return this.scene;
	}
}
