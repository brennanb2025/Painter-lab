package com.briansea.paintme;

/**
 * Created by bsea on 1/24/18.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.Point;
import android.graphics.RectF;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Represents the custom stamps to draw on the screen
 */
public abstract class Stamp extends RectShape{

    private Paint style;            // Color and style of stamp
    private Point topLeft;          // Upper Left corner of the rectangular area
    private int width;              // negative indicates inverted around X-Axis
    private int height;             // negative indicated inverted around Y-Axis


    public Stamp() {
        style = new Paint();
        topLeft = new Point(0,0);
    }

    public Stamp( Stamp other ) {
        this.style = new Paint(other.style);
        this.topLeft = new Point( other.topLeft.x, other.topLeft.y);
    }

    public void draw( Canvas canvas, Paint paint ){
        super.draw( canvas, paint );
    }





    /**
     * Indicates if the stamp is inverted around the X-Axis
     * @return true if inverted, false otherwise
     */
    public boolean invertedX(){
        return width < 0;
    }

    /**
     * Indicated if the stamp is inverted around the Y-Axis
     * @return true if inverted, false otherwise
     */
    public boolean invertedY() {
        return height < 0;
    }

    /**
     * Set the top-left coordinate of the rectangular area of the stamp
     * @param tl the top-left point
     * @return true if the stamp's top-left point is set correctly, false otherwise
     */
    public boolean setTopLeft( Point tl ) {
        boolean rtn = false;
        if( tl != null ){
            topLeft = tl;

            RectF topLeft = this.rect();
            topLeft.left = tl.x;
            topLeft.top = tl.y;
            rtn = true;
        }
        return rtn;
    }

    /**
     * the Top-Left coordinate of the rectangular area in which the stamp is drawn
     * @return the Point of the Top-Left corner
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Sets the drawing style for this stamp
     * @param style painting component to use
     * @return true if the style is changed, false otherwise
     */
    public boolean setStyle( Paint style ) {
        boolean rtn = false;
        if( style != null ) {
            this.style = style;
            rtn = true;
        }
        return rtn;
    }

    /**
     * Sets the width and height of the stamp
     * @param width width of the stamp; negative numbers indicate inversion
     * @param height height of the stamp; negative numbers indicate inversion
     */
    public void setDimensions( int width, int height ){
        this.width = width;
        this.height = height;

        this.resize( width(), height() );
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

    /**
     * The width of the rectangular area containing the stamp
     * @return a non-negative integer that is the stamp's width
     */
    public int width() {
        return Math.abs(this.width);
    }

    /**
     * The height of the rectangular area containing the stamp
     * @return a non-negative integer that is the stamp's height
     */
    public int height() {
        return Math.abs(this.height);
    }

    /**
     * Gets the current style (color, stoke size, etc) of the stamp
     * @return the style of the stamp
     */
    public Paint getStyle(){
        return style;
    }

    /**
     * Get a new object of the same type as this
     * @return a new, default object of this
     */
    public abstract Stamp newInstance();

}
