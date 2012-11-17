package gamers.associate.Slime.game;

import org.cocos2d.nodes.CCNode;

import android.content.Intent;

public class Sharer extends CCNode {
	public static String twitterTag = "http://bit.ly/SVdACw @GamersAssociate";
	public static String DefaultMessage = "Love Slime Attack: " + twitterTag;
	
	private String shareMessage;
	
	public Sharer() {
		this.shareMessage = DefaultMessage;
	}
	
	public void shareApp(Object sender) {
		//create the send intent
		Intent shareIntent = 
		 new Intent(android.content.Intent.ACTION_SEND);

		//set the type
		shareIntent.setType("text/plain");

		//add a subject
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
		 "Slime Attack!");

		//add the message
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
		 getShareMessage());

		//start the chooser for sharing
		SlimeFactory.ContextActivity.startActivity(Intent.createChooser(shareIntent, 
		 "Share on"));
	}

	public String getShareMessage() {
		return shareMessage;
	}

	public void setShareMessage(String shareMessage) {
		this.shareMessage = shareMessage;
	}
}
