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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.dobando.golly.Game.Cell;
import com.dobando.golly.Game.SnakeNode;

public class MainActivity extends Activity  implements View.OnClickListener
{

	public static int width;
	public static int height;
	
	private LinearLayout ml;
	private GameView landView;
	private Button stop,start,clear,reset;
	public TextView gameInfo;
	
	private Button up,down,left,right;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		ml = (LinearLayout)findViewById(R.id.mainLinearLayout1);
		landView = new GameView(MainActivity.this,this);
		stop = (Button)findViewById(R.id.stop);
		start = (Button)findViewById(R.id.start);
		clear = (Button)findViewById(R.id.clear);
		reset = (Button)findViewById(R.id.reset);
		up = (Button)findViewById(R.id.move_up);
		down = (Button)findViewById(R.id.move_down);
		left = (Button)findViewById(R.id.move_left);
		right = (Button)findViewById(R.id.move_right);
		stop.setOnClickListener(this);
		start.setOnClickListener(this);
		clear.setOnClickListener(this);
		reset.setOnClickListener(this);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		ml.setLayoutParams(new LayoutParams(width,width));
		ml.addView(landView);
    }

	
	@Override
	protected void onResume()
	{
		super.onResume();
	}

	
	
	@Override
	public void onClick(View p1)
	{
		int direction = 0;
		switch(p1.getId()){
			case R.id.stop:
				landView.stopGame();
				break;
			case R.id.start:
				landView.startGame();
				break;
			case R.id.clear:
				landView.land.initializeLand(1);
				break;
			case R.id.reset:
				landView.land.initializeLand(0.97);
				landView.land.snake.init();
				break;
			case R.id.move_up:
				direction = SnakeNode.DIRECTION_UP;
				break;
			case R.id.move_down:
				direction = SnakeNode.DIRECTION_DOWN;
				break;
			case R.id.move_left:
				direction = SnakeNode.DIRECTION_LEFT;
				break;
			case R.id.move_right:
				direction = SnakeNode.DIRECTION_RIGHT;
				break;
		}
		if(direction!=0){
			//调用🐍移动的方法
			landView.land.snake.move(direction);
		}
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		landView.isStop = false;
		landView.isDraw = false;
		Log.d("MainActivity","onPause");
	}
	
	@Override
	protected void onStop()
	{
		// TODO: Implement this method
		super.onStop();
		Log.d("MainActivity","onStop");
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		Log.d("MainActivity","onDestroy");
	}
	
}

