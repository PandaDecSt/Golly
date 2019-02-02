package com.dobando.golly.Game;

import java.util.ArrayList;

public class Snake
{
	
	public int length = 10;
	public ArrayList<SnakeNode> snakeBody;
	
	Snake(){
		snakeBody = new ArrayList<SnakeNode>();
		init();
	}
	
	private void init(){
		for(int i = 0;i<length;i++){
			snakeBody.add(new SnakeNode(i,0,SnakeNode.DIRECTION_RIGHT));
		}
	}
	
	public void move(int direction){
		SnakeNode head = snakeBody.get(snakeBody.size()-1);
		ArrayList<SnakeNode> newSnake = new ArrayList<SnakeNode>();
		SnakeNode newHead = head;
		for(int i = 1;i < snakeBody.size();i++){
			newSnake.add(snakeBody.get(i));
		}
		switch(direction){
			case SnakeNode.DIRECTION_UP:
				newHead.posY--;
				break;
			case SnakeNode.DIRECTION_DOWN:
				newHead.posY++;
				break;
			case SnakeNode.DIRECTION_LEFT:
				newHead.posX--;
				break;
			case SnakeNode.DIRECTION_RIGHT:
				newHead.posX++;
				break;
		}
		newSnake.add(newHead);
		snakeBody =newSnake;
	}
	
	
	public boolean isThePostionSnake(int i,int j){
		for(SnakeNode sn:snakeBody){
			if(sn.isSamePositionAsCell(i,j)) return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		for(int i = 0;i<length;i++){
			SnakeNode sn = snakeBody.get(i);
			string.append(i+""+sn.posX+","+sn.posY+"|");
		}
		return string.toString();
	}
}
