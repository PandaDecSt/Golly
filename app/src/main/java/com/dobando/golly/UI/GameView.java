package com.dobando.golly.UI;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import com.dobando.golly.MainActivity;
import com.dobando.golly.Game.Land;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.widget.TextView;
import com.dobando.golly.R;
import android.view.MotionEvent;
import com.dobando.golly.Game.Cell;
import com.dobando.golly.Game.SnakeNode;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    public RenderThread renderThread;
    public boolean isDraw = false;// 控制绘制的开关
	public int created = 0;
	public Land land;
	private Land lastLand;
	public Canvas canvas;
	private Context ct;
	private Activity mainAct;
	private TextView text;
	public int cellSize = 5;
	
	public StringBuilder info = new StringBuilder();

    public GameView(Context context,Activity theAct) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
		ct = context;
		mainAct = theAct;
		text = (TextView)theAct.findViewById(R.id.gameInfo);
		//info.append("Golly生命游戏-Land by Dob\n");

        renderThread = new RenderThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
		created++;
		if(created==1){
		land = new Land(MainActivity.width/cellSize);
        renderThread.start();
		}
		else land = lastLand;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;
		lastLand = land;
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
            while (true) {
				if(isDraw){
					land.renovateLand();
					drawUI();
					info.setLength(0);
					info.append("Golly生命游戏-Land by Dob\n")
					      .append("Days:"+land.days+"\n")
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
        //设置画笔样式
        //paint.setStyle(Paint.Style.STROKE);
		for(int i = 0;i < land.LAND_SIZE;i++){
			for(int j = 0;j < land.LAND_SIZE;j++){
				Cell theCell = land.getCell(i,j);
				if(theCell.getState()==1){
					paint.setColor(Color.BLACK);
					canvas.drawRect(i*size,j*size,i*size+size,j*size+size,paint);
					}
				else{
					paint.setColor(Color.WHITE);
					canvas.drawRect(i*size,j*size,i*size+size,j*size+size,paint);
				}
			}
		}
		drawSnake(canvas);
    }
	
	public void drawSnake(Canvas canvas){
		//canvas.drawColor(Color.WHITE);
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
				paint.setColor(Color.YELLOW);
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
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
				int strokeSize = 3;
				int posX = (int)(event.getX()/cellSize),posY = (int)(event.getY()/cellSize);
				
					for(int i = posX-strokeSize;i<=posX+strokeSize;i++)
					for(int j = posY-strokeSize;j<=posY+strokeSize;j++)
						if(i>=0&&i<land.LAND_SIZE&&j>=0&&j<land.LAND_SIZE){
					  land.getCell(i,j).toLife();}
					drawUI();
			break;
				
		}
		return false;
	}
	
	public void stopGame(){
		isDraw = false;
	}
	public void startGame(){
		isDraw = true;
	}
}
