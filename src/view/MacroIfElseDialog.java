package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

import model.Code;
import model.Code.Proposition;
import model.IfElseCode;

public class MacroIfElseDialog extends JDialog{

	static int value = 0;
	JPanel basic;
	static String[] listofBooleans = {"NONE", "Facing East", "Facing West", "Facing North", "Facing South", "Next to a Beeper", "Front is Clear",
			"Right is Clear", "Left is Clear"};
	static JComboBox booleans;
	JButton done;
	JButton cancel;
	static JList if_list;
	static JList else_list;
	JLabel if_label;
	JLabel else_label;
	DefaultListModel if_model;
	DefaultListModel else_model;
	JScrollPane scrollbar1;
	JScrollPane scrollbar2;
	static String condition;
	static IfElseCode if_code_piece;
	boolean isempty = true;
	int list; //1 for else list, 0 for if list
	
	JButton to_if;
	JButton to_else;
	
	JButton remove;
	
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			
			if(source == done){
				if(booleans.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(null, "if condition cannot be none", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else{
					condition = (String)booleans.getSelectedItem();
					System.out.println(condition);
					
					
					Proposition p = Util.stringtoProposition(condition);
					ArrayList<Code> body1 = Util.getBody(if_list);
					ArrayList<Code> body2 = Util.getBody(else_list);
					
					((IfElseCode) if_code_piece).setCondition(p);
					((IfElseCode) if_code_piece).setBody1(body1);
					((IfElseCode) if_code_piece).setBody2(body2);
					
					System.out.println(isempty);
					
					if(isempty == true){
						MacroCreation.updateCustomActions(if_code_piece, Util.EditIndex);
					}
					else{
						Util.cntrl.getCodeList().remove(Util.EditIndex);
						Util.updateCodeList(Util.EditIndex, if_code_piece);
					}
					
					value = 1;
					
					dispose();
				}
				
				
			}
			else if(source == to_if){
				add(if_list, BasicActions.getElement());
				
			}
			else if(source == to_else){
				add(else_list, BasicActions.getElement());
			}
			else if(source == remove){
				if(list == 0){
					//if_model.remove(if_list.getSelectedIndex());
					remove(if_list,if_list.getSelectedIndex() );
				}
				else if (list == 1){
					//else_model.remove(else_list.getSelectedIndex());
					remove(else_list,else_list.getSelectedIndex() );
				}
				else{
					
				}
				
			}
			else{
				value = -1;
				dispose();
			}
			
			
		}
	}

	public MacroIfElseDialog(JFrame owner, Code c){
		super(owner, "Create If-Else statement", true);
		
		if(c == null){
			if_code_piece = new IfElseCode(null, null, null);
		}
		else{
			if_code_piece = (IfElseCode)c;
			isempty = false;
		}
		
		basic = new BasicActions();
		
		booleans = new JComboBox(listofBooleans);
		booleans.setSelectedIndex(0);
		
		
		done = new JButton("Done");
		done.addActionListener(new ButtonListener());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ButtonListener());
		to_if = new JButton("Add to if");
		to_if.addActionListener(new ButtonListener());
		to_else = new JButton("Add to else");
		to_else.addActionListener(new ButtonListener());
		remove = new JButton("Remove");
		remove.addActionListener(new ButtonListener());
		
		JLabel message = new JLabel("Pick a condition for your if statement:");
		if_label = new JLabel("If:");
		else_label = new JLabel("Else:");
		
		if_model = new DefaultListModel();
		else_model = new DefaultListModel();
		
