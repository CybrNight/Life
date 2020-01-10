package com.life.main;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class GridController {

    LinkedList<Cell> grid = new LinkedList<>();

    int[][] intGrid;

    int cellSize = 0;
    int cols, rows;

    int clock = 0;

    Random r;

    GridController(int cellSize){
        r = new Random();

        this.cellSize = cellSize;

        cols = Main.WIDTH/cellSize;
        rows = Main.HEIGHT/cellSize;

        for (int x = cellSize; x < Main.WIDTH-cellSize; x+=cellSize){
            for (int y = cellSize; y < Main.HEIGHT-cellSize; y+=cellSize){
                int value = r.nextInt(11);

               /*if (value >= 3)
                    addCell(new Cell(x, y, 1, this));
                else
               */     addCell(new Cell(x, y, 0, this));
                addCell(new Cell(x,y,0,this));
            }
        }

        getCell(64,32).enable();
        getCell(64,96).enable();

    }


    public Cell getCell(int x, int y){
        if (x < 0 || y < 0 || x > Main.WIDTH-cellSize || y > Main.WIDTH-cellSize)
            return null;

        for (int i = 0; i < grid.size(); i++){
            Cell cell = grid.get(i);

            if (x == cell.x && y == cell.y)
                return cell;
        }
        return null;
    }

    public boolean isActive(int x, int y){
        if (x < 0 || y < 0 || x > Main.WIDTH-cellSize || y > Main.WIDTH-cellSize)
            return false;

        for (int i = 0; i < grid.size(); i++){
            Cell cell = grid.get(i);

            if (x == cell.x && y == cell.y)
                if (cell.active) return true;
                else return false;
        }
        return false;
    }

    public void addCell(Cell cell){
        grid.add(cell);
    }

    public void tick(){
        clock += 1;

        if (clock >= 20){
            for (int i = 0; i < grid.size(); i++){
                Cell cell = grid.get(i);
                cell.checkNeighbors();
            }
            clock = 0;
        }
        for (int i = 0; i < grid.size(); i++){
            Cell cell = grid.get(i);
            cell.update();
        }

    }

    public void render(Graphics g){
        for (int i = 0; i < grid.size(); i++){
            Cell cell = grid.get(i);
            cell.render(g);
        }
    }
}
