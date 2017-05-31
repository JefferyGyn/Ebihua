package Epackage.data;

import android.graphics.Rect;

public class Vertex{
	final Rect rect = new Rect();
	//
	public int x, y;
	//
	public int radii = 20;
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		rect.set(x-radii, y-radii, x+radii, y+radii);
	}
	//
	@Override
	public Vertex clone(){ //????????????????????????????????????????????
		return new Vertex(x, y);
	}

	public boolean contains(float x, float y){
		return rect.contains((int)x, (int)y);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vertex){
			final Vertex v = (Vertex)o;
			return ((v.x == x) && (v.y == y));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return ((x << 16) | y);
	}


}
