package gamers.associate.Slime.game;

import gamers.associate.Slime.R;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.utils.collections.IntMap;

import android.media.MediaPlayer;

public class Sounds {
	private static IntMap<MediaPlayer> soundsMap = new IntMap<MediaPlayer>();
	private static int lastSndId = -1;
	private static boolean disableEffects;
	
	public static boolean isMusicPlaying;
	
	public static void preload() {		
		preloadMusic(R.raw.menumusic);
		preloadEffect(R.raw.menuselect);
		preloadEffect(R.raw.slimycharging);
		preloadEffect(R.raw.slimyjumpa);
		preloadEffect(R.raw.slimyjumpb);
		preloadEffect(R.raw.slimyjumpd);
		preloadEffect(R.raw.slimyjumpe);
		preloadEffect(R.raw.slimyjumpc);
		preloadEffect(R.raw.victory);
		preloadEffect(R.raw.slimyfire);
		preloadEffect(R.raw.lose);
		preloadEffect(R.raw.portalgoal);
		preloadEffect(R.raw.tick);
		preloadEffect(R.raw.slimyland);
		preloadEffect(R.raw.bump);
		preloadEffect(R.raw.slimydeath);
		preloadEffect(R.raw.slimyswitch);
		preloadEffect(R.raw.star);
		preloadEffect(R.raw.platformbump);
		preloadEffect(R.raw.platformstick);
		preloadEffect(R.raw.bipcamera);
		
	}		
	
	public static void setEffectsDisable(boolean isDisable) {
		disableEffects = isDisable;
	}
	
	
	public static void preloadEffect(int soundId) {
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), soundId);
	}
	
	public static void playEffect(int soundId) {
		playEffect(soundId, false);
	}
	
	// < 5 sec
	public static void playEffect(int soundId, boolean force) {
		if (!disableEffects || force) {
			synchronized(soundsMap) {
				MediaPlayer mp = soundsMap.get(soundId);
				if (mp != null) {
					mp.start();
				}
				else {
					SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), soundId);
				}
			}
		}		
	}
	
	public static void stopEffect(int soundId) {
		synchronized(soundsMap) {
			//MediaPlayer mp = soundsMap.get(soundId);
			//if (mp != null) {
				SoundEngine.sharedEngine().stopEffect(CCDirector.sharedDirector().getActivity(), soundId);
			//}
		}
	}
	
	private static void preloadMusic(int soundId) {
		SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), soundId);
	}
	
	// > 5 sec
	public static void playMusic(int soundId, boolean loop) {
		lastSndId = soundId;
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), soundId, loop);
		isMusicPlaying = true;
	}
	
	public static void destroy() {
		SoundEngine.sharedEngine().realesAllSounds();
		SoundEngine.sharedEngine().realesAllEffects();
		isMusicPlaying = false;
	}
	
	public static void pauseMusic() {		
		SoundEngine.sharedEngine().pauseSound();
		isMusicPlaying = false;
	}
	
	public static void resumeMusic() {
		SoundEngine.sharedEngine().resumeSound();
		isMusicPlaying = true;
	}
	
	public static void stopMusic() {
		if (lastSndId == -1)
			return;
		
		MediaPlayer mp = null;
		synchronized(soundsMap) {
			mp = soundsMap.get(lastSndId);
			if (mp == null)
				return;
		}
		mp.stop();		
	}
}
