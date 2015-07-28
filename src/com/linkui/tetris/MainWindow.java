package com.linkui.tetris;

import javax.swing.JFrame;

public class MainWindow extends JFrame{
	
	public MainWindow(){
		GameCanvas gc = new GameCanvas();
		this.setLocation(300, 200);
		this.add(gc);
		gc.setFocusable(true);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args){
		new MainWindow();
	}
}
