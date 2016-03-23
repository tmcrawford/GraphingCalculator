package BusinessAccumulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sun.swing.AccumulativeRunnable;
//ECE 309 Lab 8 - Meagan Raviele, Isaiah Smoak, Timothy Crawford, Rachel Williams
//WE COMPLETED THE EXTRA CREDIT PORTION OF THE LAB
public class BusinessAccumulator implements Runnable, ActionListener, KeyListener  {
	JFrame accumulateWindow = new JFrame(); // the graphics window for the program
	JButton clearButton = new JButton("CLEAR"); //clears the amount text box
	JTextArea inputArea  = new JTextArea(""); //area to type in amount to enter in
	JTextArea outputArea = new JTextArea("$0"); //the area that displays the total accumulated amount
	JTextArea logArea = new JTextArea(); //area to log all calculations
	JLabel errorOutput = new JLabel("");
	JPanel topPanel = new JPanel(); //panel to hold the buttons
	JScrollPane accountScrollPane = new JScrollPane(logArea); //allows the log to be scrollable
	JLabel instruction1 = new JLabel("Total:"); //labels for textboxes
	JLabel instruction2 = new JLabel("Enter Amount:");
	
	// instance variables for calculation 
	String oldAmount; // total accumulated amt 
	String newAmount; // new amt to be added 
	double prevAmount; // total accumulated amt, type dbl 
	double currentAmt; // new amt to be added, type dbl
	double totalAmt; // total accumulated amt, at each line 
	
	public static void main(String[] args) {
		//Meagan Raviele, Isaiah Smoak, Timothy Crawford, Rachel Williams
		//WE COMPLETED THE EXTRA CREDIT PORTION OF THE LAB
		// loads it in dynamic memory 
		System.out.println("ECE 309 Lab 8 - Meagan Raviele, Isaiah Smoak, Rachel Williams, Timothy Crawford");
		new BusinessAccumulator(); 
		
	}
	
	public BusinessAccumulator() {
		// building the GUI Layout
		instruction1.setFont(new Font("default", Font.BOLD, 20));
		instruction2.setFont(new Font("default", Font.BOLD, 20));
		errorOutput.setFont(new Font("default", Font.BOLD, 20));
		errorOutput.setForeground(Color.red);
		
		inputArea.setFont(new Font("default", Font.BOLD, 20));
		outputArea.setFont(new Font("default", Font.BOLD, 20));
		outputArea.setSize(100,400);
		outputArea.setVisible(true);
		outputArea.setBackground(Color.white);
		outputArea.setEditable(false);
		
		clearButton.addActionListener(this);
		
		topPanel.setLayout(new GridLayout(1,5));
		topPanel.add(clearButton);
		topPanel.add(instruction1);
		topPanel.add(outputArea);
		topPanel.add(instruction2);
		topPanel.add(inputArea);

		logArea.setFont(new Font("default", Font.BOLD, 20));
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		logArea.setText("");
		logArea.setSize(400,400);
		logArea.setEditable(false);
		accountScrollPane.setSize(400,400);

		accumulateWindow.getContentPane().add(topPanel, "North");
		accumulateWindow.getContentPane().add(accountScrollPane, "Center");
		accumulateWindow.getContentPane().add(errorOutput, "South");
		accumulateWindow.setSize(800, 600);
		accumulateWindow.setLocation(300, 300);
		accumulateWindow.setVisible(true);
		accumulateWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		accumulateWindow.setTitle("Accounting Calculator");
		inputArea.addKeyListener(this); 
	}
	
	public void actionPerformed(ActionEvent button) {
		// action listener for clear button 
		if(button.getSource() == clearButton){
			outputArea.setText("");
			
			// clear the amounts
			inputArea.setText("");
			errorOutput.setText("");
			oldAmount = "0";
			newAmount = "0";
			prevAmount = 0;
			currentAmt = 0;
			totalAmt = 0; 
		}
		//moved to the key pressed
//		try {
//			checkError();
//		} catch (Exception e) {
//			//e.printStackTrace();
//			// print error message to JLabel 
//			System.out.println("Error!");
//			errorOutput.setText("Error! " + e); 
//			return;
//		}
//		
//		accumulateTotal();
	}
	
	public void accumulateTotal(){
		// add new amount to old amount, format amt correctly   
		addAmount(); 
		
		// print to output text area, update the log
		showAmount(); 
	}

	
	
