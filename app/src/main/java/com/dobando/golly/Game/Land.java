package com.dobando.golly.Game;

//陆地(细胞的生存空间)
public class Land
{
	final int LAND_SIZE = 30;
	//二维矩阵-生存空间
	public Cell[][] land = new Cell[LAND_SIZE][LAND_SIZE];
	
	public Land(){
		initializeLand();
	}
	
	public void initializeLand(){
		for(int i = 0;i < LAND_SIZE;i++){
			for(int j = 0;j < LAND_SIZE;j++){
				Cell newLife = new Cell(zeroOne());
				land[i][j] = newLife;
			}
		}
	}
	public void renovateLand(){
		for(int i = 0;i < LAND_SIZE;i++){
			for(int j = 0;j < LAND_SIZE;j++){
				Cell theCell = land[i][j];
				int n = getCellCount(i,j);
				if(theCell.getState()==1){
					if(n<2) theCell.toDie();
					else if(n==2||n==3) theCell.toLife();
					else if(n>3) theCell.toDie();
				}
				else if(theCell.getState()==0){
					if(n==3) theCell.toLife();
				}
			}
		}
	}
	
	public int getCellCount(int posX,int posY){
		int count = 0;
		for(int i = posX-1;i < posX+1;i++){
			for(int j = posY-1;j < posY+1;j++){
				if(i>=0&&i<LAND_SIZE&&j>=0&&j<LAND_SIZE) count+=getCell(i,j).state;
			}
		}
		return count;
	}
	public Cell getCell(int posX,int posY){
		return land[posX][posY];
	}
	public int zeroOne(){
		if(Math.random()<0.5) return 0;
		else return 1;
	}
}
