package gamers.associate.Slime.game;

import java.util.ArrayList;
import java.util.Random;

public class LoadingQuoteGenerator {
	
	private static Random rand = new Random();
	public static String NewQuote() {
		
		
		String[] catchPhrases=new String[9];
		catchPhrases[0] = "Resistance is futile";
		catchPhrases[1] = "We want information, information, information.";
		catchPhrases[2] = "“I’m just a mean green mothah from outer space";
		catchPhrases[3] = "Eterminate, exterminate.";
		catchPhrases[4] = "Prepare to die!";
		catchPhrases[5] = "Welcome Humans! I am ready for you";
		catchPhrases[6] = "I'm sorry Dave, I can't do that";
		catchPhrases[7] = "I'm melting! Melting!";
		catchPhrases[8] = "Brains … more brains!";
		int bb=rand.nextInt(catchPhrases.length);
		return catchPhrases[bb];
		
	}
	
	
}
