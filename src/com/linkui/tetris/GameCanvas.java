package com.linkui.tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameCanvas extends Canvas{
	static final int ROWS = 25;
	static final int COLS = 15;
	static final int BLOCK_SIZE = 15;
	private Random r = new Random();
	
	private Block currentBlock = null;
	private List<Block> blockList = new ArrayList();
	private boolean[][] drawMap = new boolean[25][15];
	private Image offScreenImage = null;
	
	public GameCanvas(){
		currentBlock = new Block(this,r.nextInt(7)+1, drawMap);
		blockList.add(currentBlock);
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
	
		currentBlock.move(drawMap);
		for(int i = 0; i < blockList.size(); i++){
			Block b = blockList.get(i);
			b.draw(g);
			g.setColor(c);
		}
		
		if(currentBlock.isStop()){
			currentBlock.fillMap(drawMap);
			currentBlock = new Block(this, r.nextInt(7)+1, drawMap);
			blockList.add(currentBlock);
		}
	}
	
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class PaintThread extends Thread{
		public void run() {
			while(true){
				repaint();
				try{
					Thread.sleep(200);
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
			currentBlock.KeyPressed(e);
			if(key == KeyEvent.VK_M){
				for(int i = 0; i<25 ; i++){
					for (int j = 0; j<15; j++){
						if(drawMap[i][j]) System.out.print("1 ");
						else System.out.print("0 ");
					}
					System.out.println();
				}
			}
		}
		
	}


}

