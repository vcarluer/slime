package gamers.associate;

public class Vector2 {
	public float x;
	public float y;
	
	public static Vector2 make(float x, float y) {
		Vector2 v = new Vector2();
		v.x = x;
		v.y = y;
		return v;
	}
}
