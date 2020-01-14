package com.life.main;

import java.awt.*;

public class Cell {

    protected int x, y;
    GridController gc;

    int neighborsCount = 0;
    protected int status = 0;
    boolean active = false;
    int cellSize;

    public Cell(int x, int y, int status, GridController gc){
        this.x = x;
        this.y = y;
        this.status = status;

        this.gc = gc;

        cellSize = gc.cellSize;
        checkNeighbors();
        update();
    }

    public void enable(){
        active = true;
        status = 1;
    }

    public void disable(){
        active = false;
        status = 0;
    }

    public void update(){
        if (status == 0)
            disable();
        else
            enable();
    }

    public void checkNeighbors(){
        neighborsCount = 0;
        if (gc.isActive(x-cellSize,y))
            neighborsCount++;
        if (gc.isActive(x+cellSize,y))
            neighborsCount++;
        if (gc.isActive(x,y-cellSize))
            neighborsCount++;
        if (gc.isActive(x,y+cellSize))
            neighborsCount++;
        if (gc.isActive(x-cellSize,y-cellSize))
            neighborsCount++;
        if (gc.isActive(x+cellSize,y+cellSize))
            neighborsCount++;
        if (gc.isActive(x-cellSize,y+cellSize))
            neighborsCount++;
        if (gc.isActive(x+cellSize,y-cellSize))
            neighborsCount++;

        if (!active){
            if (neighborsCount == 3)
                status = 1;
        }else{
            if (neighborsCount > 3)
                status = 0;
            else if (neighborsCount < 2)
                status = 0;
        }


    }

    public void render(Graphics g){
        if (active == false) {
            g.setColor(Color.black);
            if (Main.debug) g.drawString(Integer.toString(neighborsCount), x, y);
            g.drawRect(x, y, cellSize, cellSize);
        } else {
            g.setColor(Color.black);
            g.fillRect(x, y, cellSize, cellSize);

            if (Main.debug) {
                g.setColor(Color.white);
                g.drawString(Integer.toString(neighborsCount), x, y);
            }
        }
    }

}
