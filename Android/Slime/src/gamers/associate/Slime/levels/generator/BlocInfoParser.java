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
	// TODO: change extension for blocs?
	public static String blocFileExtension = ".slime";
	public static final String BlocsAssetsBase = "blocs";
	
	public static void buildAll() {
		int i = 0;
		try {			
			for (String resourceName : SlimeFactory.ContextActivity.getAssets().list(BlocsAssetsBase)) {				
				i++;
				
				if (resourceName.contains(".slime")) {
					String resourcePath = BlocsAssetsBase + resourceName;
					buildBlocDef(resourcePath);
				}
			}
		} catch (IOException e) {
			Log.e(Slime.TAG, "Error during read of Blocs definition in " + BlocsAssetsBase + ", number: " + String.valueOf(i));
			e.printStackTrace();
		}
	}
	
	public static void buildBlocDef(String resourcePath) {
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
						Log.d(Slime.TAG, line);
						String[] items = line.split(";", -1);		
						String itemType = items[0];
						if (itemType == BlocInfoDef.Handled_Info) {
							BlocInfoDef blocDef = new BlocInfoDef();
							blocDef.parseAndCreate(line, null);
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
