package gamers.associate.SlimeAttack.layers;

import android.annotation.SuppressLint;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;

import java.util.LinkedList;
import java.util.Queue;

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
	private static float fntSize = 42 * SlimeFactory.SGSDensity;
	private CCColorLayer colored;
	private float messageWidthSizeBase;
	private float messageX;
	
	public static MessageLayer get() {
		if (layer == null) {
			layer = new MessageLayer();
		}
		
		return layer;
	}
	
	@Override
	public void onEnter() {	
		super.onEnter();
		this.isShowing = false;
		this.showNext();
	}
	
	@Override
	public void onExit() {
		this.setPosition(this.hidePos);
		this.isShowing = false;
		this.stopAllActions();
		super.onExit();
	}

	protected MessageLayer() {
		this.messages = new LinkedList<MessageInfo>();
		this.width = CCDirector.sharedDirector().winSize().width / 3;
		this.height = this.width * heightRatio;
		
		this.icon = CCSprite.sprite("world-items/star-unlock.png");
		float iconScale = this.height / this.icon.getContentSizeRef().height;
		this.icon.setScale(iconScale);
		this.iconWidth = this.icon.getContentSizeRef().width * iconScale;
		this.icon.setPosition(this.iconWidth / 2f + paddingX, this.height / 2f);
		this.addChild(this.icon);
		
		this.message = SlimeFactory.getLabel(" ", fntSize);
		this.messageX = iconWidth + paddingX * 2;
		this.messageWidthSizeBase = this.width - this.messageX;
		this.message.setPosition(messageX, this.height / 2f);
		this.message.setColor(SlimeFactory.ColorSlime);
		this.addChild(this.message);
		
		float messageWidth = this.messageWidthSizeBase;
		this.width = messageWidth + this.messageX;
		
		float yPos = 
				CCDirector.sharedDirector().winSize().getHeight() - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY) - this.height - PauseLayer.PaddingY;
		this.basePos = CGPoint.make(0, yPos);
		this.hidePos = CGPoint.make(-this.width, yPos);
		this.setPosition(hidePos);
	}
	
	public void show(String text) {
		MessageInfo info = new MessageInfo(text);
		this.messages.add(info);
		this.showNext();
	}
	
	@SuppressLint("DefaultLocale") private void showNext() {
		if (!this.isShowing && this.messages.size() > 0) {
			MessageInfo info = this.messages.poll();
			this.isShowing = true;
			this.message.setString(info.getText().toUpperCase());
			this.message.setPosition(this.iconWidth + this.message.getContentSizeRef().width / 2f + paddingX * 2, this.height / 2f);
			
			float messageWidth = this.messageWidthSizeBase;
			if (messageWidth < this.message.getContentSizeRef().width) {
				messageWidth = this.message.getContentSizeRef().width + paddingX;
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
