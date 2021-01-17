package com.pandadecst.golly.threads;

import com.pandadecst.golly.ui.GameView;

/**
 * 绘制界面的线程
 * 
 * @author Dobando
 * 
 */
public class RenderThread {    
    GameView gameview;
    CalculateThread calculateThread;
    DrawCanvasThread DrawCanvasThread;
    DrawSnakeThread DrawSnakeThread;

    public RenderThread(GameView g) {
        gameview = g;
        DrawCanvasThread = new DrawCanvasThread();
        DrawSnakeThread = new DrawSnakeThread();
        calculateThread = new CalculateThread();
    }

    public void start() {
        calculateThread.start();
        DrawCanvasThread.start();
        DrawSnakeThread.start();
    }

    class DrawSnakeThread extends Thread {    
        @Override
        public void run() {
            super.run();
            while (gameview.isStop) {
                if (gameview.isDraw) {
                    gameview.drawSnake();
                }
                try {
                    this.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class DrawCanvasThread extends Thread {    
        @Override
        public void run() {
            super.run();
            while (gameview.isStop) {
                if (gameview.isDraw) {
                    gameview.drawCanvas();
                }
                try {
                    this.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CalculateThread extends Thread {
        @Override
        public void run() {
            // 计算绘制参数
            super.run();
            while (gameview.isStop) {
                if (gameview.isDraw) {
                    gameview.fpsCompute.count();
                    gameview.world.renovateworld();
                    gameview.world.snake.move(gameview.world.snake.getHead().direction);
                    gameview.world.check();
                    gameview.info.setLength(0);
                    gameview.info.append("生命游戏-by Dob\n")
                        .append("Days:" + gameview.world.days + " " + "FPS:" + gameview.nowFps + "\n")
                        .append("剩余空间:" + gameview.world.deadCell + "  存活细胞:" + gameview.world.aliveCell);
                    gameview.updateGameInfo();
                }
                try {
                    this.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
