package wangxujie.zjgsu.com.ebihua.date;

public class Line{

	public final Vertex	sPt;//线起点
	public final Vertex	ePt;//线终点
	
	public final boolean direction;	//1,y1 ==> x2,y2
	public final int nRepeat;	//
	
	public int	nPass = 0;	//
	
	public Line(float x1, float y1, float x2, float y2, boolean dir, int repeat) {
		this((int)x1, (int)y1, (int)x2, (int)y2, dir, repeat);
	}//定义数组
	
	public Line(int x1, int y1, int x2, int y2, boolean dir, int repeat) {
		//开始点
		sPt = new Vertex(x1, y1);
		//结束点
		ePt = new Vertex(x2, y2);
		//线的方向
		direction = dir;
		//重复次数
		nRepeat = repeat;
	}
	
	//判断线是否是同一条线。
	@Override
	public boolean equals(Object o) {
		if(o instanceof Line){
			final Line l = (Line)o;
			return (l.sPt.equals(sPt) && l.ePt.equals(ePt));
		}
		return false;
	}
	//判断点是否重复，实现欧拉回路算法（游戏的开始点和结束点是否为同一个点，同一个点是游戏过关的必要条件）
	public final boolean findVertex(Vertex s, Vertex e){
		return ((sPt.equals(s) && ePt.equals(e)) || (ePt.equals(s) && sPt.equals(e)));
	}
	
	//toString
	@Override
	public String toString() {
		return sPt.toString() + " - " + ePt.toString() + "<"+nRepeat+">" + "  Pass:" + nPass;
	}
}
