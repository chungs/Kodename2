package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.Code;
import model.LoopCode;

public class LoopDialog extends JDialog{
	
	JPanel basic;
	String[] no_of_iterations = {"0", "1", "2", "3", "4"};
	JComboBox iterations;
	JButton done;
	JButton cancel;
	JButton add;
	static JList for_list;
	JLabel for_label;
	JLabel end_label;
	static DefaultListModel for_model;
	JScrollPane scrollbar;
	int count;
	LoopCode loop_code_piece;
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JButton source = (JButton)e.getSource();
			
			if(source == done){
				if(iterations.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(null, "repeat count cannot be 0", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else{
					//String s = ((String)iterations.getSelectedItem()).trim();
					int count = iterations.getSelectedIndex();
					System.out.println("count: " + count);
					
					ArrayList<Code> body = Util.getBody(for_list);
					
					((LoopCode) loop_code_piece).setCounter(count);
					((LoopCode) loop_code_piece).setBody(body);
					
					Util.updateCodeList(loop_code_piece);
					
					dispose();
				}
			
			}
			else if(source == add){
				add(BasicActions.getElement());
			}
			else{
				dispose();
			}
		}
	}
	
	public LoopDialog(JFrame owner){
		super(owner, "Create Loop statement", true);
		
		loop_code_piece = new LoopCode(0, null);
		
		basic = new BasicActions();
		
		iterations = new JComboBox(no_of_iterations);
		iterations.setSelectedIndex(0);
		
		done = new JButton("Done");
		done.addActionListener(new ButtonListener());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ButtonListener());
		add = new JButton("Add");
		add.addActionListener(new ButtonListener());
		
		JLabel message = new JLabel("How many times do you want to repeat the loop:");
		for_label = new JLabel("For:");
		end_label = new JLabel("End For");
		
		for_model = new DefaultListModel();
		
		String[] empty = new String[1];
		empty[0] = "Empty";
		
		for(int i = 0; i < empty.length; i++){
			for_model.addElement(empty[i]);
		}
		
		for_list = new JList(for_model);
		for_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for_list.setLayoutOrientation(JList.VERTICAL_WRAP);
		for_list.setFixedCellWidth(200);
		for_list.setEnabled(true);
		for_list.setVisible(true);
		for_list.setSelectedIndex(-1);
		
		scrollbar = new JScrollPane(for_list);
		
		JPanel bpanel = new JPanel();
		bpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		bpanel.add(cancel);
		bpanel.add(done);
		
		setLayout(new GridBagLayout());
		GridBagConstraints x = new GridBagConstraints();
		
		x.anchor = GridBagConstraints.WEST;
		x.gridx = 0;
		x.gridy = 1;
		x.gridheight = 5;
		add(basic, x);
		
		x.gridx = 1;
		x.gridy = 3;
		x.anchor = GridBagConstraints.CENTER;
		x.gridheight = 1;
		add(add, x);
		
		x.anchor = GridBagConstraints.WEST;
		x.gridx = 2;
		x.gridy = 0;
		add(message, x);
		
		x.gridy = 1;
		add(iterations, x);
		
		x.gridy = 2;
		add(for_label, x);
		
		x.gridy = 3;
		x.gridheight = 2;
		x.anchor = GridBagConstraints.NORTHWEST;
		add(for_list, x);
		
		x.gridy = 5;
		x.gridheight = 1;
		add(end_label, x);
		
		x.gridx = 2;
		x.gridy = 6;
		x.anchor = GridBagConstraints.EAST;
		add(bpanel, x);
		
		
	}
	
	public static void getForDialog(){
		JDialog c = new LoopDialog(Main.currentWindow);
		c.pack();
		c.setLocationRelativeTo(null);
		c.setVisible(true);
		c.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	public static void add(String data){
		System.out.println(data + " to some list");
		//DefaultListModel model = (DefaultListModel)for_list.getModel();
		
		if(for_model.getSize() == 0 || for_model.getSize() == 1){
			if(((String)for_model.getElementAt(0)).equalsIgnoreCase("empty")){
				for_model.remove(0);
				for_model.addElement(data);
			}
			else{
				for_model.addElement(data);
			}
		}
		else{
			for_model.addElement(data);
		}
		
		for_list.setSelectedIndex(-1);
	}
	
	
}
