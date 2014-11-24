package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

// Class to call world grid if we include preview feature
public class StartPageGrid extends JPanel {

	JPanel wg;
	
	public StartPageGrid() {
		
		wg = new WorldGrid(10, 10);
		
		add(wg, BorderLayout.EAST);
	}
}