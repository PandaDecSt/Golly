package com.dobando.golly.Game;

//细胞
public class Cell
{
	final int STATE_DEAD = 0;
	final int STATE_LIVELY = 1;
	
	int state;
	
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
