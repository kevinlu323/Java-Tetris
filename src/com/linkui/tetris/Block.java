package com.linkui.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Block {
	List<Node> nodeList = new ArrayList<Node>(4);
	enum Direction {L, R, D, STOP}
	Direction dir = Direction.D;
	GameCanvas gc = null;
	boolean[][] map;
	
	public Block(GameCanvas gc, int type, boolean[][] map){
		this.gc = gc;
		this.map = map;
		switch(type){
		case 1:
			nodeList.add(new Node(1,6)); //the first block is the center one.
			nodeList.add(new Node(1,7));
			nodeList.add(new Node(1,8));
			nodeList.add(new Node(0,6));
			break;
		case 2:
			nodeList.add(new Node(1,8)); //the first block is the center one.
			nodeList.add(new Node(1,7));
			nodeList.add(new Node(1,6));
			nodeList.add(new Node(0,8));
			break;
		case 3:
			nodeList.add(new Node(1,8)); //the first block is the center one.
			nodeList.add(new Node(1,9));
			nodeList.add(new Node(0,8));
			nodeList.add(new Node(0,7));
			break;
		case 4:
			nodeList.add(new Node(1,8)); //the first block is the center one.
			nodeList.add(new Node(1,7));
			nodeList.add(new Node(0,8));
			nodeList.add(new Node(0,9));
			break;
		case 5:
			nodeList.add(new Node(1,8)); //the first block is the center one.
			nodeList.add(new Node(1,9));
			nodeList.add(new Node(0,8));
			nodeList.add(new Node(0,9));
			break;
		case 6:
			nodeList.add(new Node(1,8)); //the first block is the center one.
			nodeList.add(new Node(1,7));
			nodeList.add(new Node(1,9));
			nodeList.add(new Node(0,8));
			break;
		case 7:
			nodeList.add(new Node(1,8)); //the first block is the center one.
			nodeList.add(new Node(1,6));
			nodeList.add(new Node(1,7));
			nodeList.add(new Node(1,9));
			break;
		}
		
	}
	
	public void move(boolean[][] map){
		List<Node> tmpList = new ArrayList<Node>(); //Save node position before moving
		for(int i =0; i<nodeList.size(); i++){
			tmpList.add(new Node(nodeList.get(i).getRow(), nodeList.get(i).getCol()));
		}
		for(int i =0; i<nodeList.size(); i++){
			nodeList.get(i).move(dir);
		}
		if(isTouchGround() || isHitOthers(map)){ 
			nodeList=tmpList;
			dir = Direction.STOP;
		} else {
			dir = Direction.D;
		}
	}
	
	/**
	 * This method is used to rotate block.
	 */
	public void rotate(){
		List<Node> tmpList = new ArrayList<Node>(); //Save node position before moving
		for(int i =0; i<nodeList.size(); i++){
			tmpList.add(new Node(nodeList.get(i).getRow(), nodeList.get(i).getCol()));
		}
		int x0, y0; //center coordinate of block, here it is the coordinate of the first node.
		int x, y; //coordinate after rotate;
		x0 = nodeList.get(0).getCol();
		y0 = nodeList.get(0).getRow();
		for(int i=1; i < nodeList.size();i++){
			x = x0 + (nodeList.get(i).getRow()-y0);
			y = y0 - (nodeList.get(i).getCol()-x0);
			nodeList.get(i).setCol(x);
			nodeList.get(i).setRow(y);
		}
		if(isTouchGround() || isHitLeft() || isHitRight() || isHitOthers(map)){ 
			nodeList=tmpList;
		}
	}
	
	public void fillMap(boolean[][] map){
		for(int i=0; i < nodeList.size();i++){
			map[nodeList.get(i).getRow()][nodeList.get(i).getCol()] = true;			
		}
	}
	public boolean isHitOthers(boolean[][] map){
		for(int i = 0; i < nodeList.size(); i++){
			Node n = nodeList.get(i);
			if(true == map[n.getRow()][n.getCol()]){
				return true; // has collision with current Nodes, return true
			}
		}
		return false;
	}
	
	public boolean isStop(){
		if (dir == Direction.STOP)
			return true;
		else return false;
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
		if(maxRow >= GameCanvas.ROWS) return true;
		else return false;
	}
	
	public boolean isHitLeft(){
		int minCol= GameCanvas.COLS;
		int nodeCol;
		Iterator<Node> it = nodeList.iterator();
		while(it.hasNext()){
			Node n = it.next();
			nodeCol = n.getCol();
			if(minCol >= n.getCol()){
				minCol = n.getCol();
			}
		}
		if(minCol <= 0) return true;
		else return false;
	}
	
	public boolean isHitRight(){
		int nodeCol, maxCol = 0;
		Iterator<Node> it = nodeList.iterator();
		while(it.hasNext()){
			Node n = it.next();
			nodeCol = n.getCol();
			if(maxCol <= n.getCol()){
				maxCol = n.getCol();
			}
		}
		if(maxCol>= GameCanvas.COLS -1) return true;
		else return false;
	}
	
	public void KeyPressed(KeyEvent e){
		if(dir == Direction.STOP) return;
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			if(!isHitLeft())
				dir = Direction.L;
			break;
		case KeyEvent.VK_RIGHT:
			if(!isHitRight())
				dir = Direction.R;
			break;
		case KeyEvent.VK_UP:
			rotate();
			break;
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
			if(dir == Direction.D)
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
		
		public void setRow(int row){
			this.row = row;
		}
		
		public void setCol (int col){
			this.col = col;
		}
	}
}
