package com.pandadecst.golly.core;
import android.graphics.Color;

public abstract class Cell {
    
	public static final int STATE_DEAD = 0;
	public static final int STATE_LIVELY = 1;
	
	int state;
    int X, Y;
    public int defaultColor = Color.BLACK;
    public int color;
	
	public Cell(int s, int x, int y){
		this.state = s;
        X = x;
        Y = y;
        this.color = defaultColor;
	}

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
    
    public boolean isAlive(){
        if(this.state == STATE_LIVELY)
            return true;
            
            return false;
    }

    public boolean isDie(){
        if(this.state == STATE_DEAD)
            return true;

        return false;
	}
    
	public void toLive(){
		this.state = STATE_LIVELY;
	}
    
	public void toDie(){
		this.state = STATE_DEAD;
	}
    
	public int getState(){
		return state;
	}
    
}



