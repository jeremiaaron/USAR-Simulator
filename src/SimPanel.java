package src;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import algorithm.Algorithm;
import algorithm.AlgNode;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SimPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -6688071736268730475L;
	
	ArrayList<Node> nodes; //list of nodes
	ArrayList<Building> buildings, currPriority1, currPriority2; //list of buildings and priorities based on destruction level
	ArrayList<Integer> buildingNums, exitPoints; //list of building numbers and exit points
	List<Integer> shortestPaths1, shortestPaths2, destList; //list of shortest paths and destruction level
	
	private int index1, index2, checkedBuilding;	//indexes for shortestPaths iteration
	private final int passDelay = 1, longDelay = 4, shortDelay = 2; //every type of delay time
	
	private boolean startIsClicked, pauseIsClicked;	//for determining pause duration
	private boolean sar1isChecking, sar2isChecking, sar1isAtExit, sar2isAtExit;
	private boolean moveBack1, moveBack2, moveToExit1, moveToExit2, victimFound1, victimFound2;
	private long pauseTime, startTime, endTime, pauseStartTime, pauseEndTime;
	
    Sar sar1, sar2;
    private int sar1Spawn, sar2Spawn; //SAR Team 1 and 2 spawn location
    private int currCarry1, currCarry2, carryLimit; //current move indicator, current victim(s) carried amount & limit
    
    //labels for building data prompt and checked buildings text
    JLabel mallDest, hotelDest, restaurantDest, parkDest, apartmentDest, policeStationDest,
    	   officeDest, dinerDest, schoolDest,
    	   mallVictim, hotelVictim, restaurantVictim, parkVictim, apartmentVictim, policeStationVictim,
    	   officeVictim, dinerVictim, schoolVictim,
    	   mallText, hotelText, restaurantText, parkText, apartmentText, policeStationText,
    	   officeText, dinerText, schoolText, checkedBuildingsText;
    
    JLabel status, logTitle, clock;  //labels for simulation status and "Simulation log"
    private int clockHour, clockMin;
    
    //text area and scroll pane for Simulation log
    JTextArea logArea;
    JScrollPane logScroll;
    
    //buttons for the simulation
    JButton startDisasterBtn, startSimBtn, randomizeBtn, showDataBtn, saveLogBtn;
    
    //map image
    Image map, frameBgImage;
    
    Timer timer1, timer2, pause, clockTimer;  //timer for animation
    Algorithm g;  //Graph for dijkstra algorithm
    
    //declares Building objects
    Building mall, hotel, restaurant, park, apartment, policeStation, office, diner, school;
    Building lastBuilding1, lastBuilding2;
    
    FileWriter filewriter; //filewriter for saving simulation log
    
    /* DO NOT MODIFY THE CONSTRUCTOR
     * contains the default generated nodes, buildings, edges, etc. (will never change)
     */
    SimPanel() {
     	
    	buildings = new ArrayList<Building>();
    	currPriority1 = new ArrayList<Building>();
    	currPriority2 = new ArrayList<Building>();
    	destList = new ArrayList<Integer>();
    	shortestPaths1 = new ArrayList<Integer>();
    	shortestPaths2 = new ArrayList<Integer>();
    	buildingNums = new ArrayList<Integer>();
    	exitPoints = new ArrayList<Integer>();
    	
    	clockHour = getClockHour(); clockMin = getClockMin();
    	carryLimit = getCarryLimit();
    	checkedBuilding = 0;
    	index1 = 0; index2 = 0;
    	pauseStartTime = 0; pauseEndTime = 0;
    	startTime = 0; endTime = 0; pauseTime = 0;
    	currCarry1 = 0; currCarry2 = 0;
    	startIsClicked = false; pauseIsClicked = false;
    	sar1isChecking = false; sar2isChecking = false; sar1isAtExit = false; sar2isAtExit = false;
    	moveBack1 = false; moveToExit1 = false; victimFound1 = false;
    	moveBack2 = false; moveToExit2 = false; victimFound2 = false;
    	
    	frameBgImage = (new ImageIcon(getClass().getResource("/res/frameBgImage.jpg")).getImage());
    	if(getClockHour() > 6 && getClockHour() < 18)
    		map = (new ImageIcon(getClass().getResource("/res/realisticMap.jpg")).getImage());
    	else
    		map = (new ImageIcon(getClass().getResource("/res/realisticMapNight.jpg")).getImage());
    	
    	this.setBounds(0, 0, 900, 550);
        this.setBackground(new Color(0xB0B0B0));
    	
        
        //initializes all nodes as Node(x, y, num)
        //nums are for identifying the nodes
    	Node node1 = new Node(180, 366, 1); Node node2 = new Node(60, 170, 2); Node node3 = new Node(230, 39, 3);
        Node node4 = new Node(165, 126, 4); Node node5 = new Node(380, 45, 5); Node node6 = new Node(548, 140, 6);
        Node node7 = new Node(480, 330, 7); Node node8 = new Node(305, 342, 8); Node node9 = new Node(180, 342, 9);
        Node node10 = new Node(98, 342, 10); Node node11 = new Node(98, 190, 11); Node node12 = new Node(60, 190, 12);
        Node node13 = new Node(137, 190, 13); Node node14 = new Node(137, 126, 14); Node node15 = new Node(137, 63, 15);
        Node node16 = new Node(230, 63, 16); Node node17 = new Node(305, 63, 17); Node node18 = new Node(305, 21, 18);
        Node node19 = new Node(380, 21, 19); Node node20 = new Node(572, 21, 20); Node node21 = new Node(572, 140, 21);
        Node node22 = new Node(572, 190, 22); Node node23 = new Node(480, 190, 23); Node node24 = new Node(480, 306, 24);
        Node node25 = new Node(305, 306, 25); Node node26 = new Node(305, 190, 26); Node node27 = new Node(98, 270, 27);
        Node node28 = new Node(122, 270, 28); Node node29 = new Node(420, 190, 29); Node node30 = new Node(420, 214, 30);
        Node node31 = new Node(305, 390, 31); Node node32 = new Node(10, 190, 32); Node node33 = new Node(305, 10, 33);
        Node node34 = new Node(590, 190, 34); Node node35 = new Node(590, 306, 35);
        
        //adds the every node to nodes array list
        NodeList.addNode(node1); NodeList.addNode(node2); NodeList.addNode(node3);
        NodeList.addNode(node4); NodeList.addNode(node5); NodeList.addNode(node6);
        NodeList.addNode(node7); NodeList.addNode(node8); NodeList.addNode(node9);
        NodeList.addNode(node10); NodeList.addNode(node11); NodeList.addNode(node12);
        NodeList.addNode(node13); NodeList.addNode(node14); NodeList.addNode(node15);
        NodeList.addNode(node16); NodeList.addNode(node17); NodeList.addNode(node18);
        NodeList.addNode(node19); NodeList.addNode(node20); NodeList.addNode(node21);
        NodeList.addNode(node22); NodeList.addNode(node23); NodeList.addNode(node24);
        NodeList.addNode(node25); NodeList.addNode(node26); NodeList.addNode(node27);
        NodeList.addNode(node28); NodeList.addNode(node29); NodeList.addNode(node30);
        NodeList.addNode(node31); NodeList.addNode(node32); NodeList.addNode(node33);
        NodeList.addNode(node34); NodeList.addNode(node35);
    	
        //initializes graph and every vertex according to the nodes and their distances
    	g = new Algorithm();
        g.addAlgNode(1, Arrays.asList(new AlgNode(9, node1.getY() - node9.getY())));
        g.addAlgNode(2, Arrays.asList(new AlgNode(12, node12.getY() - node2.getY())));
        g.addAlgNode(3, Arrays.asList(new AlgNode(16, node16.getY() - node3.getY())));
        g.addAlgNode(4, Arrays.asList(new AlgNode(14, node4.getX() - node14.getX())));
        g.addAlgNode(5, Arrays.asList(new AlgNode(19, node5.getY() - node19.getY())));
        g.addAlgNode(6, Arrays.asList(new AlgNode(21, node21.getX() - node6.getX())));
        g.addAlgNode(7, Arrays.asList(new AlgNode(24, node7.getY() - node24.getY())));
        g.addAlgNode(8, Arrays.asList(new AlgNode(9, node8.getX() - node9.getX()), new AlgNode(25, node8.getY() - node25.getY()),
        							 new AlgNode(31, node31.getY() - node8.getY())));
        g.addAlgNode(9, Arrays.asList(new AlgNode(8, node8.getX() - node9.getX()), new AlgNode(10, node9.getX() - node10.getX()),
        							 new AlgNode(1, node1.getY() - node9.getY())));
        g.addAlgNode(10, Arrays.asList(new AlgNode(9, node9.getX() - node10.getX()), new AlgNode(27, node10.getY() - node27.getY())));
        g.addAlgNode(11, Arrays.asList(new AlgNode(27, node27.getY() - node11.getY()), new AlgNode(12, node11.getX() - node12.getX()),
        							  new AlgNode(13, node13.getX() - node11.getX())));
        g.addAlgNode(12, Arrays.asList(new AlgNode(2, node12.getY() - node2.getY()), new AlgNode(11, node11.getX() - node12.getX()),
        							  new AlgNode(32, node12.getX() - node32.getX())));
        g.addAlgNode(13, Arrays.asList(new AlgNode(11, node13.getX() - node11.getX()), new AlgNode(26, node26.getX() - node13.getX()),
        							  new AlgNode(14, node13.getY() - node14.getY())));
        g.addAlgNode(14, Arrays.asList(new AlgNode(13, node13.getY() - node14.getY()), new AlgNode(4, node4.getX() - node14.getX()),
        							  new AlgNode(15, node14.getY() - node15.getY())));
        g.addAlgNode(15, Arrays.asList(new AlgNode(14, node14.getY() - node15.getY()), new AlgNode(16, node16.getX() - node15.getX())));
        g.addAlgNode(16, Arrays.asList(new AlgNode(3, node16.getY() - node3.getY()), new AlgNode(15, node16.getX() - node15.getX()),
        							  new AlgNode(17, node17.getX() - node16.getX())));
        g.addAlgNode(17, Arrays.asList(new AlgNode(16, node17.getX() - node16.getX()), new AlgNode(18, node17.getY() - node18.getY()),
        							  new AlgNode(26, node26.getY() - node17.getY())));
        g.addAlgNode(18, Arrays.asList(new AlgNode(17, node17.getY() - node18.getY()), new AlgNode(19, node19.getX() - node18.getX()),
        							  new AlgNode(33, node18.getY() - node33.getY())));
        g.addAlgNode(19, Arrays.asList(new AlgNode(5, node5.getY() - node19.getY()), new AlgNode(18, node19.getX() - node18.getX()),
        							  new AlgNode(20, node20.getX() - node19.getX())));
        g.addAlgNode(20, Arrays.asList(new AlgNode(19, node20.getX() - node19.getX()), new AlgNode(21, node21.getY() - node20.getY())));
        g.addAlgNode(21, Arrays.asList(new AlgNode(6, node21.getX() - node6.getX()), new AlgNode(20, node21.getY() - node20.getY()),
        							  new AlgNode(22, node22.getY() - node21.getY())));
        g.addAlgNode(22, Arrays.asList(new AlgNode(21, node22.getY() - node21.getY()), new AlgNode(23, node22.getX() - node23.getX()),
        							  new AlgNode(34, node34.getX() - node22.getX())));
        g.addAlgNode(23, Arrays.asList(new AlgNode(22, node22.getX() - node23.getX()), new AlgNode(24, node24.getY() - node23.getY()),
        							  new AlgNode(29, node23.getX() - node29.getX())));
        g.addAlgNode(24, Arrays.asList(new AlgNode(7, node7.getY() - node24.getY()), new AlgNode(23, node24.getY() - node23.getY()),
        							  new AlgNode(25, node24.getX() - node25.getX()), new AlgNode(35, node35.getX() - node24.getX())));
        g.addAlgNode(25, Arrays.asList(new AlgNode(8, node8.getY() - node25.getY()), new AlgNode(24, node24.getX() - node25.getX()),
        							  new AlgNode(26, node25.getY() - node26.getY())));
        g.addAlgNode(26, Arrays.asList(new AlgNode(13, node26.getX() - node13.getX()), new AlgNode(17, node26.getY() - node17.getY()),
        							  new AlgNode(25, node25.getY() - node26.getY()), new AlgNode(29, node29.getX() - node26.getX())));
        g.addAlgNode(27, Arrays.asList(new AlgNode(10, node10.getY() - node27.getY()), new AlgNode(11, node27.getY() - node11.getY()),
        							  new AlgNode(28, node28.getX() - node27.getX())));
        g.addAlgNode(28, Arrays.asList(new AlgNode(27, node28.getX() - node27.getX())));
        g.addAlgNode(29, Arrays.asList(new AlgNode(23, node23.getX() - node29.getX()), new AlgNode(26, node29.getX() - node26.getX()),
        							  new AlgNode(30, node30.getY() - node29.getY())));
        g.addAlgNode(30, Arrays.asList(new AlgNode(29, node30.getY() - node29.getY())));
        g.addAlgNode(31, Arrays.asList(new AlgNode(8, node31.getY() - node8.getY())));
        g.addAlgNode(32, Arrays.asList(new AlgNode(12, node12.getX() - node32.getX())));
        g.addAlgNode(33, Arrays.asList(new AlgNode(18, node18.getY() - node33.getY())));
        g.addAlgNode(34, Arrays.asList(new AlgNode(22, node34.getX() - node22.getX())));
        g.addAlgNode(35, Arrays.asList(new AlgNode(24, node35.getX() - node24.getX())));
        
        //initializes every building as Building(node x, node y, node num, index, building name)
        mall = new Building(node1.getX(), node1.getY(), 1, 1, "Mall");
        hotel = new Building(node28.getX(), node28.getY(), 28, 2, "Hotel");
        restaurant = new Building(node2.getX(), node2.getY(), 2, 3, "Restaurant");
        park = new Building(node4.getX(), node4.getY(), 4, 4, "Park");
        apartment = new Building(node3.getX(), node3.getY(), 3, 5, "Apartment");
        policeStation = new Building(node5.getX(), node5.getY(), 5, 6, "Police station");
        office = new Building(node6.getX(), node6.getY(), 6, 7, "Office");
        diner = new Building(node30.getX(), node30.getY(), 30, 8, "Diner");
        school = new Building(node7.getX(), node7.getY(), 7, 9, "School");
        
        //adds every building to the buildings array list
        buildings.add(mall); buildingNums.add(1);
        buildings.add(hotel); buildingNums.add(28);
        buildings.add(restaurant); buildingNums.add(2);
        buildings.add(park); buildingNums.add(4);
        buildings.add(apartment); buildingNums.add(3);
        buildings.add(policeStation); buildingNums.add(5);
        buildings.add(office); buildingNums.add(6);
        buildings.add(diner); buildingNums.add(30);
        buildings.add(school); buildingNums.add(7);
        
        //adds exit points to the list
        exitPoints.add(31);
        exitPoints.add(32);
        exitPoints.add(33);
        exitPoints.add(34);
        exitPoints.add(35);
        
        initGraphics(); //initializes the graphics
        
        //timer for SAR Team 1
    	timer1 = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//if simulation exceeds time limit
				if(!ParameterFrame.timeField.getText().equals("") &&
						(double)(((System.currentTimeMillis() - startTime)/1000) - (pauseTime/1000)) >
						Double.parseDouble(ParameterFrame.timeField.getText())) {
						simFailed();
					}
				
				
				else if(checkedBuilding >= buildings.size()) { //if all buildings are checked
					completeSim();
				}
				
				
				else move1(); //else execute move animation
				
				repaint();
			}
    		
    	});
        
    	if(isTwoTeam()) {
    		//timer for SAR Team 2
	    	timer2 = new Timer(10, new ActionListener() {
	    		
	    		@Override
	    		public void actionPerformed(ActionEvent e) {
	    			
	    			//if simulation exceeds time limit
	    			if(!ParameterFrame.timeField.getText().equals("") &&
	    					(double)(((System.currentTimeMillis() - startTime)/1000) - (pauseTime/1000)) >
	    					Double.parseDouble(ParameterFrame.timeField.getText())) {
	    					simFailed();
	    				}
	    			
	    			
	    			else if(checkedBuilding >= buildings.size()) { //if all buildings are checked
	    				completeSim();
	    			}
	    			
	    			
	    			else move2(); //else execute move animation
	    			
	    			repaint();
	    		}
	    		
	    	});
    	}
    	
    	//timer for clock
    	clockTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				clockMin += 10;
				if(clockMin > 59) {
					clockHour += 1;
					if(clockHour > 23) {
						clockHour = 0;
					}
					clockMin -= 60;
				}
				
				String hour = "" + clockHour, min = "" + clockMin;
				if(clockHour < 10) {
					hour = "0" + clockHour;
				}
				
				if(clockMin < 10) {
					min = "0" + clockMin;
				}
				
				clock.setText(hour + ":" + min);
				
				if(clockHour == 18) {
					if(isFlood()) {
						map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedFloodNight.jpg")).getImage());
					}
					else map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedQuakeNight.jpg")).getImage());
				}
				else if(clockHour == 7) {
					if(isFlood()) {
						map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedFlood.jpg")).getImage());
					}
					else map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedQuake.jpg")).getImage());
				}
				
				repaint();
			}
    	});
    	}
    
     
    /* initGraphics(), addToPanel(), initBuildingData(), paintComponent(Graphics g)
     * initializes necessary graphics
     */
    private void initGraphics() {	//initialize the graphics
    	
    	initBuildingData(); //initialize building data (displays destruction level and victim number)
    	
    	initInterface(); //initialize interface elements (texts, buttons, etc.)
    	
    	initCheckedBuildingsText(); //initialize checked buildings text
    	
        addToPanel(); //adds every element to the SimPanel
        
        nodes = NodeList.getNodes();	//store every node to the list of nodes
        
        //RANDOMIZE SAR TEAM SPAWN LOCATION BETWEEN 5 SPAWN POINTS
        @SuppressWarnings("serial")
		ArrayList<Integer> spawnList = new ArrayList<Integer>() {
        	{
        		add(31); add(32); add(33); add(34); add(35);
        	}
        };
        
        int rnd = (int)(Math.random() * spawnList.size());
        sar1Spawn = spawnList.get(rnd);
        spawnList.remove(rnd);
        
        rnd = (int)(Math.random() * spawnList.size());
        sar2Spawn = spawnList.get(rnd);
        
        spawnList.removeAll(spawnList);
        
        sar1 = new Sar(nodes.get(sar1Spawn - 1).getX() - 10, nodes.get(sar1Spawn - 1).getY() - 10,
        	   new ImageIcon(getClass().getResource("/res/1px.png")), sar1Spawn);
        sar1.setLastPos(sar1Spawn);
        
        sar2 = new Sar(nodes.get(sar2Spawn - 1).getX() - 10, nodes.get(sar2Spawn - 1).getY() - 10,
         	   new ImageIcon(getClass().getResource("/res/1px.png")), sar2Spawn);
        sar2.setLastPos(sar2Spawn);
        
    }
    
    private void initBuildingData() {	//initializes the prompts for building data
    	
    	mallDest = new JLabel();					
    	mallDest.setText("");
    	mallDest.setForeground(Color.WHITE);
    	mallDest.setBounds(0, 0, 120, 20);
    	mallDest.setLocation(190, 360);
    	mallDest.setBackground(new Color(0x061F1F));
    	mallDest.setOpaque(false);
    	mall.setDestText(mallDest);
    	
    	mallVictim = new JLabel();
    	mallVictim.setText("");
    	mallVictim.setForeground(Color.WHITE);
    	mallVictim.setBounds(0, 0, 120, 20);
    	mallVictim.setLocation(190, 380);
    	mallVictim.setBackground(new Color(0x061F1F));
    	mallVictim.setOpaque(false);
    	mall.setVictimText(mallVictim);
    	
    	hotelDest = new JLabel();					
    	hotelDest.setText("");
    	hotelDest.setForeground(Color.WHITE);
    	hotelDest.setBounds(0, 0, 120, 20);
    	hotelDest.setLocation(150, 280);
    	hotelDest.setBackground(new Color(0x061F1F));
    	hotelDest.setOpaque(false);
    	hotel.setDestText(hotelDest);
    	
    	hotelVictim = new JLabel();
    	hotelVictim.setText("");
    	hotelVictim.setForeground(Color.WHITE);
    	hotelVictim.setBounds(0, 0, 120, 20);
    	hotelVictim.setLocation(150, 300);
    	hotelVictim.setBackground(new Color(0x061F1F));
    	hotelVictim.setOpaque(false);
    	hotel.setVictimText(hotelVictim);
    	
    	restaurantDest = new JLabel();					
    	restaurantDest.setText("");
    	restaurantDest.setForeground(Color.WHITE);
    	restaurantDest.setBounds(0, 0, 120, 20);
    	restaurantDest.setLocation(0, 60);
    	restaurantDest.setBackground(new Color(0x061F1F));
    	restaurantDest.setOpaque(false);
    	restaurant.setDestText(restaurantDest);
    	
    	restaurantVictim = new JLabel();
    	restaurantVictim.setText("");
    	restaurantVictim.setForeground(Color.WHITE);
    	restaurantVictim.setBounds(0, 0, 120, 20);
    	restaurantVictim.setLocation(0, 80);
    	restaurantVictim.setBackground(new Color(0x061F1F));
    	restaurantVictim.setOpaque(false);
    	restaurant.setVictimText(restaurantVictim);
    	
    	parkDest = new JLabel();					
    	parkDest.setText("");
    	parkDest.setForeground(Color.WHITE);
    	parkDest.setBounds(0, 0, 120, 20);
    	parkDest.setLocation(160, 136);
    	parkDest.setBackground(new Color(0x061F1F));
    	parkDest.setOpaque(false);
    	park.setDestText(parkDest);
    	
    	parkVictim = new JLabel();
    	parkVictim.setText("");
    	parkVictim.setForeground(Color.WHITE);
    	parkVictim.setBounds(0, 0, 120, 20);
    	parkVictim.setLocation(160, 156);
    	parkVictim.setBackground(new Color(0x061F1F));
    	parkVictim.setOpaque(false);
    	park.setVictimText(parkVictim);
    	
    	apartmentDest = new JLabel();					
    	apartmentDest.setText("");
    	apartmentDest.setForeground(Color.WHITE);
    	apartmentDest.setBounds(0, 0, 120, 20);
    	apartmentDest.setLocation(70, 5);
    	apartmentDest.setBackground(new Color(0x061F1F));
    	apartmentDest.setOpaque(false);
    	apartment.setDestText(apartmentDest);
    	
    	apartmentVictim = new JLabel();
    	apartmentVictim.setText("");
    	apartmentVictim.setForeground(Color.WHITE);
    	apartmentVictim.setBounds(0, 0, 120, 20);
    	apartmentVictim.setLocation(70, 25);
    	apartmentVictim.setBackground(new Color(0x061F1F));
    	apartmentVictim.setOpaque(false);
    	apartment.setVictimText(apartmentVictim);
    	
    	policeStationDest = new JLabel();					
    	policeStationDest.setText("");
    	policeStationDest.setForeground(Color.WHITE);
    	policeStationDest.setBounds(0, 0, 120, 20);
    	policeStationDest.setLocation(320, 100);
    	policeStationDest.setBackground(new Color(0x061F1F));
    	policeStationDest.setOpaque(false);
    	policeStation.setDestText(policeStationDest);
    	
    	policeStationVictim = new JLabel();
    	policeStationVictim.setText("");
    	policeStationVictim.setForeground(Color.WHITE);
    	policeStationVictim.setBounds(0, 0, 120, 20);
    	policeStationVictim.setLocation(320, 120);
    	policeStationVictim.setBackground(new Color(0x061F1F));
    	policeStationVictim.setOpaque(false);
    	policeStation.setVictimText(policeStationVictim);
    	
    	officeDest = new JLabel();					
    	officeDest.setText("");
    	officeDest.setForeground(Color.WHITE);
    	officeDest.setBounds(0, 0, 120, 20);
    	officeDest.setLocation(440, 135);
    	officeDest.setBackground(new Color(0x061F1F));
    	officeDest.setOpaque(false);
    	office.setDestText(officeDest);
    	
    	officeVictim = new JLabel();
    	officeVictim.setText("");
    	officeVictim.setForeground(Color.WHITE);
    	officeVictim.setBounds(0, 0, 120, 20);
    	officeVictim.setLocation(440, 155);
    	officeVictim.setBackground(new Color(0x061F1F));
    	officeVictim.setOpaque(false);
    	office.setVictimText(officeVictim);
    	
    	dinerDest = new JLabel();					
    	dinerDest.setText("");
    	dinerDest.setForeground(Color.WHITE);
    	dinerDest.setBounds(0, 0, 120, 20);
    	dinerDest.setLocation(340, 250);
    	dinerDest.setBackground(new Color(0x061F1F));
    	dinerDest.setOpaque(false);
    	diner.setDestText(dinerDest);
    	
    	dinerVictim = new JLabel();
    	dinerVictim.setText("");
    	dinerVictim.setForeground(Color.WHITE);
    	dinerVictim.setBounds(0, 0, 120, 20);
    	dinerVictim.setLocation(340, 270);
    	dinerVictim.setBackground(new Color(0x061F1F));
    	dinerVictim.setOpaque(false);
    	diner.setVictimText(dinerVictim);
    	
    	schoolDest = new JLabel();					
    	schoolDest.setText("");
    	schoolDest.setForeground(Color.WHITE);
    	schoolDest.setBounds(0, 0, 120, 20);
    	schoolDest.setLocation(460, 330);
    	schoolDest.setBackground(new Color(0x061F1F));
    	schoolDest.setOpaque(false);
    	school.setDestText(schoolDest);
    	
    	schoolVictim = new JLabel();
    	schoolVictim.setText("");
    	schoolVictim.setForeground(Color.WHITE);
    	schoolVictim.setBounds(0, 0, 120, 20);
    	schoolVictim.setLocation(460, 350);
    	schoolVictim.setBackground(new Color(0x061F1F));
    	schoolVictim.setOpaque(false);
    	school.setVictimText(schoolVictim);
    }
    
    private void initInterface() {
    	
    	clock = new JLabel();
    	clock.setForeground(new Color(0xFFFFFF));
    	
    	String hour = "" + clockHour, min = "" + clockMin;
    	if(clockHour < 10) {
    		hour = "0" + clockHour;
    	}
    	if(clockMin < 10) {
    		min = "0" + clockMin;
    	}
    	clock.setText(hour + ":" + min);
    		
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		clock.setFont(font.deriveFont(Font.PLAIN, 14));
    	}
    	catch(Exception e){}
    	clock.setBounds(550, 0, 135, 40);
    	
    	logTitle = new JLabel();
    	logTitle.setForeground(new Color(0x33FFFD));
    	logTitle.setText("SIMULATION LOG:");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		logTitle.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	logTitle.setBounds(675, 230, 135, 50);
    	logTitle.setHorizontalAlignment(JLabel.CENTER);
    	logTitle.setVerticalAlignment(JLabel.TOP);
    	logTitle.setOpaque(false);
    	
    	logArea = new JTextArea();
    	logArea.setEditable(false);
    	logArea.setBackground(new Color(0x061F1F));
    	logArea.setForeground(new Color(0x33FFFD));
    	logScroll = new JScrollPane(logArea);
    	logScroll.setBounds(610, 250, 267, 250);
    	logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    	
    	status = new JLabel();
    	status.setForeground(new Color(0x33FFFD));
    	status.setText("WELCOME TO SAR SIMULATION");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		status.setFont(font.deriveFont(Font.PLAIN, 18));
    	}
    	catch(Exception e){}
    	status.setBounds(0, 415, 600, 100);
    	status.setHorizontalAlignment(JLabel.CENTER);
    	status.setVerticalAlignment(JLabel.TOP);
    	status.setOpaque(false);
    	
    	startDisasterBtn = new JButton();
    	startDisasterBtn.addActionListener(this);
    	startDisasterBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	startDisasterBtn.setForeground(new Color(0xFFFFFF));
    	startDisasterBtn.setBackground(new Color(0x061F1F));
    	startDisasterBtn.setBounds(610, 10, 267, 40);
    	startDisasterBtn.setText("START DISASTER");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		startDisasterBtn.setFont(font.deriveFont(Font.PLAIN, 10));
    	}
    	catch(Exception e){}
    	startDisasterBtn.setFocusable(false);
    	startDisasterBtn.setEnabled(true);
    	
    	randomizeBtn = new JButton();
    	randomizeBtn.addActionListener(this);
    	randomizeBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	randomizeBtn.setForeground(new Color(0xFFFFFF));
    	randomizeBtn.setBackground(new Color(0x061F1F));
    	randomizeBtn.setBounds(610, 60, 267, 40);
    	randomizeBtn.setText("GENERATE RANDOM DATA");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		randomizeBtn.setFont(font.deriveFont(Font.PLAIN, 10));
    	}
    	catch(Exception e){}
    	randomizeBtn.setFocusable(false);
    	randomizeBtn.setEnabled(false);
    	
    	startSimBtn = new JButton();
    	startSimBtn.addActionListener(this);
    	startSimBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	startSimBtn.setForeground(new Color(0xFFFFFF));
    	startSimBtn.setBackground(new Color(0x061F1F));
    	startSimBtn.setBounds(610, 110, 267, 40);
    	startSimBtn.setText("START SIMULATION");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		startSimBtn.setFont(font.deriveFont(Font.PLAIN, 10));
    	}
    	catch(Exception e){}
    	startSimBtn.setFocusable(false);
    	startSimBtn.setEnabled(false);
    	
    	showDataBtn = new JButton();
    	showDataBtn.addActionListener(this);
    	showDataBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	showDataBtn.setForeground(new Color(0xFFFFFF));
    	showDataBtn.setBackground(new Color(0x061F1F));
    	showDataBtn.setBounds(610, 160, 267, 40);
    	showDataBtn.setText("SHOW BUILDING DATA");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		showDataBtn.setFont(font.deriveFont(Font.PLAIN, 10));
    	}
    	catch(Exception e){}
    	showDataBtn.setFocusable(false);
        
    	saveLogBtn = new JButton();
    	saveLogBtn.addActionListener(this);
    	saveLogBtn.setBorder(new LineBorder(new Color(0x33FFFD), 2));
    	saveLogBtn.setForeground(new Color(0xFFFFFF));
    	saveLogBtn.setBackground(new Color(0x061F1F));
    	saveLogBtn.setBounds(610, 500, 267, 40);
    	saveLogBtn.setText("SAVE LOG DATA");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		saveLogBtn.setFont(font.deriveFont(Font.PLAIN, 10));
    	}
    	catch(Exception e){}
    	saveLogBtn.setEnabled(false);
    	saveLogBtn.setFocusable(false);
    }
    
    private void initCheckedBuildingsText() {
    	
    	checkedBuildingsText = new JLabel();
    	checkedBuildingsText.setText("CHECKED BUILDINGS:");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		checkedBuildingsText.setFont(font.deriveFont(Font.PLAIN, 14));
    	}
    	catch(Exception e){}
    	checkedBuildingsText.setBounds(210, 450, 180, 20);
    	checkedBuildingsText.setForeground(new Color(0xFFFFFF));
    	checkedBuildingsText.setHorizontalAlignment(JLabel.CENTER);
    	checkedBuildingsText.setVerticalAlignment(JLabel.CENTER);
    	
    	mallText = new JLabel();					
    	mallText.setText("MALL");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		mallText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	mallText.setBounds(50, 480, 120, 20);
    	mallText.setForeground(new Color(0xFFFFFF));
    	mallText.setHorizontalAlignment(JLabel.LEFT);
    	mallText.setVerticalAlignment(JLabel.CENTER);
    	mall.setBuildingText(mallText);
    	
    	hotelText = new JLabel();					
    	hotelText.setText("HOTEL");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		hotelText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	hotelText.setBounds(50, 510, 120, 20);
    	hotelText.setForeground(new Color(0xFFFFFF));
    	hotelText.setHorizontalAlignment(JLabel.LEFT);
    	hotelText.setVerticalAlignment(JLabel.CENTER);
    	hotel.setBuildingText(hotelText);
    	
    	restaurantText = new JLabel();					
    	restaurantText.setText("RESTAURANT");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		restaurantText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	restaurantText.setBounds(140, 480, 120, 20);
    	restaurantText.setForeground(new Color(0xFFFFFF));
    	restaurantText.setHorizontalAlignment(JLabel.LEFT);
    	restaurantText.setVerticalAlignment(JLabel.CENTER);
    	restaurant.setBuildingText(restaurantText);
    	
    	parkText = new JLabel();					
    	parkText.setText("PARK");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		parkText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	parkText.setBounds(140, 510, 120, 20);
    	parkText.setForeground(new Color(0xFFFFFF));
    	parkText.setHorizontalAlignment(JLabel.LEFT);
    	parkText.setVerticalAlignment(JLabel.CENTER);
    	park.setBuildingText(parkText);
    	
    	apartmentText = new JLabel();					
    	apartmentText.setText("APARTMENT");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		apartmentText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	apartmentText.setBounds(260, 480, 120, 20);
    	apartmentText.setForeground(new Color(0xFFFFFF));
    	apartmentText.setHorizontalAlignment(JLabel.LEFT);
    	apartmentText.setVerticalAlignment(JLabel.CENTER);
    	apartment.setBuildingText(apartmentText);
    	
    	policeStationText = new JLabel();					
    	policeStationText.setText("POLICE STATION");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		policeStationText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	policeStationText.setBounds(260, 510, 120, 20);
    	policeStationText.setForeground(new Color(0xFFFFFF));
    	policeStationText.setHorizontalAlignment(JLabel.LEFT);
    	policeStationText.setVerticalAlignment(JLabel.CENTER);
    	policeStation.setBuildingText(policeStationText);
    	
    	officeText = new JLabel();					
    	officeText.setText("OFFICE");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		officeText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	officeText.setBounds(410, 480, 120, 20);
    	officeText.setForeground(new Color(0xFFFFFF));
    	officeText.setHorizontalAlignment(JLabel.LEFT);
    	officeText.setVerticalAlignment(JLabel.CENTER);
    	office.setBuildingText(officeText);
    	
    	dinerText = new JLabel();					
    	dinerText.setText("DINER");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		dinerText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	dinerText.setBounds(410, 510, 120, 20);
    	dinerText.setForeground(new Color(0xFFFFFF));
    	dinerText.setHorizontalAlignment(JLabel.LEFT);
    	dinerText.setVerticalAlignment(JLabel.CENTER);
    	diner.setBuildingText(dinerText);
    	
    	schoolText = new JLabel();					
    	schoolText.setText("SCHOOL");
    	try{
    		Font font = Font.createFont(Font.TRUETYPE_FONT, MenuPage.class.getResourceAsStream("/res/LeagueSpartan-Bold.otf"));
    		schoolText.setFont(font.deriveFont(Font.PLAIN, 12));
    	}
    	catch(Exception e){}
    	schoolText.setBounds(500, 480, 120, 20);
    	schoolText.setForeground(new Color(0xFFFFFF));
    	schoolText.setHorizontalAlignment(JLabel.LEFT);
    	schoolText.setVerticalAlignment(JLabel.CENTER);
    	school.setBuildingText(schoolText);
    }    

    private void addToPanel() {		//adds the graphics to the panel
    	
    	this.add(checkedBuildingsText);
    	this.add(mallDest); this.add(mallVictim); this.add(mallText);
        this.add(hotelDest); this.add(hotelVictim); this.add(hotelText);
        this.add(restaurantDest); this.add(restaurantVictim); this.add(restaurantText);
        this.add(parkDest); this.add(parkVictim); this.add(parkText); this.add(parkText);
        this.add(apartmentDest); this.add(apartmentVictim); this.add(apartmentText);
        this.add(policeStationDest); this.add(policeStationVictim); this.add(policeStationText);
        this.add(officeDest); this.add(officeVictim); this.add(officeText);
        this.add(dinerDest); this.add(dinerVictim); this.add(dinerText);
        this.add(schoolDest); this.add(schoolVictim); this.add(schoolText);
        
        this.add(startDisasterBtn);
        this.add(startSimBtn);
        this.add(randomizeBtn);
        this.add(showDataBtn);
        this.add(saveLogBtn);
        
        this.add(clock);
        this.add(status);
        this.add(logTitle);
        
        this.add(logScroll);
        
        this.setLayout(null);
    }    

    public void paintComponent(Graphics g) {	//paints images which utilize Graphics2D
    	
    	super.paintComponent(g);
    	Graphics2D sar1Dot = (Graphics2D) g;
    	Graphics2D sar2Dot = (Graphics2D) g;
    	Graphics2D mapImage = (Graphics2D) g;
    	Graphics2D bgImage = (Graphics2D) g;
    	
    	bgImage.drawImage(frameBgImage, 0, 0, null);
    	mapImage.drawImage(map, 0, 0, null);
    	sar1Dot.drawImage(sar1.getImage(), sar1.getX(), sar1.getY(), null);
    	if(isTwoTeam()) {
    		sar2Dot.drawImage(sar2.getImage(), sar2.getX(), sar2.getY(), null);        	
    	}
    }    

    /* actionPerformed(ActionEvent e)
     * handles movement animation and button-pressed actions
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startDisasterBtn) {
			startDisasterBtnPressed();
		}
		
		else if(e.getSource() == startSimBtn) {
			startSimBtnPressed();
		}
		
		else if(e.getSource() == randomizeBtn) {
			randomizeBtnPressed();
		}
		
		else if(e.getSource() == showDataBtn) {
			showDataBtnPressed();
		}
		
		else if(e.getSource() == saveLogBtn) {
			saveLogBtnPressed();
		}
		
	}
	
	
    /* 
     * methods for button-pressing action
     */
	private void startDisasterBtnPressed() {	//"Start earthquake" button
		
		if(isFlood())
			status.setText("A FLOODING HAS OCCURRED");
		else
			status.setText("AN EARTHQUAKE HAS OCCURRED");
		status.setForeground(new Color(0xFF5BB4));
		
    	String hour = "" + clockHour, min = "" + clockMin;
    	if(clockHour < 10) {
    		hour = "0" + clockHour;
    	}
    	if(clockMin < 10) {
    		min = "0" + clockMin;
    	}
		
		if(isFlood())
			logArea.append("**Flood occurred at " + hour + ":" + min + "**\n");
		else
			logArea.append("**Earthquake occurred at " + hour + ":" + min + "**\n");
		
		if(isTwoTeam()) {
			logArea.append("**SAR Team 1 has arrived at entry point " + (sar1Spawn - 30) + "**\n");
			logArea.append("**SAR Team 2 has arrived at entry point " + (sar2Spawn - 30) + "**\n");
		}
		else {
			logArea.append("**SAR Team 1 has arrived at entry point " + (sar1Spawn - 30) + "**\n");
		}
		
		saveLogBtn.setEnabled(true);

    	if(getClockHour() > 6 && getClockHour() < 18) {
    		if(isFlood()) {
    			map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedFlood.jpg")).getImage());
    		}
    		else map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedQuake.jpg")).getImage());
    	}
    	else {
    		if(isFlood()) {
    			map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedFloodNight.jpg")).getImage());
    		}
    		else map = (new ImageIcon(getClass().getResource("/res/realisticMapDestroyedQuakeNight.jpg")).getImage());
    	}
		
		switch(sar1Spawn) {
			case 31:
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Up.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Up.png")));
				break;
			case 32:
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Right.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Right.png")));
				break;
			case 33:
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Down.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Down.png")));
				break;
			case 34: case 35:
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Left.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Left.png")));
				break;
		}

		if(isTwoTeam()) {
			switch(sar2Spawn) {
				case 31:
					if(isFlood()) {
						sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Up.png")));
					}
					else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Up.png")));
					break;
				case 32:
					if(isFlood()) {
						sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Right.png")));
					}
					else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Right.png")));
					break;
				case 33:
					if(isFlood()) {
						sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Down.png")));
					}
					else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Down.png")));
					break;
				case 34: case 35:
					if(isFlood()) {
						sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Left.png")));
					}
					else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Left.png")));
					break;
			}
		}
		
		randomizeBtn.setEnabled(true);
		startDisasterBtn.setEnabled(false);
		
		repaint();
	}
	
	private void startSimBtnPressed() {		//"Start simulation" or "Stop simulation" button
		
		if(!startIsClicked) {
			startIsClicked = true;
			startTime = System.currentTimeMillis();		
		}
		
		if(timer1.isRunning()) {
			pauseStartTime = System.currentTimeMillis();
			timer1.stop();
			clockTimer.stop();
			if(isTwoTeam()) {
				timer2.stop();
			}
			startSimBtn.setText("START SIMULATION");
			status.setText("SIMULATION PAUSED");
			status.setForeground(new Color(0x33FFFD));
			logArea.append("\nSimulation paused\n");
			if(!pauseIsClicked) {
				pauseIsClicked = true;
			}
		}
		
		else {
			if(startIsClicked && pauseIsClicked) {
				pauseEndTime = System.currentTimeMillis();
				pauseTime = pauseTime + (pauseEndTime - pauseStartTime);
			}

			timer1.start();
			clockTimer.start();
			if(isTwoTeam()) {
				timer2.start();
			}
			startSimBtn.setText("PAUSE SIMULATION");
			status.setText("SAR TEAM SEARCHING FOR VICTIMS...");
			status.setForeground(new Color(0xFF5BB4));
			logArea.append("Simulation started\n");
		}
		
		randomizeBtn.setEnabled(false);
		
		repaint();
	}	

	private void randomizeBtnPressed() {	//"Generate random data" button
		
		startSimBtn.setEnabled(true);
		logArea.append("*Random building data generated*\n");
		
		//GENERATES DESTRUCTION LEVEL DATA AND UPDATES THE DATA GUI	
		for(Building b : buildings) {
			b.setDestLv((int) (Math.random() * 4 + 1));
			if(!b.getDestText().getText().equals(""))
				b.getDestText().setText("Destruction Level: " + b.getDestLv());
		}
		
		//GENERATES THE VICTIM NUMBER BASED ON BUILDING DESTRUCTION LEVEL AND UPDATES THE DATA GUI
		for(Building b : buildings) {
			if(b.getDestLv() > 2) {
				b.setVictimNum((int) (Math.random() * 3 + 3));
				if(!b.getVictimText().getText().equals(""))
					b.getVictimText().setText("Victim Number: " + b.getVictimNum());
			}
			else {
				b.setVictimNum((int) (Math.random() * 3));
				if(!b.getVictimText().getText().equals(""))
					b.getVictimText().setText("Victim Number: " + b.getVictimNum());
			}
		}

		if(!destList.isEmpty()) {
			destList.removeAll(destList);
		}
		
		for(Building b : buildings) {
			destList.add(b.getDestLv());
		}
		
		repaint();
	}	

	private void showDataBtnPressed() {		//"Show building data" or "Hide building data" button
		if(mall.getDestText().getText().equals(""))
			showDataBtn.setText("HIDE BUILDING DATA");
		else
			showDataBtn.setText("SHOW BUILDING DATA");
		
		for(Building b : buildings) {
			if(b.getDestText().getText().equals("")) {
				b.getDestText().setText("Destruction Level: " + b.getDestLv());
				b.getDestText().setOpaque(true);
				b.getVictimText().setText("Victim Number: " + b.getVictimNum());
				b.getVictimText().setOpaque(true);
			}
			else {
				b.getDestText().setText("");
				b.getDestText().setOpaque(false);
				b.getVictimText().setText("");
				b.getVictimText().setOpaque(false);
			}
		}

		repaint();
	}	
	
	private void saveLogBtnPressed() {		//"Save log data" button
		try (BufferedWriter fileOut = new BufferedWriter(new FileWriter("simulation_log.txt"))) {
			logArea.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
    /* 
     * methods for SAR Team 1 and SAR Team 2 animation movements
     */
	private void move1() {
		if(!sar1.isMoving() && moveToExit1) {
			
			int closestDist = Integer.MAX_VALUE;
			
			for(Integer i : exitPoints) {
				
				int tempDist = checkDistance(sar1.getLastPos(), i);		
				
				if(tempDist < closestDist) {
					closestDist = tempDist;
					shortestPaths1 = g.getShortestPath(sar1.getLastPos(), i);
				}
			}
			
			Collections.reverse(shortestPaths1);
			
			sar1.setIsMoving(true);
		}
		
		else if(!sar1.isMoving() && moveBack1) {
			shortestPaths1 = g.getShortestPath(sar1.getLastPos(), lastBuilding1.getNum());
			
			Collections.reverse(shortestPaths1);
			
			sar1.setIsMoving(true);
		}
		
		else if(!sar1.isMoving() && !destList.isEmpty()) {
			
			int max = Collections.max(destList);
			
			for(Building b : buildings) {
				if(!b.isHeaded() && !b.isChecked() && b.getDestLv() == max) {
					currPriority1.add(b);
				}
			}
			
			destList.remove(destList.indexOf(max));
			
			int target = 0;
			if(currPriority1.size() > 1) {
	
				int closestDist = Integer.MAX_VALUE;
				
				for(Building c : currPriority1) {
					
					int tempDist = checkDistance(sar1.getLastPos(), c.getNum());				
					if(tempDist < closestDist) {
						closestDist = tempDist;
						shortestPaths1 = g.getShortestPath(sar1.getLastPos(), c.getNum());
						target = c.getIndex();
					}
				}
				
				currPriority1.removeAll(currPriority1);
			}
			
			else if (!currPriority1.isEmpty()) {
				shortestPaths1 = g.getShortestPath(sar1.getLastPos(), currPriority1.get(0).getNum());
				target = currPriority1.get(0).getIndex();
				currPriority1.removeAll(currPriority1);
			}
			
			buildings.get(target - 1).setIsHeaded(true);
			
			Collections.reverse(shortestPaths1);
			
			sar1.setIsMoving(true);
		}
		
		if(index1 < shortestPaths1.size()) {
			
			Integer i = shortestPaths1.get(index1);
			
			if(nodes.get(i-1).getY() - 10 < sar1.getY() && nodes.get(i-1).getX() - 10 == sar1.getX()) {
				sar1.setY(sar1.getY() - sar1.getYV());
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Up.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Up.png")));
				if(nodes.get(i-1).getY() - 10 == sar1.getY() && nodes.get(i-1).getX() - 10 == sar1.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit1(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus1(nodes.get(i-1));
					}
					else index1++;
				}
			}
			
			else if(nodes.get(i-1).getY() - 10 > sar1.getY() && nodes.get(i-1).getX() - 10 == sar1.getX()) {
				sar1.setY(sar1.getY() + sar1.getYV());
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Down.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Down.png")));
				if(nodes.get(i-1).getY() - 10 == sar1.getY() && nodes.get(i-1).getX() - 10 == sar1.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit1(nodes.get(i-1).getNum());
					}
					else {
						changeCheckingStatus1(nodes.get(i-1));
						index1++;
					}
				}
			}
			
			else if(nodes.get(i-1).getX() - 10 > sar1.getX() && nodes.get(i-1).getY() - 10 == sar1.getY()) {
				sar1.setX(sar1.getX() + sar1.getXV());
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Right.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Right.png")));
				if(nodes.get(i-1).getY() - 10 == sar1.getY() && nodes.get(i-1).getX() - 10 == sar1.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit1(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus1(nodes.get(i-1));
					}
					else index1++;
				}
			}
			
			else if(nodes.get(i-1).getX() - 10 < sar1.getX() && nodes.get(i-1).getY() - 10 == sar1.getY()) {
				sar1.setX(sar1.getX() - sar1.getXV());
				if(isFlood()) {
					sar1.setImage(new ImageIcon(getClass().getResource("/res/boat1Left.png")));
				}
				else sar1.setImage(new ImageIcon(getClass().getResource("/res/car1Left.png")));
				if(nodes.get(i-1).getY() - 10 == sar1.getY() && nodes.get(i-1).getX() - 10 == sar1.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit1(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus1(nodes.get(i-1));
					}
					else index1++;
				}
			}
		}
		
		repaint();
	}	
	
	private void move2() {
		
		if(!sar2.isMoving() && moveToExit2) {
			
			int closestDist = Integer.MAX_VALUE;
			
			for(Integer i : exitPoints) {
				
				int tempDist = checkDistance(sar2.getLastPos(), i);		
				
				if(tempDist < closestDist) {
					closestDist = tempDist;
					shortestPaths2 = g.getShortestPath(sar2.getLastPos(), i);
				}
			}
			
			Collections.reverse(shortestPaths2);
			
			sar2.setIsMoving(true);
		}
		
		else if(!sar2.isMoving() && moveBack2) {
			shortestPaths2 = g.getShortestPath(sar2.getLastPos(), lastBuilding2.getNum());
			
			Collections.reverse(shortestPaths2);
			
			sar2.setIsMoving(true);
		}
		
		
		else if(!sar2.isMoving() && !destList.isEmpty()) {
			
			int max = Collections.max(destList);
			
			for(Building b : buildings) {
				if(!b.isHeaded() && !b.isChecked() && b.getDestLv() == max) {
					currPriority2.add(b);
				}
			}
			
			destList.remove(destList.indexOf(max));
			
			int target = 0;
			if(currPriority2.size() > 1) {
	
				int closestDist = Integer.MAX_VALUE;
				
				for(Building c : currPriority2) {
					
					int tempDist = checkDistance(sar2.getLastPos(), c.getNum());				
					if(tempDist < closestDist) {
						closestDist = tempDist;
						shortestPaths2 = g.getShortestPath(sar2.getLastPos(), c.getNum());
						target = c.getIndex();
					}
				}
				
				currPriority2.removeAll(currPriority2);
			}
			
			else if (!currPriority2.isEmpty()) {
				shortestPaths2 = g.getShortestPath(sar2.getLastPos(), currPriority2.get(0).getNum());
				target = currPriority2.get(0).getIndex();
				currPriority2.removeAll(currPriority2);
			}
			
			
			buildings.get(target - 1).setIsHeaded(true);
			
			Collections.reverse(shortestPaths2);
			
			sar2.setIsMoving(true);
		}
		
		if(index2 < shortestPaths2.size()) {
			
			Integer i = shortestPaths2.get(index2);
			
			if(nodes.get(i-1).getY() - 10 < sar2.getY() && nodes.get(i-1).getX() - 10 == sar2.getX()) {
				sar2.setY(sar2.getY() - sar2.getYV());
				if(isFlood()) {
					sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Up.png")));
				}
				else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Up.png")));
				if(nodes.get(i-1).getY() - 10 == sar2.getY() && nodes.get(i-1).getX() - 10 == sar2.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit2(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus2(nodes.get(i-1));
					}
					else index2++;
				}
			}
			
			else if(nodes.get(i-1).getY() - 10 > sar2.getY() && nodes.get(i-1).getX() - 10 == sar2.getX()) {
				sar2.setY(sar2.getY() + sar2.getYV());
				if(isFlood()) {
					sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Down.png")));
				}
				else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Down.png")));
				if(nodes.get(i-1).getY() - 10 == sar2.getY() && nodes.get(i-1).getX() - 10 == sar2.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit2(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus2(nodes.get(i-1));
					}
					else index2++;
				}
			}
			
			else if(nodes.get(i-1).getX() - 10 > sar2.getX() && nodes.get(i-1).getY() - 10 == sar2.getY()) {
				sar2.setX(sar2.getX() + sar2.getXV());
				if(isFlood()) {
					sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Right.png")));
				}
				else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Right.png")));
				if(nodes.get(i-1).getY() - 10 == sar2.getY() && nodes.get(i-1).getX() - 10 == sar2.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit2(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus2(nodes.get(i-1));
					}
					else index2++;
				}
			}
			
			else if(nodes.get(i-1).getX() - 10 < sar2.getX() && nodes.get(i-1).getY() - 10 == sar2.getY()) {
				sar2.setX(sar2.getX() - sar2.getXV());
				if(isFlood()) {
					sar2.setImage(new ImageIcon(getClass().getResource("/res/boat2Left.png")));
				}
				else sar2.setImage(new ImageIcon(getClass().getResource("/res/car2Left.png")));
				if(nodes.get(i-1).getY() - 10 == sar2.getY() && nodes.get(i-1).getX() - 10 == sar2.getX()) {
					if(exitPoints.contains(nodes.get(i-1).getNum())) {
						arrivedAtExit2(nodes.get(i-1).getNum());
					}
					else if(buildingNums.contains(i)){
						changeCheckingStatus2(nodes.get(i-1));
					}
					else index2++;
				}
			}
		}
		
		repaint();
	}
	
	
    /* 
     * other method definitions for the simulation
     */
	
	private int checkDistance(int a, int b) {	//checks distance between two nodes
		return ((int) (Math.sqrt((Math.pow(nodes.get(a-1).getX() - nodes.get(b-1).getX(), 2))
						 	   + (Math.pow(nodes.get(a-1).getY() - nodes.get(b-1).getY(), 2)))));
	}	
	
	private void changeCheckingStatus1(Node n) {		//contains all changes when SAR Team 1 reaches a building
    	switch(n.getNum()) {
			case 1: check1(mall); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 2: check1(restaurant); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 3: check1(apartment); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 4: check1(park); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 5: check1(policeStation); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 6: check1(office); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 7: check1(school); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 28: check1(hotel); startSimBtn.setEnabled(false); sar1isChecking = true; break;
			case 30: check1(diner); startSimBtn.setEnabled(false); sar1isChecking = true; break;
    	}
	}

	private void changeCheckingStatus2(Node n) {		//contains all changes when SAR Team 2 reaches a building
    	switch(n.getNum()) {
			case 1: check2(mall); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 2: check2(restaurant); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 3: check2(apartment); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 4: check2(park); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 5: check2(policeStation); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 6: check2(office); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 7: check2(school); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 28: check2(hotel); startSimBtn.setEnabled(false); sar2isChecking = true; break;
			case 30: check2(diner); startSimBtn.setEnabled(false); sar2isChecking = true; break;
    	}
	}
	

	private void completeSim() {	//when every building is checked

		endTime = System.currentTimeMillis();
		logArea.append("**All buildings are checked and all victims are rescued**\n");
		logArea.append("Simulation took " + ((int)(endTime - startTime)/100) + " minutes"
						+ " (" + ((float)(endTime - startTime)/1000) + " seconds in realtime)\n");
		logArea.append("Total pause duration: " + ((float)(pauseTime)/1000) + " seconds\n");
		status.setText("ALL BUILDINGS CHECKED & ALL VICTIMS RESCUED");
		status.setForeground(new Color(0x33FF7B));
		timer1.stop();
		clockTimer.stop();
		if(isTwoTeam()) {
			timer2.stop();
		}
		startSimBtn.setText("START SIMULATION");
		startSimBtn.setEnabled(false);
	}
	
	private void simFailed() {		//when the simulation doesn't end within the time limit
		
		logArea.append("**Simulation exceeded time limit (" + ParameterFrame.timeField.getText() + " seconds)**\n");
		
		int uncheckedCount = 0;
		for(Building b : buildings) {
			if(!b.isChecked()) {
				uncheckedCount++;
			}
		}
		
		if(uncheckedCount > 1) {
			status.setText("" + uncheckedCount + " BUILDINGS FAILED TO BE FULLY CHECKED & RESCUED");
			status.setForeground(new Color(0xFF3737));
			logArea.append("" + uncheckedCount + " buildings failed to be fully checked & rescued\n");
		}
		else if(uncheckedCount == 1) {
			status.setText("1 BUILDING FAILED TO BE FULLY CHECKED & RESCUED");
			status.setForeground(new Color(0xFF3737));
			logArea.append("1 building failed to be fully checked & rescued\n");
		}
			
		timer1.stop();
		if(pause != null) {
			pause.stop();
		}
		clockTimer.stop();
		if(isTwoTeam()) {
			timer2.stop();
		}
		
		startSimBtn.setText("START SIMULATION");
		startSimBtn.setEnabled(false);
	}
	
	private void check1(Building b) {	//when the SAR 1 team enters a building
		
		if(!victimFound1) {
			String buildingName = b.getName().substring(0, 1).toLowerCase() + b.getName().substring(1);
			logArea.append("SAR Team 1 is checking " + buildingName + "...\n");
		}
		
		timer1.stop();
		
		pause = new Timer((b.getDestLv() >= 3 ? (longDelay * 1000) : (shortDelay * 1000)), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(b.getVictimNum() == 0) {
					logArea.append(b.getName() + " checked by SAR Team 1! No victim found\n");
					
					b.setVictimTextColor(new Color(0x33FF7B));
					b.setBuildingTextColor(new Color(0x33FF7B));
					
					b.setIsChecked(true);
					checkedBuilding++;
					b.setIsHeaded(false);
					
				}
				
				else if(b.getVictimNum() > 0 && b.getVictimNum() <= carryLimit) {
					
					if(!victimFound1) {
						logArea.append(b.getName() + " checked by SAR Team 1! " + b.getVictimNum() + " victim(s) were found\n");
						victimFound1 = true;
					}
					
					logArea.append("SAR Team 1 is carrying " + b.getVictimNum() + " victim(s) to the nearest exit point...\n");
					currCarry1 = b.getVictimNum();
					
					b.setVictimNum(0);
					if(!b.getVictimText().getText().equals(""))
						b.getVictimText().setText("Victim Number: " + b.getVictimNum());
					
					lastBuilding1 = b;
					moveBack1 = false;
					moveToExit1 = true;
				}
				
				else {
					
					if(!victimFound1) {
						logArea.append(b.getName() + " checked by SAR Team 1! " + b.getVictimNum() + " victim(s) were found\n");
						victimFound1 = true;
					}
					
					logArea.append("SAR Team 1 is carrying " + carryLimit + " victim(s) to the nearest exit point...\n");
					currCarry1 = carryLimit;
					
					b.setVictimNum(b.getVictimNum() - carryLimit);
					if(!b.getVictimText().getText().equals(""))
						b.getVictimText().setText("Victim Number: " + b.getVictimNum());
					
					lastBuilding1 = b;
					moveBack1 = true;
					moveToExit1 = true;
				}
				
				sar1isChecking = false;
				if(!sar1isChecking && !sar2isChecking && !sar1isAtExit && !sar2isAtExit) {
					startSimBtn.setEnabled(true);
				}
				
				sar1.setLastPos(b.getNum());
				
				shortestPaths1.removeAll(shortestPaths1);
				index1 = 0;
				sar1.setIsMoving(false);
				
				timer1.start();
			}
		});
		
		pause.setRepeats(false);
		pause.start();
	}
	
	private void check2(Building b) {	//when the SAR 2 team enters a building
		
		if(!victimFound2) {
			String buildingName = b.getName().substring(0, 1).toLowerCase() + b.getName().substring(1);
			logArea.append("SAR Team 2 is checking " + buildingName + "...\n");
		}
		
		timer2.stop();
		
		pause = new Timer((b.getDestLv() >= 3 ? (longDelay * 1000) : (shortDelay * 1000)), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(b.getVictimNum() == 0) {
					logArea.append(b.getName() + " checked by SAR Team 2! No victim found\n");
					
					b.setVictimTextColor(new Color(0x33FF7B));
					b.setBuildingTextColor(new Color(0x33FF7B));
					
					b.setIsChecked(true);
					checkedBuilding++;
					b.setIsHeaded(false);
					
					shortestPaths2.removeAll(shortestPaths2);
					sar2.setIsMoving(false);
					index2 = 0;
				}
				
				else if(b.getVictimNum() > 0 && b.getVictimNum() <= carryLimit) {
					
					if(!victimFound2) {
						logArea.append(b.getName() + " checked by SAR Team 2! " + b.getVictimNum() + " victim(s) were found\n");
						victimFound2 = true;
					}
					
					logArea.append("SAR Team 2 is carrying " + b.getVictimNum() + " victim(s) to the nearest exit point...\n");
					currCarry2 = b.getVictimNum();
					
					b.setVictimNum(0);
					if(!b.getVictimText().getText().equals(""))
						b.getVictimText().setText("Victim Number: " + b.getVictimNum());
					
					lastBuilding2 = b;
					moveBack2 = false;
					moveToExit2 = true;
				}
				
				else {
					
					if(!victimFound2) {
						logArea.append(b.getName() + " checked by SAR Team 2! " + b.getVictimNum() + " victim(s) were found\n");
						victimFound2 = true;
					}
					
					logArea.append("SAR Team 2 is carrying " + carryLimit + " victim(s) to the nearest exit point...\n");
					currCarry2 = carryLimit;
					
					b.setVictimNum(b.getVictimNum() - carryLimit);
					if(!b.getVictimText().getText().equals(""))
						b.getVictimText().setText("Victim Number: " + b.getVictimNum());
					
					lastBuilding2 = b;
					moveBack2 = true;
					moveToExit2 = true;
				}
				
				sar2isChecking = false;
				if(!sar1isChecking && !sar2isChecking && !sar1isAtExit && !sar2isAtExit) {
					startSimBtn.setEnabled(true);
				}
				
				sar2.setLastPos(b.getNum());
				
				shortestPaths2.removeAll(shortestPaths2);
				index2 = 0;
				sar2.setIsMoving(false);
				
				timer2.start();
			}
		});
		
		pause.setRepeats(false);
		pause.start();
	}
	
	private void arrivedAtExit1(int num) {	//when the SAR 1 team arrives at an exit point
		
		startSimBtn.setEnabled(false);
		sar1isAtExit = true;
		
		logArea.append("SAR Team 1 arrived at exit point\n");
		logArea.append("SAR Team 1 is passing " + currCarry1 + " victim(s) to the medical unit...\n");
		timer1.stop();
		
		pause = new Timer((passDelay * 1000) + (currCarry1 * 500), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				logArea.append("**" + currCarry1 + " victim(s) have been successfully rescued by SAR Team 1!**\n");
				
				if(!moveBack1) {
					
					lastBuilding1.setVictimTextColor(new Color(0x33FF7B));
					lastBuilding1.setBuildingTextColor(new Color(0x33FF7B));

					victimFound1 = false;
					
					lastBuilding1.setIsChecked(true);
					checkedBuilding++;
					lastBuilding1.setIsHeaded(false);
				}
					
					shortestPaths1.removeAll(shortestPaths1);
					index1 = 0;
					currCarry1 = 0;
					
					sar1.setIsMoving(false);
					moveToExit1 = false;
					sar1.setLastPos(num);
					
					sar1isAtExit = false;
					if(!sar1isAtExit && !sar2isAtExit && !sar1isChecking && !sar2isChecking)
						startSimBtn.setEnabled(true);
					
					timer1.start();
			}	
		});
		
		pause.setRepeats(false);
		pause.start();
	}
	
	private void arrivedAtExit2(int num) {	//when the SAR 2 team arrives at an exit point
		
		startSimBtn.setEnabled(false);
		sar2isAtExit = true;
		
		logArea.append("SAR Team 2 arrived at exit point\n");
		logArea.append("SAR Team 2 is passing " + currCarry2 + " victim(s) to the medical unit...\n");
		timer2.stop();
		
		pause = new Timer((passDelay * 1000) + (currCarry2 * 500), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				logArea.append("**" + currCarry2 + " victim(s) have been successfully rescued by SAR Team 2!**\n");
				
				if(!moveBack2) {
					
					lastBuilding2.setVictimTextColor(new Color(0x33FF7B));
					lastBuilding2.setBuildingTextColor(new Color(0x33FF7B));

					victimFound2 = false;
					
					lastBuilding2.setIsChecked(true);
					checkedBuilding++;
					lastBuilding2.setIsHeaded(false);
				}
					
					shortestPaths2.removeAll(shortestPaths2);
					index2 = 0;
					currCarry2 = 0;
					
					sar2.setIsMoving(false);
					moveToExit2 = false;
					sar2.setLastPos(num);
					
					sar2isAtExit = false;
					if(!sar1isAtExit && !sar2isAtExit && !sar1isChecking && !sar2isChecking)
						startSimBtn.setEnabled(true);
					
					timer2.start();
			}	
		});
		
		pause.setRepeats(false);
		pause.start();
	}

	private boolean isTwoTeam() {	//checks if the parameter of SAR Team amount is 2
		return (ParameterFrame.sarCombo.getSelectedItem().equals("2"));
	}
	
	private int getClockHour() {	//gets clock hour from the parameter setting
		if(!ParameterFrame.clockHourField.getText().equals(""))
			return (Integer.parseInt(ParameterFrame.clockHourField.getText()));
		else
			return 12;
	}
	
	private int getClockMin() {		//gets clock minute from the parameter setting
		if(!ParameterFrame.clockMinField.getText().equals(""))
			return (Integer.parseInt(ParameterFrame.clockMinField.getText()));
		else
			return 0;
	}
	
	private int getCarryLimit() {	//gets carry limit from the parameter setting
    	if(!ParameterFrame.carryField.getText().equals("")) {
    		return Integer.parseInt(ParameterFrame.carryField.getText());
    	}
    	else {
    		return 2;
    	}
	}
	
	private boolean isFlood() {		//checks if the disaster is flood
		return ParameterFrame.disasterCombo.getSelectedItem().equals("Flood");
	}
}