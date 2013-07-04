package org.tyler.powertrainer.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.tyler.powertrainer.nodes.Droping;
import org.tyler.powertrainer.nodes.Training;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1842465602144504268L;
	private JButton startButton;
	private JTextField idsTextField;
	private JComboBox skillComboBox;
	private JCheckBox prioritiseBox;
	
	public Gui() {
		super("TPowerTrainer");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(245, 140);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new FlowLayout());

		JLabel infoLabel = new JLabel("Enter rock id (separate with  \",\") : ");
		add(infoLabel);

		idsTextField = new JTextField("                ");
		idsTextField.setToolTipText(" separate ids with a with \",\"");
		add(idsTextField);

		JLabel prioritiseLabel = new JLabel("Check to prioritise : ");
		add(prioritiseLabel);

		prioritiseBox = new JCheckBox();
		prioritiseBox.setToolTipText(" not needed if you using 1 object");
		add(prioritiseBox);

		JLabel chooseSkillLabel = new JLabel("Select the skill :  ");
		add(chooseSkillLabel);

		final String[] skillsString = {"Mining", "WoodCutting"};
		skillComboBox = new JComboBox(skillsString);
		skillComboBox.setToolTipText("Put the skill you want to power traine");
		add(skillComboBox);

		startButton = new JButton("Start Script");
		add(startButton);
		setVisible(true);
		startButton.addActionListener(new EventHandler());

	}
	public class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(startButton)) {
				final String idsText = idsTextField.getText().replaceAll("\\s", "");

				if (idsText.contains(",")) {
					final String[] stringIds = idsText.split(",");
					int[] ids = new int[stringIds.length];
					for (int y = 0; y < stringIds.length; y++) {
						ids[y] = Integer.parseInt(stringIds[y]);
					}
					Training.objectIds = ids;
				} else {
					Training.objectIds = new int[1];
					Training.objectIds[0] = Integer.parseInt(idsText);
				}
				Training.actionName = skillComboBox.getSelectedItem().toString().contains("Mining") ? "Mine" : "Chop down";
				Droping.toolName=skillComboBox.getSelectedItem().toString().contains("Mining") ? "pickaxe" : "hatchet";
				Training.prioritise=prioritiseBox.isSelected();
				setVisible(false);
			}
		}
	}
}
