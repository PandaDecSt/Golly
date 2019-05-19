package com.dobando.golly.Game;

import com.dobando.golly.MainActivity;
import com.dobando.golly.UI.*;


//陆地(细胞的生存空间)
public class Land
{
	public int size;
	//二维矩阵-生存空间
	public Cell[][] land;
	public Snake snake;
	public int days = 0;
	public int deadCell = 0,aliveCell = 0;

	public Land(int size,boolean type)
	{
		this.size = size;
		land = new Cell[size][size];
		if(type){
		initializeLand(0.5);
		snake = new Snake();
		}
	}

	public void check()
	{
		int posX = snake.getHead().posX,posY = snake.getHead().posY;
		if (posX <= -1 || posX >= size || posY <= -1 || posY >= size)
		{
			snake.init();
		}
		if (posX >= 0 && posX < size && posY >= 0 && posY < size)
		{
			Cell theCell = getCell(posX, posY);
			if (theCell.getState() == Cell.STATE_LIVELY)
			{
				snake.addHead();
				theCell.toDie();
			}
		}
	}

	public void initializeLand(double p)
	{
		for (int i = 0;i < size;i++)
		{
			for (int j = 0;j < size;j++)
			{
				Cell newLife = new Cell(zeroOne(p));
				land[i][j] = newLife;
			}
		}
	}


	public void renovateLand()
	{
		days++;
		deadCell = aliveCell = 0;
		Land stepLand = new Land(MainActivity.width/GameView.cellSize,false);
		stepLand.initializeLand(1);
		for (int i = 0;i < size;i++)
		{
			for (int j = 0;j < size;j++)
			{
				Cell theCell = getCell(i,j);
				Cell stepCell = stepLand.getCell(i,j);
				int n = getCellCount(i, j);
				if (theCell.getState() == 1)
				{
					aliveCell++;
					if (n < 2) stepCell.toDie();
					else if (n == 2 || n == 3)
					{
						stepCell.toLife();
					}
					else if (n > 3) stepCell.toDie();
				}
				else if (stepCell.getState() == 0)
				{
					deadCell++;
					if (n == 3)
					{
						stepCell.toLife();
					}
				}
			}
		}
		land = stepLand.land;
	}

	public int getCellCount(int posX, int posY)
	{
		int count = 0;
		for (int i = posX - 1;i <= posX + 1;i++)
		{
			for (int j = posY - 1;j <= posY + 1;j++)
			{
				if (i >= 0 && i < size && j >= 0 && j < size)
				{
					Cell theCell = getCell(i, j);
					if (i == posX && j == posY) count += 0;
					else count += theCell.getState();
				}
			}
		}
		return count;
	}
	public Cell getCell(int posX, int posY)
	{
		return land[posX][posY];
	}

	public int zeroOne(double p)
	{
		if (Math.random() < p) return 0;
		else return 1;
	}
}
