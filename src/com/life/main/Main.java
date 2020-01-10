package com.life.main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{

    private static final long serialVersionUID = 8703577945772343437L;

    public static final int WIDTH = 640, HEIGHT = 480;
    private Thread thread;

    GridController gc = new GridController(32);

    private boolean running = false;

    private Main(){
        new Window(WIDTH, HEIGHT, "Life", this);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick(){
        gc.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,WIDTH,HEIGHT);

        gc.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Main();
    }
}