package com.dobando.golly;

import android.app.Activity;
import android.os.Bundle;
import com.dobando.golly.UI.GameView;
import android.widget.LinearLayout;
import com.dobando.golly.Game.Land;
import android.view.Display;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity  implements View.OnClickListener
{
	public static int width;
	public static int height;
	
	private LinearLayout ml;
	private GameView landView;
	private Button stop;
	private Button start;
	public TextView gameInfo;
	
	public StringBuilder info;
	
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
		stop = (Button)findViewById(R.id.stop);
		start = (Button)findViewById(R.id.start);
		stop.setOnClickListener(this);
		start.setOnClickListener(this);
		ml.setLayoutParams(new LayoutParams(width,width));
		ml.addView(landView);
		
		info.append("Golly生命游戏-Land by Dob\n");
    }

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.stop:
				landView.stopGame();
				break;
			case R.id.start:
				landView.startGame();
				break;
		}
	}
	public void showInfo(){
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				
			}
		});
	}
}
