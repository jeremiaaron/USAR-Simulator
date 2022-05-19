package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LaunchPage extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 8427633282240937071L;
	JButton startBtn, quitBtn;
	JLabel launchImage;
	Image bgImage;
	
	LaunchPage() {
		
		ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/logo.png"));
		ImageIcon launch = new ImageIcon(getClass().getResource("/res/launchImage.gif"));
		launchImage = new JLabel(launch);
		launchImage.setBounds(0, 0, 720, 400);
		
    	startBtn = new JButton("START");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		startBtn.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	startBtn.addActionListener(this);
    	startBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	startBtn.setForeground(new Color(0xFFFFFF));
    	startBtn.setBackground(new Color(0x061F1F));
    	startBtn.setBounds(10, 410, 350, 60);
    	startBtn.setFocusable(false);
    	startBtn.setEnabled(true);
    	
    	quitBtn = new JButton("QUIT");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		quitBtn.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	quitBtn.addActionListener(this);
    	quitBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	quitBtn.setForeground(new Color(0xFFFFFF));
    	quitBtn.setBackground(new Color(0x061F1F));
    	quitBtn.setBounds(360, 410, 335, 60);
    	quitBtn.setFocusable(false);
    	quitBtn.setEnabled(true);
		
        this.setTitle("USAR Simulation - Launch Page");
        this.setIconImage(programIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final Image bgImage = (new ImageIcon(getClass().getResource("/res/launchPageBg.jpg")).getImage());;
        this.setContentPane(new JPanel(new BorderLayout()) {

			private static final long serialVersionUID = -2988788339988980249L;

			@Override
        	public void paintComponent(Graphics g) {
        		g.drawImage(bgImage, 0, 0, null);
        	}
        });
        
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(720, 520);
        this.setVisible(true);
        this.add(launchImage);
        this.add(startBtn);
        this.add(quitBtn);
        this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startBtn) {
			new MenuPage();
			this.dispose();
		}
		
		else if(e.getSource() == quitBtn) {
			this.dispose();
		}
	}
}
