package com.dobando.golly;

import android.app.Activity;
import android.os.Bundle;
import com.dobando.golly.UI.GameView;
import android.widget.LinearLayout;
import com.dobando.golly.Game.Land;
import android.view.Display;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity 
{
	public static int width;
	public static int height;
	
	private LinearLayout ml;
	private GameView landView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		ml = (LinearLayout)findViewById(R.id.mainLinearLayout1);
		landView = new GameView(MainActivity.this);
		ml.setLayoutParams(new LayoutParams(width,width));
		ml.addView(landView);
    }
}
