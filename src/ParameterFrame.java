package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.text.*;


public class ParameterFrame extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 685428748501657684L;
	
	JLabel disasterDesc, sarDesc, timeDesc, carryDesc, clockHourDesc, clockMinDesc, seconds, victims;
	JLabel defaultDisaster, defaultSar, defaultTime, defaultCarry, defaultHour, defaultMin;
	JButton confirmBtn;
	static JTextField timeField, carryField, clockHourField, clockMinField;
	@SuppressWarnings("rawtypes")
	static JComboBox sarCombo, disasterCombo;
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	ParameterFrame() {
		
		ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/logo.png"));
		
    	String[] disasterType = {"Earthquake", "Flood"};
    	disasterCombo = new JComboBox(disasterType);
    	disasterCombo.addActionListener(this);
    	disasterCombo.setBounds(105, 13, 100, 25);
    	disasterCombo.setBackground(new Color(0xF4F4F4));
		
    	String[] sarAmount = {"1", "2"};
    	sarCombo = new JComboBox(sarAmount);
    	sarCombo.addActionListener(this);
    	sarCombo.setBounds(140, 53, 50, 25);
    	sarCombo.setBackground(new Color(0xF4F4F4));
    	
    	timeField = new JTextField();
    	((AbstractDocument)timeField.getDocument()).setDocumentFilter(new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*(\\.\\d{0,2})?");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            					throws BadLocationException {          
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                	return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    	timeField.setBounds(80, 95, 50, 25);
    	
    	carryField = new JTextField();
    	((AbstractDocument)carryField.getDocument()).setDocumentFilter(new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            					throws BadLocationException {          
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                	return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    	carryField.setBounds(130, 135, 50, 25);
    	
    	clockHourField = new JTextField();
    	((AbstractDocument)clockHourField.getDocument()).setDocumentFilter(new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            					throws BadLocationException {          
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                	return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    	clockHourField.setBounds(185, 175, 50, 25);
		
    	clockMinField = new JTextField();
    	((AbstractDocument)clockMinField.getDocument()).setDocumentFilter(new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            					throws BadLocationException {          
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                	return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    	clockMinField.setBounds(200, 215, 50, 25);
    	
    	disasterDesc = new JLabel();
    	disasterDesc.setFont(new Font("Arial", Font.PLAIN, 14));
    	disasterDesc.setForeground(Color.BLACK);
    	disasterDesc.setText("Disaster type: ");
    	disasterDesc.setBounds(10, 0, 150, 50);
    	
    	sarDesc = new JLabel();
    	sarDesc.setFont(new Font("Arial", Font.PLAIN, 14));
    	sarDesc.setForeground(Color.BLACK);
    	sarDesc.setText("SAR Team amount: ");
    	sarDesc.setBounds(10, 40, 150, 50);
    	
    	timeDesc = new JLabel();
    	timeDesc.setFont(new Font("Arial", Font.PLAIN, 14));
    	timeDesc.setForeground(Color.BLACK);
    	timeDesc.setText("Time limit: ");
    	timeDesc.setBounds(10, 80, 80, 50);
 
    	carryDesc = new JLabel();
    	carryDesc.setFont(new Font("Arial", Font.PLAIN, 14));
    	carryDesc.setForeground(Color.BLACK);
    	carryDesc.setText("Carry limit (>= 1): ");
    	carryDesc.setBounds(10, 120, 120, 50);
    	
    	clockHourDesc = new JLabel();
    	clockHourDesc.setFont(new Font("Arial", Font.PLAIN, 14));
    	clockHourDesc.setForeground(Color.BLACK);
    	clockHourDesc.setText("Clock starting hour (0-23): ");
    	clockHourDesc.setBounds(10, 160, 175, 50);
    	
    	clockMinDesc = new JLabel();
    	clockMinDesc.setFont(new Font("Arial", Font.PLAIN, 14));
    	clockMinDesc.setForeground(Color.BLACK);
    	clockMinDesc.setText("Clock starting minute (0-59): ");
    	clockMinDesc.setBounds(10, 200, 185, 50);
    	
    	seconds = new JLabel();
    	seconds.setFont(new Font("Arial", Font.PLAIN, 14));
    	seconds.setForeground(Color.BLACK);
    	seconds.setText("second(s) ");
    	seconds.setBounds(135, 80, 100, 50);
    	
    	victims = new JLabel();
    	victims.setFont(new Font("Arial", Font.PLAIN, 14));
    	victims.setForeground(Color.BLACK);
    	victims.setText("victim(s) ");
    	victims.setBounds(185, 120, 100, 50);
    	
    	defaultDisaster = new JLabel();
    	defaultDisaster.setFont(new Font("Arial", Font.PLAIN, 14));
    	defaultDisaster.setForeground(Color.BLACK);
    	defaultDisaster.setText("(Default: Earthquake)");
    	defaultDisaster.setBounds(215, 0, 150, 50);
    	
    	defaultSar = new JLabel();
    	defaultSar.setFont(new Font("Arial", Font.PLAIN, 14));
    	defaultSar.setForeground(Color.BLACK);
    	defaultSar.setText("(Default: 1)");
    	defaultSar.setBounds(200, 40, 100, 50);
    	
    	defaultTime = new JLabel();
    	defaultTime.setFont(new Font("Arial", Font.PLAIN, 14));
    	defaultTime.setForeground(Color.BLACK);
    	defaultTime.setText("(Default: No limit)");
    	defaultTime.setBounds(210, 80, 150, 50);
    	
    	defaultCarry = new JLabel();
    	defaultCarry.setFont(new Font("Arial", Font.PLAIN, 14));
    	defaultCarry.setForeground(Color.BLACK);
    	defaultCarry.setText("(Default: 2)");
    	defaultCarry.setBounds(250, 120, 100, 50);
    	
    	defaultHour = new JLabel();
    	defaultHour.setFont(new Font("Arial", Font.PLAIN, 14));
    	defaultHour.setForeground(Color.BLACK);
    	defaultHour.setText("(Default: 12)");
    	defaultHour.setBounds(245, 160, 100, 50);
    	
    	defaultMin = new JLabel();
    	defaultMin.setFont(new Font("Arial", Font.PLAIN, 14));
    	defaultMin.setForeground(Color.BLACK);
    	defaultMin.setText("(Default: 0)");
    	defaultMin.setBounds(260, 200, 100, 50);
    	
    	confirmBtn = new JButton();
    	confirmBtn.addActionListener(this);
    	confirmBtn.setBackground(new Color(0xF4F4F4));
    	confirmBtn.setBounds(150, 260, 100, 25);
    	confirmBtn.setText("Confirm");
    	confirmBtn.setFocusable(false);
    	confirmBtn.setEnabled(true);
    	
        this.setTitle("USAR Simulation - Parameter Setting Page");
        this.setIconImage(programIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0xB0B0B0));
        this.setLayout(null);
        this.setSize(400, 340);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(disasterDesc);
        this.add(sarDesc);
        this.add(timeDesc);
        this.add(carryDesc);
        this.add(clockHourDesc);
        this.add(clockMinDesc);
        this.add(disasterCombo);
        this.add(sarCombo);
        this.add(timeField);
        this.add(carryField);
        this.add(clockHourField);
        this.add(clockMinField);
        this.add(seconds);
        this.add(victims);
        this.add(defaultDisaster);
        this.add(defaultSar);
        this.add(defaultTime);
        this.add(defaultCarry);
        this.add(defaultHour);
        this.add(defaultMin);
        this.add(confirmBtn);
        this.setModal(true);
        this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == confirmBtn) {
			long clockHour = 12, clockMin = 0, carryLimit = 2;
			
			if(!carryField.getText().equals("")) {
				carryLimit = Long.parseLong(carryField.getText());
			}
			
			if(!clockHourField.getText().equals("")) {
				clockHour = Long.parseLong(clockHourField.getText());
			}
			
			if(!clockMinField.getText().equals("")) {
				clockMin = Long.parseLong(clockMinField.getText());
			}
			
			if(carryLimit < 1 || carryLimit > Integer.MAX_VALUE || clockHour > 23 || clockMin > 59) {
				
				JLabel warningText = new JLabel();
				ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/logo.png"));
				warningText.setText("<html>Please enter a value of >= 1 for carry limit"
									+ "<br/>0-23 for clock hour, and 0-59 for clock minute"
									+ "<br/> (all values should also be less than max value of integer)</html>");
				warningText.setBounds(0, 0, 370, 80);
				warningText.setHorizontalAlignment(JLabel.CENTER);
				warningText.setVerticalAlignment(JLabel.CENTER);
					
				JDialog warning = new JDialog(this, "*Warning*");
				warning.setIconImage(programIcon.getImage());
				warning.setLayout(null);
				warning.setSize(380, 120);
				warning.setLocationRelativeTo(null);
				warning.setResizable(false);
				warning.add(warningText);
				warning.setModal(true);
				warning.setVisible(true);
			}
			
			else {
				MenuPage.simulateBtn.setEnabled(true);
				this.dispose();
			}
		}
		
		repaint();
	}
	
}
