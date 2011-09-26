package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Triangulate;
import gamers.associate.Slime.items.custom.SlimyFactory;

import java.util.ArrayList;
import java.util.Arrays;

import org.cocos2d.types.CGPoint;

public class PolygonDef extends ItemDefinition {
	private static String Handled_Def = "Polygon";
	private float yReference;
	private float heightReference;
	private String path;
	private boolean isdynamic;
	
	@Override
	public void createItem(Level level) {		
		String[] pathBase = this.path.split(" ");
		CGPoint[] polygon = new CGPoint[pathBase.length];
		int i = 0;
		float cumulX = 0;
		float cumulY = 0;
		for (String coordSvgBase : pathBase) {
			String[] coordSvg = coordSvgBase.split(",");			
			float x = Float.parseFloat(coordSvg[0]);
			float y = Float.parseFloat(coordSvg[1]);
			 
			if (i == 0) {
				y = this.yReference + this.heightReference - y;
				this.x = x;
				this.y = y;
				x = 0;
				y = 0;
				cumulX = 0;
				cumulY = 0;
			}
			else {
				cumulX = cumulX + x;
				x = cumulX;
				y = - y;
				
				cumulY = cumulY + y;
				y = cumulY;
			}
			
			CGPoint point = CGPoint.make(x, y);
			polygon[i] = point;
			i++;
		}

		ArrayList<CGPoint> contour = new ArrayList<CGPoint>(Arrays.asList(polygon));
		ArrayList<CGPoint> body = new ArrayList<CGPoint>();
		Triangulate.process(contour, body);		
		
		CGPoint[] points = new CGPoint[body.size()];
		int j = 0;
		for(CGPoint point : body) {
			points[j] = point;
			j++;
		}
		
		SlimeFactory.Polygon.create(this.x, this.y, this.width, this.height, this.isdynamic, polygon, points);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.yReference = Float.parseFloat(infos[start]);
		this.heightReference = Float.parseFloat(infos[start+1]);
		this.path = infos[start + 2];
		this.isdynamic = Boolean.parseBoolean(infos[start+3]);
	}

}
