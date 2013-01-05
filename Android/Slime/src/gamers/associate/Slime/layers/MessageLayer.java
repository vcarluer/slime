package gamers.associate.Slime.layers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;

public class MessageLayer extends CCLayer {
	private static final float heightRatio = 1 / 5f;
	private CCLabel message;
	private CGPoint hidePos;
	private float width;
	private float height;
	private CGPoint basePos;
	private static MessageLayer layer;
	private Queue<MessageInfo> messages;
	private boolean isShowing;
	
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
		CCColorLayer colored = CCColorLayer.node(SlimeFactory.getColorLight(230),this.width, this.height);
		this.addChild(colored, Level.zUnder);
		this.message = SlimeFactory.getLabel(" ");
		this.message.setPosition(this.width / 2f, this.height / 2f);
		this.message.setColor(SlimeFactory.ColorSlime);
		this.addChild(this.message);
		
		this.basePos = CGPoint.make(0, - this.height);
		this.hidePos = CGPoint.zero();
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
			CCMoveTo moveTo = CCMoveTo.action(0.5f, this.hidePos);
			CCDelayTime delay = CCDelayTime.action(5);
			CCMoveTo moveBack = CCMoveTo.action(0.5f, basePos);
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
