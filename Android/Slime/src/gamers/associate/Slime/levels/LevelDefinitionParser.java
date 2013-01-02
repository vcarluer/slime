package gamers.associate.Slime.levels;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.SurvivalGame;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.EvacuationPlug;
import gamers.associate.Slime.items.custom.LevelEnd;
import gamers.associate.Slime.levels.itemdef.BecBunsenDef;
import gamers.associate.Slime.levels.itemdef.BoxDef;
import gamers.associate.Slime.levels.itemdef.BumperAngleDef;
import gamers.associate.Slime.levels.itemdef.ButtonDef;
import gamers.associate.Slime.levels.itemdef.CameraDef;
import gamers.associate.Slime.levels.itemdef.CircularSawDef;
import gamers.associate.Slime.levels.itemdef.DirectorDef;
import gamers.associate.Slime.levels.itemdef.EnergyBallDef;
import gamers.associate.Slime.levels.itemdef.EnergyBallGunDef;
import gamers.associate.Slime.levels.itemdef.GoalPortalDef;
import gamers.associate.Slime.levels.itemdef.ItemDefinition;
import gamers.associate.Slime.levels.itemdef.LaserGunDef;
import gamers.associate.Slime.levels.itemdef.LevelInfoDef;
import gamers.associate.Slime.levels.itemdef.LiquidDef;
import gamers.associate.Slime.levels.itemdef.LiquidSurfaceDef;
import gamers.associate.Slime.levels.itemdef.MenuNodeDef;
import gamers.associate.Slime.levels.itemdef.MetaMenuDef;
import gamers.associate.Slime.levels.itemdef.PlatformDef;
import gamers.associate.Slime.levels.itemdef.PolygonDef;
import gamers.associate.Slime.levels.itemdef.RedDef;
import gamers.associate.Slime.levels.itemdef.SpawnDef;
import gamers.associate.Slime.levels.itemdef.SpriteDef;
import gamers.associate.Slime.levels.itemdef.StarDef;
import gamers.associate.Slime.levels.itemdef.SurvivalDef;
import gamers.associate.Slime.levels.itemdef.TargetDef;
import gamers.associate.Slime.levels.itemdef.TeslaCoilDef;
import gamers.associate.Slime.levels.itemdef.TimeAttackDef;
import gamers.associate.Slime.levels.itemdef.TriggerTimeDef;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

@SuppressLint("DefaultLocale")
@SuppressWarnings("rawtypes")
public class LevelDefinitionParser extends LevelDefinition
{	
	private static String SpecialLevel = "Special";
	private String resourceName;	
	private ArrayList<ItemDefinition> itemDefinitions;
	private HashMap<String, ItemDefinition> typeHandler;
	private HashMap<Class, ItemDefinition> classHandler;
	protected ItemDefinition postBuildItem;
	protected boolean isLocalStorage;
	protected Set<Class> ignoredClasses;
	protected Set<String> ignoredItems;
	
	public LevelDefinitionParser() {		
	}
		
	public LevelDefinitionParser(String resourceName, boolean noUserInfoStore) {		
		this.noStore = noUserInfoStore;
		this.gamePlay = GamePlay.ManuallyDefined;
		this.setResourceName(resourceName);		
		this.itemDefinitions = new ArrayList<ItemDefinition>();
		this.typeHandler = new HashMap<String, ItemDefinition>();
		this.classHandler = new HashMap<Class, ItemDefinition>();
		this.ignoredClasses = new HashSet<Class>();
		this.ignoredItems = new HashSet<String>();
		this.createItemDefinitions();		
		this.buildItemTypeMap();
		this.defineIgnoredItems();
		this.defineIgnoreClasses();		
	}
	
	protected void defineIgnoredItems() {
	}

	public LevelDefinitionParser(String resourceName) {
		this(resourceName, false);
	}
	
	private void defineIgnoreClasses() {
		this.ignoredClasses.add(LevelEnd.class);
		this.ignoredClasses.add(EvacuationPlug.class);
	}

