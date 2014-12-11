package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.Controller;

public class LoadSession extends Window{

	JFrame mW;
	
	JButton newBut = new JButton("New Session");
	JButton loadBut = new JButton("Load Session");
	
	DefaultListModel dlmModel = new DefaultListModel();
	JList jlist;
	JScrollPane scroller;
	public String[] displayList = {"Temple Of Doom", "40.31.21, 74.27.46", "Batman's Bat Cave", "Sinatra's Lake House", "Ben's Kitchen"};
	ArrayList<String> sessionNames = new ArrayList<String>();
	
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	
	public LoadSession() {
		super("Load Session");
		
		jlist = new JList(displayList);
		jlist.setModel(dlmModel);
		
		initiate();
		
		for (int i = 0; i < sessionNames.size(); i++) {
			dlmModel.addElement("     " + sessionNames.get(i) + "     ");
		}
		jlist.setSelectedIndex(0);			
		jlist.setVisibleRowCount(10);
		
		setLayout(new GridLayout());
		
		leftPanel.add(jlist);
		rightPanel.add(newBut);
		rightPanel.add(loadBut);
		
		add(leftPanel);
		add(rightPanel);
		
		newBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage.createStartPage();
				dispose();
			}
		});
		
		loadBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(jlist.getSelectedIndex() == -1){
					return;
				}
				
				String filename = (String)dlmModel.get(jlist.getSelectedIndex());
				File f = new File(filename);
				Util.cntrl = Controller.load(f);
				
				if(Util.cntrl != null){
					Main.currentWindow.dispose();
					mW = new MainWindow(Util.cntrl.getWorld().getXSize(), Util.cntrl.getWorld().getYSize()/*, cntrl*/);
					mW.setVisible(true);
					mW.setSize(1000,600);
					mW.setLocationRelativeTo(null);
					Util.drawWorld(null, null);
					dispose();
				}
			}
		});
		
	}
	
	public static void createLoadSessionPage() {
		
		JFrame frame = new LoadSession();
		//frame = new StartPage();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setTitle("Start Page World Selection");
		frame.setSize(600,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	private void initiate()
	{
		String dataFolder = null;
		File[] files;
		File currentDirectory = new File(new File(".").getAbsolutePath());
		
		
		try {
			dataFolder = currentDirectory.getCanonicalPath() + File.separator + "data";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File folder = new File(dataFolder);

		files = folder.listFiles();
		
		if(files == null){
			return;
		}
		
		sessionNames = new ArrayList<String>();
		
		for(int i = 0; i < files.length; i++)
		{
			if(files[i].isFile())
			{
				sessionNames.add(stripExtension(files[i]));
			}
		}
	}
	
	private String stripExtension(File file)
	{
		if(file == null) return null;
		
		String name = file.getName();
		
		int pos = name.indexOf(".");
		
		return name.substring(0, pos);
	}

}