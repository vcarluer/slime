package gamers.associate;

import java.util.ArrayList;

public class TestIt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println("");
		ArrayList<Vector2> contour = new ArrayList<Vector2>();
		contour.add(Vector2.make(0, 0));
		contour.add(Vector2.make(80, 0));
		contour.add(Vector2.make(77, 20));
		contour.add(Vector2.make(70, 35));
		contour.add(Vector2.make(50, 50));
		contour.add(Vector2.make(20, 40));
		write(contour, "source");
		ArrayList<Vector2> result = new ArrayList<Vector2>();		
		Triangulate.process(contour, result);				
		write(result, "target");		
	}
	
	private static void write(ArrayList<Vector2> vectors, String name) {
		System.out.println(name + ":");
		for	(Vector2 v : vectors) {
			System.out.println(String.valueOf(v.x) + ", " + String.valueOf(v.y));
		}
	}
}
