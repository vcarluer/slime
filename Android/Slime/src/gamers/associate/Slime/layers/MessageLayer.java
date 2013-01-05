package gamers.associate.Slime.layers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.MenuSprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class MessageLayer extends CCLayer {
	private static final float heightRatio = 1 / 5f;
	private static final float paddingX = 10;
	private CCLabel message;
	private CGPoint hidePos;
	private float width;
	private float height;
	private CGPoint basePos;
	private static MessageLayer layer;
	private Queue<MessageInfo> messages;
	private boolean isShowing;
	private CCSprite icon;
	private float iconWidth;
	private static float fntSize = 42;
	private CCColorLayer colored;
	private float messageWidthSizeBase;
	private float messageX;
	
	public static MessageLayer get() {
		if (layer == null) {
			layer = new MessageLayer();
		}
		
		return layer;
	}
	
	protected MessageLayer() {
		this.messages = new LinkedList<MessageInfo>();
		this.width = CCDirector.sharedDirector().winSize().width / 3;
		this.height = this.width * heightRatio;
		
		this.icon = CCSprite.sprite("world-items/star-lock.png");
		float iconScale = this.height / this.icon.getContentSize().height;
		this.icon.setScale(iconScale);
		this.iconWidth = this.icon.getContentSize().width * iconScale;
		this.icon.setPosition(this.iconWidth / 2f + paddingX, this.height / 2f);
		this.addChild(this.icon);
		
		this.message = SlimeFactory.getLabel(" ", fntSize);
		this.messageX = iconWidth + paddingX * 2;
		this.messageWidthSizeBase = this.width - this.messageX;
		this.message.setPosition(messageX, this.height / 2f);
		this.message.setColor(SlimeFactory.ColorSlime);
		this.addChild(this.message);
		
		this.basePos = CGPoint.make(- this.width, CCDirector.sharedDirector().winSize().getHeight() - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY));
		this.hidePos = CGPoint.make(0, CCDirector.sharedDirector().winSize().getHeight() - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY));
		this.setPosition(basePos);
	}
	
	public void show(String text) {
		MessageInfo info = new MessageInfo(text);
		this.messages.add(info);
		this.showNext();
	}
	
	private void showNext() {
		if (!this.isShowing && this.messages.size() > 0) {
			MessageInfo info = this.messages.poll();
			this.isShowing = true;
			this.message.setString(info.getText().toUpperCase());
			this.message.setPosition(this.iconWidth + this.message.getContentSize().width / 2f + paddingX * 2, this.height / 2f);
			
			float messageWidth = this.messageWidthSizeBase;
			if (messageWidth < this.message.getContentSize().width) {
				messageWidth = this.message.getContentSize().width + paddingX;
			}
			
			if (this.colored != null) {
				this.removeChild(this.colored, true);
			}
			
			this.width = messageWidth + this.messageX;
			this.colored = CCColorLayer.node(SlimeFactory.getColorLight(230), this.width, this.height);
			this.addChild(colored, Level.zUnder);
			
			float yPos = 
					CCDirector.sharedDirector().winSize().getHeight() - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY) - this.height - PauseLayer.PaddingY;
			
			this.basePos = CGPoint.make(0, yPos);
			this.hidePos = CGPoint.make(- this.width, yPos);
			
			this.setPosition(this.hidePos);
			CCMoveTo moveTo = CCMoveTo.action(0.5f, this.basePos);
			CCDelayTime delay = CCDelayTime.action(5);
			CCMoveTo moveBack = CCMoveTo.action(0.5f, hidePos);
			CCCallFunc call = CCCallFunc.action(this, "endShow");
			CCSequence seq = CCSequence.actions(moveTo, delay, moveBack, call);
			this.runAction(seq);
		}
	}
	
	public void endShow() {
		this.isShowing = false;
		this.showNext();
	}
}
