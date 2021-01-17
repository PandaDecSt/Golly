package com.pandadecst.golly;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.pandadecst.golly.core.SnakeNode;
import com.pandadecst.golly.ui.GameView;

public class MainActivity extends Activity implements View.OnClickListener
{

	public static int width;
	public static int height;
	
	private LinearLayout ml;
	private GameView worldView;
	private Button stop,start,clear,reset;
	public TextView gameInfo;
	
	private Button up,down,left,right;
	private EditText input_text;
	private Button bt_add;
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		ml = (LinearLayout)findViewById(R.id.mainLinearLayout1);
		worldView = new GameView(MainActivity.this,this);
		stop = (Button)findViewById(R.id.stop);
		start = (Button)findViewById(R.id.start);
		clear = (Button)findViewById(R.id.clear);
		reset = (Button)findViewById(R.id.reset);
		up = (Button)findViewById(R.id.move_up);
		down = (Button)findViewById(R.id.move_down);
		left = (Button)findViewById(R.id.move_left);
		right = (Button)findViewById(R.id.move_right);
		input_text = findViewById(R.id.input_array);
		bt_add = findViewById(R.id.bt_add);
		stop.setOnClickListener(this);
		start.setOnClickListener(this);
		clear.setOnClickListener(this);
		reset.setOnClickListener(this);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		bt_add.setOnClickListener(this);
		ml.setLayoutParams(new LayoutParams(width,width));
		ml.addView(worldView);
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
				worldView.stopGame();
				break;
			case R.id.start:
				worldView.startGame();
				break;
			case R.id.clear:
				worldView.world.clear();
				break;
			case R.id.reset:
				worldView.world.initializeworld(0.5);
				worldView.world.snake.init();
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
			case R.id.bt_add:
				parseInputArrayAndAdd();
				break;
		}
		if(direction!=0){
			//调用🐍移动的方法
			worldView.world.snake.move(direction);
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
		worldView.isStop = false;
		worldView.isDraw = false;
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
	
	private boolean parseInputArrayAndAdd(){
		return true;
	}
	
}

