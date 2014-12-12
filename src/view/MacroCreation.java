package view;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.CustomCode;

// Class that holds the individual parts of Macro Creation screen and puts it together.
// It includes panel for buttons to remove code, list of selected code, list of available
// code to add, and panel for creating name and saving.
public class MacroCreation extends JDialog {

	MacroName mN = new MacroName();
	MacroBasicLoop mBL = new MacroBasicLoop();
	PseudocodeList pL = new PseudocodeList(true);
	PseudocodeButtons pB = new PseudocodeButtons();

	public MacroCreation() {

		add(pB, BorderLayout.SOUTH);
		add(pL, BorderLayout.CENTER);
		add(mBL, BorderLayout.WEST);
		add(mN, BorderLayout.NORTH);

		// When user clicks Save Button
		mN.ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Make sure user enters a name for macro
				if (mN.tx.getText().trim().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "You left the Macro Name field blank.", "Macro must have a name.",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Make sure there is not already a macro with this name
				if (Util.cntrl.getMacroMap().containsKey(mN.tx.getText().trim())) {
					JOptionPane.showMessageDialog(null, "Macro name is in use.", "Macro name in use.",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (Util.getBodyMacro() == null) {
					JOptionPane.showMessageDialog(null, "The Macro does not have any code in it.",
							"Empty Macro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Make this dialog disappear
				setVisible(false);
				dispose();
				// Enable create macro button in Customs/CustomButtons panel
				CustomButtons.create.setEnabled(true);

				CustomCode cc = new CustomCode(mN.tx.getText().trim(), Util.getBodyMacro());

				Util.cntrl.getMacroMap().put(cc.getName(), cc);
				//System.out.println("SIZE: " + Util.cntrl.getMacroMap().size());

				// Save to list
				if (Customs.model.get(0).equals("No Custom Actions created")) {
					Customs.model.remove(0);
				}

				Customs.model.addElement(cc.getName());
				CustomButtons.delete.setEnabled(true);

				// Explicitly tell class that there is a switch between pseudocodes
				PseudocodeList.isMacro = false;

				return;
			}
		});

	}

}
