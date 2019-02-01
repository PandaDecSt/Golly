package com.dobando.golly.Game;
//细胞
public class Cell
{
	static final int TYPE_CELL = 0;
	static final int TYPE_NODE =1;
	
	static final int STATE_DEAD = 0;
	static final int STATE_LIVELY = 1;
	
	static final int DIRECTION_UP = 1;
	static final int DIRECTION_DOWN = -1;
	static final int DIRECTION_LEFT = 2;
	static final int DIRECTION_RIGHT = -2;
	
	
	int type;
	int state;
	int direction;
	
	Cell(int s){
		this.state = s;
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
}
