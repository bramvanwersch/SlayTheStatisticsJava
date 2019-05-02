package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.geom.AffineTransform;

public class GraphBuilder{

	private JPanel contentPane;
	private int[] xData;
	private int[] yData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GraphBuilder(new int[] {0,1,2,16,5000}, new int[] {0,1,24,3,6002});
				}
			});
	}

	public GraphBuilder(int[] xData, int[] yData) {
		this.xData = xData;
		this.yData= yData;
		createNewGui();
	}

	protected void createNewGui() {
		SwingUtilities.isEventDispatchThread();
        JFrame f = new JFrame("Graph");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel(xData,yData, 300, 200, new String[] {"Floor", "Gold"}));
        f.pack();
        f.setVisible(true);
    }
}
class MyPanel extends JPanel {
	private int[] xData;
	private int[] yData;
	private int pHeigth;
	private int pWidth;
	//distance from side to y or x axis
	private int DISTANCE_BORDER;
	//distance from graph to end of pane
	private int DISTANCE_END = 10;
	//min distance between axis points.
	private int MIN_AXIS_NUMBER_DISTANCE = 20;
	//maximum number of axis points 
	private int NUMBER_OF_AXIS_POINTS_X;
	private int NUMBER_OF_AXIS_POINTS_Y;
	//distance of numbers from x and y axis
	private int NUMBER_DISTANCE = 20;
	//length of stripe extending trough x or y axis.
	private int AXIS_STRIPE_LENGHT = 5;
	//size of data orbs
	private int SIZE_OF_POINTS = 7;
	//size of x axis.
	private int SIZE_X_AXIS;
	//size of y axis
	private int SIZE_Y_AXIS;
	private String[] axisNames;
	private int AXIS_FONT_SIZE = 12;


    public MyPanel(int[] xData, int[] yData,int width,int height,String[] axisNames) {
    	this.xData =xData;
    	this.yData = yData;
    	this.pHeigth = height;
    	this.pWidth= width;
    	this.axisNames = axisNames;
    	this.DISTANCE_BORDER = (int) (pHeigth *0.05 +55);
    	this.SIZE_X_AXIS = pWidth - DISTANCE_BORDER - DISTANCE_END;
    	this.SIZE_Y_AXIS = pHeigth - DISTANCE_BORDER -DISTANCE_END;
    	this.NUMBER_OF_AXIS_POINTS_X = getNrOfAxisPoints(SIZE_X_AXIS);
    	this.NUMBER_OF_AXIS_POINTS_Y = getNrOfAxisPoints(SIZE_Y_AXIS);
    }

    public Dimension getPreferredSize() {
        return new Dimension(pWidth,pHeigth);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraphGrid(g);
        drawGraphLine(g);
        drawAxisNames(g);
    }
    
    public int getNrOfAxisPoints(int axisSize) {
    	double nrOfPoints = 100;
    	while (axisSize / nrOfPoints < MIN_AXIS_NUMBER_DISTANCE) {
    		nrOfPoints -= 1;
    	}
    	return (int) nrOfPoints;
    }
    
    public void drawGraphLine(Graphics g) {
    	//get the amount of pixels per 1 number. 
        for (int i = 0; i < xData.length-1; i++) {
        	g.setColor(Color.RED);
        	int x1 = (int) (xData[i]*nrToPixelX() + DISTANCE_BORDER);
        	int x2 = (int) (xData[i+1]*nrToPixelX() + DISTANCE_BORDER);
        	int y1 = (int) ((pHeigth - yData[i]*nrToPixelY()) - DISTANCE_BORDER);
        	int y2 = (int) ((pHeigth -yData[i+1]*nrToPixelY()) - DISTANCE_BORDER);
            g.drawLine(x1, y1, x2, y2);
            g.setColor(Color.BLACK);
            g.fillOval(x1 - SIZE_OF_POINTS/2, y1 - SIZE_OF_POINTS/2,SIZE_OF_POINTS, SIZE_OF_POINTS);
            g.fillOval(x2 - SIZE_OF_POINTS/2, y2 - SIZE_OF_POINTS/2,SIZE_OF_POINTS, SIZE_OF_POINTS);
        }
    }
    
