package gamers.associate.Slime.game;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.utils.collections.IntMap;

import android.media.MediaPlayer;

import gamers.associate.Slime.R;;

public class Sounds {
//	private static IntMap<MediaPlayer> soundsMap = new IntMap<MediaPlayer>();
	
	public static boolean isMusicPlaying;
	public static void preload() {		
		preloadMusic(R.raw.menumusic);
		preloadEffect(R.raw.menuselect);
		preloadEffect(R.raw.slimyjump);
		preloadEffect(R.raw.victory);
		preloadEffect(R.raw.slimyfire);
		preloadEffect(R.raw.lose);
		preloadEffect(R.raw.slimyselect);
		preloadEffect(R.raw.portalgoal);
		preloadEffect(R.raw.tick);
		preloadEffect(R.raw.slimyland);
		preloadEffect(R.raw.bump);
		preloadEffect(R.raw.slimydeath);
	}
	
	private static void preloadEffect(int soundId) {
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), soundId);
//		synchronized(soundsMap) {			
//			MediaPlayer mp = soundsMap.get(soundId);
//			if (mp != null)
//				return;
//			
//			mp = MediaPlayer.create(CCDirector.sharedDirector().getActivity(), soundId);
////			mp.prepareAsync();
//			soundsMap.put(soundId, mp);
//		}
	}
	
	// < 5 sec
	public static void playEffect(int soundId) {
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), soundId);
//		synchronized(soundsMap) {
//			MediaPlayer mp = soundsMap.get(soundId);
//			if (mp != null) {
//				mp.start();
//			}
//		}
	}
	
	private static void preloadMusic(int soundId) {
		SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), soundId);
	}
	
	// > 5 sec
	public static void playMusic(int soundId, boolean loop) {
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), soundId, loop);
		isMusicPlaying = true;
	}
	
	public static void stopLastMusic() {
		SoundEngine.sharedEngine().stopSound();
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
}
