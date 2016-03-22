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

import BusinessAccumulator.BusinessAccumulator;
import ExpressionCalculator.ExpressionCalculator;

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
	private BusinessAccumulator accum = new BusinessAccumulator();
	private ExpressionCalculator expre = new ExpressionCalculator();

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
				//accumulate(); //these functions automatically take care of updating display + log
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
}