    public void drawGraphGrid(Graphics g) {
    	//basic x y axis
    	g.drawLine(DISTANCE_BORDER, pHeigth - DISTANCE_BORDER, pWidth - DISTANCE_END, pHeigth - DISTANCE_BORDER);
    	g.drawLine(DISTANCE_BORDER, DISTANCE_END, DISTANCE_BORDER, pHeigth - DISTANCE_BORDER);
    	//values on axis
    	int xMax = max(xData);
    	int yMax = max(yData);
    	//draw the 0
    	drawCenteredString(DISTANCE_BORDER, pHeigth - DISTANCE_BORDER + NUMBER_DISTANCE,"0", g);
    	drawCenteredString(DISTANCE_BORDER - NUMBER_DISTANCE, pHeigth - DISTANCE_BORDER, "0", g);
    	
    	int stepSizeX = graphStepSize(xMax, NUMBER_OF_AXIS_POINTS_X)[0];
    	int stepSizeY = graphStepSize(yMax, NUMBER_OF_AXIS_POINTS_Y)[0];
    	int count = 1;
    	//x axis numbers
    	for (int i = stepSizeX; i <= xMax; i += stepSizeX) {
    		double xCoord = DISTANCE_BORDER + nrToPixelX()*stepSizeX*count;
    		int yCoord = pHeigth - DISTANCE_BORDER;
    		String nrText = i +"";
    		//make numbers to 10 power above 1000.
    		if (i >= 1000) {
    			nrText = getPowerTenNumber(nrText); 
    		}
    		//slope numbers if they get to long.
    		if (g.getFontMetrics().stringWidth(nrText) > MIN_AXIS_NUMBER_DISTANCE) {
    			drawCenteredSlopedString((int) xCoord, yCoord + NUMBER_DISTANCE, nrText,g);
    		}
    		else {
    			drawCenteredString((int) xCoord, yCoord + NUMBER_DISTANCE, nrText,g);
    		}
    		g.drawLine((int) xCoord, yCoord - SIZE_Y_AXIS, (int) xCoord, yCoord + AXIS_STRIPE_LENGHT);
    		count ++;
    	}
    	//y axis numbers
    	count = 1;
    	for (int i= stepSizeY; i <= graphStepSize(yMax, NUMBER_OF_AXIS_POINTS_Y)[1]; i += stepSizeY ) {
    		int xCoord = DISTANCE_BORDER;
    		double yCoord =  pHeigth - DISTANCE_BORDER - nrToPixelY()*stepSizeY*count;
    		String nrText = i +"";
    		if (i >= 1000) {
    			nrText = getPowerTenNumber(nrText); 
    		}
    		drawCenteredString(xCoord- NUMBER_DISTANCE, (int) yCoord, nrText, g);
    		g.drawLine(xCoord- AXIS_STRIPE_LENGHT, (int) yCoord, xCoord + SIZE_X_AXIS,(int) yCoord);
    		count++;
    	}
    }

	private void drawAxisNames(Graphics g) {
    	Font axisFont=new Font("Ariel", Font.BOLD, AXIS_FONT_SIZE );
    	g.setFont(axisFont);
    	//drawing x axis name
    	double xCoord1 = (SIZE_X_AXIS/ 2  + DISTANCE_BORDER);
    	double yCoord1 = (2*pHeigth - DISTANCE_BORDER + NUMBER_DISTANCE)/2;
    	drawCenteredString((int) xCoord1, (int) yCoord1 ,this.axisNames[0], g);
    	Graphics2D g2d = (Graphics2D) g;
    	// drawing y axis name
    	double xCoord2 = (DISTANCE_BORDER - NUMBER_DISTANCE - 10)/2;
    	double yCoord2 = (SIZE_Y_AXIS/ 2 + DISTANCE_END);
    	g2d.translate(xCoord2, yCoord2);
        g2d.rotate(Math.toRadians(-90));
        drawCenteredString(0,0,this.axisNames[1], g2d);
        g2d.translate(-xCoord2,-yCoord2);
        g2d.rotate(Math.toRadians(90));
    }
	
	private void drawCenteredString(int xCoord, int yCoord, String text, Graphics g) {
    	int adjXCoord = xCoord - g.getFontMetrics().stringWidth(text)/ 2;
    	int adjYCoord = yCoord  + g.getFontMetrics().getHeight()/3;
    	g.drawString(text, adjXCoord, adjYCoord);
	}
    
    private void drawCenteredSlopedString(int xCoord, int yCoord, String text, Graphics g) {
    	int adjXCoord = xCoord - g.getFontMetrics().stringWidth(text)/ 2;
    	int adjYCoord = yCoord  + g.getFontMetrics().getHeight()/3;
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.translate(xCoord, yCoord);
        g2d.rotate(Math.toRadians(-45));
    	g2d.drawString(text, adjXCoord, adjYCoord);
        g2d.translate(-xCoord,-yCoord);
        g2d.rotate(Math.toRadians(45));
    }
    
    private int max(int[] data) {
    	int maxVal = 0;
    	for (int n: data) {
    		if (n > maxVal) {
    			maxVal = n;
    		}
    	}
    	return maxVal;
    }
    
    private String getPowerTenNumber(String nr) {
		//return a string that depicts a nr in a ten power format.
    	char[] nrArray = nr.toCharArray();
    	String returnNr = nrArray[0] + "." + nrArray[1] + "E" + (nrArray.length-1);
		return returnNr;
	}
    
    //calculating the pixels per whole number on a scale.
    private double nrToPixelX() {
    	return (SIZE_X_AXIS) / new Double(max(xData));
    }
    
    private double nrToPixelY() {
    	return (SIZE_Y_AXIS) / new Double(graphStepSize(max(yData), NUMBER_OF_AXIS_POINTS_Y)[1]);
    }
    
    private int[] graphStepSize(int val, int axisPoints) {
    	String sVal = val+"";
    	int groundDiv = (int) Math.pow(10, sVal.length() -2);
    	if (groundDiv == 0){ groundDiv = 1;}
    	while (val % groundDiv != 0) {
    		val += 1;
    	}
    	int stepSize = 1;
    	int count = 0;
    	while(val / stepSize > axisPoints) {
    		if (count == 0) { 
    			stepSize *= 5;
    			count ++;
    		}else {
    			stepSize *= 2;
    			count = 0;}
    	}
    	return new int [] {stepSize, val};
    }
}
