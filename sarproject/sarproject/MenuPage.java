package sarproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MenuPage extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 3313533495044719180L;
	ParameterFrame parameterFrame;
	JTextArea infoArea;
	JScrollPane infoScroll;
	static JButton simulateBtn;
	JButton manualBtn;
	JButton parameterBtn;
	JLabel infoTitle;
	Image bgImage;
	
	MenuPage() {
		
		ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/logo.png"));
		
    	infoTitle = new JLabel("INFORMATION");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		infoTitle.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	infoTitle.setForeground(new Color(0xFFFFFF));
    	infoTitle.setBounds(285, 20, 140, 50);
    	infoTitle.setHorizontalAlignment(JLabel.CENTER);
    	infoTitle.setVerticalAlignment(JLabel.TOP);
    	infoTitle.setOpaque(false);
		
    	infoArea = new JTextArea();
    	infoArea.setEditable(false);
    	infoArea.setForeground(new Color(0x33FFFD));
    	infoArea.setBackground(new Color(0x061F1F));
    	infoScroll = new JScrollPane(infoArea);
    	infoScroll.setBounds(10, 50, 685, 340);
    	infoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
    	infoArea.append("USAR Simulation\n");
    	infoArea.append("Destruction-Level Based Urban Search and Rescue Simulation\n");
    	infoArea.append("Java Project\n");
    	infoArea.append("Developed using\n");
    	infoArea.append("Java™ SE Development Kit 15 (15.0.1)\n");
    	infoArea.append("Java™ SE Runtime Environment 8 (1.8.0 Update 251)\n");
    	infoArea.append("==========================================\n\n");
    	
    	infoArea.append("Developed by:\n");
    	infoArea.append("Jeremiah Aaron Rahardja <2020390014>\n");
    	infoArea.append("Supervised by:\n");
    	infoArea.append("Prof. Ir. Teddy Mantoro, MSc., PhD., SMIEEE\n");
    	infoArea.append("==================================\n\n");
    	
    	infoArea.append("Program Description:\n");
    	infoArea.append("================\n");
    	infoArea.append("Destruction-Level Based Urban Search and Rescue Simulation is a program made using Java programming language,\n");
    	infoArea.append("mainly utilizing Java Swing for handling the program GUI as well as animation. The main purpose of this program is to\n");
    	infoArea.append("simulate how Urban SAR team(s) work in order to search and rescue every victim in a building based on destruction-level\n");
    	infoArea.append("priority. Destruction-level represents how severe a building is damaged by a disaster. If the destruction-level\n");
    	infoArea.append("of a building is higher, the chance of a victim getting injured in that building will also be higher. Therefore, the SAR\n");
    	infoArea.append("team(s) will check the building which has the highest destruction-level and then pass the victim(s) to the medical unit\n");
    	infoArea.append("through the nearest exit point. Every path taken by the USAR is based on the closest distance to the destination,\n");
    	infoArea.append("implementing Dijkstra Algorithm.\n\n");
    	
    	infoArea.append("Menu Page Description:\n");
    	infoArea.append("==================\n");
    	infoArea.append("This menu page consists of simulate and manual button.\n");
    	infoArea.append(" - \"Manual\" button:\n");
    	infoArea.append("   First time user is highly suggested to read the manual of this program using the manual button.\n");
    	infoArea.append(" - \"Edit Parameter\" button:\n");
    	infoArea.append("   Before simulating, user has to set the parameters to be used for the simulation.\n");
    	infoArea.append(" - \"Simulate\" button:\n");
    	infoArea.append("   After reading the manual of this program, press the simulate button to start the Search and Rescue Simulation.\n\n");
    	
    	infoArea.append("Manual Page Description:\n");
    	infoArea.append("===================\n");
       	infoArea.append("The Manual Page contains the explanation on how to use the program and receive the desired result of the simulation.\n");
       	infoArea.append("User can go to this page by clicking the \"Manual\" button. The explanation will show the use of every button and text in the\n");
       	infoArea.append("Simulation Page.\n\n");
       	
    	infoArea.append("Parameter Setting Page Description:\n");
    	infoArea.append("===========================\n");
       	infoArea.append("The Parameter Setting Page contains the parameter which the user can modify to test different variables for the simulation.\n");
       	infoArea.append("User can go to this page by clicking the \"Edit Parameter\" button.\n\n");
       	
    	infoArea.append("Simulation Page Description:\n");
    	infoArea.append("======================\n");
    	infoArea.append("The Simulation Page contains the main purpose of the program, the simulation process. To see how the\n");
    	infoArea.append("Simulation Page works, please refer to the Manual Page\n\n");
    	
    	infoArea.append("Supporting software and sites used to develop the program:\n");
    	infoArea.append("============================================\n");
    	infoArea.append("- Eclipse IDE (as the main IDE to develop the code)\n");
    	infoArea.append("- VisualVM (to monitor program performance and memory usage)\n");
    	infoArea.append("- Google Maps (as a resource for individual building picture)\n");
    	infoArea.append("- Pixelsquid, GraphicsCrate, Dreamstime, PNGIX, PngFind, The Conversation, Pinterest, PNGItem\n");
    	infoArea.append("  (as resources for visual elements)\n");
    	infoArea.append("- Adobe Photoshop CC 2021 (to create and combine visual elements for the map, background images, etc.)\n");
    	
    	simulateBtn = new JButton("SIMULATE");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		simulateBtn.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	simulateBtn.addActionListener(this);
    	simulateBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	simulateBtn.setForeground(new Color(0xFFFFFF));
    	simulateBtn.setBackground(new Color(0x061F1F));
    	simulateBtn.setBounds(10, 410, 345, 60);
    	simulateBtn.setFocusable(false);
    	simulateBtn.setEnabled(false);
    	
    	manualBtn = new JButton("MANUAL");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		manualBtn.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	manualBtn.addActionListener(this);
    	manualBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	manualBtn.setForeground(new Color(0xFFFFFF));
    	manualBtn.setBackground(new Color(0x061F1F));
    	manualBtn.setBounds(365, 410, 330, 60);
    	manualBtn.setFocusable(false);
    	manualBtn.setEnabled(true);
		
    	parameterBtn = new JButton("EDIT PARAMETER");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		parameterBtn.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	parameterBtn.addActionListener(this);
    	parameterBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	parameterBtn.setForeground(new Color(0xFFFFFF));
    	parameterBtn.setBackground(new Color(0x061F1F));
    	parameterBtn.setBounds(190, 480, 335, 60);
    	parameterBtn.setFocusable(false);
    	parameterBtn.setEnabled(true);
    	
        this.setTitle("USAR Simulation - Menu Page");
        this.setIconImage(programIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final Image bgImage = (new ImageIcon(getClass().getResource("/res/darkBgImage.jpg")).getImage());
        this.setContentPane(new JPanel(new BorderLayout()) {

			private static final long serialVersionUID = 832675746236547626L;

			@Override
        	public void paintComponent(Graphics g) {
        		g.drawImage(bgImage, 0, 0, null);
        	}
        });
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(720, 600);
        this.setVisible(true);
        this.add(infoTitle);
        this.add(infoScroll);
        this.add(simulateBtn);
        this.add(manualBtn);
        this.add(parameterBtn);
        this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == simulateBtn) {
			new MainFrame();
			this.dispose();
			parameterFrame = null;
		}
		
		else if(e.getSource() == manualBtn) {
			new ManualPage();
		}
		
		else if(e.getSource() == parameterBtn) {
			parameterFrame = new ParameterFrame();
		}
	}
}