	protected void createItemDefinitions() {
		this.itemDefinitions.add(new PlatformDef());
		this.itemDefinitions.add(new LevelInfoDef());
		this.itemDefinitions.add(new TimeAttackDef());
		this.itemDefinitions.add(new GoalPortalDef());
		this.itemDefinitions.add(new SpawnDef());
		this.itemDefinitions.add(new BecBunsenDef());
		this.itemDefinitions.add(new BumperAngleDef());
		this.itemDefinitions.add(new ButtonDef());
		this.itemDefinitions.add(new CircularSawDef());				
		this.itemDefinitions.add(new MetaMenuDef());				
		MenuNodeDef menuNode = new MenuNodeDef();
		this.itemDefinitions.add(menuNode);
		this.postBuildItem = menuNode;
		this.itemDefinitions.add(new PolygonDef());
		this.itemDefinitions.add(new LaserGunDef());
		this.itemDefinitions.add(new TargetDef());
		this.itemDefinitions.add(new StarDef());
		this.itemDefinitions.add(new BoxDef());
		this.itemDefinitions.add(new SpriteDef());
		this.itemDefinitions.add(new RedDef());
		this.itemDefinitions.add(new LiquidDef());
		this.itemDefinitions.add(new LiquidSurfaceDef());
		this.itemDefinitions.add(new TriggerTimeDef());
		this.itemDefinitions.add(new DirectorDef());
		this.itemDefinitions.add(new TeslaCoilDef());
		this.itemDefinitions.add(new EnergyBallDef());
		this.itemDefinitions.add(new EnergyBallGunDef());
		this.itemDefinitions.add(new CameraDef());
		this.itemDefinitions.add(new SurvivalDef());
	}
	
	private void buildItemTypeMap() {
		for (ItemDefinition itemDef : this.itemDefinitions) {
			ArrayList<String> types = itemDef.getTypesHandled();
			for(String typeHandled : types) {
				this.typeHandler.put(typeHandled, itemDef);
			}
			
			ArrayList<Class> classes = itemDef.getClassesHandled();
			for(Class claz : classes) {
				this.classHandler.put(claz, itemDef);
			}
		}
	}
	
