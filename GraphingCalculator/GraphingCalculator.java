import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* Isaiah Smoak, Rachel Williams, Meagan Raviele, Tim Crawford  
   ECE 309 Lab 10
 */ 

public class GraphingCalculator implements ActionListener, KeyListener {
	String newLine = System.lineSeparator();
	private JLabel varLabel = new JLabel("X = ");
	private JTextField inputVarBox = new JTextField();
	private JTextArea logArea = new JTextArea();
	private JTextField resultBox = new JTextField();
	private JLabel errorMessage = new JLabel("Errors go here");
	private JLabel inputLabel = new JLabel("Expression: ");
	private JLabel resultLabel = new JLabel("Result: ");
	private JTextField inputBox = new JTextField();
	private JTextField xIncrementField = new JTextField();
	private JLabel xIncrementLabel = new JLabel("inc x:");
	private JPanel inputPanel = new JPanel();
	private JPanel inputPanel2 = new JPanel();
	private JScrollPane logPane = new JScrollPane(logArea);
	private JPanel mainPanel = new JPanel();	
	private JButton clearButton = new JButton("CLEAR");
	private JFrame calWindow = new JFrame("Lab 10: Graphing Calculator");
	private ExpressionCalculator expre = new ExpressionCalculator(false);

	public GraphingCalculator() {
		/* Formatting fonts and colors */ 
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
		inputLabel.setFont(new Font("default", Font.BOLD, 20));
		resultLabel.setFont(new Font("default", Font.BOLD, 20));
		xIncrementLabel.setFont(new Font("default", Font.BOLD, 20));
		xIncrementField.setFont(new Font("default", Font.BOLD, 20));

		/* Formatting input box sizes */
		inputVarBox.setPreferredSize(new Dimension(100, 30)); // Set fixed size
		inputBox.setPreferredSize(new Dimension(340, 30)); // Set fixed size of
		xIncrementField.setPreferredSize(new Dimension(100,30));		// expression box
		resultBox.setPreferredSize(new Dimension(200, 30));
		inputPanel.setLayout(new GridBagLayout()); // Set layout of the panel

		/* Adding components to the second panel */ 
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

		/* Formatting display area */ 
		Dimension display_size = new Dimension(800, 400);
		calWindow.setSize(display_size);
		calWindow.setLocation(300, 300);
		calWindow.setMinimumSize(display_size);
		calWindow.setVisible(true);
		calWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		/* Formatting log area */ 
		logArea.setSize(calWindow.getWidth() - 10, 100);
		logPane.setSize(calWindow.getWidth() - 10, 100);
		Dimension preferredSize = new Dimension(calWindow.getWidth() - 20, 200);
		logPane.setPreferredSize(preferredSize);
		logPane.setMaximumSize(preferredSize);

		/* Adding components to main panel */ 
		mainPanel.setSize(calWindow.getWidth(), calWindow.getHeight());
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(inputPanel);
		mainPanel.add(inputPanel2);
		mainPanel.add(logPane);

		/* Adding main panel to the main window */  
		calWindow.getContentPane().add(mainPanel);
		calWindow.getContentPane().add(errorMessage, "South");
		inputVarBox.addKeyListener(this);
		inputBox.addKeyListener(this);
		clearButton.addActionListener(this);
		xIncrementField.addKeyListener(this);
	}

	public void drawGraph() throws Exception{
		String expression = inputBox.getText(); 
		inputVarBox.getText();
		int xbegin = Integer.parseInt(inputVarBox.getText());
		int xinc = Integer.parseInt(xIncrementField.getText());

		// For 11 values including origin
		int size = 11; 

		double[] xValues = new double[size]; 
		double[] yValues = new double [size];

		for(int i = 0; i < xValues.length; i++){
			xValues[i] = xbegin+(xinc*i); 
		}

		for(int i = 0; i < yValues.length; i++){
			yValues[i] = calculateForGraph(expression,xValues[i]);
			logArea.append(("for x: " + xValues[i] + ", y is " + yValues[i] + newLine));
			logArea.setCaretPosition(logArea.getDocument().getLength());
		}

		JFrame graphWindow = new JFrame("Expression: " + inputBox.getText() + "(Click on the graph line to show x,y values at that point)");
		graphWindow.setSize(500, 500);
		graphWindow.setVisible(true);
		graphWindow.setLocation(400, 100);

		/* Array of x values and y values */
		graphWindow.add(new GraphPanel(xValues, yValues, this, expression));// GraphPanel handles the rest
		graphWindow.getContentPane().setBackground(Color.black);
	}

	public double calculateForGraph(String expression, double xValue) throws Exception{	
		// Initialize yValue
		double yValue; 

		// Placeholders for manipulation 
		String holdX; 
		String holdY;

		// Convert to string to be passed into calculate method
		holdX = Double.toString(xValue); 

		// Check for errors in expression 
		expre.checkErrors(holdX, expression);
		
		// Calculate expression, evaluate to get corresponding y value 
		holdY = expre.calculateExpression2(expression,holdX);

		// Convert back to double 
		yValue = Double.parseDouble(holdY); 

		return yValue; 
	}

	public void keyPressed(KeyEvent kp) {
		// Check to see what mode is, and switch based off of it. Also add/remove fields
		if(kp.getKeyCode() == KeyEvent.VK_ENTER) {
			// Clear the message box
			errorMessage.setText("");
			try {
				// Draw graph of input expression 
				drawGraph();
			} catch (Exception e) {
				errorMessage.setText(e.getMessage());
			}
		}
	}

	public void keyReleased(KeyEvent arg0){}

	public void keyTyped(KeyEvent arg0) {}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == clearButton){
			// Clear all fields 
			logArea.setText("");
			errorMessage.setText("");
			xIncrementField.setText("");
			inputVarBox.setText("");
			inputBox.setText("");
		}
	}

	public void close() {
		calWindow.dispose();
	}	
}