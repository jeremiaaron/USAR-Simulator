package sarproject;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Status {
	
	//String detail;
	Image detail;
	
	Status(ImageIcon image) {
		this.detail = image.getImage();
	}
	
	public void change(ImageIcon image) {
		this.detail = image.getImage();
	}
	
	public Image getDetail() {
		return detail;
	}
}