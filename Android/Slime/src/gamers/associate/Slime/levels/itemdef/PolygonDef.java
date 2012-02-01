package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.PhysicPolygon;

import java.util.ArrayList;

import org.cocos2d.types.CGPoint;

public class PolygonDef extends ItemDefinition {
	private static String RealPath = "REAL";
	private static String PointSep = " ";
	private static String CoordSep = ",";
	private static String Handled_Def = "Polygon";
	private float yReference;
	private float heightReference;
	// 2 modes: 
	// - path from SVG (needs more parsing)
	// - path from real points (noparsing needed)
	private String path;
	private boolean isdynamic;	
	private boolean isStickable;
	private boolean isEmpty;
	
	@Override
	public void createItem(Level level) {				
		ArrayList<CGPoint> polygon = new ArrayList<CGPoint>();
		if (this.path.substring(0, RealPath.length()).toUpperCase() == RealPath) {
			// Read path directly
			String pathMid = this.path.substring(RealPath.length());
			String[] pathBase = pathMid.split(PointSep);
			for(String coordBase : pathBase) {
				String[] coord = coordBase.split(CoordSep);
				if (coord.length > 1) {
					float x = Float.parseFloat(coord[0]);
					float y = Float.parseFloat(coord[1]);
					CGPoint point = CGPoint.make(x, y);
					polygon.add(point);
				}								
			}
		} else {
			// Compute path based on SVG
			String[] pathBase = this.path.split(PointSep);
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
					String[] coordSvg = coordSvgBase.split(CoordSep);			
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
		}

		CGPoint[] realPoints = new CGPoint[polygon.size()];
		int j = 0;
		for(CGPoint point : polygon) {
			realPoints[j] = point;
			j++;
		}
		
		int polyType = PhysicPolygon.Fill;
		if (this.isEmpty) {
			polyType = PhysicPolygon.Empty;
		}
		
		SlimeFactory.Polygon.create(this.getX(), this.getY(), this.width, this.height, this.isdynamic, realPoints, polyType, this.isStickable);
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
		try {
			this.isStickable = Boolean.parseBoolean(infos[start+4]);
			this.isEmpty = Boolean.parseBoolean(infos[start+5]);
		} catch (Exception ex) {
			this.isStickable = true;
			this.isEmpty = false;
		}
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(PhysicPolygon.class);
		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(yReference));
		line = this.addValue(line, String.valueOf(heightReference));
		line = this.addValue(line, this.path);
		line = this.addValue(line, String.valueOf(this.isdynamic));
		line = this.addValue(line, String.valueOf(this.isStickable));
		line = this.addValue(line, String.valueOf(this.isEmpty));
		
		return line;		
	}
	
	private String createPathString(CGPoint[] points) {
		String p = RealPath;
		int i = 0;
		for(CGPoint point : points) {
			if (i > 0) {
				p += PointSep ;
			}
			
			String coord = point.x + CoordSep + point.y;
			p += coord;
			i++;
		}
		
		return p;
	}

	@Override
	protected boolean getIsBL() {
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		PhysicPolygon poly = (PhysicPolygon)item;
		this.yReference = 0;
		this.heightReference = 0;
		this.path = this.createPathString(poly.getBodyPoints());
		this.isdynamic = poly.isDynamic();
		this.isStickable = !poly.isNoStick();
		this.isEmpty = poly.getType() == PhysicPolygon.Empty;
	}
}
