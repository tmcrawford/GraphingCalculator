import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class GraphMenu implements ActionListener {
	private static final int ACCUMULATE_MODE = 0;
	private static final int EXPRESSION_MODE = 1;
	private static final int GRAPHING_MODE = 2;
	
	private int currentMode = ACCUMULATE_MODE;
	
	private JLabel inputLabel = new JLabel("Click on button to open desired function");
	private JPanel inputPanel = new JPanel();
	private JRadioButton accumuButton = new JRadioButton("Accumulate");
	private JRadioButton expressButton = new JRadioButton("Expression");
	private JRadioButton graphButton = new JRadioButton("Graphing");
	private JFrame calWindow = new JFrame("Lab 10: Graphing Calculator");

	private BusinessAccumulator accum;
	private ExpressionCalculator expre;
	private GraphingCalculator grph; 
	
	public GraphMenu(){
		JFrame mainWindow = new JFrame("Lab 10 ECE 310 Project");
		mainWindow.setSize(new Dimension(400,150));
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputLabel.setFont(new Font("default", Font.BOLD, 15));
		inputLabel.setForeground(Color.red);
		ButtonGroup group = new ButtonGroup();
		accumuButton.setFont(new Font("default", Font.BOLD, 15));
		expressButton.setFont(new Font("default", Font.BOLD, 15));
		graphButton.setFont(new Font("default", Font.BOLD, 15));
	
		group.add(accumuButton);
		group.add(expressButton);
		group.add(graphButton);
		accumuButton.addActionListener(this);// box
		expressButton.addActionListener(this);
		graphButton.addActionListener(this);
		//can have one set to true by default with command below
		inputPanel.add(inputLabel);
		inputPanel.add(accumuButton);
		inputPanel.add(expressButton);
		inputPanel.add(graphButton);
		mainWindow.add(inputPanel);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == accumuButton ){
			currentMode = ACCUMULATE_MODE;
			if(expre != null){
				expre.close();
				expre = null;
			}
			if(grph != null){
				grph.close();
				grph = null;
			}
			accum = new BusinessAccumulator();
		}
		else if(arg0.getSource() == expressButton){
			currentMode = EXPRESSION_MODE;
	
			if(grph != null){
				grph.close();
				grph = null;
			}
			if(accum != null){
				accum.close();
				accum = null;
			}
			expre = new ExpressionCalculator();
			//System.out.println("express");
		}
		else if(arg0.getSource() == graphButton){
			currentMode = GRAPHING_MODE;
			
			if(accum != null){
				accum.close();
				accum = null;
			}
			if(expre != null){
				expre.close();
				expre = null;
			}
			grph = new GraphingCalculator();//System.out.println("graph");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GraphMenu();
	}

}
