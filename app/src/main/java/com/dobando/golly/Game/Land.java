package com.dobando.golly.Game;

import com.dobando.golly.MainActivity;


//陆地(细胞的生存空间)
public class Land
{
	public int LAND_SIZE;
	//二维矩阵-生存空间
	public Cell[][] land;
	public Snake snake;
	public int days = 0;
	public int deadCell = 0,aliveCell = 0;
	
	public Land(int size){
		this.LAND_SIZE = size;
		land = new Cell[LAND_SIZE][LAND_SIZE];
		initializeLand(0.97);
		snake = new Snake();
	}
	
	public void check(){
		int posX = snake.getHead().posX,posY = snake.getHead().posY;
		if(posX<=-1||posX>=LAND_SIZE||posY<=-1||posY>=LAND_SIZE){
			snake.init();
		}
		if(posX>=0&&posX<LAND_SIZE&&posY>=0&&posY<LAND_SIZE){
			Cell theCell = getCell(posX,posY);
			if(theCell.getState()==Cell.STATE_LIVELY){
				snake.addHead();
				theCell.toDie();
				}
		}
	}
	
	public void initializeLand(double p){
		for(int i = 0;i < LAND_SIZE;i++){
			for(int j = 0;j < LAND_SIZE;j++){
				Cell newLife = new Cell(zeroOne(p));
				land[i][j] = newLife;
			}
		}
	}
	
	
	public void renovateLand(){
		days++;
		deadCell = aliveCell = 0;
		for(int i = 0;i < LAND_SIZE;i++){
			for(int j = 0;j < LAND_SIZE;j++){
				Cell theCell = land[i][j];
				int n = getCellCount(i,j);
					if(theCell.getState()==1){
					aliveCell++;
					if(n<2) theCell.toDie();
					else if(n==2||n==3){
						theCell.toLife();
						}
					else if(n>3) theCell.toDie();
				}
				else if(theCell.getState()==0){
					deadCell++;
					if(n==3){
						theCell.toLife();
					}
				}
			}
		}
	}
	
	public int getCellCount(int posX,int posY){
		int count = 0;
		for(int i = posX-1;i <= posX+1;i++){
			for(int j = posY-1;j <= posY+1;j++){
				if(i>=0&&i<LAND_SIZE&&j>=0&&j<LAND_SIZE){
					Cell theCell = getCell(i,j);
					if(i==posX&&j==posY) count+=0;
					else count+= theCell.getState();
					}
			}
		}
		return count;
	}
	public Cell getCell(int posX,int posY){
		return land[posX][posY];
	}
	
	public int zeroOne(double p){
		if(Math.random()<p) return 0;
		else return 1;
	}
}
