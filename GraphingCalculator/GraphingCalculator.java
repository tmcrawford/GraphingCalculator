import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GraphingCalculator implements ActionListener, KeyListener {
	// hello testing
	private static final int ACCUMULATE_MODE = 0;
	private static final int EXPRESSION_MODE = 1;
	private static final int GRAPHING_MODE = 2;
	private int currentMode = ACCUMULATE_MODE;
	String newLine = System.lineSeparator();
	private JLabel varLabel = new JLabel("X = ");
	private JTextField inputVarBox = new JTextField();
	private JTextArea logArea = new JTextArea();
	private JTextField resultBox = new JTextField();
	private JLabel errorMessage = new JLabel("Errors go here");
	private JLabel inputLabel = new JLabel("Accumulate: ");
	private JLabel resultLabel = new JLabel("Total: ");
	private JTextField inputBox = new JTextField();
	private JTextField xIncrementField = new JTextField();
	private JLabel xIncrementLabel = new JLabel("to");
	private JPanel inputPanel = new JPanel();
	private JPanel inputPanel2 = new JPanel();
	private JScrollPane logPane = new JScrollPane(logArea);
	private JPanel mainPanel = new JPanel();	
	private JButton clearButton = new JButton("CLEAR");
	private JFrame calWindow = new JFrame("Lab 10: Graphing Calculator");
	private JRadioButton accumuButton = new JRadioButton("Accumulate");
	private JRadioButton expressButton = new JRadioButton("Expression");
	private JRadioButton graphButton = new JRadioButton("Graphing");
	private ButtonGroup group = new ButtonGroup();
	

	public GraphingCalculator() {
		// TODO Auto-generated constructor stub
		// Formatting fonts and colors
		inputVarBox.setEditable(false); //initial!
		xIncrementField.setEditable(false);
		varLabel.setFont(new Font("default", Font.BOLD, 20));
		inputBox.setFont(new Font("default", Font.BOLD, 20));
		inputVarBox.setFont(new Font("default", Font.BOLD, 20));
		logArea.setFont(new Font("default", Font.BOLD, 20));
		logArea.setEditable(false);
		logArea.setPreferredSize(new Dimension(170, 170));
		resultBox.setFont(new Font("default", Font.BOLD, 20));
		resultBox.setEditable(false);
		resultBox.setBackground(Color.white);
		errorMessage.setFont(new Font("default", Font.BOLD, 20));
		errorMessage.setForeground(Color.red); // Errors are labeled red
		inputLabel.setFont(new Font("default", Font.BOLD, 20));
		resultLabel.setFont(new Font("default", Font.BOLD, 20));
		xIncrementLabel.setFont(new Font("default", Font.BOLD, 20));
		accumuButton.setFont(new Font("default", Font.BOLD, 15));
		expressButton.setFont(new Font("default", Font.BOLD, 15));
		graphButton.setFont(new Font("default", Font.BOLD, 15));
		// Formatting input box sizes
		inputVarBox.setPreferredSize(new Dimension(100, 30)); // Set fixed size
		group.add(accumuButton);
		group.add(expressButton);
		group.add(graphButton);
		accumuButton.addActionListener(this);// box
		expressButton.addActionListener(this);
		graphButton.addActionListener(this);
		//can have one set to true by default with command below
		accumuButton.setSelected(true);
		
		inputBox.setPreferredSize(new Dimension(350, 30)); // Set fixed size of
		xIncrementField.setPreferredSize(new Dimension(100,30));		// expression box
		resultBox.setPreferredSize(new Dimension(200, 30));
		inputPanel.setLayout(new GridBagLayout()); // Set layout of the panel
		//inputPanel2.setLayout(new FlowLayout());
		//inputPanel.add(inputLabel);
		//inputPanel.add(inputBox);

		// Adding components to the second panel
		inputPanel.add(accumuButton);
		inputPanel.add(expressButton);
		inputPanel.add(graphButton);
		inputPanel.add(Box.createRigidArea(new Dimension(100,0)));
		inputPanel.add(resultLabel);
		inputPanel.add(resultBox);
		inputPanel.add(Box.createRigidArea(new Dimension(15,0)));
		inputPanel.add(clearButton);
		inputPanel2.add(inputLabel);
		inputPanel2.add(inputBox);
		inputPanel2.add(varLabel);
		inputPanel2.add(inputVarBox);
		inputPanel2.add(xIncrementLabel);
		inputPanel2.add(xIncrementField);
		
		// Formatting display area
		Dimension display_size = new Dimension(800, 400);
		calWindow.setSize(display_size);
		calWindow.setMinimumSize(display_size);
		calWindow.setVisible(true);
		calWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Formatting log area
		logArea.setSize(calWindow.getWidth() - 10, 100);
		logPane.setSize(calWindow.getWidth() - 10, 100);
		Dimension preferredSize = new Dimension(calWindow.getWidth() - 20, 200);
		logPane.setPreferredSize(preferredSize);
		logPane.setMaximumSize(preferredSize);

		// Adding components to main panel
		mainPanel.setSize(calWindow.getWidth(), calWindow.getHeight());
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(inputPanel);
		mainPanel.add(inputPanel2);
		//mainPanel.add(resultLabel);
		//mainPanel.add(resultBox);
		mainPanel.add(logPane);

		// Adding main panel to the main window
		calWindow.getContentPane().add(mainPanel);
		calWindow.getContentPane().add(errorMessage, "South");
		inputVarBox.addKeyListener(this);
		inputBox.addKeyListener(this);
		clearButton.addActionListener(this);
	}

	public static void main(String[] args) {

		System.out.println("Hello everyone GitHub is live");
		// TODO Auto-generated method stub
		new GraphingCalculator();

	}

	// =======calculator expression code ===================
	private void calculateExpression(String fullExpression, String xVariable, JTextArea log, JTextField result)
			throws Exception {

		// Macro substitution (subbing for x, pi, e, negative unary operators)
		String localFullExpression = fullExpression;
		String logExpression = fullExpression; // before substitution
		localFullExpression = substitution(localFullExpression, xVariable);

		String answer = localFullExpression; // can use an if statement...
		while (isExpression(localFullExpression)) {

			// Find highest level expression in parenthesis
			String currentSimpleExpression = findSimpleExpression(localFullExpression);
			// Evaluated answer from expression

			answer = evalSimpleExpression(currentSimpleExpression);

			// Find index of where currentExpression is in localFullExpression
			int cseIndex = localFullExpression.indexOf(currentSimpleExpression);
			int cseLength = currentSimpleExpression.length();

			// If its a number only, reformat currentSimpleExpression
			if (!(answer.contains("^") || answer.contains("r") || answer.contains("*") || answer.contains("/")
					|| answer.contains("+") || (answer.contains("-") && !answer.startsWith("-")))) {
				// Check to see if there are surrounding ()
				if (cseIndex != 0 && localFullExpression.charAt(cseIndex - 1) == '('
						&& localFullExpression.charAt(cseIndex + cseLength) == ')') {
					cseIndex = cseIndex - 1;
					cseLength = cseLength + 2;
				}
			}
			localFullExpression = localFullExpression.substring(0, cseIndex) + answer
					+ localFullExpression.substring(cseIndex + cseLength);
		}

		// Display final answer to log area
		displayAnswer(answer, logExpression, xVariable, log, result);

	}

	// ==========================END calculate()==============================
	// not duplicate! Method overload!! Returns a string
	String calculateExpression(String fullExpression, String xVariable) throws Exception {

		// Macro substitution (subbing for x, pi, e, negative unary operators)
		String localFullExpression = fullExpression;
		String logExpression = fullExpression; // before substitution
		localFullExpression = substitution(localFullExpression, xVariable);

		String answer = localFullExpression; // can use an if statement...
		while (isExpression(localFullExpression)) {

			// Find highest level expression in parenthesis
			String currentSimpleExpression = findSimpleExpression(localFullExpression);
			// Evaluated answer from expression

			answer = evalSimpleExpression(currentSimpleExpression);

			// Find index of where currentExpression is in localFullExpression
			int cseIndex = localFullExpression.indexOf(currentSimpleExpression);
			int cseLength = currentSimpleExpression.length();

			// If its a number only, reformat currentSimpleExpression
			if (!(answer.contains("^") || answer.contains("r") || answer.contains("*") || answer.contains("/")
					|| answer.contains("+") || (answer.contains("-") && !answer.startsWith("-")))) {
				// Check to see if there are surrounding ()
				if (cseIndex != 0 && localFullExpression.charAt(cseIndex - 1) == '('
						&& localFullExpression.charAt(cseIndex + cseLength) == ')') {
					cseIndex = cseIndex - 1;
					cseLength = cseLength + 2;
				}
			}
			localFullExpression = localFullExpression.substring(0, cseIndex) + answer
					+ localFullExpression.substring(cseIndex + cseLength);
		}

		// Display final answer to log area
		return answer; // should return the answer???

	}

	// ========================displayAnswer()=================================
	// Description: Format and display the final answer in resultBox
	// Developed by Meagan Raviele
	private void displayAnswer(String answer, String logExpression, String xVariable, JTextArea logArea,
			JTextField resultBox) {
		logExpression = logExpression + " = " + answer;
		if (logExpression.contains("X") || logExpression.contains("x")) {
			logExpression = logExpression + ", where X=" + xVariable;
		}
		logArea.append(newLine + logExpression);
		logArea.setCaretPosition(logArea.getDocument().getLength());
		resultBox.setText(answer);
		resultBox.setCaretPosition(0);

	}
	// ==========================END displayAnswer()=========================

	// ========================isExpression()=================================
	// Description: Determines if an expression exists to be handled returns T/F
	// (boolean)
	// Developed by Meagan Raviele
	private boolean isExpression(String simpleExpression) {
		boolean num1 = false;
		// boolean num2 = false;
		boolean operator = false;
		if (simpleExpression.contains("("))
			return true;
		for (int i = 0; i < simpleExpression.length(); i++) {
			if (simpleExpression.charAt(i) == '0' || simpleExpression.charAt(i) == '1'
					|| simpleExpression.charAt(i) == '2' || simpleExpression.charAt(i) == '3'
					|| simpleExpression.charAt(i) == '4' || simpleExpression.charAt(i) == '5'
					|| simpleExpression.charAt(i) == '6' || simpleExpression.charAt(i) == '7'
					|| simpleExpression.charAt(i) == '8' || simpleExpression.charAt(i) == '9')
				num1 = true; // don't have to worry about implicit parenthesis
			if (isOperator(simpleExpression.charAt(i)) && num1)
				operator = true;
			if (operator && num1)
				if (simpleExpression.charAt(i) == '0' || simpleExpression.charAt(i) == '1'
						|| simpleExpression.charAt(i) == '2' || simpleExpression.charAt(i) == '3'
						|| simpleExpression.charAt(i) == '4' || simpleExpression.charAt(i) == '5'
						|| simpleExpression.charAt(i) == '6' || simpleExpression.charAt(i) == '7'
						|| simpleExpression.charAt(i) == '8' || simpleExpression.charAt(i) == '9')
					return true;
		}
		if (operator && num1)
			throw new IllegalArgumentException("Operator found with no expression!");
		return false;
	}
	// ==========================END isExpression()===========================

	// ========================findSimpleExpression()==========================
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
		if (indexValue != -1) {
			simpleExpression = smallestExpression(simpleExpression, indexValue);
			return simpleExpression;
		}

		// Find operation of highest order: multiplication/division
		index1 = simpleExpression.indexOf("*");
		index2 = simpleExpression.indexOf("/");

		indexValue = takesPrecedent(index1, index2);
		if (indexValue != -1) {
			simpleExpression = smallestExpression(simpleExpression, indexValue);
			return simpleExpression;
		}

		// Find operation of highest order: addition/subtraction
		index1 = simpleExpression.indexOf("+");
		index2 = simpleExpression.indexOf("-", 1);

		indexValue = takesPrecedent(index1, index2);

		if (indexValue != -1) {
			simpleExpression = smallestExpression(simpleExpression, indexValue);
			return simpleExpression;
		}
		return simpleExpression; // replace this with throw new Illegal...
	}
	// ==========================END
	// findSimpleExpression()=========================

	// ========================takesPrecendent()====================================
	// Description: Takes in indexes of highest order operators and determines
	// which of the two operators takes precedent (if any)
	// Developed by Rachel Williams
	private int takesPrecedent(int index1, int index2) {
		if (index1 == -1 && index2 == -1) {
			// No precedent present
			return -1;

			// At least one of two operators is present
		} else {
			if (index1 == -1)
				return index2;
			else if (index2 == -1)
				return index1;
			if (index1 < index2)
				return index1;
			else
				return index2;
		}
	}
	// ==========================END
	// takesPrecendent()=============================

	// =========================smallestExpression()===============================
	// Description: Takes in expression and index of highest order operator
	// and finds the numbers involved in that operation
	// Developed by Rachel Williams
	private String smallestExpression(String expression, int indexOfOperator) {
		int leftString = indexOfOperator - 1;
		int rightString = indexOfOperator + 1;
		boolean foundnumber = false;

		for (; leftString >= 0; leftString--) {
			// Proper amount has been decremented
			if (expression.charAt(leftString) == '+' || expression.charAt(leftString) == '/'
					|| expression.charAt(leftString) == '^' || expression.charAt(leftString) == '*'
					|| expression.charAt(leftString) == 'r')
				break;

			if (expression.charAt(leftString) == '-') {
				// If negative is found and if the next character to the left
				// isn't +,-,*,r or can't go left, doesn't take negative
				if (leftString != 0) {
					// Go to the left one more time and see if operator is there
					if (!isOperator(expression.charAt(leftString - 1))) {
						break;
					} else {
						// Unary operator present
						leftString--;
						break;
					}
				}
			}
		}

		for (; rightString < expression.length(); rightString++) {
			if (expression.charAt(rightString) == '0' || expression.charAt(rightString) == '1'
					|| expression.charAt(rightString) == '2' || expression.charAt(rightString) == '3'
					|| expression.charAt(rightString) == '4' || expression.charAt(rightString) == '5'
					|| expression.charAt(rightString) == '6' || expression.charAt(rightString) == '7'
					|| expression.charAt(rightString) == '8' || expression.charAt(rightString) == '9') {
				foundnumber = true;
			}

			if (expression.charAt(rightString) == '-' || expression.charAt(rightString) == '+'
					|| expression.charAt(rightString) == '/' || expression.charAt(rightString) == '^'
					|| expression.charAt(rightString) == '*' || expression.charAt(rightString) == 'r') {
				// If the number has been found, break
				if (foundnumber)
					break;
			}
		}

		// Getting the simplest expression from the whole expression
		String simpleExpression = expression.substring(leftString + 1, rightString);
		return simpleExpression;
	}
	// ==========================END
	// smallestExpression()===============================

	// ==========================isOperator()===========================================
	// Description: Determines whether or not char parameter is an operator
	// Developed by Timothy Crawford
	private boolean isOperator(char op) {
		boolean isOp = false;
		switch (op) {
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
	// ==========================END
	// isOperator()=======================================

	// ========================findSimpleParentheses()==================================
	// Description: Find innermost parentheses operation and evaluate it
	// Developed by Rachel Williams
	private String findInnerParentheses(String expression) {
		// Simplest expression after finding innermost parentheses
		String simpleExpression = expression;

		// For keeping track of parentheses
		int leftPs = 0;
		int count = 0;

		// Check to see if expression has any parentheses
		if (!((simpleExpression.contains("(")) || (simpleExpression.contains(")")))) {
			return simpleExpression;
		}

		// Go through length of expression, count total num of left parentheses
		for (int i = 0; i < simpleExpression.length(); i++) {
			if ((simpleExpression.charAt(i) == '(')) {
				leftPs++;
			}
		}

		// Go through expression again, go to innermost parentheses
		for (int i = 0; i < simpleExpression.length(); i++) {
			if (simpleExpression.charAt(i) == '(') {
				count++;

				if (count == leftPs) {
					// Innermost parentheses expression (parentheses excluded)
					simpleExpression = simpleExpression.substring((i + 1), simpleExpression.indexOf(")", i));
				}
			}
		}
		return simpleExpression;
	}
	// ==========================END
	// findSimpleParentheses()===============================

	// ========================evalSimpleExpression()======================================
	// Description: Find innermost parentheses operation and evaluate it
	// Developed by Timothy Crawford
	private String evalSimpleExpression(String currentSimpleExpression) throws IllegalArgumentException {
		// ======================Instance Variables==============
		char operator;
		int expressionParse = 0;
		double firstNum;
		double secondNum;
		double result;
		String finalResult;
		currentSimpleExpression = currentSimpleExpression.trim();

		try {
			Double.parseDouble(currentSimpleExpression); // try to see if it's
															// just a number!
			return currentSimpleExpression;
		} catch (NumberFormatException e) {
		}

		// =====================Look for Operator==============
		for (; expressionParse < currentSimpleExpression.length(); expressionParse++) {
			if ((currentSimpleExpression.charAt(expressionParse) == '+')
					|| (currentSimpleExpression.charAt(expressionParse) == '*')
					|| (currentSimpleExpression.charAt(expressionParse) == '/')
					|| (currentSimpleExpression.charAt(expressionParse) == '^')
					|| (currentSimpleExpression.charAt(expressionParse) == 'r'))
				break; // Operator has been found
			if (currentSimpleExpression.charAt(expressionParse) == '-') {
				if (expressionParse == 0)
					continue;
				else
					break;
			}
		}

		// ======================= Format Error Checking=============
		// Number -> Expression -> Number
		if (expressionParse == 0)
			throw new IllegalArgumentException("Expression cannot start with an operator.");

		if (expressionParse == currentSimpleExpression.length())
			throw new IllegalArgumentException(
					"Expression must contain a single valid operator surrounded by two valid numbers");

		// ======================== User Number Parsing ===============

		String firstNumber = currentSimpleExpression.substring(0, expressionParse).trim();
		operator = currentSimpleExpression.charAt(expressionParse);
		String secondNumber = currentSimpleExpression.substring(expressionParse + 1).trim();

		try {
			firstNum = Double.parseDouble(firstNumber);
			secondNum = Double.parseDouble(secondNumber);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Expression must contain a single valid operator surrounded by two valid numbers");
		}

		// =========== Operation Recipe Book ====================

		switch (operator) {
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
			if (secondNum == 0)
				throw new ArithmeticException("Cannot divide by zero!");
			break;
		case '^':
			result = Math.pow(firstNum, secondNum);
			break;
		case 'r':
			result = Math.pow(firstNum, (1 / secondNum));
			break;
		default:
			throw new IllegalArgumentException("Not a valid operator!");
		}

		finalResult = Double.toString(result);
		return finalResult;
	}
	// ==========================END
	// evalSimpleExpression()===============================

	// ========================subsitution()==============================================
	// Description: Replaces all X's in string w/ value of xVariable
	// Replaces double negatives
	// Developed by Meagan Raviele
	private String substitution(String expression, String xVariable) {
		expression = expression.replaceAll("X", xVariable);
		expression = expression.replaceAll("x", xVariable);
		expression = expression.replaceAll("--", "+");
		expression = expression.replaceAll("pi", Double.toString(Math.PI));
		expression = expression.replaceAll("e", Double.toString(Math.E));
		return expression;
	}
	// ==========================END
	// subsitution()========================================

	// ========================checkErrors()==============================================
	// Description: Make sure inputVarBox is a number or is empty
	// Make sure nothing obvious is wrong with expression
	// Developed by Isaiah Smoak
	private void checkErrors(String fullExpression, String xVariable) throws Exception {
		// Checking if inputVarBox is number/or empty
		xVariable = xVariable.trim();
		fullExpression = fullExpression.trim();

		try {
			// If x isn't a number, throw an number format exception
			Double.parseDouble(xVariable);
		} catch (NumberFormatException e) {
			// For empty string check to see if X has value
			if (e.getMessage().equalsIgnoreCase("empty String")) {
				for (int i = 0; i < fullExpression.length(); i++) {
					if (fullExpression.charAt(i) == 'x')
						throw new IllegalArgumentException("Variable X exists but without a value");
				}
			} else // If there is an exception and not empty string, it isn't a
					// number
				throw new IllegalArgumentException("Value of X is not a number!");
		}

		/* Full expression */
		if (fullExpression.length() == 0)
			throw new IllegalArgumentException("No expression is given!");
		boolean number1found = false;
		boolean number2found = false;
		boolean spacefound = false;
		boolean operator = false;
		for (int i = 0; i < fullExpression.length(); i++) {
			if (fullExpression.charAt(i) == '0' || fullExpression.charAt(i) == '1' || fullExpression.charAt(i) == '2'
					|| fullExpression.charAt(i) == '3' || fullExpression.charAt(i) == '4'
					|| fullExpression.charAt(i) == '5' || fullExpression.charAt(i) == '6'
					|| fullExpression.charAt(i) == '7' || fullExpression.charAt(i) == '8'
					|| fullExpression.charAt(i) == '9')
				number1found = true;
			if (number1found && isOperator(fullExpression.charAt(i)))
				operator = true;
			if (number1found)
				if (fullExpression.charAt(i) == ' ')
					spacefound = true;
			if (number1found && number2found && operator) {
				number1found = false;
				number2found = false;
				spacefound = false;
				operator = false;
			}
			if (number1found && spacefound && !operator)
				if (fullExpression.charAt(i) == '0' || fullExpression.charAt(i) == '1'
						|| fullExpression.charAt(i) == '2' || fullExpression.charAt(i) == '3'
						|| fullExpression.charAt(i) == '4' || fullExpression.charAt(i) == '5'
						|| fullExpression.charAt(i) == '6' || fullExpression.charAt(i) == '7'
						|| fullExpression.charAt(i) == '8' || fullExpression.charAt(i) == '9')
					number2found = true;

		}
		if (number1found && spacefound && number2found)
			throw new IllegalArgumentException("Invalid Expression! Atleast one number with missing operator");
		// Parse through fullExpression
		for (int i = 0; i < fullExpression.length(); i++) {

			// See if implicit parenthesis is here
			if (fullExpression.charAt(i) == '(') // was parenthesis here
				if (i > 0)
					if (fullExpression.charAt(i - 1) != 'r' && fullExpression.charAt(i - 1) != '^'
							&& fullExpression.charAt(i - 1) != '+' && fullExpression.charAt(i - 1) != '-'
							&& fullExpression.charAt(i - 1) != '*' && fullExpression.charAt(i - 1) != '/'
							&& fullExpression.charAt(i - 1) != '(')
						throw new IllegalArgumentException("Implicit Parenthesis at location " + i);
					else if (fullExpression.charAt(i) == (')')) {
						if (i < fullExpression.length())
							if (fullExpression.charAt(i + 1) != 'r' && fullExpression.charAt(i + 1) != '^'
									&& fullExpression.charAt(i + 1) != '+' && fullExpression.charAt(i + 1) != '-'
									&& fullExpression.charAt(i + 1) != '*' && fullExpression.charAt(i + 1) != '/'
									&& fullExpression.charAt(i + 1) != '(') {

								throw new IllegalArgumentException("Implicit Parenthesis at location " + i);
							}
					}

			if (!isLegal(fullExpression.charAt(i))) {
				/* Look for pi or e if not "legal" */
				if (fullExpression.charAt(i) == 'p') {
					if (fullExpression.charAt(i + 1) == 'i')
						continue;
					else
						throw new IllegalArgumentException("Invalid symbol beginning with " + fullExpression.charAt(i)
								+ " at location " + Integer.toString(i));
				} else if (fullExpression.charAt(i) == 'i') {
					if (fullExpression.charAt(i - 1) == 'p')
						continue;
					else
						throw new IllegalArgumentException("Invalid symbol beginning with " + fullExpression.charAt(i)
								+ " at location " + Integer.toString(i));
				} else if (fullExpression.charAt(i) == 'e') { // Look for pi or
																// E
					continue; // Not truly illegal, pass
				} else
					throw new IllegalArgumentException("Invalid symbol beginning with " + fullExpression.charAt(i)
							+ " at location " + Integer.toString(i));
			}
		}
		// Lastly, handle parenthesis in expression
		checkParenthesis(fullExpression);
	}
	// ==========================END
	// checkErrors()===============================

	// ========================checkParenthesis()================================
	// Description: Checks for proper format of parenthesis
	// but ignores implicit multiplication e.g 2(2)
	// Developed by Isaiah Smoak
	private void checkParenthesis(String fullExpress) throws IllegalArgumentException {
		/* Counter for parenthesis */
		int Parenthesis = 0;

		for (int i = 0; i < fullExpress.length(); i++) {
			if (fullExpress.charAt(i) == '(')
				Parenthesis++;
			if (fullExpress.charAt(i) == ')')
				Parenthesis--;
			if (Parenthesis < 0) // At ANY point, too many right parenthesis e.g
									// ((0)))
				throw new IllegalArgumentException("Parenthesis are not matched!");
		}
		if (Parenthesis != 0) // Uneven parenthesis
			throw new IllegalArgumentException("Uneven number of parenthesis!");
	}
	// ==========================END
	// checkParenthesis()=============================

	// ===========================isLegal()=========================================
	// Description: Make sure expression has nothing but numbers or operators.
	// Doesn't check for symbols
	// Developed by Isaiah Smoak
	private boolean isLegal(char k) {
		switch (k) {
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
	// ==========================END
	// isLegal()======================================

	@Override
	public void keyPressed(KeyEvent kp) {
		// TODO Auto-generated method stub
		//check to see what mode is, and switch based off of it. Also add/remove fields
		if(kp.getKeyCode() == KeyEvent.VK_ENTER) {
			// Clear the message box
			errorMessage.setText("");
			switch(currentMode){
			case ACCUMULATE_MODE:
				System.out.println("accumulate code here");
				//accumulate();
				break;
			case EXPRESSION_MODE:
				System.out.println("expresse code here");
				//calculateExpression();
				break;
			case GRAPHING_MODE:
				System.out.println("graph code here");
				//graph();
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == accumuButton ){
			inputLabel.setText("Accumulate: ");
			inputVarBox.setEditable(false);
			currentMode = ACCUMULATE_MODE;
			xIncrementField.setEditable(false);
			resultLabel.setText("Total: ");
			//System.out.println("accum");
			logArea.setVisible(true);
		}
		else if(arg0.getSource() == expressButton){
			currentMode = EXPRESSION_MODE;
			inputLabel.setText("Expression: ");
			inputVarBox.setEditable(true);
			resultLabel.setText("Result: ");
			xIncrementField.setEditable(false);
			logArea.setVisible(true);
			//System.out.println("express");
		}
		else if(arg0.getSource() == graphButton){
			currentMode = GRAPHING_MODE;
			inputLabel.setText("Expression: ");
			inputVarBox.setEditable(true);
			resultLabel.setText("Result: ");
			xIncrementField.setEditable(true);
			logArea.setVisible(false);
			//System.out.println("graph");
		}
	}
	//handle code for things... 
}
