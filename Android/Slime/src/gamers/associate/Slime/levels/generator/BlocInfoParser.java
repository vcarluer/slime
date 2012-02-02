package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.itemdef.BlocInfoDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class BlocInfoParser {
	public static String blocFileExtension = ".slime";	
	// Used in BlocInfoDef
	public static LevelGraphGeneratorBase Generator;
	
	public static void buildAll(LevelGraphGeneratorBase generator) {
		Generator = generator;
		int i = 0;
		try {			
			for (String resourceName : SlimeFactory.ContextActivity.getAssets().list(Generator.getAssetsBase())) {				
				i++;
				
				if (resourceName.contains(".slime")) {
					String resourcePath = Generator.getAssetsBase() + "/" + resourceName;
					buildBlocDef(resourcePath, generator);
				}
			}
		} catch (IOException e) {
			Log.e(Slime.TAG, "Error during read of Blocs definition in " + Generator.getAssetsBase() + ", number: " + String.valueOf(i));
			e.printStackTrace();
		}
	}
	
	public static void buildBlocDef(String resourcePath, LevelGraphGeneratorBase generator) {
		Generator = generator;
		InputStream inputStream;
		try {
			Log.d(Slime.TAG, "Loading blocInfo from " + resourcePath);			
			inputStream = SlimeFactory.ContextActivity.getAssets().open(resourcePath);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;		
			
			int i = 0;
			try {
				// Only read first line (convention) for performance?
				while (( line = buffreader.readLine()) != null) {
					try {
						i++;						
						String[] items = line.split(";", -1);		
						String itemType = items[0];
						if (itemType.equals(BlocInfoDef.Handled_Info)) {
							Log.d(Slime.TAG, line);
							BlocInfoDef blocDef = new BlocInfoDef();
							blocDef.parse(line);							
							blocDef.createItem(resourcePath);
						}
					} catch (Exception e) {
						Log.e(Slime.TAG, "ERROR during read of " + resourcePath + " line " + String.valueOf(i));
						e.printStackTrace();
					}					
				}
			} catch (IOException e) {
				Log.e(Slime.TAG, "ERROR during read of " + resourcePath + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening of " + resourcePath);
			e1.printStackTrace();
		}
	}
}