		if(isempty){
			String[] empty = new String[1];
			empty[0] = "Empty";
		
			for(int i = 0; i < empty.length; i++){
				if_model.addElement(empty[i]);
				else_model.addElement(empty[i]);
			}
		}
		else{
			condition = Util.propositiontoString(if_code_piece.getCondition());
			System.out.println(condition);
			for(int i  = 0; i < listofBooleans.length; i ++){
				if(listofBooleans[i].trim().equalsIgnoreCase(condition)){
					booleans.setSelectedIndex(i);
					break;
				}
			}
			//booleans.setSelectedItem(condition);
			
			ArrayList<Code> Body = if_code_piece.getBody1();
			if(Body == null){
				String[] empty = new String[1];
				empty[0] = "Empty";
			
				for(int i = 0; i < empty.length; i++){
					if_model.addElement(empty[i]);
				}
			}
			else{
				for(int i = 0; i < Body.size(); i++){
					String t = Util.codetoString(Body.get(i));
					System.out.println(t + " if");
					if_model.addElement(t);
				}
			}
			Body = if_code_piece.getBody2();
			if(Body == null){
				String[] empty = new String[1];
				empty[0] = "Empty";
			
				for(int i = 0; i < empty.length; i++){
					else_model.addElement(empty[i]);
				}
			}
			else{
				for(int i = 0; i < Body.size(); i++){
					String t = Util.codetoString(Body.get(i));
					System.out.println(t + " else");
					else_model.addElement(t);
				}
			}
		}
		if_list = new JList(if_model);
		if_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if_list.setLayoutOrientation(JList.VERTICAL_WRAP);
		if_list.setFixedCellWidth(200);
		if_list.setEnabled(true);
		if_list.setVisible(true);
		if_list.setSelectedIndex(-1);
		if_list.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		    	  JList source = (JList)evt.getSource();
		    	  list = 0;
		      }
		});
		
		
		else_list = new JList(else_model);
		else_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		else_list.setLayoutOrientation(JList.VERTICAL_WRAP);
		else_list.setFixedCellWidth(200);
		else_list.setEnabled(true);
		else_list.setVisible(true);
		else_list.setSelectedIndex(-1);
		else_list.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		    	  JList source = (JList)evt.getSource();
		    	  list = 1;
		      }
		});
	
		
		
		scrollbar1 = new JScrollPane(if_list);
		scrollbar2 = new JScrollPane(else_list);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(booleans);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(if_label);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(scrollbar1);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(else_label);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(scrollbar2);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		

		JPanel bpanel = new JPanel();
		bpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		bpanel.add(cancel);
		bpanel.add(done);

		

		setLayout(new GridBagLayout());
		GridBagConstraints x = new GridBagConstraints();

		//right-most column
		x.anchor = GridBagConstraints.WEST;
		x.gridx = 0;
		x.gridy = 2;
		x.gridheight = 3;
		add(basic, x);

		//center column
		x.anchor = GridBagConstraints.CENTER;
		x.gridheight = 1;
		x.gridx = 1;
		x.gridy = 3;
		add(to_if, x);

		x.gridy = 4;
		add(remove, x);

		x.gridy = 5;
		add(to_else, x);

		//leftmost column
		x.anchor = GridBagConstraints.WEST;
		x.gridx = 2;
		x.gridy = 0;
		add(message, x);

		x.anchor = GridBagConstraints.WEST;
		x.gridx = 2;
		x.gridy = 1;
		add(booleans, x);

		x.gridy = 2;
		add(if_label, x);

		x.gridy = 3;
		//x.gridheight = 2;
		x.anchor = GridBagConstraints.NORTHWEST;
		add(scrollbar1, x);

		x.gridheight = 1;
		x.gridy = 4;
		x.anchor = GridBagConstraints.WEST;
		add(else_label, x);

		x.gridy = 5;
		//x.gridheight = 2;
		x.anchor = GridBagConstraints.NORTHWEST;
		add(scrollbar2, x);

		x.gridx = 2;
		x.gridy = 6;
		x.gridheight = 1;
		x.anchor = GridBagConstraints.EAST;
		add(bpanel, x);

	}
	
	public static void getMacroIfDialog(Code code){
		//JDialog c = new IfElseDialog(Main.currentWindow, code);
		JDialog c = new MacroIfElseDialog(null, code);
		c.pack();
		c.setLocationRelativeTo(null);
		c.setVisible(true);
		c.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		c.setModal(true);
	}
	
	public static void add(JList list, String data){
		
		if(data == null){
			JOptionPane.showMessageDialog(null, "You must select a basic action", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		System.out.println(data + " to some list");
		
		DefaultListModel model = (DefaultListModel)list.getModel();
		
		if(model.getSize() == 0 || model.getSize() == 1){
			if(((String)model.getElementAt(0)).equalsIgnoreCase("empty")){
				model.remove(0);
				model.addElement(data);
			}
			else{
				model.addElement(data);
			}
		}
		else{
			model.addElement(data);
		}
		
		list.setSelectedIndex(-1);
		return;
	}
	
	public static void remove(JList list, int index){
		DefaultListModel model = (DefaultListModel)list.getModel();
		
		if(index == -1){
			JOptionPane.showMessageDialog(null, "You must select from If or Else to remove", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(model.size() == 1){
			model.remove(0);
			model.addElement("Empty");
			
		}
		else{
			model.remove(index);
		}
		list.setSelectedIndex(-1);
		return;
	}
	
	public static int getValue(){
		return value;
	}
	
	
}
