import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ExpressionCalculator.ExpressionCalculator;
import sun.java2d.loops.DrawLine;


public class GraphPanel extends JPanel implements MouseListener {

	private int xPixelsToValueConversionFactor;
	private int xValueToPixelsConversionFactor;
	private int yValueToPixelsConversionFactor;
	private JTextField xTextField;
	private String expression;
	private GraphingCalculator calculator;
	private JTextField yTextField;
	private JFrame displayXYpairWindow;
	private double[] xValuesCopy;
	private double[] yValuesCopy;
	private final int padding = 35; //this is padding for all borders
	public GraphPanel (double[] xValues, double[] yValues) throws IllegalArgumentException
	{
		xValuesCopy = xValues;
		yValuesCopy = yValues;
		
		// To-dos for this constructor method:
		// 1 call addMouseListener(this); to register this panel as the MouseListener
		// 2 Calculate Y scale values (and save them) 
		// 3 Build the mini displayXYpairWindow (reuse for each mouse click!)
	}



	@Override

	public void paint(Graphics g) // overrides paint() in JPanel!
	{
		getRootPane().setBackground(Color.black);
		System.out.println("window size:" + getWidth() + "," + getHeight());
		int windowWidth  = getWidth();  // call methods in JPanel to get the
		int windowHeight = getHeight(); // *CURRENT* size of the window!
		g.setColor(Color.yellow);
		g.drawLine(padding, windowHeight - padding, windowWidth - padding, windowHeight - padding); //x line
		g.drawLine(padding, padding, padding, windowHeight - padding); //y line
		int tickx = (windowWidth - 2*padding)/10;
		int startx = padding + tickx;
		for(int i = 0; i < 9; i++){
			g.drawLine(startx, windowHeight-padding+5, startx, windowHeight-padding-5);
			g.drawString(Double.toString(xValuesCopy[i]), startx-7, windowHeight-padding + 20);
			startx += tickx;
		}
		int ticky = (windowHeight-2*padding)/10;
		int starty = (windowHeight - padding) - ticky;
		double[] yscle = getYScaleValues(yValuesCopy);
		for(int i = 0; i < 9; i++){
			g.drawLine(padding -5, starty, padding + 5, starty);
			g.drawString(Double.toString(yscle[i]), 2, starty+5);
			starty-= ticky;
		}
		//begin drawing the points. 
		for(int i = 0; i < xValuesCopy.length; i++){
		g.drawOval(i*xValueToPixels() + padding, 200, 4, 4);
		}
	}

	// this method may be unnecessary...taken care of in mousePressed already
	public void findXYPoint(int xPixels, int xValueToPixelsConversionFactor, int yValueToPixelsConversionFactor){
		//Convert xPixelValue to xValue
		//Find yValue for corresponding xValue, use calculateForGraph()
		//Convert yValue to pixels?
		//Pop window

	}


	public double[] getYScaleValues(double[] yPlottingPoints){
		double yScaleValues[]=yPlottingPoints;
		// Find yMin and yMax
		double yMin = yPlottingPoints[0];
		double yMax = yPlottingPoints[0];
		for(int i=1; i<yPlottingPoints.length; i+=1){
			if(yMin>yPlottingPoints[i]){
				yMin = yPlottingPoints[i];
			}
			if(yMax<yPlottingPoints[i]){
				yMax = yPlottingPoints[i];
			}
		}
		//got both the min and the max
		System.out.println(Double.toString(yMin));
		System.out.println(Double.toString(yMax));	    
		// Find y-axis tic scale values
		int yNumTicks = 10;
		double yRange = yMax-yMin; //get range
		double yTickSize;
		if (yRange != 0){
			double yTickSizeUnrounded = yRange/(yNumTicks-1);
			double x = Math.ceil(Math.log10(yTickSizeUnrounded)-1);
			double pow10x = Math.pow(10, x);
			yTickSize = Math.ceil(yTickSizeUnrounded / pow10x) * pow10x;
		}
		else{
			yTickSize = yMax/10;
			yMin = 0;
		}
		System.out.println("This is tickSize: " + Double.toString(yTickSize));   
		for(int i=0; i<yNumTicks; i+=1){
			yScaleValues[i] = yMin+yTickSize*i;
			//System.out.println(Double.toString(yScaleValues[i]));
		}
		return yScaleValues;
	}

	public int xValueToPixels(){
		int xAxisLength = getWidth() - 2*padding;
		int xNumValuesToPrint = 10;
		xValueToPixelsConversionFactor = xAxisLength / (xNumValuesToPrint - 1);// = pixels to draw the next x scale value to the right
		return xValueToPixelsConversionFactor;
	}

	public int yValueToPixels(){	
		int yAxisLength = getHeight() - 2*padding;
		int yNumValuesToPrint = 10;
		yValueToPixelsConversionFactor = yAxisLength / (yNumValuesToPrint - 1);
		return yValueToPixelsConversionFactor;

	}

	public void mousePressed(MouseEvent me) // show tiny x,y values window
	{
		// xTextField and yTextField are in the mini displayXYpairWindow
		int xInPixels = me.getX();
		double xValue = xInPixels * xPixelsToValueConversionFactor;
		String xValueString = String.valueOf(xValue);
		xTextField.setText("X = " + xValueString);

		String yValueString = null;
		try {
			yValueString = calculator.calculateForGraph(expression,xValueString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		yTextField.setText("Y = " + yValueString);

		// show mini x,y display window
		displayXYpairWindow.setLocation(me.getX(), me.getY());
		displayXYpairWindow.setVisible(true); 
	}

	public void mouseReleased(MouseEvent me) // hide tiny window
	{
		// "erase" mini x,y display window	
		displayXYpairWindow.setVisible(false);
	}

	public void mouseClicked(MouseEvent me){} // take no action
	public void mouseEntered(MouseEvent me){} // on these
	public void mouseExited(MouseEvent  me){} // window events

}
