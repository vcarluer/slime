package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteType;

import java.util.ArrayList;
import java.util.HashMap;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public class MenuNode extends GameItemCocos implements ISelectable {
	public static String Anim_Node = "blueportal";
	
	public static float Default_Width = 32f;
	public static float Default_Height = 10f;
	
	private String nodeId;
	private String targetLevel;
	private boolean isSelected;
	private CGRect scaledRect;
	private HashMap<String, String> connections;
	
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

	@Override
	public boolean canSelect(CGPoint gameReference) {
		return true;
	}

	@Override
	public CCNode getRootNode() {
		return this.rootNode;
	}

	@Override
	public CGRect getSelectionRect() {
		return this.scaledRect;
	}

	@Override
	public CCSprite getThumbail() {
		return null;
	}

	@Override
	public boolean isSelected() {
		return this.isSelected;
	}

	@Override
	public void select() {
		this.isSelected = true;		
	}

	@Override
	public void select(CGPoint gameReference) {
		this.select();
	}

	@Override
	public void selectionMove(CGPoint gameReference) {		
	}

	@Override
	public void selectionStop(CGPoint gameReference) {
		this.unselect();		
	}
	
	@Override
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
			CCMoveTo moveTo = CCMoveTo.action(1.0f, CGPoint.make(this.getPosition().x, this.getPosition().y + selection.getHeight() / 2 ));			
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
		}
		else {
			this.endEnterAnimDone();
		}
	}
	
	public void endEnterAnimDone() {
		Level.get(this.targetLevel);
	}

	@Override
	public boolean trySelect(CGPoint gameReference) {
		if (this.canSelect(gameReference)) {
			this.select();
		}
		
		return this.isSelected;
	}

	@Override
	public void unselect() {
		this.isSelected = false;		
	}
	
	public void addConnection(String connectionName) {
		this.connections.put(connectionName, connectionName);
	}
	
	public boolean isConnectionSourceOf(String targetName) {
		return this.connections.containsKey(targetName);
	}
}
