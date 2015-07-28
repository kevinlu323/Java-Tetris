package com.linkui.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Block {
	List<Node> nodeList = new ArrayList<Node>();
	enum Direction {L, R, D, STOP}
	Direction dir = Direction.D;
	
	public Block(){
		nodeList.add(new Node(3,3));
		nodeList.add(new Node(3,4));
		nodeList.add(new Node(3,5));
		nodeList.add(new Node(2,4));
	}
	
	public void move(){
		Iterator<Node> it = nodeList.iterator();
		while(it.hasNext()){
			Node n = it.next();
			n.move(dir);
		}
		if(isTouchGround()) dir = Direction.STOP;
		else dir = Direction.D;
	}
	
	public boolean isTouchGround(){
		int maxRow = 0;
		Iterator<Node> it = nodeList.iterator();
		while(it.hasNext()){
			Node n = it.next();
			if(maxRow <= n.getRow()){
				maxRow = n.getRow();
			}
		}
		if(maxRow >= GameCanvas.ROWS-1) return true;
		else return false;
	}
	
	public boolean isTouchWall(){
		int minCol= GameCanvas.COLS, maxCol = 0;
		int nodeCol;
		Iterator<Node> it = nodeList.iterator();
		while(it.hasNext()){
			Node n = it.next();
			nodeCol = n.getCol();
			if(maxCol <= n.getCol()){
				maxCol = n.getCol();
			}
			if(minCol >= n.getCol()){
				minCol = n.getCol();
			}
		}
		if(maxCol>= GameCanvas.COLS -1 || minCol <=0) return true;
		else return false;
	}
	
	public void KeyPressed(KeyEvent e){
		if(dir == Direction.STOP) return;
		int key = e.getKeyCode();
		if(!isTouchWall()){
			switch(key){
			case KeyEvent.VK_LEFT:
				dir = Direction.L;
				break;
			case KeyEvent.VK_RIGHT:
				dir = Direction.R;
				break;
			}
		}
	}
	
	public void draw(Graphics g){
		Iterator<Node> it = nodeList.iterator();
		while(it.hasNext()){
			it.next().draw(g);
		}
	}
	
	private class Node{
		int row, col;
		int w = GameCanvas.BLOCK_SIZE;
		int h = GameCanvas.BLOCK_SIZE;
		
		public Node(int row, int col){
			this.row = row;
			this.col = col;
		}
		
		public void move(Direction dir){
			if(dir == Direction.D && row < GameCanvas.ROWS-1)
				this.row += 1;
			if(dir == Direction.L)
				this.col -= 1;
			if(dir == Direction.R)
				this.col += 1;
		}
		
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.ORANGE);
			g.fillRect(col * w, row * h, w, h);
			g.setColor(c);
		}
		
		public int getRow(){
			return row;
		}
		
		public int getCol(){
			return col;
		}
	}
}
