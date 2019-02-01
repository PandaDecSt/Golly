package com.dobando.golly.Game;
//细胞
public class Cell
{
	public static final int TYPE_CELL = 0;
	public static final int TYPE_NODE =1;
	
	public static final int STATE_DEAD = 0;
	public static final int STATE_LIVELY = 1;
	
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = -1;
	public static final int DIRECTION_LEFT = 2;
	public static final int DIRECTION_RIGHT = -2;
	
	int type;
	int state;
	int direction;
	int posX;
	int posY;
	
	Cell(int type,int s,int x,int y,int d){
		this.state = s;
		this.type = type;
		this.posX = x;
		this.posY = y;
		this.direction = d;
	}
	//生
	public void toLife(){
		this.state = STATE_LIVELY;
	}
	//死
	public void toDie(){
		this.state = STATE_DEAD;
	}
	//获取细胞状态
	public int getState(){
		return state;
	}
	public void setType(int t){
		this.type = t;
	}
	public int getType(){
		return type;
	}
	public int getDirection(){
		return direction;
	}
}
