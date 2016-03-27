import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* Isaiah Smoak, Rachel Williams, Meagan Raviele, Tim Crawford  
   ECE 309 Lab 10
*/

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
	}
	
	@Override
	public void paint(Graphics g) // overrides paint() in JPanel!
	{
		Point[] pointarray = new Point[xValuesCopy.length];
		getRootPane().setBackground(Color.black);
		
		// Call methods in JPanel to get the *CURRENT* size of the window!
		int windowWidth  = getWidth();  
		int windowHeight = getHeight(); 
		g.setColor(Color.yellow);
		
		// Draw X and Y axis lines
		g.drawLine(padding, windowHeight - padding, windowWidth - padding, windowHeight - padding); 
		g.drawLine(padding, padding, padding, windowHeight - padding); 
		
		int tickx = (windowWidth - 2*padding)/xValuesCopy.length;
		int startx = padding + tickx;
		
		for(int i = 0; i < xValuesCopy.length; i++){
			g.drawLine(startx, windowHeight-padding+5, startx, windowHeight-padding-5);
			g.drawString(Double.toString(xValuesCopy[i]), startx-7, windowHeight-padding + 20);
			startx += tickx;
		}
		
		int ticky = (windowHeight-2*padding)/xValuesCopy.length;
		int starty = (windowHeight - padding) - ticky;
		double[] yscle = getYScaleValues(yValuesCopy);

		// Get max and min values 
		double maxY = yscle[yscle.length-1]; 
		double miny = yscle[0]; 
		
		double pixTotal = (ticky*(xValuesCopy.length-1));
		double conversionFactor = pixTotal/(maxY-miny);
		
		for(int i = 0; i < xValuesCopy.length; i++){
			g.drawLine(padding -5, starty, padding + 5, starty);
			BigDecimal oop = new BigDecimal(yscle[i]);
			oop = oop.setScale(2, BigDecimal.ROUND_HALF_UP);
			g.drawString(Double.toString(oop.doubleValue()), 2, starty+5);
			starty-= ticky;
		}
		
		// Begin drawing the points. 
		int startposy = padding+ticky; 
		startposy = (windowHeight-padding)-2*ticky - ((xValuesCopy.length-1)*ticky);
		startposy = 44;
		
		for(int i = 0; i < xValuesCopy.length; i++){
			int yPoint= (int)(Math.abs(maxY-yValuesCopy[i])*conversionFactor +startposy);
			pointarray[i] = new Point(i*xValueToPixels() + padding+xValueToPixels(), yPoint);
			g.drawOval(pointarray[i].x, pointarray[i].y, 4, 4);
		}
		
		// Draw lines connecting the points.
		for(int i = 0; i < xValuesCopy.length-1; i++) g.drawLine(pointarray[i].x, pointarray[i].y+2, pointarray[i+1].x, pointarray[i+1].y+2);
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
		
		// Obtained min and max values, finding y-axis tic scale values
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
		  
		for(int i=0; i<yNumTicks; i+=1) yScaleValues[i] = yMin+yTickSize*i;

		return yScaleValues;
	}

	public int xValueToPixels(){
		int xAxisLength = getWidth() - 2*padding;
		int xNumValuesToPrint = xValuesCopy.length;
		xValueToPixelsConversionFactor = xAxisLength / (xNumValuesToPrint);// = pixels to draw the next x scale value to the right
		return xValueToPixelsConversionFactor;
	}
	
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
		int tickMark = (int)(xInPixels/xValueToPixels())-1;
		double xValue = xValuesCopy[tickMark]; 
		double yValue = 0;
		String xValueString = String.valueOf(xValue);
		xTextField.setText("X = " + xValueString);
		try {
			yValue = graphExpre.calculateForGraph(expression,xValue);
			String yValueString = String.valueOf(yValue);
			yTextField.setText("Y = " + yValueString);
		} catch (Exception e1) {
			System.out.println("exception");
			e1.printStackTrace();
		}

		// Show mini x,y display window 
		displayXYpairWindow.getContentPane().add(xyPanel, "Center");
		xyPanel.add(xTextField,"North");
		xyPanel.add(yTextField,"South");
		displayXYpairWindow.setSize(150, 75);
		displayXYpairWindow.setLocation(me.getXOnScreen(), me.getYOnScreen());
		displayXYpairWindow.setVisible(true); 
	}

	public void mouseReleased(MouseEvent me) // hide tiny window
	{
		// "Erase" mini x,y display window	
		displayXYpairWindow.setVisible(false);
	}

	public void mouseClicked(MouseEvent me){} // take no action
	public void mouseEntered(MouseEvent me){} // on these
	public void mouseExited(MouseEvent  me){} // window events

}
