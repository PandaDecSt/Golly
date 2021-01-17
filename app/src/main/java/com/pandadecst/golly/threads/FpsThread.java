package com.pandadecst.golly.threads;
import com.pandadecst.golly.ui.GameView;

public class FpsThread extends Thread {
    int fps = 0;
    GameView gameview;
    
    public FpsThread(GameView g){
        gameview = g;
    }
    
    @Override
    public void run() {
        super.run();
        while (gameview.isStop) {
            gameview.nowFps = fps;
            fps = 0;
            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void count() {
        fps++;
    }

}
