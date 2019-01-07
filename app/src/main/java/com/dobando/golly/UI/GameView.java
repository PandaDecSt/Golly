package com.dobando.golly.UI;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import com.dobando.golly.MainActivity;
import com.dobando.golly.Game.Land;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    public RenderThread renderThread;
    private boolean isDraw = false;// 控制绘制的开关
	private Land land;
	private Context ct;

    public GameView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
		ct = context;

        renderThread = new RenderThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
		land = new Land(MainActivity.width/16);
        renderThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;

    }

    /**
	 * 绘制界面的线程
	 * 
	 * @author Administrator
	 * 
	 */
    private class RenderThread extends Thread {
        @Override
        public void run() {
            // 不停绘制界面
			super.run();
            while (true) {
				if(isDraw){
					drawUI();
					land.renovateLand();
					}
				try
				{
					this.sleep(500);
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
        Canvas canvas = holder.lockCanvas();
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
				if(land.getCell(i,j).getState()==1){
					paint.setColor(Color.BLACK);
					canvas.drawRect(i*16,j*16,i*16+16,j*16+16,paint);
					}
				else{
					paint.setColor(Color.WHITE);
					canvas.drawRect(i*16,j*16,i*16+16,j*16+16,paint);
					
				}
			}
		}
    }
	
	public void showInfo(){
	}
	
	public void stopGame(){
		isDraw = false;
	}
	public void startGame(){
		isDraw = true;
	}
}
