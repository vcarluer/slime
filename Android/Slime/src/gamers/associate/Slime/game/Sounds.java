package gamers.associate.Slime.game;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

import gamers.associate.Slime.R;;

public class Sounds {
	public static void preload() {		
		preloadEffect(R.raw.menuselect);
	}
	
	private static void preloadEffect(int soundId) {
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), soundId);
	}
	
	public static void playEffect(int soundId) {
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), soundId);
	}
}