	private boolean mediaAvailable() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		return mExternalStorageAvailable && mExternalStorageWriteable;
	}
	
	@Override
	public boolean buildLevel(Level level) {
		InputStream inputStream = null;
		boolean constructed = true;
		try {
			SlimeFactory.Log.d(Slime.TAG, "Loading level from " + this.getResourceName());
			if (SlimeFactory.IsLevelSelectionOn) {
				if (this.mediaAvailable()) {
					File file = this.getExternFile();
					if (file != null) {
						inputStream = new FileInputStream(file);
					}					
				}
			} else {
				if (this.isLocalStorage) {
					inputStream = SlimeFactory.ContextActivity.openFileInput(this.getResourcePath());
				} else {
					inputStream = SlimeFactory.ContextActivity.getAssets().open(this.getResourcePath());
				}
			}
						
			InputStreamReader inputreader = new InputStreamReader(inputStream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;		
			
			int i = 0;
			try {
				while (( line = buffreader.readLine()) != null) {
					try {
						i++;
						SlimeFactory.Log.d(Slime.TAG, line);
						this.HandleLine(level, line);
					} catch (Exception e) {
						SlimeFactory.Log.e(Slime.TAG, "ERROR during read of " + this.getResourceName() + " line " + String.valueOf(i));
						e.printStackTrace();
					}					
				}
			} catch (IOException e) {
				constructed = false;
				SlimeFactory.Log.e(Slime.TAG, "ERROR during read of " + this.getResourceName() + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			constructed = false;
			SlimeFactory.Log.e(Slime.TAG, "ERROR during opening of " + this.getResourceName());
			e1.printStackTrace();
		}
		
		this.postBuildItem.postBuild();
		return constructed;
	}
	
	protected void HandleLine(Level level, String line) throws Exception {
		ItemDefinition itemDef = this.getItemDef(line);
		if (itemDef != null) {
			itemDef.parseAndCreate(line, level);
		}
	}
	
	protected ItemDefinition getItemDef(String line) {			
		String itemType = this.getItemType(line);
		if (!this.ignoredItems.contains(itemType)) {
			return this.typeHandler.get(itemType);
		} else {
			SlimeFactory.Log.d(Slime.TAG, "Item " + itemType + " ignored");
			return null;
		}		
	}
	
	protected String getItemType(String line) {
		String[] items = line.split(";", -1);		
		String itemType = items[0];
		return itemType;
	}
	
	private File getExternFile() {
		if (this.mediaAvailable() && this.getGamePlay() == GamePlay.TimeAttack) {			
			File dir = this.getWorldDirectory();
			dir.mkdirs();
			File file = new File(dir, this.getResourceName());
			return file;
		}
		
		return null;
	}
	
	private File getWorldDirectory() {
		File root = Environment.getExternalStorageDirectory();
		File dirBase = new File (root.getAbsolutePath() + "/SlimeAttack");									
		File dir = new File (dirBase.getAbsolutePath() + "/" + this.getWorld().getName());
		return dir;
	}

	private File getDumpFile() {
		if (this.mediaAvailable() && this.getGamePlay() == GamePlay.TimeAttack) {				
			File dirWorld = this.getWorldDirectory();
			File dir = new File(dirWorld.getAbsolutePath() + "/Dump");
			dir.mkdirs();
			File file = new File(dir, this.getResourceName());
			return file;
		}
		
		return null;
	}
	
	public void dumpLevel() {
		if (SlimeFactory.IsLevelSelectionOn && this.getGamePlay() == GamePlay.TimeAttack) {
			File fileIn = this.getExternFile();
			File fileOut = this.getDumpFile();
			try {
				this.copy(fileIn, fileOut);
			} catch (IOException e) {
				SlimeFactory.Log.e(Slime.TAG, "ERROR, can not dump file " + this.getResourceName());
				e.printStackTrace();
			}
		}
	}
	
	public void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	
	public void storeLevel(Level level) {
		BufferedWriter buffWriter = null;
		try {
			SlimeFactory.Log.d(Slime.TAG, "Storing level in " + this.getResourceName());
			FileOutputStream fos = null;
			if (SlimeFactory.IsLevelSelectionOn) {				
				File file = this.getExternFile();				
				if (file != null) {
					fos = new FileOutputStream(file);
				}				
			} else {
				fos = SlimeFactory.ContextActivity.openFileOutput(this.getResourcePath(), Context.MODE_PRIVATE);
			}
			
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			// First line: LevelInfo
			LevelInfoDef infoDef = (LevelInfoDef) this.typeHandler.get(LevelInfoDef.Handled_Info);
			infoDef.setValuesSpe(level);
			infoDef.writeLine(buffWriter);
			// Second line: TimeAttackGame
			if (level.getGamePlay() != null) {
					switch (level.getGamePlay().getType()) {
					default:
					case TimeAttack:
						TimeAttackDef gamePlayDef = (TimeAttackDef) this.typeHandler.get(TimeAttackDef.Handled_TimeAttack);
						TimeAttackGame gamePlay = (TimeAttackGame) level.getGamePlay();
						gamePlayDef.setValues(gamePlay);
						gamePlayDef.writeLine(buffWriter);
						break;
					case Survival:
						SurvivalDef gamePlaySDef = (SurvivalDef) this.typeHandler.get(SurvivalDef.Handled_Survival);
						SurvivalGame gamePlayS = (SurvivalGame) level.getGamePlay();
						gamePlaySDef.setValues(gamePlayS);
						gamePlaySDef.writeLine(buffWriter);
				}
			}
						
			// Next lines
			for(GameItem item : level.getItemsToAdd()) {
				if (!this.ignoredClasses.contains(item.getClass())) {
					ItemDefinition itemDef = this.classHandler.get(item.getClass());
					if (itemDef != null) {			
						itemDef.setValues(item);
						itemDef.writeLine(buffWriter);
					}
					else {
						SlimeFactory.Log.e(Slime.TAG, "ERROR, ItemDefinition not found for " + item.getClass().toString() + ". Can not store item in " + this.getResourceName());
					}
				}
			}
		} catch (FileNotFoundException ex) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR, file not found " + this.getResourceName());
			ex.printStackTrace();
        } catch (IOException e1) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR during opening or write of " + this.getResourceName());
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	SlimeFactory.Log.e(Slime.TAG, "ERROR during close of " + this.getResourceName());
                ex.printStackTrace();
            }
        }
	}

	private String getResourcePath() {
		if (this.getWorld() != null) {
			return this.getWorld().getName() + "/" + this.getResourceName();
		} else {
			return this.getResourceName();
		}
		
	}

	public boolean isStored() {
		return SlimeFactory.ContextActivity.getFileStreamPath(this.getResourceName()).exists();
	}
	
	public boolean isStored(String resourcePath) {
		return SlimeFactory.ContextActivity.getFileStreamPath(resourcePath).exists();
	}

	public boolean isLocalStorage() {
		return isLocalStorage;
	}

	public void setLocalStorage(boolean isLocalStorage) {
		this.isLocalStorage = isLocalStorage;
	}
	
	public void resetStorage() {
		SlimeFactory.ContextActivity.deleteFile(this.getResourceName());
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
		if (this.getResourceName() != null) {
			if (this.getResourceName().toUpperCase().contains(SpecialLevel.toUpperCase())) {
				this.isSpecial = true;
			}
			
			int lastPeriodPos = this.getResourceName().lastIndexOf('.');
			if (lastPeriodPos != -1) {
				this.setId(this.getResourceName().substring(0, lastPeriodPos));
			}
			else {
				this.setId(this.getResourceName());
			}
		}
	}
}
