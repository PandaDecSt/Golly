package com.pandadecst.golly.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import com.pandadecst.golly.MainActivity;
import com.pandadecst.golly.R;
import com.pandadecst.golly.core.Cell;
import com.pandadecst.golly.core.World;
import com.pandadecst.golly.core.SnakeNode;
import com.pandadecst.golly.threads.FpsThread;
import com.pandadecst.golly.threads.RenderThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public int nowFps;

	private final String TAG = "GameView";

    private SurfaceHolder holder;
	//线程
    public RenderThread renderThread;
	public FpsThread fpsCompute;

    public boolean isDraw = false;
	public boolean isStop = false;// 控制绘制的开关
	public World world;
	public Canvas canvas;
    Cell theCell;
    SnakeNode theNode;
    Paint paint = new Paint();
	private Context ct;
	private Activity mainAct;
	private TextView text;
	public static int cellSize = 4;

	public StringBuilder info = new StringBuilder();

    public GameView(Context context, Activity theAct) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
		ct = context;
		mainAct = theAct;
		text = (TextView)theAct.findViewById(R.id.gameInfo);
        renderThread = new RenderThread(this);
		fpsCompute = new FpsThread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isStop = isDraw = true;
		world = new World(MainActivity.width / cellSize, true);
		Log.d(TAG, "启动线程中");
        renderThread.start();
		fpsCompute.start();
		Log.d(TAG, "线程启动完毕");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "线程已关闭");
    }
   
    /**
	 * 界面绘制
	 */
    public void drawCanvas() {
        canvas = holder.lockCanvas();
        try {
            // 在 canvas 上绘制需要的图形            
            int size = cellSize;
            //设置抗锯齿
            paint.setAntiAlias(true);
            //设置画笔宽度
            paint.setStrokeWidth(1);
            //设置画笔颜色
            paint.setColor(Color.YELLOW);
            canvas.drawColor(Color.WHITE);
            
            int nowLength = world.snake.snakeBody.size();
            
            for (int i = 0;i < world.size;i++) {
                for (int j = 0;j < world.size;j++) {
                    theCell = world.getCell(i, j);
//                    int left = size * i;
//                    int top = size * j;
//                    int right = left + size;
//                    int bottom = top + size;
                    if (theCell.getState() == Cell.STATE_LIVELY) {
                        paint.setColor(theCell.getColor());
                        canvas.drawRect(size * i, size * j, size * i + size, size * j + size, paint);
                        //canvas.drawPoint(i * size,j * size, paint);
                    } else if (theCell.getState() == Cell.STATE_DEAD) {
                        //theCell = null;
                    }
                }
            }
            //画🐍
            paint.setColor(Color.GRAY);
//            int nowLength = world.snake.snakeBody.size();
//            SnakeNode theNode;
            for (int i = 0;i < nowLength;i++) {
                theNode = world.snake.snakeBody.get(i);
                if (i == nowLength - 1) {
                    paint.setColor(Color.RED);
                    canvas.drawRect(theNode.posX * cellSize, theNode.posY * cellSize, theNode.posX * cellSize + cellSize, theNode.posY * cellSize + cellSize, paint);
                } else {
                    paint.setColor(Color.BLUE);
                    canvas.drawRect(theNode.posX * cellSize, theNode.posY * cellSize, theNode.posX * cellSize + cellSize, theNode.posY * cellSize + cellSize, paint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

	public void drawSnake() {
//        canvas = holder.lockCanvas();
//        try {
//      
//            
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            holder.unlockCanvasAndPost(canvas);
//        }
		
	}

	public void updateGameInfo() {
		mainAct.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    text.setText(info);
                }
            });
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int strokeSize = 3;
		int posX = (int)(event.getX() / cellSize),posY = (int)(event.getY() / cellSize);
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				for (int i = posX - strokeSize;i <= posX + strokeSize;i++) {
                    for (int j = posY - strokeSize;j <= posY + strokeSize;j++) {
                        if (i >= 0 && i < world.size && j >= 0 && j < world.size) {
                            world.getCell(i, j).toLive();
                        }}}
                //drawUI();
				break;
        }
		return true;
	}

	public void stopGame() {
		isDraw = false;
	}
	public void startGame() {
		isDraw = true;
	}

    private int rectDrawWidth(int index) {
        return (index * cellSize) + (cellSize - 1);
    }
}
