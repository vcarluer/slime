package gamers.associate.Slime.game;

import gamers.associate.Slime.layers.LevelSelectionLayer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;

public class LevelSelection {
	private static LevelSelection levelSelection;
	
	private CCScene scene;	
	private LevelSelectionLayer selectionLayer;
	private CCLayer backgroundLayer;
	private boolean isActivated;
	
	public static LevelSelection get()
	{
		if (levelSelection == null) {
			levelSelection = new LevelSelection();
		}
		
		return levelSelection;
	}
	
	protected LevelSelection() {
		this.scene = CCScene.node();
		this.selectionLayer = new LevelSelectionLayer(this);
		this.scene.addChild(this.selectionLayer);
	}
	
	public void goBack() {
		this.selectionLayer.goBack();
	}
	
	public CCScene getScene()
	{
		return this.scene;
	}
	
	public void activate(){
		this.isActivated = true;
	}
	
	public void desactivate() {
		this.isActivated = false;
	}
	
	public boolean getActivated() {
		return this.isActivated;
	}
}