package com.briansea.paintme;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

/**
 * Created by thebr on 2/3/2018.
 */

public class TriStamp extends Stamp {


    public TriStamp() {
        super();
    }

    public TriStamp( TriStamp other ) {
        super(other);
    }

    /**
     * Draws the stamp on a canvas
     * @param canvas the canvas to draw on
     * @param paint the paint attributes to use when drawing
     */
    public void draw(Canvas canvas, Paint paint ) {



        Point topLeft = getTopLeft();
        RectF rect = new RectF(topLeft.x, topLeft.y,
                topLeft.x+getWidth(), topLeft.y+getHeight() );

        Point point1;
        Point point2;
        Point point3;

        if(invertedY()) {
            point1 = new Point((int) (topLeft.x + getWidth() / 2), topLeft.y);
            point2 = new Point(topLeft.x, (int) (topLeft.y + getHeight()));
            point3 = new Point((int) (topLeft.x + getWidth()), (int) (topLeft.y + getHeight()));
        }
        else{
            point1 = new Point((int) (topLeft.x + getWidth() / 2), (int)(topLeft.y+getHeight()));
            point2 = new Point(topLeft.x, topLeft.y);
            point3 = new Point((int) (topLeft.x + getWidth()), topLeft.y);
        }


        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1.x,point1.y);
        path.lineTo(point2.x,point2.y);
        path.lineTo(point3.x,point3.y);
        path.lineTo(point1.x,point1.y);
        path.close();

        canvas.drawPath(path, paint);
    }




    public boolean contains(Point p, Stamp s) {
        return true;
    }

    @Override
    /**
     * Returns a new instance of the current type
     */
    public Stamp newInstance(){
        return new TriStamp();
    }
}
