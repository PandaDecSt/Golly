package com.dobando.golly.UI;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.view.MotionEvent;
import com.dobando.golly.Game.Cell;
import com.dobando.golly.Game.SnakeNode;
import com.dobando.golly.MainActivity;
import com.dobando.golly.Game.Land;
import com.dobando.golly.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	public int nowFps;
	
	private final String TAG = "GameView";
	
    private SurfaceHolder holder;
	//线程
    public RenderThread renderThread;
	public FpsThread fpsCompute;
	
    public boolean isDraw = false;
	public boolean isStop = false;// 控制绘制的开关
	public Land land;
	public Canvas canvas;
	private Context ct;
	private Activity mainAct;
	private TextView text;
	public static int cellSize = 2;
	
	public StringBuilder info = new StringBuilder();

    public GameView(Context context,Activity theAct) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
		ct = context;
		mainAct = theAct;
		text = (TextView)theAct.findViewById(R.id.gameInfo);
        renderThread = new RenderThread();
		fpsCompute = new FpsThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isStop = isDraw = true;
		land = new Land(MainActivity.width/cellSize,true);
		Log.d(TAG,"启动线程中");
        renderThread.start();
		fpsCompute.start();
		Log.d(TAG,"线程启动完毕");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG,"线程已关闭");
    }

    /**
	 * 绘制界面的线程
	 * 
	 * @author Dobando
	 * 
	 */
    private class RenderThread extends Thread {
        @Override
        public void run() {
            // 不停绘制界面
			super.run();
            while (isStop) {
				if(isDraw){
					fpsCompute.count();
					land.renovateLand();
					drawUI();
					land.snake.move(land.snake.getHead().direction);
					land.check();
					info.setLength(0);
					info.append("生命游戏-by Dob\n")
					      .append("Days:"+land.days+" "+"FPS:"+nowFps+"\n")
						  .append("剩余空间:"+land.deadCell+"  存活细胞:"+land.aliveCell);
					updateGameInfo();
					}
				try
				{
					this.sleep(0);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
            }
        }
    }
	
	private class FpsThread extends Thread
	{
		int fps = 0;
		@Override
		public void run()
		{
			super.run();
			while(isStop){
				nowFps = fps;
				fps = 0;
				try
				{
				this.sleep(1000);
				}
				catch (InterruptedException e)
				{
				e.printStackTrace();
				}
			}
		}
		
		public void count(){
			fps++;
		}
		
	}

    /**
	 * 界面绘制
	 */
    public void drawUI() {
        canvas = holder.lockCanvas();
        try {
            drawCanvas(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawCanvas(Canvas canvas) {
        // 在 canvas 上绘制需要的图形
		Paint paint = new Paint();
		int size = cellSize;
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置画笔宽度
        paint.setStrokeWidth(5);
        //设置画笔颜色
        paint.setColor(Color.YELLOW);
		canvas.drawColor(Color.WHITE);
		for(int i = 0;i < land.size;i++){
			for(int j = 0;j < land.size;j++){
				Cell theCell = land.getCell(i,j);
				if(theCell.getState()==1){
					paint.setColor(Color.BLACK);
					canvas.drawRect(i*size,j*size,i*size+size,j*size+size,paint);
					}
			}
		}
		drawSnake(canvas);
    }
	
	public void drawSnake(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GRAY);
		int nowLength = land.snake.snakeBody.size();
		for(int i = 0;i<nowLength;i++){
			SnakeNode theNode = land.snake.snakeBody.get(i);
			if(i==nowLength-1){
				paint.setColor(Color.RED);
				canvas.drawRect(theNode.posX*cellSize,theNode.posY*cellSize,theNode.posX*cellSize+cellSize,theNode.posY*cellSize+cellSize,paint);
				}
			else{
				paint.setColor(Color.BLUE);
				canvas.drawRect(theNode.posX*cellSize,theNode.posY*cellSize,theNode.posX*cellSize+cellSize,theNode.posY*cellSize+cellSize,paint);
				}
		}
	}
	
	public void updateGameInfo(){
		mainAct.runOnUiThread(new Runnable(){
			@Override
			public void run(){
				text.setText(info);
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int strokeSize = 3;
		int posX = (int)(event.getX()/cellSize),posY = (int)(event.getY()/cellSize);
		switch(event.getAction()){
			case MotionEvent.ACTION_MOVE:
				for(int i = posX-strokeSize;i<=posX+strokeSize;i++){
				for(int j = posY-strokeSize;j<=posY+strokeSize;j++){
				if(i>=0&&i<land.size&&j>=0&&j<land.size){
					land.getCell(i,j).toLife();
					}}}
					drawUI();
				break;
				}
		return true;
	}
	
	public void stopGame(){
		isDraw = false;
	}
	public void startGame(){
		isDraw = true;
	}
}
