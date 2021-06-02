package com.briansea.paintme;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private LinearLayout toolbox;
    private DrawingPane drawingArea;
    private Button clearButton;
    private Button removeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab the objects from the UI
        toolbox = findViewById(R.id.toolbox);
        drawingArea = findViewById( R.id.drawingarea );

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new clearButtonListener());

        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new removeButtonListener());


        // Stamps to create buttons for
        // TODO: Add new stamps below
        Stamp[] tools = { new RectStamp(), new EllipseStamp(), new TriStamp() };

        View.OnClickListener listener = new ToolSelector();

        // Create the tool buttons and attach listeners
        for( Stamp tool : tools ) {
            Tool t = new Tool(toolbox.getContext(), null);
            t.setMinimumHeight(200);
            t.setStamp(tool);
            t.setOnClickListener( listener );
            toolbox.addView(t);
        }
    }

    public class ToolSelector implements View.OnClickListener {
        public void onClick( View view ) {
            Tool selectedTool = (Tool) view;
            drawingArea.setStamp(selectedTool.getStamp().newInstance());
        }
    }

    private class clearButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            drawingArea.setStamps(new ArrayList<Stamp>(), new ArrayList<Paint>());
        }
    }

    private class removeButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            if(drawingArea.getStamps().size() > 0) {
                ArrayList<Stamp> editStamps = drawingArea.getStamps();
                ArrayList<Paint> editPaint = drawingArea.getStampStyles();
                editStamps.remove(editStamps.size()-1);
                editPaint.remove(editPaint.size()-1);
                drawingArea.setStamps(editStamps, editPaint);
            }
        }
    }

}
