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
	public GraphPanel (double[] xValues, double[] yValues) throws IllegalArgumentException
    {
		
    // To-dos for this constructor method:
    // 1 call addMouseListener(this); to register this panel as the MouseListener
    // 2 Calculate Y scale values (and save them) 
    // 3 Build the mini displayXYpairWindow (reuse for each mouse click!)
    }

	

@Override

public void paint(Graphics g) // overrides paint() in JPanel!
    {
	getRootPane().setBackground(Color.black);
    int windowWidth  = getWidth();  // call methods in JPanel to get the
    int windowHeight = getHeight(); // *CURRENT* size of the window!
    g.setColor(Color.yellow);
    g.drawLine(25, windowHeight/2, windowWidth - 25, windowHeight/2);
    g.drawLine(windowWidth/2, 25, windowWidth/2, windowHeight - 25);
    int tickx = (windowWidth - 50)/10;
    int startx = 25 + tickx;
    for(int i = 0; i < 9; i++){
    	if(i == 4){
    		startx += tickx;
    		continue; //don't draw that hideous tickmark
    	}
    	g.drawLine(startx, windowHeight/2-5, startx, windowHeight/2+5);
    	startx += tickx;
    }
    int ticky = (windowHeight-50)/10;
    int starty = 25 + ticky;
    for(int i = 0; i < 9; i++){
    	if(i == 4){
    		starty += ticky;
    		continue; //don't draw that hideous tickmark
    	}
    	g.drawLine(windowWidth/2 -5, starty, windowWidth/2 + 5, starty);
    	starty+= ticky;
    }
    // expression = 2x; x-increment by 1
    /*double xPlottingPoints[]={-4, -3, -2, -1, 0, 1, 2, 3, 4}; // xScaleValues
    double yPlottingPoints[]={-8, -6, -4, -2, 0, 2, 4, 6, 8};
    double yScaleValues[]={};
    xValueToPixels(windowWidth);
    yValueToPixels(windowHeight);
    // Find yScale values
    double yMin = yPlottingPoints[0];
    double yMax = yPlottingPoints[yPlottingPoints.length-1];
    int yScale = (int)(yMin+yMax)/yPlottingPoints.length;
    for(int i=0; i<=yPlottingPoints.length; i++){
    	yScaleValues[i] = yMin+yScale*i;
    	System.out.println(Double.toString(yScaleValues[i]));
    }*/
    }

// this method may be unnecessary...taken care of in mousePressed already
public void findXYPoint(int xPixels, int xValueToPixelsConversionFactor, int yValueToPixelsConversionFactor){
	//Convert xPixelValue to xValue
	//Find yValue for corresponding xValue, use calculateForGraph()
	//Convert yValue to pixels?
	//Pop window

}

public int xValueToPixels(int width){
	int xAxisLength = width - 50;
	int xNumValuesToPrint = 10;
	xValueToPixelsConversionFactor = xAxisLength / (xNumValuesToPrint - 1);// = pixels to draw the next x scale value to the right
	return xValueToPixelsConversionFactor;
}

public int yValueToPixels(int height){	
	int yAxisLength = height - 50;
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
