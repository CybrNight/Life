package com.life.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

public class GridController extends MouseAdapter {

    LinkedList<Cell> grid = new LinkedList<>();
    LinkedList<LinkedList<Cell>> previousGrids = new LinkedList<>();

    int cellSize;
    int borderWidth = 2;

    int clock = 0;
    int mouseTimer = 0;

    int generation = 0;

    Random r;

    private Main main;

    GridController(int cellSize, Main main) {
        r = new Random();

        this.cellSize = cellSize;
        this.main = main;
        generateGrid();
    }

    public void generateGrid() {
        for (int x = cellSize * 2; x < Main.WIDTH - cellSize * 2; x += cellSize) {
            for (int y = cellSize * 2; y < Main.HEIGHT - cellSize * 2; y += cellSize) {
                addCell(new Cell(x, y, 0, this));
            }
        }
    }


    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x > Main.WIDTH - cellSize * 2 || y > Main.HEIGHT - cellSize * 2) {
            return null;
        }

        for (int i = 0; i < grid.size(); i++) {
            Cell cell = grid.get(i);

            if (x == cell.x && y == cell.y)
                return cell;
        }
        return null;
    }

    public boolean isActive(int x, int y){
        if (x < 0 || y < 0 || x > Main.WIDTH - cellSize * 2 || y > Main.HEIGHT - cellSize * 2)
            return false;

        for (int i = 0; i < grid.size(); i++){
            Cell cell = grid.get(i);

            if (x == cell.x && y == cell.y)
                if (cell.active) return true;
                else return false;
        }
        return false;
    }

    public void addCell(Cell cell) {
        grid.add(cell);
    }

    public void advanceGeneration() {
        LinkedList<Cell> tempGrid = new LinkedList<>();

        for (Cell cell : grid) {
            tempGrid.add(cell);
        }

        previousGrids.add(tempGrid);

        for (int i = 0; i < grid.size(); i++) {
            Cell cell = grid.get(i);
            cell.checkNeighbors();
        }

        for (int i = 0; i < grid.size(); i++) {
            Cell cell = grid.get(i);
            cell.update();
        }
        generation++;
    }

    public void resetGenerations() {
        grid.clear();
        generateGrid();
        generation = 0;
    }

    public void mousePressed(MouseEvent e) {
        int mx = Math.floorDiv(e.getX(), cellSize) * cellSize;
        int my = Math.floorDiv(e.getY(), cellSize) * cellSize;

        if (mouseTimer < 1 && e.getButton() == MouseEvent.BUTTON1 && generation == 0) {
            Cell cell = getCell(mx, my);
            if (cell.active)
                cell.disable();
            else
                cell.enable();
        }
    }

    public void mouseReleased(MouseEvent e) {
        mouseTimer = 0;
    }

    public void tick() {
        if (main.gameState == STATE.Paused) {
            clock = 0;
            return;
        }

        clock += 1;

        if (clock >= 5) {
            for (int i = 0; i < grid.size(); i++) {
                Cell cell = grid.get(i);
                cell.checkNeighbors();
            }

            for (int i = 0; i < grid.size(); i++) {
                Cell cell = grid.get(i);
                cell.update();
            }

            generation++;

            clock = 0;
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawString("Generation #:" + generation, 32, 32);

        for (int i = 0; i < grid.size(); i++) {
            Cell cell = grid.get(i);
            cell.render(g);
        }
    }
}
