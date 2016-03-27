import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.java2d.loops.DrawLine;

//Isaiah Smoak, Rachel Williams, Meagan Raviele, Tim Crawford
//ECE 309 Lab 10

public class GraphPanel extends JPanel implements MouseListener {
	
	private int xValueToPixelsConversionFactor;
	private int yValueToPixelsConversionFactor;
	private String expression;
	private JTextField xTextField = new JTextField();
	private JTextField yTextField = new JTextField();
	private JFrame displayXYpairWindow = new JFrame("XY Pair");
	private JPanel xyPanel  = new JPanel();
	
	private double[] xValuesCopy;
	private double[] yValuesCopy;
	private GraphingCalculator graphExpre;
	private final int padding = 35; //this is padding for all borders
	public GraphPanel (double[] xValues, double[] yValues, GraphingCalculator graphExpress, String enteredExpression) throws IllegalArgumentException
	{
		graphExpre = graphExpress;
		xValuesCopy = xValues;
		yValuesCopy = yValues;
		expression = enteredExpression;
		addMouseListener(this);
		
		// To-dos for this constructor method:
		// 1 call addMouseListener(this); to register this panel as the MouseListener
		// 2 Calculate Y scale values (and save them) 
		// 3 Build the mini displayXYpairWindow (reuse for each mouse click!)
	}



	@Override

	public void paint(Graphics g) // overrides paint() in JPanel!
	{
		Point[] pointarray = new Point[xValuesCopy.length];
		getRootPane().setBackground(Color.black);
		System.out.println("===================================");
		System.out.println("window size:" + getWidth() + "," + getHeight());
		int windowWidth  = getWidth();  // call methods in JPanel to get the
		int windowHeight = getHeight(); // *CURRENT* size of the window!
		g.setColor(Color.yellow);
		g.drawLine(padding, windowHeight - padding, windowWidth - padding, windowHeight - padding); //x line
		g.drawLine(padding, padding, padding, windowHeight - padding); //y line
		int tickx = (windowWidth - 2*padding)/xValuesCopy.length;
		int startx = padding + tickx;
		for(int i = 0; i < xValuesCopy.length; i++){
			g.drawLine(startx, windowHeight-padding+5, startx, windowHeight-padding-5);
			g.drawString(Double.toString(xValuesCopy[i]), startx-7, windowHeight-padding + 20);
			startx += tickx;
		}
		int ticky = (windowHeight-2*padding)/xValuesCopy.length;
		System.out.println("This is ticky:" + ticky);
		int starty = (windowHeight - padding) - ticky;
		
		double[] yscle = getYScaleValues(yValuesCopy);
		for(int i = 0; i < yscle.length; i++){
			System.out.println(yscle[i]);
		}
		double maxY = yscle[yscle.length-1]; //get upmost value
		double miny = yscle[0]; //get least value
		double pixTotal = (ticky*(xValuesCopy.length-1));
		double conversionFactor = pixTotal/(maxY-miny);
		for(int i = 0; i < xValuesCopy.length; i++){
			g.drawLine(padding -5, starty, padding + 5, starty);
			//System.out.println("at this " + i + " you get " + starty);
			g.drawString(Double.toString(yscle[i]), 2, starty+5);
			starty-= ticky;
		}
		//begin drawing the points. 
		//44 <= how to get?
		//ticky is 10??? 
		int startposy = padding+ticky; //where it begins, temp!
		System.out.println(ticky + "this is ticky!");
		startposy = (windowHeight-padding)-2*ticky - ((xValuesCopy.length-1)*ticky);
		startposy = 44;
		for(int i = 0; i < xValuesCopy.length; i++){
			System.out.println("maxY: " + maxY + "yValue: " + yValuesCopy[i]);
			int yPoint= (int)(Math.abs(maxY-yValuesCopy[i])*conversionFactor +startposy);
			pointarray[i] = new Point(i*xValueToPixels() + padding+xValueToPixels(), yPoint);
			//g.drawOval(pointarray[i].x, pointarray[i].y, 4, 4);
			g.drawOval(pointarray[i].x, pointarray[i].y, 4, 4);

		}
		//draw lines connecting the points.
		for(int i = 0; i < xValuesCopy.length-1; i++)
			g.drawLine(pointarray[i].x, pointarray[i].y+2, pointarray[i+1].x, pointarray[i+1].y+2);
			//g.drawLine(i*xValueToPixels() + padding+xValueToPixels(), windowHeight-padding-20-(int)yValuesCopy[i]*yValueToPixels(), (i+1)*xValueToPixels() + padding+xValueToPixels(), windowHeight-padding-20-(int)yValuesCopy[i+1]*yValueToPixels());

	}

	// this method may be unnecessary...taken care of in mousePressed already
	public void findXYPoint(int xPixels, int xValueToPixelsConversionFactor, int yValueToPixelsConversionFactor){
		//Convert xPixelValue to xValue
		//Find yValue for corresponding xValue, use calculateForGraph()
		//Convert yValue to pixels?
		//Pop window

	}


	public double[] getYScaleValues(double[] yPlottingPoints){
		double yScaleValues[]= yPlottingPoints.clone();
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
		int yNumTicks = yValuesCopy.length;
		double yRange = yMax-yMin; //get range
		double yTickSize;
		if (yRange != 0){
			double yTickSizeUnrounded = yRange/(yNumTicks-1);
			double x = Math.ceil(Math.log10(yTickSizeUnrounded)-1);
			double pow10x = Math.pow(10, x);
			yTickSize = Math.ceil(yTickSizeUnrounded / pow10x) * pow10x;
		}
		else{
			yTickSize = yMax/yValuesCopy.length;
			yMin = yMax/2;
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
		int xNumValuesToPrint = xValuesCopy.length;
		xValueToPixelsConversionFactor = xAxisLength / (xNumValuesToPrint);// = pixels to draw the next x scale value to the right
		return xValueToPixelsConversionFactor;
	}
//this one needs work
	public int yValueToPixels(){	
		int yAxisLength = getHeight() - 2*padding;
		int yNumValuesToPrint = yValuesCopy.length;
		yValueToPixelsConversionFactor = yAxisLength / (yNumValuesToPrint );
		return yValueToPixelsConversionFactor;

	}

	public void mousePressed(MouseEvent me) // show tiny x,y values window
	{
		if(me.getX()>getWidth()-padding)return;
		if(me.getY()>getHeight()-padding)return;
		int xInPixels = me.getX();
		System.out.println("clicked ("+me.getX()+","+me.getY()+")");
		//doesn't account for padding???
		int tickMark = (int)(xInPixels/xValueToPixels())-1;
		System.out.println("tick mark:"+tickMark);
		double xValue = xValuesCopy[tickMark]; 
		double yValue = 0;
		String xValueString = String.valueOf(xValue);
		System.out.println("clicked x="+xValueString);
		xTextField.setText("X = " + xValueString);
		try {
			yValue = graphExpre.calculateForGraph(expression,xValue);
			String yValueString = String.valueOf(yValue);
			System.out.println("clicked y="+yValueString);
			yTextField.setText("Y = " + yValueString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("exception");
			e1.printStackTrace();
		}

		// show mini x,y display window //FIX
		displayXYpairWindow.getContentPane().add(xyPanel, "Center");
		xyPanel.add(xTextField,"North");
		xyPanel.add(yTextField,"South");
		displayXYpairWindow.setSize(150, 75);
		displayXYpairWindow.setLocation(me.getXOnScreen(), me.getYOnScreen());
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
