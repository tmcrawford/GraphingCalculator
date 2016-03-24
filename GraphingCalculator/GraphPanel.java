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
	private ExpressionCalculator calculator;
	private JTextField yTextField;
	private JFrame displayXYpairWindow;
	public GraphPanel (double[] xValues, double[] yValues) throws IllegalArgumentException
    {
    // To-dos for this constructor method:
    // 1 call addMouseListener(this); to register this panel as the MouseListener
    // 2 Calculate Y scale values (and save them) 
    // 3 Build the mini displayXYpairWindow (reuse for each mouse click!)
    }

	
	//========================isExpression()=================================
	// Description: Determines if an expression exists to be handled returns T/F (boolean)
	
@Override
public void paint(Graphics g) // overrides paint() in JPanel!
    {
    int windowWidth  = getWidth();  // call methods in JPanel to get the
    int windowHeight = getHeight(); // *CURRENT* size of the window!
    // 4 Calculate x and y pixels-to-value conversion factors (can't do in CTOR!) 	 
    // 5 Do ALL drawing here in paint() 
    // draw x and y scales and the expression graph here.
    //Use drawOval() to plot pairs of given x,y values as points
    //Use drawLine() to draw a straight line between successive graphed points to complete the graph

    }

public void findXYPoint(int xPixels, int xValueToPixelsConversionFactor, int yValueToPixelsConversionFactor){
	//Convert xPixelValue to xValue
	//Find yValue for corresponding xValue, use calculateForGraph()
	//Convert yValue to pixels?
	//Pop window

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
		yValueString = calculator.calculateExpression(expression,xValueString);
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
