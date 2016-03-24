package ExpressionCalculator;

//=======================Lab 10 - Graphing Calculator=============================
//Developed by Isaiah Smoak || Meagan Raviele || Rachel Williams || Timothy Crawford

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ExpressionCalculator implements Runnable, ActionListener, KeyListener {    
	/* GUI Variables */
	JFrame calWindow = new JFrame(); // the graphics window for the program
	JTextArea logArea = new JTextArea(""); // area to log all calculations
	JScrollPane logPane = new JScrollPane(logArea);
	JTextField inputBox = new JTextField(""); // input expressions
	JLabel expressLabel = new JLabel("Enter Expression:");
	JTextField inputVarBox = new JTextField(""); // input value for x variable
	JLabel varLabel = new JLabel("For X = ");
	JLabel resultLabel = new JLabel("Result: ");
	JTextField resultBox = new JTextField("");
	JButton clearButton = new JButton("CLEAR"); // clear the required fields
	JLabel errorMessage = new JLabel("Errors are printed here:"); // label to print error messages

	JPanel mainPanel = new JPanel();  // panel consist of log panel and the two panels below
	JPanel inputPanel = new JPanel(); // panel for expression
	JPanel inputPanel2 = new JPanel(); // panel for variable + clear button

	/* Instance Variables */
	String fullExpression; // Holds the expression
	String logExpression; // User entered expression to be logged
	String xVariable; // Holds value of x variable
	String newLine = System.lineSeparator();
	boolean test=false;

	/* Values of PI and E */
	public final static double pi = Math.PI;
	public final static double e =  Math.E;

	public static void main(String[] args) {
		System.out.println("Lab 9 - Expression Calculation "
				+ "by Isaiah Smoak, Meagan Raviele, Rachel Williams, Timothy Crawford");
		new ExpressionCalculator(true);
	}

	public ExpressionCalculator(boolean GUI) {
		// Formatting fonts and colors
		
		if (GUI) {
			varLabel.setFont(new Font("default", Font.BOLD, 20));
			inputBox.setFont(new Font("default", Font.BOLD, 20));
			inputVarBox.setFont(new Font("default", Font.BOLD, 20));
			logArea.setFont(new Font("default", Font.BOLD, 20));
			logArea.setEditable(false);
			resultBox.setFont(new Font("default", Font.BOLD, 20));
			resultBox.setEditable(false);
			resultBox.setBackground(Color.white);
			errorMessage.setFont(new Font("default", Font.BOLD, 20));
			errorMessage.setForeground(Color.red); // Errors are labeled red
			expressLabel.setFont(new Font("default", Font.BOLD, 20));
			resultLabel.setFont(new Font("default", Font.BOLD, 20));
			// Formatting input box sizes
			inputVarBox.setPreferredSize(new Dimension(100, 30)); // Set fixed size of variable box
			inputBox.setPreferredSize(new Dimension(350, 30)); // Set fixed size of expression box
			resultBox.setPreferredSize(new Dimension(150, 30));
			inputPanel.setLayout(new GridBagLayout()); // Set layout of the panel
			inputPanel.add(expressLabel);
			inputPanel.add(inputBox);
			// Adding components to the second panel
			inputPanel2.add(varLabel);
			inputPanel2.add(inputVarBox);
			inputPanel2.add(clearButton);
			// Formatting display area
			Dimension display_size = new Dimension(650, 400);
			calWindow.setSize(display_size);
			calWindow.setLocation(300, 300);
			calWindow.setMinimumSize(display_size);
			calWindow.setVisible(true);
			calWindow.setTitle("Expression Calculator");
			calWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// Formatting log area
			logArea.setSize(calWindow.getWidth() - 10, 300);
			logPane.setSize(calWindow.getWidth() - 10, 300);
			Dimension preferredSize = new Dimension(calWindow.getWidth() - 20, 200);
			logPane.setPreferredSize(preferredSize);
			logPane.setMaximumSize(preferredSize);
			// Adding components to main panel
			mainPanel.setSize(calWindow.getWidth(), calWindow.getHeight());
			mainPanel.setLayout(new FlowLayout());
			mainPanel.add(inputPanel);
			mainPanel.add(inputPanel2);
			mainPanel.add(resultLabel);
			mainPanel.add(resultBox);
			mainPanel.add(logPane);
			// Adding main panel to the main window
			calWindow.getContentPane().add(mainPanel);
			calWindow.getContentPane().add(errorMessage, "South");
			inputVarBox.addKeyListener(this);
			inputBox.addKeyListener(this);
			clearButton.addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == clearButton){
			// Clear fields and errorMessage label
			inputBox.setText("");
			inputVarBox.setText("");
			errorMessage.setText("");
			resultBox.setText("");
		}        
	}

	public void keyPressed(KeyEvent kp) {
		// If Enter Is Pressed
		if(kp.getKeyCode() == KeyEvent.VK_ENTER) {
			// Clear the message box
			errorMessage.setText("");
			try {
				// Get text from expression and variable fields
				logExpression = inputBox.getText();
				fullExpression = logExpression;
				xVariable = inputVarBox.getText();

				// Clear the textBoxes
				inputBox.setText("");
				inputVarBox.setText("");

				// Make sure both fields are valid
				checkErrors();

				// Solve expression
				calculateExpression(fullExpression, xVariable);
			}
			catch (Exception e) {
				//e.printStackTrace();
				errorMessage.setText("ERROR: " + e.getMessage());
				return;
			}
		}
	}

	//==========================calculate()=======================================
	// Description : Continue calculating one simple expression until error occurs
	// Developed  by Isaiah Smoak
	public String calculateExpression(String fullExpression, String xVariable) throws Exception {

		// Macro substitution (subbing for x, pi, e, negative unary operators)
		String localFullExpression = fullExpression;
		
		localFullExpression = substitution(localFullExpression);

		String answer = localFullExpression; //can use an if statement...
		while(isExpression(localFullExpression)){

			// Find highest level expression in parenthesis
			String currentSimpleExpression = findSimpleExpression(localFullExpression);
			// Evaluated answer from expression 

			answer = evalSimpleExpression(currentSimpleExpression);

			// Find index of where currentExpression is in localFullExpression
			int cseIndex = localFullExpression.indexOf(currentSimpleExpression);
			int cseLength = currentSimpleExpression.length();

			// If its a number only, reformat currentSimpleExpression
			if(!(answer.contains("^")
					|| answer.contains("r")
					|| answer.contains("*")
					|| answer.contains("/")
					|| answer.contains("+")
					|| (answer.contains("-") && !answer.startsWith("-"))
					) ){
				// Check to see if there are surrounding ()
				if(cseIndex!=0 && localFullExpression.charAt(cseIndex-1)=='(' && localFullExpression.charAt(cseIndex+cseLength)==')'){
					cseIndex = cseIndex-1;
					cseLength = cseLength+2;
				}
			}
			localFullExpression = localFullExpression.substring(0, cseIndex) + answer + localFullExpression.substring(cseIndex+cseLength);
		}

		// Display final answer to log area
		displayAnswer(answer);
		return answer;

	}
	//==========================END calculate()==============================

	//========================displayAnswer()=================================
	// Description: Format and display the final answer in resultBox
	//Developed by Meagan Raviele
	private void displayAnswer(String answer) {
		logExpression = logExpression + " = " + answer;
		if(logExpression.contains("X") || logExpression.contains("x")) {
			logExpression = logExpression + ", where X="+xVariable;
		}
		logArea.append(newLine + logExpression);
		logArea.setCaretPosition(logArea.getDocument().getLength());
		resultBox.setText(answer);
		resultBox.setCaretPosition(0);

	} 
	//==========================END displayAnswer()=========================

	//========================isExpression()=================================
	// Description: Determines if an expression exists to be handled returns T/F (boolean)
	// Developed by Meagan Raviele
	private boolean isExpression(String simpleExpression) {
		boolean num1 = false;
		//boolean num2 = false;
		boolean operator = false;
		if(simpleExpression.contains("("))
			return true;
		for(int i = 0; i < simpleExpression.length(); i++){
			if(simpleExpression.charAt(i) == '0' || simpleExpression.charAt(i) == '1' ||simpleExpression.charAt(i) == '2' ||simpleExpression.charAt(i) == '3' ||simpleExpression.charAt(i) == '4' ||simpleExpression.charAt(i) == '5' ||simpleExpression.charAt(i) == '6' ||simpleExpression.charAt(i) == '7' ||simpleExpression.charAt(i) == '8' ||simpleExpression.charAt(i) == '9' )
				num1 = true; //don't have to worry about implicit parenthesis
			if(isOperator(simpleExpression.charAt(i)) && num1)
				operator = true;
			if(operator && num1)	
				if(simpleExpression.charAt(i) == '0' || simpleExpression.charAt(i) == '1' ||simpleExpression.charAt(i) == '2' ||simpleExpression.charAt(i) == '3' ||simpleExpression.charAt(i) == '4' ||simpleExpression.charAt(i) == '5' ||simpleExpression.charAt(i) == '6' ||simpleExpression.charAt(i) == '7' ||simpleExpression.charAt(i) == '8' ||simpleExpression.charAt(i) == '9' )
					return true;
		}
		if(operator && num1)
			throw new IllegalArgumentException("Operator found with no expression!");
		return false;
	}  
	//==========================END isExpression()===========================

	//========================findSimpleExpression()==========================
	// Description: Find highest order precedence operation PEMDAS!
	// Developed by Rachel Williams
	private String findSimpleExpression(String expression) throws Exception {
		// Inner most parentheses expression
		String simpleExpression = findInnerParentheses(expression);

		// Indexes of two highest order operators to compare
		int index1;
		int index2;

		// Index of operator that will take precedence
		int indexValue;

		// Find operation of highest order: exponent/root
		index1 = simpleExpression.indexOf("^");
		index2 = simpleExpression.indexOf("r");

		indexValue = takesPrecedent(index1, index2);
		if(indexValue != -1){
			simpleExpression = smallestExpression(simpleExpression,indexValue);
			return simpleExpression;
		}

		// Find operation of highest order: multiplication/division
		index1 = simpleExpression.indexOf("*");
		index2 = simpleExpression.indexOf("/");

		indexValue = takesPrecedent(index1, index2);
		if(indexValue != -1){
			simpleExpression = smallestExpression(simpleExpression,indexValue);
			return simpleExpression;
		}

		// Find operation of highest order: addition/subtraction
		index1 = simpleExpression.indexOf("+");
		index2 = simpleExpression.indexOf("-", 1);

		indexValue = takesPrecedent(index1, index2);

		if(indexValue != -1){
			simpleExpression = smallestExpression(simpleExpression,indexValue);
			return simpleExpression;
		}
		return simpleExpression;  //replace this with throw new Illegal...
	}    
	//==========================END findSimpleExpression()=========================

	//========================takesPrecendent()====================================
	// Description: Takes in indexes of highest order operators and determines
	//              which of the two operators takes precedent (if any)
	// Developed by Rachel Williams
	private int takesPrecedent(int index1, int index2){
		if(index1 == -1 && index2 == -1){
			// No precedent present 
			return -1; 

		// At least one of two operators is present 
		}else{ 
			if(index1 == -1) return index2;
			else if(index2 == -1) return index1;
			if(index1 < index2) return index1;
			else return index2;
		}
	}   
	//==========================END takesPrecendent()=============================

	//=========================smallestExpression()===============================
	// Description: Takes in expression and index of highest order operator
	//              and finds the numbers involved in that operation
	// Developed by Rachel Williams
	private String smallestExpression(String expression, int indexOfOperator){
		int leftString = indexOfOperator -1;
		int rightString = indexOfOperator +1;
		boolean foundnumber = false;

		for(; leftString >= 0; leftString--){
			// Proper amount has been decremented 
			if(expression.charAt(leftString) == '+' || expression.charAt(leftString) == '/' ||expression.charAt(leftString) == '^' ||expression.charAt(leftString) == '*' || expression.charAt(leftString) == 'r') break;

			if(expression.charAt(leftString) == '-'){
				// If negative is found and if the next character to the left isn't +,-,*,r or can't go left, doesn't take negative 
				if(leftString != 0){ 
					// Go to the left one more time and see if operator is there 
					if(!isOperator(expression.charAt(leftString-1))){
						break;
					}else{ 
						// Unary operator present
						leftString--;
						break;
					}
				}
			}
		}

		for(; rightString < expression.length(); rightString++){
			if(expression.charAt(rightString) == '0' || expression.charAt(rightString) == '1' || expression.charAt(rightString) == '2' || expression.charAt(rightString) == '3' || expression.charAt(rightString) == '4' || expression.charAt(rightString) == '5' || expression.charAt(rightString) == '6' ||expression.charAt(rightString) == '7' ||expression.charAt(rightString) == '8' ||expression.charAt(rightString) == '9'){
				foundnumber = true;
			}

			if(expression.charAt(rightString) == '-' || expression.charAt(rightString) == '+' || expression.charAt(rightString) == '/' ||expression.charAt(rightString) == '^' ||expression.charAt(rightString) == '*' || expression.charAt(rightString) == 'r'){
				// If the number has been found, break 
				if(foundnumber) break;
			}
		}

		// Getting the simplest expression from the whole expression
		String simpleExpression = expression.substring(leftString+1 ,rightString);
		return simpleExpression;
	}
	//==========================END smallestExpression()===============================

	//==========================isOperator()===========================================
	// Description: Determines whether or not char parameter is an operator 
	// Developed by Timothy Crawford 
	private boolean isOperator(char op){
		boolean isOp = false;
		switch(op){
		case '+':
		case '-':
		case '*':
		case '/':
		case '^':
		case 'r':
			isOp = true;
		}
		return isOp;
	} 
	//==========================END isOperator()=======================================

	//========================findSimpleParentheses()==================================
	// Description: Find innermost parentheses operation and evaluate it
	// Developed by Rachel Williams
	private String findInnerParentheses(String expression){
		// Simplest expression after finding innermost parentheses
		String simpleExpression = expression;

		// For keeping track of parentheses
		int leftPs = 0;
		int count = 0;

		// Check to see if expression has any parentheses
		if(!((simpleExpression.contains("(")) ||  (simpleExpression.contains(")")))){
			return simpleExpression;
		}

		// Go through length of expression, count total num of left parentheses
		for(int i=0; i< simpleExpression.length(); i++){
			if((simpleExpression.charAt(i)=='(')){
				leftPs++;
			}
		}

		// Go through expression again, go to innermost parentheses
		for(int i=0; i< simpleExpression.length(); i++){
			if(simpleExpression.charAt(i) == '('){
				count++;

				if(count == leftPs){
					// Innermost parentheses expression (parentheses excluded)
					simpleExpression = simpleExpression.substring((i+1),simpleExpression.indexOf(")", i));
				}
			}
		}
		return simpleExpression;
	}  
	//==========================END findSimpleParentheses()===============================

	//========================evalSimpleExpression()======================================
	// Description: Find innermost parentheses operation and evaluate it
	// Developed by Timothy Crawford
	private String evalSimpleExpression(String currentSimpleExpression) throws IllegalArgumentException {
		//======================Instance Variables==============
		char operator;
		int expressionParse= 0 ;
		double firstNum;
		double secondNum;
		double result;
		String finalResult;
		currentSimpleExpression = currentSimpleExpression.trim();
		
		try{
			Double.parseDouble(currentSimpleExpression); //try to see if it's just a number!
			return currentSimpleExpression;
		}
		catch(NumberFormatException e){}

		//=====================Look for Operator==============
		for (; expressionParse < currentSimpleExpression.length(); expressionParse++){
			if ((currentSimpleExpression.charAt(expressionParse) == '+')
					|| (currentSimpleExpression.charAt(expressionParse) == '*')
					|| (currentSimpleExpression.charAt(expressionParse) == '/')
					|| (currentSimpleExpression.charAt(expressionParse)== '^')
					|| (currentSimpleExpression.charAt(expressionParse)== 'r'))
				break; // Operator has been found 
			if(currentSimpleExpression.charAt(expressionParse) == '-'){
				if(expressionParse == 0) continue;
				else break;
			}
		}

		//======================= Format Error Checking=============
		// Number -> Expression -> Number
		if (expressionParse == 0) throw new IllegalArgumentException("Expression cannot start with an operator.");

		if (expressionParse == currentSimpleExpression.length())
			throw new IllegalArgumentException("Expression must contain a single valid operator surrounded by two valid numbers");

		//======================== User Number Parsing ===============

		String firstNumber =currentSimpleExpression.substring(0,expressionParse).trim();
		operator = currentSimpleExpression.charAt(expressionParse);
		String secondNumber = currentSimpleExpression.substring(expressionParse+1).trim();

		try {
			firstNum = Double.parseDouble(firstNumber);
			secondNum = Double.parseDouble(secondNumber);
		}catch (NumberFormatException e){
			throw new IllegalArgumentException("Expression must contain a single valid operator surrounded by two valid numbers");
		}

		//=========== Operation Recipe Book ====================

		switch (operator){
		case '+':
			result = firstNum + secondNum;
			break;
		case '-':
			result = firstNum - secondNum;
			break;
		case '*':
			result = firstNum * secondNum;
			break;
		case '/':
			result = firstNum / secondNum;
			if(secondNum == 0)
				throw new ArithmeticException("Cannot divide by zero!");
			break;
		case '^':
			result = Math.pow(firstNum, secondNum);
			break;
		case 'r':
			result = Math.pow(firstNum, (1/secondNum));
			break;
		default:
			throw new IllegalArgumentException("Not a valid operator!");
		}

		finalResult = Double.toString(result);
		return finalResult;
	}
	//==========================END evalSimpleExpression()===============================

	//========================subsitution()==============================================
	// Description: Replaces all X's in string w/ value of xVariable
	//              Replaces double negatives
	// Developed by Meagan Raviele
	private String substitution(String expression) {
		expression = expression.replaceAll("X",xVariable);
		expression = expression.replaceAll("x", xVariable);
		expression = expression.replaceAll("--", "+");
		expression = expression.replaceAll("pi", Double.toString(Math.PI));
		expression = expression.replaceAll("e", Double.toString(Math.E));
		return expression;
	}
	//==========================END subsitution()========================================

	//========================checkErrors()==============================================
	// Description: Make sure inputVarBox is a number or is empty
	//              Make sure nothing obvious is wrong with expression
	// Developed by Isaiah Smoak
	private void checkErrors() throws Exception{
		// Checking if inputVarBox is number/or empty
		xVariable = xVariable.trim();
		fullExpression = fullExpression.trim();
		
		try{
			// If x isn't a number, throw an number format exception
			Double.parseDouble(xVariable); 
		}
		catch(NumberFormatException e){
			// For empty string check to see if X has value
			if(e.getMessage().equalsIgnoreCase("empty String")){ 
				for(int i = 0; i< fullExpression.length(); i++){
					if(fullExpression.charAt(i) == 'x')
						throw new IllegalArgumentException("Variable X exists but without a value");
				} 
			}
			else // If there is an exception and not empty string, it isn't a number
				throw new IllegalArgumentException("Value of X is not a number!");
		} 

		/* Full expression*/
		if(fullExpression.length() == 0) throw new IllegalArgumentException("No expression is given!");
		boolean number1found = false;
		boolean number2found = false;
		boolean spacefound = false;
		boolean operator = false;
		for(int i = 0; i < fullExpression.length(); i++){
			if(fullExpression.charAt(i) == '0' || fullExpression.charAt(i) == '1' || fullExpression.charAt(i) == '2' || fullExpression.charAt(i) == '3' || fullExpression.charAt(i) == '4' || fullExpression.charAt(i) == '5' || fullExpression.charAt(i) == '6' ||fullExpression.charAt(i) == '7' ||fullExpression.charAt(i) == '8' ||fullExpression.charAt(i) == '9')
				number1found = true;
			if(number1found && isOperator(fullExpression.charAt(i)))
				operator = true;
			if(number1found) 
				if(fullExpression.charAt(i) == ' ')
					spacefound = true;
			if(number1found && number2found && operator)
			{
				number1found = false; number2found = false; spacefound = false; operator = false;
			}
			if(number1found && spacefound && !operator)
				if(fullExpression.charAt(i) == '0' || fullExpression.charAt(i) == '1' || fullExpression.charAt(i) == '2' || fullExpression.charAt(i) == '3' || fullExpression.charAt(i) == '4' || fullExpression.charAt(i) == '5' || fullExpression.charAt(i) == '6' ||fullExpression.charAt(i) == '7' ||fullExpression.charAt(i) == '8' ||fullExpression.charAt(i) == '9')
					number2found = true;
			
		}
		if(number1found && spacefound && number2found)
			throw new IllegalArgumentException("Invalid Expression! Atleast one number with missing operator");
		// Parse through fullExpression
		for(int i = 0; i < fullExpression.length(); i++){

			// See if implicit parenthesis is here
			if(fullExpression.charAt(i) == '(') //was parenthesis here
				if(i > 0)
					if(fullExpression.charAt(i-1) != 'r' && fullExpression.charAt(i-1) != '^' && fullExpression.charAt(i-1) != '+' && fullExpression.charAt(i-1) != '-' && fullExpression.charAt(i-1) != '*' && fullExpression.charAt(i-1) != '/' && fullExpression.charAt(i-1) != '(')
						throw new IllegalArgumentException("Implicit Parenthesis at location " + i);
					else if(fullExpression.charAt(i) == (')'))
					{
						if(i < fullExpression.length())
							if(fullExpression.charAt(i+1) != 'r' && fullExpression.charAt(i+1) != '^' && fullExpression.charAt(i+1) != '+' && fullExpression.charAt(i+1) != '-' && fullExpression.charAt(i+1) != '*' && fullExpression.charAt(i+1) != '/' && fullExpression.charAt(i+1) != '(')
							{

								throw new IllegalArgumentException("Implicit Parenthesis at location " + i);
							}
					}
			
			if(!isLegal(fullExpression.charAt(i))){
				/* Look for pi or e if not "legal" */
				if(fullExpression.charAt(i) == 'p'){
					if(fullExpression.charAt(i+1) == 'i') continue;
					else
						throw new IllegalArgumentException("Invalid symbol beginning with "+ fullExpression.charAt(i) + " at location " + Integer.toString(i));
				}
				else if(fullExpression.charAt(i) == 'i' ){
					if(fullExpression.charAt(i-1) == 'p' ) continue;
					else
						throw new IllegalArgumentException("Invalid symbol beginning with "+ fullExpression.charAt(i) + " at location " + Integer.toString(i));
				}
				else if(fullExpression.charAt(i) == 'e'){ // Look for pi or E
					continue; // Not truly illegal, pass
				}
				else
					throw new IllegalArgumentException("Invalid symbol beginning with "+ fullExpression.charAt(i) + " at location " + Integer.toString(i));
			}
		}
		// Lastly, handle parenthesis in expression
		checkParenthesis(fullExpression);			
	}  
	//==========================END checkErrors()===============================

	//========================checkParenthesis()================================
	// Description: Checks for proper format of parenthesis
	//              but ignores implicit multiplication e.g 2(2)
	// Developed by Isaiah Smoak
	private void checkParenthesis(String fullExpress) throws IllegalArgumentException{
		/* Counter for parenthesis*/
		int Parenthesis = 0;
		
		for(int i = 0; i < fullExpress.length(); i++){
			if(fullExpress.charAt(i) == '(') Parenthesis++;
			if(fullExpress.charAt(i) == ')')Parenthesis--;
			if(Parenthesis < 0) // At ANY point, too many right parenthesis e.g ((0)))
				throw new IllegalArgumentException("Parenthesis are not matched!");
		}
		if(Parenthesis != 0) // Uneven parenthesis
			throw new IllegalArgumentException("Uneven number of parenthesis!");
	}  
	//==========================END checkParenthesis()=============================

	//===========================isLegal()=========================================
	// Description:  Make sure expression has nothing but numbers or operators. 
	//               Doesn't check for symbols
	// Developed by Isaiah Smoak
	private boolean isLegal(char k) {
		switch (k){
		case 'x':
		case ' ':
		case '.':
		case '+':
		case '-':
		case '*':
		case '/':
		case '^':
		case 'r':
		case '(':
		case ')':
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return true;
		default:
			return false;
		}
	}
	//==========================END isLegal()======================================

	public void run() {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void close() {
		// TODO Auto-generated method stub
		calWindow.dispose();
	}
}