	public void keyPressed(KeyEvent kp) {  
		//If Enter Is Pressed
		if(kp.getKeyCode() == KeyEvent.VK_ENTER) { 
			//Gets text from field
			newAmount = inputArea.getText().trim(); 
	
			inputArea.setText("");
			try {checkError(); errorOutput.setText("");} 
			catch (Exception e) {
				//e.printStackTrace();
				errorOutput.setText(e.toString()); 
				return;
			}
			accumulateTotal();
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void run() {
		//not needed
	}
	
	public void checkError() throws Exception{ 
		//local copy of this variable
		String newAmount = this.newAmount;
		if(newAmount.isEmpty()){
			//output illegal argument if empty!
			throw new IllegalArgumentException(); 
		}
		// verify is there is an amount entered
		// check if first character is "$" 
		if(newAmount.charAt(0) == '$'){
			//remove the $ string (that is hopefully the first character!
			newAmount = newAmount.substring(1); 
		}
		
		//seeing if has exactly 2 decimals
		if(newAmount.contains(".")){ 
			int val = newAmount.indexOf('.');
			String temp = newAmount.substring(val+1);
			if(temp.length() != 2){
				//is NOT two decimal places after the decimal
				//send that guy outta here
				throw new Exception("Exactly two decimals are required following a decimal point.");
				//throw new IllegalArgumentException(); 
			}
		} 
		//extra credit part
		if(newAmount.contains(",") && newAmount.charAt(0) != ','){
			int last_comma = newAmount.lastIndexOf(",", newAmount.length());
			int last_part = newAmount.indexOf(".");
			if(last_part == -1){
				last_part = newAmount.length();
			}
			
			while(last_comma != -1){
				if(!(((last_comma - last_part)%4) == 0))
					throw new IllegalArgumentException("Commas are incorrect!");
				last_comma = newAmount.lastIndexOf(",", last_comma -1); //do this by default
			}
			newAmount = newAmount.replace(",", "");
		}
		//next step
		//end of extra credit part
		// verify new amount is int/double
		// decimal equivalent.. should throw error if problem
		// lastly, need to find out how many decimal places
		BigDecimal newd1 = new BigDecimal(newAmount); 
		// set currentAmt to new value
		// verifies if int/double, else should throws exception!
		currentAmt = newd1.doubleValue(); 
	}
	
	public void addAmount(){
		// performing calculation
		totalAmt = prevAmount + currentAmt; 
		
		// return if it's a whole number 
		if (totalAmt%1 == 0) return; 
		
		//
		BigDecimal bd = new BigDecimal(totalAmt);
		
		//
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		
		//
		totalAmt = bd.doubleValue();
	}

	public void showAmount(){
		//-----------Display in log area----------// 
		String previous, current, total;
		String newLine = System.lineSeparator();
		BigDecimal pAmount = new BigDecimal(prevAmount).setScale(2, BigDecimal.ROUND_HALF_UP); 
		BigDecimal cAmount = new BigDecimal(currentAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal tAmount = new BigDecimal(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
		try{
			int p2Amount = pAmount.intValueExact();
			previous = Integer.toString(p2Amount);//convert to a string, and done!
		}
		catch(ArithmeticException e){
			//then it's a decimal... print only two values.. print this!
			
			previous = pAmount.toString();
		}
		try{
			int c2Amount = cAmount.intValueExact();
			current = Integer.toString(c2Amount);//convert to a string, and done!
		}
		catch(ArithmeticException e){
			//then it's a decimal... print only two values.. print this!
			current = cAmount.toString();
		}
		try{
			int t2Amount = tAmount.intValueExact();
			total = Integer.toString(t2Amount);//convert to a string, and done!
		}
		catch(ArithmeticException e){
			//then it's a decimal... print only two values.. print this!
			total = tAmount.toString();
		}
		
		// checking if user input (currentAmt) amounts is negative 
		if(currentAmt < 0){//first option is negative
			logArea.append(newLine + previous + current + "=" + total );
		}else{
			logArea.append(newLine + previous + "+" + current + "=" + total);
		}
		logArea.setCaretPosition(logArea.getDocument().getLength());
		
		//---------Display in output area---------//
		outputArea.setText("$" + total);
		
		// updating value of prevAmount for next calculation
		prevAmount = totalAmt; 
	}

	public void close() {
		// TODO Auto-generated method stub
		accumulateWindow.dispose();
	}

}
