package com.dobando.golly.Game;

import com.dobando.golly.MainActivity;
//陆地(细胞的生存空间)
public class Land
{
	public int LAND_SIZE;
	//二维矩阵-生存空间
	public Cell[][] land;
	public Snake snake = new Snake();
	public int days = 0;
	public int deadCell = 0,aliveCell = 0;
	
	public Land(int size){
		this.LAND_SIZE = size;
		land = new Cell[LAND_SIZE][LAND_SIZE];
		initializeLand();
	}
	
	public void initializeLand(){
		for(int i = 0;i < LAND_SIZE;i++){
			for(int j = 0;j < LAND_SIZE;j++){
				Cell newLife = new Cell(Cell.TYPE_CELL,zeroOne(),i,j,0);
				land[i][j] = newLife;
			}
		}
		createSnake();
		/*land[20][20].setType(Cell.TYPE_NODE);
		land[20][20].toLife();*/
	}
	
	public void createSnake(){
		for(Cell theNode:snake.body){
			land[theNode.posX][theNode.posY] = new Cell(Cell.TYPE_NODE,Cell.STATE_LIVELY,theNode.posX,theNode.posY,theNode.direction);
			//land[theNode.posY][theNode.posY].setType(Cell.TYPE_NODE);
			}
	}
	
	public void renovateLand(){
		days++;
		deadCell = aliveCell = 0;
		for(int i = 0;i < LAND_SIZE;i++){
			for(int j = 0;j < LAND_SIZE;j++){
				Cell theCell = land[i][j];
				int type = theCell.getType();
				int n = getCellCount(i,j);
				if(type==Cell.TYPE_CELL){
					if(theCell.getState()==1){
					aliveCell++;
					if(n<2) theCell.toDie();
					else if(n==2||n==3){
						theCell.toLife();
						theCell.setType(Cell.TYPE_CELL);
						}
					else if(n>3) theCell.toDie();
				}
				else if(theCell.getState()==0){
					deadCell++;
					if(n==3){
						theCell.toLife();
						theCell.setType(Cell.TYPE_CELL);
					}
				}
				}
				else if(type==Cell.TYPE_NODE){}
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
					else if(theCell.getType()==Cell.TYPE_CELL)count+=theCell.getState();
					}
			}
		}
		return count;
	}
	public Cell getCell(int posX,int posY){
		return land[posX][posY];
	}
	public int zeroOne(){
		if(Math.random()<0.97) return 0;
		else return 1;
	}
}
