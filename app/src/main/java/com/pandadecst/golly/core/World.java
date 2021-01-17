package com.pandadecst.golly.core;

import com.pandadecst.golly.MainActivity;
import com.pandadecst.golly.ui.*;
import com.pandadecst.golly.core.elements.special.Life;


//陆地(细胞的生存空间)
public class World
{
	public int size;
	//二维矩阵-生存空间
	public Cell[][] world;
	public Snake snake;
	public int days = 0;
	public int deadCell = 0,aliveCell = 0;
    Gravity gravity = new Gravity(0, -10, 0);

	public World(int size,boolean type)
	{
		this.size = size;
		world = new Cell[size][size];
		if(type){
		initializeworld(0.5);
		snake = new Snake();
		}
	}

	public void check()
	{
        //贪吃蛇行为
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

	public void initializeworld(double p)
	{
		for (int i = 0;i < size;i++)
		{
			for (int j = 0;j < size;j++)
			{
                    world[i][j] = new Life(zeroOne(p), i, j);
			}
		}
	}
    
    public void clear()
    {
        for (int i = 0;i < size;i++)
        {
            for (int j = 0;j < size;j++)
            {
                getCell(i, j).toDie();
            }
        }
	}


	public void renovateworld()
	{
		days++;
		deadCell = aliveCell = 0;
		World stepworld = new World(MainActivity.width/GameView.cellSize,false);
		stepworld.initializeworld(1);
        //行为
		for (int i = 0;i < size;i++)
		{
			for (int j = 0;j < size;j++)
			{
				Cell theCell = getCell(i,j);
				Cell stepCell = stepworld.getCell(i,j);
				int n = getCellCount(i, j);
				if (theCell.getState() == Cell.STATE_LIVELY)
				{
					aliveCell++;//计数
					if (n < 2) stepCell.toDie();
					else if (n == 2 || n == 3)
					{
						stepCell.toLive();
					}
					else if (n > 3) stepCell.toDie();
				}
				else if (stepCell.getState() == Cell.STATE_DEAD)
				{
					deadCell++;
					if (n == 3)
					{
						stepCell.toLive();
					}
				}
			}
		}
		world = stepworld.world;
	}

	public int getCellCount(int posX, int posY)
	{
        //用于检测周围的cell
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
		return world[posX][posY];
	}

	public int zeroOne(double p)
	{
		if (Math.random() < p) return 0;
		else return 1;
	}
}
