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
				new GraphBuilder(new int[] {1,2,30,44}, new int[] {1,24,3,4});
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
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel(xData,yData, 400, 300));
        f.pack();
        f.setVisible(true);
    }
}
class MyPanel extends JPanel {
	private int[] xData;
	private int[] yData;
	private int pHeigth;
	private int pWidth;
	private int DISTANCE_BORDER = 30;
	private int NUMBER_DISTANCE = 15;
	private int NUMBER_OF_AXIS_POINTS = 10;

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
        for (int i = 0; i < xData.length-1; i++) {
            g.drawLine(xData[i],yData[i],xData[i+1],yData[i+1]);
        }
    }
    
    public void drawGraphGrid(Graphics g) {
    	//basic x y axis
    	g.drawLine(DISTANCE_BORDER, pHeigth - DISTANCE_BORDER, pWidth - DISTANCE_BORDER, pHeigth - DISTANCE_BORDER);
    	g.drawLine(DISTANCE_BORDER, DISTANCE_BORDER, DISTANCE_BORDER, pHeigth - DISTANCE_BORDER);
    	//values on axis
    	int xLineLen = pWidth - 2* DISTANCE_BORDER;
    	int yLineLen = pHeigth - 2*DISTANCE_BORDER;
    	int xMax = closestUpperTenVal(max(xData));
    	int yMax = closestUpperTenVal(max(yData));
    	//draw the 0
    	g.drawString("0", DISTANCE_BORDER, pHeigth - DISTANCE_BORDER + NUMBER_DISTANCE );
    	g.drawString("0", DISTANCE_BORDER - NUMBER_DISTANCE, pHeigth - DISTANCE_BORDER );

    	int count = 1;
    	//x axis numbers
    	for (int i = xMax/NUMBER_OF_AXIS_POINTS; i <= xMax; i += xMax/10) {
    		g.drawString(i+ "", DISTANCE_BORDER + (xLineLen/NUMBER_OF_AXIS_POINTS)*count 
    				,pHeigth - DISTANCE_BORDER + NUMBER_DISTANCE);
    		count ++;
    	}
    	//y axis numbers
    	count = 1;
    	for (int i = yMax/NUMBER_OF_AXIS_POINTS; i <= yMax; i += yMax/10) {
    		g.drawString(i +"",DISTANCE_BORDER - NUMBER_DISTANCE,
    				pHeigth - DISTANCE_BORDER - (yLineLen/NUMBER_OF_AXIS_POINTS)*count);
    		count++;
    	}
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
