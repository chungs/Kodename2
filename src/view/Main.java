package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.FlowLayout;

public class Main {
	
	static Window currentWindow;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		int x = 10;
		int y = 10;
		//MainWindow.createMainWindow(x, y);
		
		LoadSession.createLoadSessionPage();
	}
}
