package sarproject;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Map {
	
	Image image;
	
	Map(ImageIcon image) {
		this.image = image.getImage();
	}
	
	public void change(ImageIcon image) {
		this.image = image.getImage();
	}
	
	public Image getImage() {
		return image;
	}
}
