package com.briansea.paintme;

/**
 * Created by bsea on 1/24/18.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;

/**
 * A rectangular stamp
 */
public class RectStamp extends Stamp{

    public RectStamp() {
        super();
    }

    public RectStamp( RectStamp other ) {
        super(other);
    }

    /**
     * Draws the stamp on a canvas
     * @param canvas the canvas to draw on
     * @param paint the paint attributes to use when drawing
     */
    public void draw( Canvas canvas, Paint paint ) {

        Point topLeft = getTopLeft();
        RectF rect = new RectF(topLeft.x, topLeft.y,
                topLeft.x+getWidth(), topLeft.y+getHeight() );
        canvas.drawRect(rect, paint);

    }

    public boolean contains(Point p, Stamp s) {
        if (p.x < s.getTopLeft().x + s.width()) { //point click is less than max x value
            if (p.x > s.getTopLeft().x) { //point click is greater than min x value
                if (p.y < s.getTopLeft().y + s.height()) { // less than max y value
                    if (p.y > s.getTopLeft().y) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    /**
     * Returns a new instance of the current type
     */
    public Stamp newInstance(){
        return new RectStamp();
    }
}
