package Epackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import Epackage.data.Line;
import Epackage.data.Vertex;
public class GameView extends View {

	private float fWdith, fHeight;
	private	float offX, offY;
	//画线的画笔
	private	Paint linePaint = new Paint();		//划线
	private	Paint trackPaint = new Paint();	//轨迹
	//画点的画笔
	private	Paint vertexPaint = new Paint();
	boolean		bStarting = false;	//击中第一个顶点即开始
	Vertex		lastVertex = null;	//上一次的顶点
	
	//可以形成多种形状，如矩形，长方形，一次曲线，二次曲线
	private	final Path	arrow = new Path();

	//一个点对应一个map，第一个Vertex是线的一个端点，第二个Vertex是线的另一个端点，line是这两点的线
	private Map<Vertex, Map<Vertex, Line>> ascVertex = new HashMap<Vertex, Map<Vertex,Line>>(); 
	
	//顶点集合
	private	final HashSet<Vertex> mVertexSet = new HashSet<Vertex>();
	
	//移动画的线
	private	final Vector<Vertex>    mTrack = new Vector<Vertex>();
	
	private final List<Line> mLines = new ArrayList<Line>();
	
	public GameView(Context context) {
		super(context);
		InitView(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		InitView(getContext());
	}
	
	final void InitView(Context context){
		setClickable(true);
		setFocusable(true);
		
		trackPaint.setAntiAlias(true);
		trackPaint.setColor(0xffffffff);
		trackPaint.setStyle(Style.STROKE);
		trackPaint.setStrokeWidth(10.0f);

		linePaint.setStyle(Style.FILL);
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(6.0f);
		linePaint.setColor(0xffffffff);
		
		vertexPaint.setStyle(Style.FILL);
		vertexPaint.setAntiAlias(true);
		vertexPaint.setColor(0xfffff000);
		vertexPaint.setStrokeWidth(1.5f);
		
		arrow.moveTo(0, 0);
		arrow.lineTo(40, 30);
		arrow.lineTo(20, 0);
		arrow.lineTo(40, -30);
		arrow.close();
		
	}
	
	IGameAction mAction = null;
	
	public final void setGameAction(IGameAction action){
		mAction = action;
	}


	//屏幕适配
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = getMeasuredWidth();
		final int height = getMeasuredHeight();
		if(width > 0 && height > 0){
			fHeight = height * 0.7f;
			fWdith = width * 0.7f;
			offX = (width - fWdith) / 2;
			offY = (height - fHeight) / 2;
			bAfresh = true;
			afreshCalculate();
		}
	}

	float	currFx, currFy;
	private final Vertex findVertex(float x, float y){
		final Iterator<Vertex> iterator2 = mVertexSet.iterator();
		while(iterator2.hasNext()){
			final Vertex v = iterator2.next();
			if(v.contains(x, y))
				return v;
		}
		return null;
	}
	
	@Override

	  //获取点击事件

	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		//获取点击点的坐标
		currFx = event.getX();
		currFy = event.getY();

		switch(action){
		case MotionEvent.ACTION_DOWN:
			mTrack.clear();
			bStarting = false;
			lastVertex = findVertex(currFx, currFy);
			if(lastVertex != null){
				mTrack.add(lastVertex);
				bStarting = true;	//游戏开始
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(!bStarting) return true;
			Vertex v = findVertex(currFx, currFy);
			if (v != null && v != lastVertex) {
				mTrack.add(v);
				lastVertex = v;
			}
			break;
		case MotionEvent.ACTION_UP://手指移开
			if(!bStarting) return true;//
			if(check_unicursal()){//手指移开以后对游戏结果进行判断
				if(mAction != null){//展示过关的界面
					mAction.OnGameToll();
				}
			}else{
				mTrack.clear();
				if(mAction != null){
					mAction.OnGameOver();
				}
			}
			bStarting = false;
			lastVertex = null;
			break;
		case MotionEvent.ACTION_CANCEL:
			if(!bStarting) return true;
			break;
		}
		postInvalidate();
		return true;
	}


