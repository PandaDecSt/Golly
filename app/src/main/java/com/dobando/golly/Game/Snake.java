package com.dobando.golly.Game;

import java.util.ArrayList;

public class Snake
{
	int initSnakeLength = 40;
	ArrayList<Cell> body;
	
	Snake(){
		body = new ArrayList<Cell>();
		createSnake();
	}
	
	public void createSnake(){
		for(int i = 0;i<initSnakeLength;i++){
			Cell theCell = new Cell(Cell.TYPE_NODE,Cell.STATE_LIVELY,i,50,Cell.DIRECTION_RIGHT);
			body.add(theCell);
			}
	}
}
