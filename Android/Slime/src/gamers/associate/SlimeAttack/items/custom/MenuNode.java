package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.LevelBuilder;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Util;
import gamers.associate.SlimeAttack.items.base.GameItemCocos;
import gamers.associate.SlimeAttack.items.base.ISelectable;
import gamers.associate.SlimeAttack.items.base.SpriteType;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelDefinitionParserCache;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public class MenuNode extends GameItemCocos implements ISelectable {
	public static String Anim_Node = "teleporter-blue-portal";
	
	public static float Default_Width = 32f;
	public static float Default_Height = 10f;
	
	private String nodeId;
	private String targetLevel;
	private boolean isSelected;
	private CGRect scaledRect;
	private HashMap<String, String> connections;
	private LevelDefinition levelDef;
	private boolean postBuildParsed;
	private ArrayList<MenuNode> parents;
	
	public MenuNode(float x, float y, float width, float height) {
		super(x, y, width, height);		
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;
		}
		
		this.spriteType = SpriteType.ANIM_SCALE;
		
		this.scaledRect = CGRect.getZero();
		Util.getScaledRect(CGRect.make(this.getPosition(), CGSize.make(this.width, this.height)), MenuNode.Default_Width, MenuNode.Default_Height, Level.currentLevel.getCameraManager().getCurrentZoom(), this.scaledRect);
		
		this.connections = new HashMap<String, String>();
		this.parents = new ArrayList<MenuNode>();
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return MenuNode.Anim_Node;
	}
	
	public void createPortal() {
		CCAction animate = CCRepeatForever.action(CCAnimate.action(this.animationList.get(Anim_Node), false));				
		this.sprite.runAction(animate);		
	}

	public void setTargetLevel(String targetLevel) {
		this.targetLevel = targetLevel;
	}

	public String getTargetLevel() {
		return targetLevel;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public boolean canSelect(CGPoint gameReference) {
		return this.getLevelDefinition().isUnlock();
	}

	public CCNode getRootNode() {
		return this.rootNode;
	}

	public CGRect getSelectionRect() {
		return this.scaledRect;
	}

	public CCSprite getThumbail() {
		return null;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void select() {
		this.isSelected = true;		
	}

	public void select(CGPoint gameReference) {
		this.select();
	}

	public void selectionMove(CGPoint gameReference) {		
	}

	public void selectionStop(CGPoint gameReference) {
		this.unselect();		
	}
	
	public boolean simpleSelect() {		
		MenuNode current = SlimeFactory.MenuNode.getCurrentNode();
		if (current.getNodeId().equals(this.getNodeId())) {
			this.enterLevel();
		} else {
			if (current.isConnectionSourceOf(this.getNodeId())) {
				this.moveSelectionToNode();
			}
		}		
				
		return false;
	}
	
	private void moveSelectionToNode() {		
		if (Level.currentLevel.getStartItem() instanceof GameItemCocos)
		{
			GameItemCocos selection = (GameItemCocos)Level.currentLevel.getStartItem();
			CCMoveTo moveTo = CCMoveTo.action(0.5f, CGPoint.make(this.getPosition().x, this.getPosition().y + selection.getHeight() / 2 ));			
			CCCallFunc callback = CCCallFunc.action(this, "endMoveAnimDone");
			CCSequence sequence = CCSequence.actions(moveTo, callback);
			selection.getSprite().runAction(sequence);
		}
		else {
			this.endMoveAnimDone();
		}
	}
	
	public void endMoveAnimDone() {			
		SlimeFactory.MenuNode.setCurrentNode(this);		
	}
	
	private void enterLevel() {
		if (Level.currentLevel.getStartItem() instanceof GameItemCocos)
		{
			GameItemCocos selection = (GameItemCocos)Level.currentLevel.getStartItem();
			CCRotateBy rotate = CCRotateBy.action(0.5f, 720);
			CCCallFunc callback = CCCallFunc.action(this, "endEnterAnimDone");
			CCSequence sequence = CCSequence.actions(rotate, callback);
			selection.getSprite().runAction(sequence);
//			Sounds.playEffect(R.raw.getrupee);
		}
		else {
			this.endEnterAnimDone();
		}
	}
	
	@Override
	public void initItem() {	
		super.initItem();		
		this.handleLockState();
	}
	
	private void handleLockState() {		
		// hide road
		// hide sprite
		this.getSprite().setVisible(this.getLevelDefinition().isUnlock());		
	}

	public void endEnterAnimDone() {
		Level.get(this.getLevelDefinition());
	}

	public boolean trySelect(CGPoint gameReference) {
		if (this.canSelect(gameReference)) {
			this.select();
		}
		
		return this.isSelected;
	}


	public void unselect() {
		this.isSelected = false;		
	}
	
	public void addConnection(String connectionName) {
		this.connections.put(connectionName, connectionName);
	}
	
	public boolean isConnectionSourceOf(String targetName) {
		return this.connections.containsKey(targetName);
	}
	
	private LevelDefinition getLevelDefinition() {
		if (this.levelDef == null) {
			this.levelDef = LevelDefinitionParserCache.get(this.targetLevel + LevelBuilder.LevelExtension);
		}
		
		return this.levelDef;
	}

	public void setUnlock(boolean isUnlock) {
		boolean hasChanged = false;
		if (isUnlock != this.getLevelDefinition().isUnlock()) {
			hasChanged = true;
		}
		
		this.getLevelDefinition().setUnlock(isUnlock);
		this.handleLockState();
		if (hasChanged) {
			if (isUnlock = true) {
				CCFadeIn fadeIn = CCFadeIn.action(2.0f);
				this.getSprite().runAction(fadeIn);
//				Sounds.playEffect(R.raw.key);
			}
		}
	}
	
	public boolean isUnlock() {
		return this.getLevelDefinition().isUnlock();
	}
	
	public void unlockChildConnectionsGraph(MenuNode parent) {		
		if (parent != null) {
			this.parents.add(parent);
		}

		if (!this.postBuildParsed) {						
			this.postBuildParsed = true;					

			if (this.getLevelDefinition().isFinished()) {
				for(String connectionName : this.connections.values()) {
					MenuNode child = SlimeFactory.MenuNode.get(connectionName);
					if (child != null) {
						child.setUnlock(true);
						child.unlockChildConnectionsGraph(this);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		SlimeFactory.MenuNode.remove(this.getNodeId());
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
		for(MenuNode parent : this.parents) {
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(0.0f, 170f / 255f, 54f / 255f, 1.0f);
			gl.glLineWidth(2f);										
			CCDrawingPrimitives.ccDrawLine(gl, this.getPosition(), parent.getPosition());
		}
	}
	
	public boolean isCurrentSelection() {
		return this.getLevelDefinition().isCurrentSelection();
	}
	
	public void setCurrentSelection(boolean value) {
		this.getLevelDefinition().setCurrentSelection(value);
	}

	@Override
	public boolean isThumbnailAwaysOn() {
		return false;
	}

	@Override
	public boolean isThumbnailActive() {
		return true;
	}
}
