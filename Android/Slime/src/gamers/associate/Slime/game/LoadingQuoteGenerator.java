package gamers.associate.Slime.game;

import java.util.ArrayList;
import java.util.Random;

public class LoadingQuoteGenerator {
	
	private static Random rand = new Random();
	public static String NewQuote() {
		
		
		String[] catchPhrases=new String[22];
		catchPhrases[0] = "Resistance is futile";
		catchPhrases[1] = "We want information, information, information.";
		catchPhrases[2] = "“I’m just a mean green mothah from outer space";
		catchPhrases[3] = "Eterminate, exterminate.";
		catchPhrases[4] = "Prepare to die!";
		catchPhrases[5] = "Welcome Humans! I am ready for you";
		catchPhrases[6] = "I'm sorry Dave, I can't do that";
		catchPhrases[7] = "I'm melting! Melting!";
		catchPhrases[8] = "You Homosapiens and your guns...";
		catchPhrases[9] = "Congratulation at last you have done some coding!";
		catchPhrases[10] = "Lets Be Bad Guys";
		catchPhrases[11] = "Beam me up, Scotty.";
		catchPhrases[12] = "Never Give up, Never Surrender";
		catchPhrases[13] = "soylent green is made of PEOPLE!!!";
		catchPhrases[14] = "I am a normal human worm baby";
		catchPhrases[15] = "it's a TRAP!";
		catchPhrases[16] = "I'm altering the deal. Pray I don't alter it further...";
		catchPhrases[17] = "Live long and prosper";
		catchPhrases[18] = "Don't make me angry ...";
		catchPhrases[19] = "If it bleeds, we can kill it.";
		catchPhrases[20] = "Anything you think can be held against you.";
		catchPhrases[21] = "That can't be good,";
		int bb=rand.nextInt(catchPhrases.length);
		return catchPhrases[bb];
		
	}
	
	
}
