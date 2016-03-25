import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ExpressionCalculator.ExpressionCalculator;


public class GraphPanel extends JPanel implements MouseListener {
	
	private int xPixelsToValueConversionFactor;
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
    int windowWidth  = getWidth();  // call methods in JPanel to get the
    int windowHeight = getHeight(); // *CURRENT* size of the window!
    System.out.println(windowWidth + " and " + windowHeight);
    // 4 Calculate x and y pixels-to-value conversion factors (can't do in CTOR!) 	 
    // 5 Do ALL drawing here in paint() 
    // draw x and y scales and the expression graph here.
    //Use drawOval() to plot pairs of given x,y values as points
    //Use drawLine() to draw a straight line between successive graphed points to complete the graph

    }

// this method may be unnecessary...taken care of in mousePressed already
public void findXYPoint(int xPixels, int xValueToPixelsConversionFactor, int yValueToPixelsConversionFactor){
	//Convert xPixelValue to xValue
	//Find yValue for corresponding xValue, use calculateForGraph()
	//Convert yValue to pixels?
	//Pop window

}

public int xValueToPixels(){
	//xScreenWidth = getWidth() on JPanel to get width of screen in pixels
	//double xScreenWidth = GraphPanel.getWidth();
	//xAxisLength = screenSize - left and right margins
	//xNumValuesToPrint = size of xValue array
	//xValueToPixelsConversionFactor = xAxisLength / (xValuesToPrint - 1) = pixels to draw the next x scale value to the right
	//Values on x axis are the SAME as x points to plot
	int xValutToPixelsConversionFactor;
	//return xValueToPixelsConversionFactor;
}

public int yValuesToPixels(){	
	//Similar to xValueToPixelsConversionFactor
	//Use the range of provided y values and the length of yaxis to get this value then multiply each provided y value by the conversion factor to get its offset in pixels from the x axis
	//yScaleValues = values printed to y axis
	//yPlottingPoints = numbers we need to plot
	//Values on y axis are NOT the same as y points to plot
	//CalculateYScaleValues in lab directory
	int yValueToPixelsConversionFactor;
	//return yValueToPixelsConversionFactor;
	
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
