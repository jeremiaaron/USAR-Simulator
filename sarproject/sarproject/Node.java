package sarproject;

public class Node {
	
    private int x;
    private int y;
    private int num;
    private boolean isChecked = false;
    
    Node(int x, int y, int num) {
    	this.x = x;
        this.y = y;
        this.num = num;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getNum() {
    	return num;
    }

    public void setIsChecked(boolean b) {
    	this.isChecked = b;
    }
    
    public boolean isChecked() {
    	return isChecked;
    }
    
}

