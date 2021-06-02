package com.briansea.paintme;


import java.lang.reflect.Array;
import java.util.Random;

import android.content.Context;

import android.view.MotionEvent;
import android.view.View;

import android.util.AttributeSet;
import android.util.Log;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Color;


import java.util.ArrayList;

/**
 * Created by bsea on 1/24/18.
 *
 * The GUI for the custom drawing pane
 */

public class DrawingPane extends View {

    private Paint drawingStyle;         // Current style to use for painting
    private ArrayList<Stamp> stamps;    // Stamps on the screen
    private ArrayList<Paint> stampStyles;
    private ArrayList<Stamp> changeStamps;
    private ArrayList<Paint> changePaint;

    private Random rand;

    private Stamp selectedStamp;
    private Stamp drawing;


    public DrawingPane(Context context, AttributeSet attrs ){
        super( context, attrs );
        setFocusable(true);
        setFocusableInTouchMode(true);
        drawingStyle = setupPaint(Color.BLACK);


        this.setOnTouchListener( new DrawStamp() );
        stamps = new ArrayList<Stamp>();
        stampStyles = new ArrayList<Paint>();


        rand = new Random();
    }

    // Creates a default paint style
    private Paint setupPaint( int strokeColor ){
        Paint style = new Paint();
        style.setColor( strokeColor );
        style.setAntiAlias( true );
        style.setStrokeWidth( 5 );
        style.setStyle( Paint.Style.FILL_AND_STROKE );
        style.setStrokeJoin( Paint.Join.ROUND );
        style.setStrokeCap( Paint.Cap.ROUND );
        return style;
    }

    @Override
    /**
     * Called each time the View needs to be drawn
     */

    protected void onDraw( Canvas canvas ){



        for(int i = 0; i < stamps.size(); i++) {
            stamps.get(i).draw(canvas, stampStyles.get(i));
        }
        if(drawing != null) {
            drawing.draw(canvas, drawing.getStyle());
        }

        // Draw the current stamp


    }

    /**
     * Sets the stamps to use when drawing
     * @param stamp the stamp to use
     */
    public void setStamp( Stamp stamp ) {
        this.selectedStamp = stamp;
    }

    public ArrayList<Stamp> getStamps() {return stamps;}
    public ArrayList<Paint> getStampStyles() {return stampStyles;}

    public void setStamps( ArrayList<Stamp> s, ArrayList<Paint> p) {
        stamps = s;
        stampStyles = p;
        invalidate();
    }

    private class DrawStamp implements View.OnTouchListener {

        // location of the initial touch when drawing a stamp
       private Point downPoint;

       public boolean onTouch( View view, MotionEvent event ) {
            if( event.getAction() == MotionEvent.ACTION_DOWN && selectedStamp != null ){



                drawing = selectedStamp.newInstance();




                int randColor = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                //int randColor = (int)(Math.random()*255);
                drawingStyle.setColor(randColor);
                drawing.setStyle( drawingStyle );


                downPoint = new Point( (int)event.getX() , (int)event.getY() );



                for(int i = stamps.size()-1; i >= 0 ; i--) {
                    if(stamps.get(i).contains(downPoint, stamps.get(i))== true){
                        changeStamps = stamps;
                        changePaint = stampStyles;
                        changeStamps.add(stamps.get(i));
                        changePaint.add(stampStyles.get(i));
                        changeStamps.remove(i);
                        changePaint.remove(i);
                        setStamps(changeStamps, changePaint);
                        break;
                    }
                }



            }

            // Safety valve to make sure a tool must be selected
            if( selectedStamp == null ){
                return true;
            }

            if( event.getAction() == MotionEvent.ACTION_MOVE ){
                Point movePoint = new Point( (int)event.getX(), (int)event.getY() ); //if cursor moves, create new point movePoint.



                if(movePoint.x <= downPoint.x && movePoint.y >= downPoint.y){ //left and down
                    drawing.setTopLeft( new Point(movePoint.x, downPoint.y));

                }
                if(movePoint.y <= downPoint.y && movePoint.x >= downPoint.x){ //right and up
                    drawing.setTopLeft( new Point(downPoint.x, movePoint.y ));

                }
                if(movePoint.x <= downPoint.x && downPoint.y >= movePoint.y){ //left and up
                    drawing.setTopLeft( movePoint );

                }
                if(movePoint.x >= downPoint.x && movePoint.y >= downPoint.y) { //right and down
                    drawing.setTopLeft( downPoint );

                }



                drawing.setDimensions(downPoint.x-movePoint.x, downPoint.y-movePoint.y);


                //force a redraw of the stamps
                invalidate();
            }

            if( event.getAction() == MotionEvent.ACTION_UP ){
                Point upPoint = new Point( (int) event.getX(), (int) event.getY() );


                if(drawing.height() != 0 && drawing.width() != 0) {
                    stamps.add(drawing);
                    stampStyles.add(new Paint(drawing.getStyle()));
                }

                drawing = null;



                // Force a redraw
                invalidate();
            }

            // true means the event does not "bubble up"
            return true;
       }
    }

}
