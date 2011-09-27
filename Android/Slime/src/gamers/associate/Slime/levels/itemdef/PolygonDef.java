package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Triangulate;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.custom.SlimyFactory;

import java.util.ArrayList;
import java.util.Arrays;

import org.cocos2d.types.CGPoint;

public class PolygonDef extends ItemDefinition {
	private static String Handled_Def = "Polygon";
	private static String Relative = "m"; // M = Absolute
	private float yReference;
	private float heightReference;
	private String path;
	private boolean isdynamic;
	
	@Override
	public void createItem(Level level) {		
		String[] pathBase = this.path.split(" ");
		ArrayList<CGPoint> polygon = new ArrayList<CGPoint>();
		float cumulX = 0;
		float cumulY = 0;
		int i = 0;
		boolean isRelative = false;
		for (String coordSvgBase : pathBase) {			
			if (coordSvgBase.length() == 1) {
				char ch = coordSvgBase.charAt(0);
				if (Character.isLowerCase(ch)) {				
					isRelative = true;
				}
				else {
					isRelative = false;
				}
			}
			else {
				String[] coordSvg = coordSvgBase.split(",");			
				float x = Float.parseFloat(coordSvg[0]);
				float y = Float.parseFloat(coordSvg[1]);
								
				if (i == 0) {
					y = this.yReference + this.heightReference - y;
					this.x = x;
					this.y = y;
					x = 0;
					y = 0;
				}
				else {
					if (isRelative) {
						x = cumulX + x;
						
						y = - y;
						y = cumulY + y;
					}
					else {
						x = x - this.x;
						y = this.yReference + this.heightReference - y;
						y = y - this.y;
					}
				}			
				
				CGPoint point = CGPoint.make(x, y);
				cumulX = x;
				cumulY = y;
				polygon.add(point);
				i++;
			}						
		}

		CGPoint[] realPoints = new CGPoint[polygon.size()];
		int j = 0;
		for(CGPoint point : polygon) {
			realPoints[j] = point;
			j++;
		}
		
		SlimeFactory.Polygon.create(this.x, this.y, this.width, this.height, this.isdynamic, realPoints);
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
