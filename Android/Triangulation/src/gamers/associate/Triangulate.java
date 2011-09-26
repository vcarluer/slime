package gamers.associate;

import java.util.ArrayList;

//Original source: http://flipcode.net/archives/Efficient_Polygon_Triangulation.shtml
public class Triangulate {
	private static float EPSILON = 0.0000000001f;

	private static float area(ArrayList<Vector2> contour) {
		int n = contour.size();

		float A = 0.0f;

		for (int p = n - 1, q = 0; q < n; p = q++) {
			A += contour.get(p).x * contour.get(q).y - contour.get(q).x
					* contour.get(p).y;
		}

		return A * 0.5f;
	}

	private static boolean insideTriangle(float Ax, float Ay, float Bx,
			float By, float Cx, float Cy, float Px, float Py) {

		float ax, ay, bx, by, cx, cy, apx, apy, bpx, bpy, cpx, cpy;
		float cCROSSap, bCROSScp, aCROSSbp;

		ax = Cx - Bx;
		ay = Cy - By;
		bx = Ax - Cx;
		by = Ay - Cy;
		cx = Bx - Ax;
		cy = By - Ay;
		apx = Px - Ax;
		apy = Py - Ay;
		bpx = Px - Bx;
		bpy = Py - By;
		cpx = Px - Cx;
		cpy = Py - Cy;

		aCROSSbp = ax * bpy - ay * bpx;
		cCROSSap = cx * apy - cy * apx;
		bCROSScp = bx * cpy - by * cpx;

		return ((aCROSSbp >= 0.0f) && (bCROSScp >= 0.0f) && (cCROSSap >= 0.0f));
	}

	private static boolean snip(ArrayList<Vector2> contour, int u, int v,
			int w, int n, int[] V) {
		int p;
		float Ax, Ay, Bx, By, Cx, Cy, Px, Py;

		Ax = contour.get(V[u]).x;
		Ay = contour.get(V[u]).y;

		Bx = contour.get(V[v]).x;
		By = contour.get(V[v]).y;

		Cx = contour.get(V[w]).x;
		Cy = contour.get(V[w]).y;

		if (EPSILON > (((Bx - Ax) * (Cy - Ay)) - ((By - Ay) * (Cx - Ax))))
			return false;

		for (p = 0; p < n; p++) {
			if ((p == u) || (p == v) || (p == w))
				continue;
			Px = contour.get(V[p]).x;
			Py = contour.get(V[p]).y;
			if (Triangulate.insideTriangle(Ax, Ay, Bx, By, Cx, Cy, Px, Py))
				return false;
		}

		return true;
	}
	
	public static boolean process(ArrayList<Vector2> contour,ArrayList<Vector2> result)
	{
	  /* allocate and initialize list of Vertices in polygon */

	  int n = contour.size();
	  if ( n < 3 ) return false;

	  int[] V = new int[n];

	  /* we want a counter-clockwise polygon in V */

	  if ( 0.0f < Triangulate.area(contour) )
	    for (int v=0; v<n; v++) V[v] = v;
	  else
	    for(int v=0; v<n; v++) V[v] = (n-1)-v;

	  int nv = n;

	  /*  remove nv-2 Vertices, creating 1 triangle every time */
	  int count = 2*nv;   /* error detection */

	  for(int m=0, v=nv-1; nv>2; )
	  {
	    /* if we loop, it is probably a non-simple polygon */
	    if (0 >= (count--))
	    {
	      //** Triangulate: ERROR - probable bad polygon!
	      return false;
	    }

	    /* three consecutive vertices in current polygon, <u,v,w> */
	    int u = v  ; if (nv <= u) u = 0;     /* previous */
	    v = u+1; if (nv <= v) v = 0;     /* new v    */
	    int w = v+1; if (nv <= w) w = 0;     /* next     */

	    if ( Triangulate.snip(contour,u,v,w,nv,V) )
	    {
	      int a,b,c,s,t;

	      /* true names of the vertices */
	      a = V[u]; b = V[v]; c = V[w];

	      /* output Triangle */
	      result.add(contour.get(a));
	      result.add(contour.get(b));
	      result.add(contour.get(c));

	      m++;

	      /* remove v from remaining polygon */
	      for(s=v,t=v+1;t<nv;s++,t++) V[s] = V[t]; nv--;

	      /* resest error detection counter */
	      count = 2*nv;
	    }
	  }

	  return true;
	}
}