	@Override
	protected void onDraw(Canvas canvas) {	//关卡绘图

		super.onDraw(canvas);

		//画正常的线，较细
		linePaint.setStrokeWidth(6.0f);

		//循环画线
		final Iterator<Line> iterator = mLines.iterator();
		while(iterator.hasNext()){
			final Line l = iterator.next();
			if(l.Repeat > 1){
				linePaint.setColor(0xfffff000);//绘图的参数
			}else{
				linePaint.setColor(0xffffffff);
			}

			canvas.drawLine(l.start_point.x, l.start_point.y, l.end_point.x, l.end_point.y, linePaint);
			if(l.direction){

				float x = (l.start_point.x + l.end_point.x ) / 2;
				float y = (l.start_point.y + l.end_point.y ) / 2;

				//将笛卡尔坐标（x,y）转化为 (r, theta) 的 theta 组件
				//将弧度转化为角度

				float degree  = (float) Math.toDegrees(Math.atan2(x, y));

				canvas.save();
				canvas.translate(x, y);
				canvas.rotate(degree);
				canvas.drawPath(arrow, linePaint);
				canvas.restore();
			}
		}

		//画各结点
		final Iterator<Vertex> iterator2 = mVertexSet.iterator();
		while(iterator2.hasNext()){
			final Vertex v = iterator2.next();
			canvas.drawCircle(v.x, v.y, 16, vertexPaint);
		}

		//画连接线的画笔。较粗
		linePaint.setStrokeWidth(10.0f);
		linePaint.setColor(0xffff0000);
      //画已经点击点之间的连接
		for(int i=1; i<mTrack.size(); i++){
			final Vertex s = mTrack.get(i-1);
			final Vertex e = mTrack.get(i);

			canvas.drawLine(s.x, s.y, e.x, e.y, linePaint);
			canvas.drawCircle(s.x, s.y, 20, vertexPaint);
			canvas.drawCircle(e.x, e.y, 20, vertexPaint);
		}
		//连接上一次正确点击的点与当前手指点击的点之间的连接与圆圈重绘
		final Vertex v = lastVertex;
		if(v != null){
			canvas.drawLine(v.x, v.y, currFx, currFy, trackPaint);
			canvas.drawCircle(v.x, v.y, 20, vertexPaint);
			canvas.drawCircle(currFx, currFy, 20, vertexPaint);
		}
	}

	int	nMaxX, nMaxY;
	int[][]	souLines;
	boolean	bAfresh = true;


	//对关卡数据进行处理成线，初始化关卡，显示图形


	final void afreshCalculate(){
		if(!bAfresh) return ;
		if(souLines == null || fWdith <= 0.0f || fHeight <= 0.0f){
			return ;
		}

		//清空之前所有数据，为重新加入做准备
		Iterator<Vertex> i = ascVertex.keySet().iterator();
		while(i.hasNext()){
			ascVertex.get(i.next()).clear();
		};
		ascVertex.clear();
		mVertexSet.clear();
		mLines.clear();
		mTrack.clear();

		 System.gc();

		nMaxX = nMaxY = 0;
		for(int[] line : souLines){
			nMaxX = Math.max(nMaxX, Math.max(line[0], line[2]));
			nMaxY = Math.max(nMaxY, Math.max(line[1], line[3]));
		}

		float sx = fWdith / nMaxX;
		float sy = fHeight / nMaxY;
		float x = offX ;
		float y = offY + fHeight;

		for(int[] line : souLines){

			Line l = new Line((int)(x+line[0]*sx), (int)(y-line[1]*sy),
					(int)(x+line[2]*sx), (int)(y-line[3]*sy),
					line[4] != 0, line[5]);//取出二维数组预处理 并且转换成实际需要的像素点

			mVertexSet.add(l.start_point);//把线里的点加到点的集合里面，相同的点只会成功加入一次
			mVertexSet.add(l.end_point);
			mLines.add(l);//把线加入线的集合里

			if(!ascVertex.containsKey(l.start_point)){//起点对应的map不存在时
				ascVertex.put(l.start_point, new HashMap<Vertex, Line>());
			}
			if(!ascVertex.containsKey(l.end_point)){//终点对应的map不存在时
				ascVertex.put(l.end_point, new HashMap<Vertex, Line>());
			}

			ascVertex.get(l.start_point).put(l.end_point, l);//将线加入起点对应的map中
			ascVertex.get(l.end_point).put(l.start_point, l);//将线加入终点对应的map中
		}
		bAfresh = false;

		//刷新界面

		postInvalidate();

	}
	/**
	 * 给每个关卡设置各种线，从二维数组里加载
	 * @param lines
	 */
	public final void setLines(int[][] lines){
		souLines = lines;
		bAfresh = true;
		afreshCalculate();
	}

	private final boolean check_unicursal(){
		try{
			for(int i=1; i<mTrack.size(); i++){
				final Vertex s = mTrack.get(i-1);
				final Vertex e = mTrack.get(i);
				final Line l = ascVertex.get(s).get(e);
				if(l == null) return false;//没有此线段
				if(l.direction){
					if(!l.start_point.equals(s)){
						//具有方向的线段

						return false; //
					}
				}
				l.Pass ++;
				if(l.Repeat < l.Pass){
					//超过指定次数
					return false;
				}
			}//第一次检查，所有画的线都存在，并且次数没有超过规定次数
			for(Line l : mLines){
				if(l.Repeat != l.Pass){
					return false;
				}
			}//第二次检查画线的次数不足的情况、
			return true;
		}finally{
			for(Line l : mLines){
				l.Pass = 0;
			}
		}
	}

	public void setGameOver(){
		mAction.OnGameOver();
	}
}
