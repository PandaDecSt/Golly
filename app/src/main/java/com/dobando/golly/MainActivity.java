package com.dobando.golly;

import android.app.Activity;
import android.os.Bundle;
import com.dobando.golly.UI.GameView;
import android.widget.LinearLayout;
import com.dobando.golly.Game.Land;

public class MainActivity extends Activity 
{
	private LinearLayout ml;
	private GameView landView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ml = (LinearLayout)findViewById(R.id.mainLinearLayout1);
		landView = new GameView(MainActivity.this);
		ml.addView(landView);
    }
}
