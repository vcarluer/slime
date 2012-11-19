package gamers.associate.Slime.game;

import android.content.Context;
import android.os.Vibrator;

public class Vibe {
	public static void vibrate() {
		// Get instance of Vibrator from current Context
		Vibrator v = (Vibrator) SlimeFactory.ContextActivity.getSystemService(Context.VIBRATOR_SERVICE);
		 
		// Vibrate for 300 milliseconds
		v.vibrate(50);

	}
}
