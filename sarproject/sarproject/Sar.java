package sarproject;

import java.awt.*;
import javax.swing.*;

public class Sar {

	Image image;
	private int x;
	private int y;
	private int xVelocity = 1;
	private int yVelocity = 1;
	private int movement;
	private int lastMove;
	private int spawnNum;
	private int lastPos;
	private boolean isMoving = false;
	
	Sar(int x, int y, ImageIcon image, int spawnNum) {
		this.x = x;
		this.y = y;
		this.image = image.getImage();
		this.spawnNum = spawnNum;
	}	
	
	public void setImage(ImageIcon image) {
		this.image = image.getImage();
	}
	
	public Image getImage() {
		return image;
	}
	
	public void	setX(int x) {
		this.x = x;
	}
	
	public void	setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getSpawnNum() {
		return spawnNum;
	}
	
	public void setXV(int xv) {
		this.xVelocity = xv;
	}
	
	public void setYV(int yv) {
		this.yVelocity = yv;
	}
	
	public int getXV() {
		return xVelocity;
	}
	
	public int getYV() {
		return yVelocity;
	}
	
	public int getMovement() {
		return movement;
	}
	
	public void setMovement(int i) {
		this.movement = i;
	}
	
	public void setLastMove(int lastMove) {
    	this.lastMove = lastMove;
    }
    
    public int getLastMove() {
    	return lastMove;
    }
    
    public void setLastPos(int num) {
    	this.lastPos = num;
    }
    
    public int getLastPos() {
    	return lastPos;
    }
    
    public void setIsMoving(boolean b) {
    	this.isMoving = b;
    }
    
    public boolean isMoving() {
    	return isMoving;
    }
}