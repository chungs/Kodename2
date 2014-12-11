package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.Controller;

import model.World;
import model.World.Contents;

public class CustomWorld extends JDialog{
	
	JFrame mW;
	
	public CustomWorld() {
		
		super();
		
		setLayout(new FlowLayout());
		
		final JTextField xCoorTF = new JTextField(3);
		final JTextField yCoorTF = new JTextField(3);

		JButton addBut = new JButton("Add element");
		JButton doneBut = new JButton("Take Me To My World");
		JButton cancelBut = new JButton("Cancel");
		
		String[] elementArr = {"Beeper", "Wall"};
		final JComboBox elementBox = new JComboBox(elementArr);
		
		JLabel xCoorL = new JLabel("X Coordinate: ");
		JLabel yCoorL = new JLabel("Y Coordinate: ");
		
		final StartPageGrid gd = new StartPageGrid();

		add(xCoorL);
		add(xCoorTF);
		add(yCoorL);
		add(yCoorTF);
		add(doneBut);
		add(cancelBut);
		add(elementBox);
		add(addBut);
		add(gd);
		
		final World w = new World(6, 6);
		
		addBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = Integer.parseInt(xCoorTF.getText());
				int y = Integer.parseInt(yCoorTF.getText());
				
				String el = (String)elementBox.getItemAt(elementBox.getSelectedIndex());
				
				if(el.equals("Beeper")){
					w.setContents(x, y, Contents.BEEPER);
				}else{
					w.setContents(x, y, Contents.WALL);
				}
				
				Util.cntrl = new Controller(w);
				gd.renderWorld(w);
				
			}
		});
		
		doneBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Main.currentWindow.dispose();
					mW = new MainWindow(w.getXSize(), w.getYSize()/*, cntrl*/);
					mW.setVisible(true);
					mW.setSize(1000,600);
					mW.setLocationRelativeTo(null);
					Util.drawWorld(null, null);
				}

		});
		
		cancelBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				}

		});

	}
}