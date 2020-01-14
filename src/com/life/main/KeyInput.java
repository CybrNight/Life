package com.life.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private GridController gc;
    private Main main;

    KeyInput(GridController gc, Main main) {
        this.gc = gc;
        this.main = main;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT) {
            //Advance Generation
            gc.advanceGeneration();
        }

        if (key == KeyEvent.VK_R) {
            gc.resetGenerations();
        }

        if (key == KeyEvent.VK_SPACE) {
            if (main.gameState == STATE.Paused)
                main.gameState = STATE.Playing;
            else
                main.gameState = STATE.Paused;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
    }
}
