package com.briansea.paintme;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.graphics.Point;

import android.graphics.Canvas;
/**
 * Represents a graphics button with a custom stamp drawn on top of it
 * Created by bsea on 1/24/18.
 */

public class Tool extends ImageButton{

    // The stamp to be drawn on the button
    private Stamp stamp;

    /**
     * Create a new button attached to parent view
     * @param context the parent context
     * @param attrs options to use for the button
     */
    public Tool( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    /**
     * @param stamp the new stamp to draw on the button
     */
    public void setStamp( Stamp stamp ) {
        this.stamp = stamp;
    }

    /**
     * @return the stamp drawn on the button
     */
    public Stamp getStamp() {
        return stamp;
    }

    @Override
    /**
     * Called each time that the button needs to be drawn on the screen
     */
    public void onDraw( Canvas canvas ) {
        super.onDraw(canvas);

        // Make sure we have a stamp
        if( stamp != null ){

            // Place the stamp in the button and resize it
            stamp.setTopLeft(new Point(getWidth()/4, getHeight()/4 ));
            stamp.resize(getWidth()/2,getHeight()/2);

            // Draw onto the button
            stamp.draw(canvas, stamp.getStyle());
        }
    }

}
