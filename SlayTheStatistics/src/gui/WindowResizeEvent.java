package gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class WindowResizeEvent extends Main implements ComponentListener {
	
	@Override
	public void componentResized(ComponentEvent e) {
		super.setGrapHeigth();
		super.setGraphWidth();
		updateGraphs();

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
