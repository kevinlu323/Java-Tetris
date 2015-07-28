package com.linkui.tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameCanvas extends Canvas{
	static final int ROWS = 25;
	static final int COLS = 15;
	static final int BLOCK_SIZE = 15;
	
	private Block b = new Block();
	
	public GameCanvas(){
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		this.addKeyListener(new KeyMonitor());
		new PaintThread().start();
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		g.setColor(Color.GRAY);
		for(int i = 1; i < ROWS; i++){
			g.drawLine(0, i*BLOCK_SIZE, COLS * BLOCK_SIZE, i*BLOCK_SIZE);
		}
		for(int i = 1; i < COLS; i++){
			g.drawLine(i*BLOCK_SIZE, 0, i*BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		b.move();
		b.draw(g);
		g.setColor(c);
	}
	
	private class PaintThread extends Thread{
		public void run() {
			while(true){
				repaint();
				try{
					Thread.sleep(100);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_Z){
				System.out.println("Key is pressed");
			}
			b.KeyPressed(e);
		}
		
	}
}

