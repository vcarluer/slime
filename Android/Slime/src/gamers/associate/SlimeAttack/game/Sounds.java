package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttackLite.R;
import gamers.associate.SlimeAttack.levels.LevelHome;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

public class Sounds {
	private static boolean disableEffects;
	
	public static boolean isMusicPlaying;
	
	public static void preload() {		
		preloadMusic(R.raw.menumusic);
		preloadMusic(R.raw.slimy_credits);
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
//		if (!disableEffects || force) {
//			synchronized(soundsMap) {
//				MediaPlayer mp = soundsMap.get(soundId);
//				if (mp != null) {
//					mp.start();
//				}
//				else {
//					SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), soundId);
//				}
//			}
//		}	
		
		if (!disableEffects || force) {
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), soundId);
		}
	}
	
	public static void stopEffect(int soundId) {
//		synchronized(soundsMap) {
			//MediaPlayer mp = soundsMap.get(soundId);
			//if (mp != null) {
				SoundEngine.sharedEngine().stopEffect(CCDirector.sharedDirector().getActivity(), soundId);
			//}
//		}
	}
	
	private static void preloadMusic(int soundId) {
		SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), soundId);
	}
	
	// > 5 sec
	public static void playMusic(int soundId, boolean loop) {
		if (!isMute) {
			SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), soundId, loop);
			isMusicPlaying = true;
		}
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
		if (!isMute) {			
			SoundEngine.sharedEngine().resumeSound();
			isMusicPlaying = true;
		}
	}
	
	public static void stopMusic() {
//		if (lastSndId == -1)
//			return;
//		
//		MediaPlayer mp = null;
//		synchronized(soundsMap) {
//			mp = soundsMap.get(lastSndId);
//			if (mp == null)
//				return;
//		}
//		mp.stop();
		
		SoundEngine.sharedEngine().pauseSound();
	}
	
	private static boolean isMute;
	
	public static void toggleMute(int soundVolume) {
		if (isMute) {
			isMute = false;
			SoundEngine.sharedEngine().setEffectsVolume((float) soundVolume);
			if (Level.currentLevel != null && (!Level.currentLevel.getActivated() || Level.currentLevel.getCurrentLevelName() == LevelHome.Id)) {
				resumeMusic();
			}
		} else {
			isMute = true;
			SoundEngine.sharedEngine().setEffectsVolume(0f);
			pauseMusic();
		}
		
		
	}
	
	public static boolean isMute() {
		return isMute;
	}
}
