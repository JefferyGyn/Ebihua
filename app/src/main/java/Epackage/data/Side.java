package Epackage.data;

import java.util.HashMap;


//边
public class Side extends Vertex {

	//关联点形成多条边
	private HashMap<Vertex, Line> mSide = new HashMap<Vertex, Line>();

	public Side(int x, int y) {
		super(x, y);
	}

	public Side(Vertex v){
		super(v.x, v.y);
	}
	
	public void addSide(Line l){
		if(l.start_point.equals(this)){
			mSide.put(l.end_point, l);
		}else if(l.end_point.equals(this)){
			mSide.put(l.start_point, l);
		}
	}
}
