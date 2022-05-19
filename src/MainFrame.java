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

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	SimPanel simPanel;
	JButton backBtn;
	
    MainFrame() {
    	
    	backBtn = new JButton("GO BACK TO MENU PAGE");
    	backBtn.addActionListener(this);
    	backBtn.setBounds(320, 550, 250, 50);
    	backBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	backBtn.setForeground(new Color(0xFFFFFF));
    	backBtn.setBackground(new Color(0x061F1F));
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		backBtn.setFont(font.deriveFont(Font.PLAIN, 10));
    	}
    	catch(Exception e){}
    	backBtn.setEnabled(true);
    	backBtn.setFocusable(false);
    	
    	simPanel = new SimPanel();
		ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/logo.png"));

        this.setTitle("USAR Simulation - Simulation Page");
        this.setIconImage(programIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Image bgImage = (new ImageIcon(getClass().getResource("/res/frameBgImage.jpg")).getImage());
        this.setContentPane(new JPanel(new BorderLayout()) {

			private static final long serialVersionUID = -6306199206089986184L;

			@Override
        	public void paintComponent(Graphics g) {
        		g.drawImage(bgImage, 0, 0, null);
        	}
        });
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(900, 650);
        this.add(simPanel);
        this.add(backBtn);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == backBtn) {
			this.dispose();
			simPanel = null;
			new MenuPage();
		}
	}
    
    
}
