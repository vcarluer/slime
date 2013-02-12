package gamers.associate.SlimeAttack.levels.generator;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.levels.itemdef.BlocInfoDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BlocInfoParser {
	public static String blocFileExtension = ".slime";	
	// Used in BlocInfoDef
	public static LevelGraphGeneratorBase Generator;
	
	public static void buildAll(LevelGraphGeneratorBase generator) {
		Generator = generator;
		int i = 0;
		try {			
			for (String assetBase : Generator.getAssetsBase()) {
				for (String resourceName : SlimeFactory.ContextActivity.getAssets().list(assetBase)) {				
					i++;
					
					if (resourceName.contains(".slime")) {
						String resourcePath = assetBase + "/" + resourceName;
						buildBlocDef(resourcePath, generator);
					}
				}
			}			
		} catch (IOException e) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "Error during read of Blocs definition in " + Generator.getAssetsBase() + ", number: " + String.valueOf(i));
			e.printStackTrace();
		}
	}
	
	public static void buildBlocDef(String resourcePath, LevelGraphGeneratorBase generator) {
		Generator = generator;
		InputStream inputStream;
		try {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Loading blocInfo from " + resourcePath);			
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
							SlimeFactory.Log.d(SlimeAttack.TAG, line);
							BlocInfoDef blocDef = new BlocInfoDef();
							blocDef.parse(line);							
							blocDef.createItem(resourcePath);
						}
					} catch (Exception e) {
						SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during read of " + resourcePath + " line " + String.valueOf(i));
						e.printStackTrace();
					}					
				}
			} catch (IOException e) {
				SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during read of " + resourcePath + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during opening of " + resourcePath);
			e1.printStackTrace();
		}
	}
}
