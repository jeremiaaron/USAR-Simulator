package src;

import java.awt.Color;
import javax.swing.JLabel;

public class Building {
	
	private int x;
	private int y;
	private int victimNum = 0;
	private int destLv = 0;
	private int num;
	private int index;
	private JLabel buildingText;
	private JLabel destText;
	private JLabel victimText;
	private boolean isChecked = false;
	private boolean isHeaded = false;
	private String name;
	
	Building(int x, int y, int num, int index, String name) {
		this.x = x;
		this.y = y;
		this.num = num;
		this.index = index;
		this.name = name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setVictimNum(int i) {
		this.victimNum = i;
	}
	
	public int getVictimNum() {
		return victimNum;
	}
	
	public void setDestLv(int i) {
		this.destLv = i;
	}
	
	public int getDestLv() {
		return destLv;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIsChecked(boolean b) {
		this.isChecked = b;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setIsHeaded(boolean b) {
		this.isHeaded = b;
	}
	
	public boolean isHeaded() {
		return isHeaded;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBuildingText(JLabel buildingText) {
		this.buildingText = buildingText;
	}
	
	public JLabel getBuildingText() {
		return buildingText;
	}
	
	public void setDestText(JLabel victimText) {
		this.destText = victimText;
	}
	
	public JLabel getDestText() {
		return destText;
	}
	
	public void setVictimText(JLabel victimText) {
		this.victimText = victimText;
	}
	
	public JLabel getVictimText() {
		return victimText;
	}
	
	public void setBuildingTextColor(Color color) {
		buildingText.setForeground(color);
	}
	
	public void setVictimTextColor(Color color) {
		victimText.setForeground(color);
	}
	
	public String toString() {
		return name;
	}
}
