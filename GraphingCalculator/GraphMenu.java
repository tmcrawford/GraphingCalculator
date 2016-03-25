import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class GraphMenu implements ActionListener {
	
	private JLabel inputLabel = new JLabel("Click on button to open desired function");
	private JPanel inputPanel = new JPanel();
	private JRadioButton accumuButton = new JRadioButton("Accumulate");
	private JRadioButton expressButton = new JRadioButton("Expression");
	private JRadioButton graphButton = new JRadioButton("Graphing");
	private JFrame mainWindow = new JFrame("Lab 10 ECE 310 Project");

	private BusinessAccumulator accum;
	private ExpressionCalculator expre;
	private GraphingCalculator grph; 
	
	public GraphMenu(){
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
		
	
			if(grph != null){
				grph.close();
				grph = null;
			}
			if(accum != null){
				accum.close();
				accum = null;
			}
			expre = new ExpressionCalculator(true);
			//System.out.println("express");
		}
		else if(arg0.getSource() == graphButton){
			
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
