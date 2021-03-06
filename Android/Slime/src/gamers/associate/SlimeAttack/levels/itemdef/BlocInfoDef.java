package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.base.ITrigerable;
import gamers.associate.SlimeAttack.items.custom.Button;
import gamers.associate.SlimeAttack.items.custom.Camera;
import gamers.associate.SlimeAttack.levels.generator.BlocDefinition;
import gamers.associate.SlimeAttack.levels.generator.BlocDefinitionParser;
import gamers.associate.SlimeAttack.levels.generator.BlocDirection;
import gamers.associate.SlimeAttack.levels.generator.BlocInfoParser;
import gamers.associate.SlimeAttack.levels.generator.Connector;
import gamers.associate.SlimeAttack.levels.generator.LevelGenNode;

import java.util.ArrayList;
import java.util.List;

public class BlocInfoDef extends ItemDefinition {
	public static String Handled_Info = "BlocInfo";
	private static String connectorsSep = ",";
	private static String facesSep = ",";
	
	private String id;
	private int complexity;
	@Override
	public void postBuild() {		
		super.postBuild();
		
		ArrayList<GameItem> items = new ArrayList<GameItem>(Level.currentLevel.getItemsToAdd());
		for(GameItem item : items) {
			if (item instanceof Button) {
				Button button = (Button) item;
				ArrayList<ITrigerable> targets = Level.currentLevel.getTrigerablesInItemToAdd(button.getTarget());
				if (targets.size() == 0) {
					Level.currentLevel.addItemToRemove(item);
				}
			}
			
			if (item instanceof Camera) {
				Camera camera = (Camera) item;
				ArrayList<ITrigerable> targets = Level.currentLevel.getTrigerablesInItemToAdd(camera.getTargetName());
				if (targets.size() == 0) {
					Level.currentLevel.addItemToRemove(item);
				}
			}
		}			
	}

	private String entries;
	private String exits;	
	private boolean isStart;
	private boolean isEnd;
	private boolean isBoss;
	private String openFaces;
	
	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Info);
	}

	@Override
	protected void initClassHandled() {
		// NONE
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.id = infos[start];
		this.complexity = Integer.valueOf(infos[start + 1]).intValue();
		this.entries = infos[start + 2];
		this.exits = infos[start + 3];		
		this.isStart = Boolean.valueOf(infos[start + 4]).booleanValue();
		this.isEnd = Boolean.valueOf(infos[start + 5]).booleanValue();
		this.isBoss = Boolean.valueOf(infos[start + 6]).booleanValue();
		this.openFaces = infos[start + 7];
	}

	
	public void createItem(Level level) {		
	}
	
	public void createItem(String resource) {	
		BlocDefinition bloc = new BlocDefinitionParser(resource);
		//Todo: bloc width here
		LevelGenNode node = new LevelGenNode();
		node.setId(id);
		node.setComplexity(this.complexity);
		node.addConnectorsEntry(this.getConnectors(this.entries));
		node.addConnectorsExit(this.getConnectors(this.exits));
		node.addConnectors(this.getConnectorsFaces(this.openFaces));
		node.setIsLevelStart(this.isStart);
		node.setIsLevelEnd(this.isEnd);
		node.setBoss(this.isBoss);
		
		bloc.setGenNode(node);
		BlocInfoParser.Generator.addNode(node);
	}

	private List<Integer> getConnectorsFaces(String openFacesParam) {		
		List<Integer> list = new ArrayList<Integer>();
		if (!openFacesParam.equals(ItemDefinition.None)) {
			String[] faces = openFacesParam.split(facesSep, -1);
			for(String faceStr : faces) {
				String face = faceStr.trim().toLowerCase();
				if (face.equals("t")) {
					list.addAll(LevelGenNode.getConnectorsFor(BlocDirection.Top));
				}
				if (face.equals("r")) {
					list.addAll(LevelGenNode.getConnectorsFor(BlocDirection.Right));
				}
				if (face.equals("b")) {
					list.addAll(LevelGenNode.getConnectorsFor(BlocDirection.Bottom));
				}
				if (face.equals("l")) {
					list.addAll(LevelGenNode.getConnectorsFor(BlocDirection.Left));
				}
			}
		}
		
		return list;
	}

	private List<Integer> getConnectors(String connParam) {
		List<Integer> list = new ArrayList<Integer>();
		String[] connectors = connParam.split(connectorsSep, -1);
		for(String connectorStr : connectors) {
			String tc = connectorStr.trim().toLowerCase();
			if (tc.equals("tl")) {
				list.add(Connector.TopLeft);
			}
			if (tc.equals("tm")) {
				list.add(Connector.TopMid);
			}
			if (tc.equals("tr")) {
				list.add(Connector.TopRight);
			}
			
			if (tc.equals("rt")) {
				list.add(Connector.RightTop);
			}
			if (tc.equals("rm")) {
				list.add(Connector.RightMid);
			}
			if (tc.equals("rb")) {
				list.add(Connector.RightBottom);
			}
			
			if (tc.equals("bl")) {
				list.add(Connector.BottomLeft);
			}
			if (tc.equals("bm")) {
				list.add(Connector.BottomMid);
			}
			if (tc.equals("br")) {
				list.add(Connector.BottomRight);
			}
			
			if (tc.equals("lt")) {
				list.add(Connector.LeftTop);
			}
			if (tc.equals("lm")) {
				list.add(Connector.LeftMid);
			}
			if (tc.equals("lb")) {
				list.add(Connector.LeftBottom);
			}
		}
		
		return list;
	}

	@Override
	protected String writeNext(String line) {
		return null;
	}

	@Override
	protected boolean getIsBL() {
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return null;
	}

	@Override
	protected void setValuesNext(GameItem item) {
	}

}
