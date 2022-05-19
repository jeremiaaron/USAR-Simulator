package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;

public class ManualPage extends JDialog {
	
	JPanel manualPanel;
	JTextArea textArea;
	JScrollPane textScroll;
	JLabel manualTitle;
	Image bgImage;
	
	private static final long serialVersionUID = -1295452802361377323L;

	ManualPage() {
		
		ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/logo.png"));
        final Image bgImage = (new ImageIcon(getClass().getResource("/res/darkBgImage.jpg")).getImage());
		
    	manualTitle = new JLabel("MANUAL - HOW TO USE THE PROGRAM");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		manualTitle.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	manualTitle.setForeground(new Color(0xFFFFFF));
    	manualTitle.setBounds(0, 20, 600, 100);
    	manualTitle.setHorizontalAlignment(JLabel.CENTER);
    	manualTitle.setVerticalAlignment(JLabel.TOP);
    	manualTitle.setOpaque(false);
		
    	textArea = new JTextArea();
    	textArea.setEditable(false);
    	textArea.setForeground(new Color(0x33FFFD));
    	textArea.setBackground(new Color(0x061F1F));
    	textScroll = new JScrollPane(textArea);
    	textScroll.setBounds(10, 50, 565, 400);
    	textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	textArea.append("USAR SIMULATION MANUAL\n");
    	textArea.append("Please read carefully!\n");
    	textArea.append("======================\n\n");
    	
    	textArea.append("Before starting the simulation, edit the parameter setting using the \"Edit Parameter\" button\n");
    	textArea.append("from the Menu Page\n\n");
    	
    	textArea.append("HOW TO EDIT THE PARAMETER\n");
    	textArea.append("==========================\n");
    	textArea.append("- Disaster type: choose earthquake or flood as the disaster type (the default is earthquake)\n\n");
    	textArea.append("- SAR Team amount: choose 1 or 2 SAR Team(s) for the simulation (the default amount is 1)\n\n");
    	textArea.append("- Time limit: to limit the duration of the simulation (the default is no time limit), and\n");
    	textArea.append("  *The time limit should be an integer or a double value*\n\n");
    	textArea.append("- Carry limit: how many victim(s) a SAR Team can carry at once (the default carry limit is 2)\n");
    	textArea.append("  *The value entered should be larger than 0 and lower than the max value of integer*\n\n");
    	textArea.append("- Clock starting hour: at which hour the simulation should start (the default clock hour is 12)\n");
    	textArea.append("  *The value entered should be between 0 and 23 inclusive*\n\n");
    	textArea.append("- Clock starting minute: at which minute the simulation should start (the default clock minute is 0)\n");
    	textArea.append("  *The value entered should be between 0 and 59 inclusive*\n\n\n");
    	
    	textArea.append("After editing the parameter, enter the Simulation Page using the \"Simulate\" button\n");
    	textArea.append("in the Menu Page\n\n");
    	
    	textArea.append("HOW TO USE THE SIMULATION\n");
    	textArea.append("=========================\n");
    	textArea.append("1. First of all, click the \"Start Disaster\" button allow the SAR Team(s) to arrive at the destroyed city\n");
    	textArea.append("   The SAR Team(s) will arrive randomly at 5 different entry/exit points as shown in the map.\n\n");
    	
    	textArea.append("2. Next, click the \"Generate Random Data\" button to generate different destruction-level for each\n");
    	textArea.append("   building. The higher the destruction-level, the greater the probability of more victims appearing.\n\n");
    	
    	textArea.append("3. *Optional* Click the \"Show Building Data\" button to display the destruction-level and victim number\n");
    	textArea.append("   number of each building. If the data is displayed, click \"Hide Building Data\" to hide the data.\n\n");
    	
    	textArea.append("4. Click the \"Start Simulation\" button to start the clock and the simulation process. If needed, user may\n");
    	textArea.append("   pause the simulation anytime except when the SAR Team(s) are checking a building and/or passing\n");
    	textArea.append("   a victim to a medical unit at the exit point.\n\n");
    	
    	textArea.append("THE SIMULATION PROCESS\n");
    	textArea.append("=======================\n");
    	textArea.append("     The clock will start when the simulation starts. Each second in realtime is equivalent to\n");
    	textArea.append("10 minutes in the simulation. The SAR Team(s) will proceed to search the building with.\n");
    	textArea.append("the highest destruction-level. If there are multiple buildings with the highest destruction level,\n");
    	textArea.append("the search will be based on the closest distance.\n\n");
    	
    	textArea.append("     After a SAR Team has found victim(s) in a building, they will carry the victim(s) based on the\n");
    	textArea.append("amount of victim(s) they can carry (carry limit) to the nearest exit point to pass the victim(s) to a\n");
    	textArea.append("medical unit. If there are still victim(s) in the building, the SAR Team(s) will return to the\n");
    	textArea.append("building and carry them to the exit point. This process will be executed until there is no victim\n");
    	textArea.append("left in the building, and the SAR Team(s) will continue to search the next building. If there is no\n");
    	textArea.append("victim in the checked building, the SAR Team(s) will immediately search the next building.\n\n");
    	
    	textArea.append("END OF THE SIMULATION\n");
    	textArea.append("=====================\n");
    	textArea.append("There are two possible outcomes in the simulation:\n\n");
    	
    	textArea.append("a) All buildings are checked and all victims are rescued\n");
    	textArea.append("   This outcome can be reached if every victim is successfully rescued with a duration below the\n");
    	textArea.append("   time limit (if there is any). In the simulation log, there will be an information on how long\n");
    	textArea.append("   the simulation took as well as how long the user paused the simulation.\n\n");
    	
    	textArea.append("b) Not every building is fully checked and rescued\n");
    	textArea.append("   This outcome will happen if every victim is not successfully rescued after the time limit has\n");
    	textArea.append("   been reached. In the simulation log, there will be an information on how many building(s)\n");
    	textArea.append("   are failed to be fully checked and rescued.\n\n");
    	
    	textArea.append("After the simulation has ended, user can save the simulation log by clicking the \"Save Log Data\"\n");
    	textArea.append("button to review and analyze the simulation process.\n");
    	
    	JPanel manualPanel = new JPanel() {

			private static final long serialVersionUID = -5539393375561252367L;

			@Override
    		protected void paintComponent(Graphics g) {
    			super.paintComponent(g);
    			g.drawImage(bgImage, 0, 0, 600, 500, this);
    		}
    	};
    	manualPanel.setLayout(null);
    	manualPanel.setSize(600, 500);
		manualPanel.add(manualTitle);
		manualPanel.add(textScroll);
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			textScroll.getVerticalScrollBar().setValue(0);
    		}
    	});
    	manualPanel.setVisible(true);
    	
		this.setTitle("USAR Simulation - Manual Page");
		this.setIconImage(programIcon.getImage());
		this.setLayout(null);
		this.add(manualPanel);
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
	}
}
