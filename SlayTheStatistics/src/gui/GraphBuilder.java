package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;

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
				new GraphBuilder(new int[] {0,1,2,3,34}, new int[] {0,1,2,3,40});
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
        f.add(new MyPanel(xData,yData, 1000	, 1000));
        f.pack();
        f.setVisible(true);
    }
}
class MyPanel extends JPanel {
	private int[] xData;
	private int[] yData;
	private int pHeigth;
	private int pWidth;
	private int DISTANCE_BORDER = 50;
	private int NUMBER_DISTANCE = 20;
	private int NUMBER_OF_AXIS_POINTS = 10;
	private int AXIS_STRIPE_LENGHT = 5;
	private int SIZE_OF_POINTS = 7;

    public MyPanel(int[] xData, int[] yData,int width,int height) {
    	this.xData =xData;
    	this.yData = yData;
    	this.pHeigth = height;
    	this.pWidth= width;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(pWidth,pHeigth);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraphGrid(g);
        drawGraphLine(g);
    }
    
    public void drawGraphLine(Graphics g) {
      	double xMax = max(xData);
    	double yMax = closestUpperTenVal(max(yData));
    	//get the amount of pixels per 1 number. 
    	double noToPixelX = (pWidth - 2*DISTANCE_BORDER) / xMax;
    	double noToPixelY = (pHeigth- 2*DISTANCE_BORDER) / yMax;
        for (int i = 0; i < xData.length-1; i++) {
        	g.setColor(Color.RED);
        	int x1 = (int) (xData[i]*noToPixelX + DISTANCE_BORDER);
        	int x2 = (int) (xData[i+1]*noToPixelX + DISTANCE_BORDER);
        	int y1 = (int) ((pHeigth - yData[i]*noToPixelY) - DISTANCE_BORDER);
        	int y2 = (int) ((pHeigth -yData[i+1]*noToPixelY) - DISTANCE_BORDER);
            g.drawLine(x1, y1, x2, y2);
            g.setColor(Color.BLACK);
            g.fillOval(x1 - SIZE_OF_POINTS/2, y1 - SIZE_OF_POINTS/2,SIZE_OF_POINTS, SIZE_OF_POINTS);
            g.fillOval(x2 - SIZE_OF_POINTS/2, y2 - SIZE_OF_POINTS/2,SIZE_OF_POINTS, SIZE_OF_POINTS);
        }
    	
    }
    
    public void drawGraphGrid(Graphics g) {
    	//basic x y axis
    	g.drawLine(DISTANCE_BORDER, pHeigth - DISTANCE_BORDER, pWidth - DISTANCE_BORDER, pHeigth - DISTANCE_BORDER);
    	g.drawLine(DISTANCE_BORDER, DISTANCE_BORDER, DISTANCE_BORDER, pHeigth - DISTANCE_BORDER);
    	//values on axis
    	int xLineLen = pWidth - 2* DISTANCE_BORDER;
    	int yLineLen = pHeigth - 2*DISTANCE_BORDER;
    	int xMax = max(xData);
    	int yMax = closestUpperTenVal(max(yData));
    	//draw the 0
    	g.drawString("0", DISTANCE_BORDER, pHeigth - DISTANCE_BORDER + NUMBER_DISTANCE );
    	g.drawString("0", DISTANCE_BORDER - NUMBER_DISTANCE, pHeigth - DISTANCE_BORDER );

    	int count = 1;
    	//x axis numbers
    	for (int i = xMax/NUMBER_OF_AXIS_POINTS; i < xMax; i += xMax/10) {
    		int xCoord = DISTANCE_BORDER + (xLineLen/NUMBER_OF_AXIS_POINTS)*count;
    		int yCoord = pHeigth - DISTANCE_BORDER;
    		drawCenteredString(xCoord, yCoord + NUMBER_DISTANCE, i+"",g);
    		g.drawLine(xCoord, yCoord + AXIS_STRIPE_LENGHT, xCoord, yCoord);
    		count ++;
    	}
    	//y axis numbers
    	count = 1;
    	for (int i = yMax/NUMBER_OF_AXIS_POINTS; i <= yMax; i += yMax/10) {
    		int xCoord = DISTANCE_BORDER;
    		int yCoord = pHeigth - DISTANCE_BORDER - (yLineLen/NUMBER_OF_AXIS_POINTS)*count;
    		drawCenteredString(xCoord- NUMBER_DISTANCE, yCoord, i+"", g);
    		g.drawLine(xCoord, yCoord, xCoord - AXIS_STRIPE_LENGHT, yCoord);
    		count++;
    	}
    }
    
    private void drawCenteredString(int xCoord, int yCoord, String text, Graphics g) {
    	int adjXCoord = xCoord - g.getFontMetrics().stringWidth(text)/ 2;
    	int adjYCoord = yCoord  + g.getFontMetrics().getHeight()/3;
    	g.drawString(text, adjXCoord, adjYCoord);
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
    
    private int closestUpperTenVal(int val) {
    	while (val % NUMBER_OF_AXIS_POINTS != 0) {
    		val += 1;
    	}
    	return val;
    }
}
