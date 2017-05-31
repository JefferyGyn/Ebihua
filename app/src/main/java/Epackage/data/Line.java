package Epackage.data;

public class Line{

	public final Vertex start_point;//线起点
	public final Vertex end_point;//线终点
	
	public final boolean direction;	//1,y1 ==> x2,y2
	public final int Repeat;	//
	
	public int	Pass = 0;	//
	
	public Line(float x1, float y1, float x2, float y2, boolean dir, int repeat) {
		this((int)x1, (int)y1, (int)x2, (int)y2, dir, repeat);
	}//定义数组
	
	public Line(int x1, int y1, int x2, int y2, boolean dir, int repeat) {
		//开始点
		start_point = new Vertex(x1, y1);
		//结束点
		end_point = new Vertex(x2, y2);
		//线的方向
		direction = dir;
		//重复次数
		Repeat = repeat;
	}
	
	//判断线是否是同一条线。
	@Override
	public boolean equals(Object o) {
		if(o instanceof Line){
			final Line l = (Line)o;
			return (l.start_point.equals(start_point) && l.end_point.equals(end_point));
		}
		return false;
	}
	//判断点的重复
	public final boolean findVertex(Vertex s, Vertex e){
		return ((start_point.equals(s) && end_point.equals(e)) || (end_point.equals(s) && start_point.equals(e)));
	}



}
