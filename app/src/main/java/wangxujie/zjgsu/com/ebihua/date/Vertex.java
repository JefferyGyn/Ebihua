package wangxujie.zjgsu.com.ebihua.date;

import android.graphics.Rect;

/**
 * Created by xu10858 on 2017/4/23.
 */

public class Vertex {
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
}
