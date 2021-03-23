package Dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.application.Platform;

import javax.swing.*;

public class View extends JFrame {

	JPanel basePanel = new JPanel(new GridLayout(5, 1));
	JPanel panel1 = new JPanel(new GridLayout(2, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JPanel panel3 = new JPanel(new GridLayout(1, 3));
	JPanel panel4 = new JPanel(new GridLayout(1, 2));
	JPanel panel5 = new JPanel(new GridLayout(1, 2));

	JPanel panel11 = new JPanel(new FlowLayout());
	JPanel panel12 = new JPanel(new FlowLayout());
	JPanel panel13 = new JPanel(new FlowLayout());
	JPanel panel14 = new JPanel(new FlowLayout());

	JPanel panel21 = new JPanel(new FlowLayout());
	JPanel panel22 = new JPanel(new GridLayout(4, 1));
	JPanel panel221 = new JPanel(new FlowLayout());
	JPanel panel222 = new JPanel(new FlowLayout());
	JPanel panel223 = new JPanel(new FlowLayout());
	JPanel panel224 = new JPanel(new FlowLayout());

	JPanel panel31 = new JPanel(new FlowLayout());
	JPanel panel32 = new JPanel(new GridLayout(3, 1));
	JPanel panel33 = new JPanel(new FlowLayout());

	JPanel panel41 = new JPanel(new GridLayout(3, 1));
	JPanel panel42 = new JPanel(new GridLayout(3, 1));

	JPanel panel51 = new JPanel(new FlowLayout());
	JPanel panel52 = new JPanel(new FlowLayout());

	JLabel campusSizeLabel = new JLabel("Size of Campus:");
	JLabel numberOfOrdersLabel = new JLabel("Number of Orders");
	JLabel numberOfCustomersLabel = new JLabel("Number of Customers:");
	JLabel chargingGoalLabel = new JLabel("Charging Goal:");
	JLabel startPTLabel = new JLabel("Start Peaktime:");
	JLabel endPTLabel = new JLabel("End Peaktime:");
	JLabel sdLabel = new JLabel("Stand-Dev:");
	static JLabel exportLabel = new JLabel("");
	static JLabel fillerLabel = new JLabel("");
	static JLabel outputLabel = new JLabel("Output:");
	static JLabel errorLabel = new JLabel("Error:");

	static JTextField campusSizeTextField = new JTextField("750");
	static JTextField numberOfOrdersTextField = new JTextField("250");
	static JTextField numberOfCustomersTextField = new JTextField("10");
	JSlider chargingGoalSlider = new JSlider(0, (int) DataDashboard.batteryCapacity, 300);
	static JTextField sliderValueTextField = new JTextField("300");
	static JTextField startPTTextField = new JTextField("300");
	static JTextField endPTTextField = new JTextField("420");
	static JTextField sdTextField = new JTextField("100");

	static JRadioButton normalDistributionRadioButton = new JRadioButton("Normaldistribution");
	JRadioButton equalDistributionRadioButton = new JRadioButton("Equaldistribution");

	static JRadioButton noChargingButton = new JRadioButton("No Battery constraints");
	static JRadioButton intervalChargingButton = new JRadioButton("Interval charging");
	static JRadioButton idleTimeChargingButton = new JRadioButton("Idle-time charging");
	static JRadioButton operationsPerHourButton = new JRadioButton("Charging Operations per hour");
	static JRadioButton missedOrdersSOCGoalButton = new JRadioButton("Missed Orders per Charging Goal");
	static JTextField intervalTextField = new JTextField("20");
	JLabel intervalLabel = new JLabel("In which intervals shall the bots be charged? [min]");
	ButtonGroup distributionGroup = new ButtonGroup();
	ButtonGroup chargingGroup = new ButtonGroup();
	ButtonGroup graficGroup = new ButtonGroup();

	JButton runButton = new JButton("Run simulation");
	JButton graficButton = new JButton("Show graph");

	public View() {
		Container c;
		c = getContentPane();

		c.add(basePanel);

		basePanel.add(panel1);
		basePanel.add(panel2);
		basePanel.add(panel3);
		basePanel.add(panel4);
		basePanel.add(panel5);

		panel1.add(panel11);
		panel1.add(panel12);
		panel1.add(panel13);
		panel1.add(panel14);

		panel2.add(panel21);
		panel2.add(panel22);

		panel3.add(panel31);
		panel3.add(panel32);
		panel3.add(panel33);

		panel4.add(panel41);
		panel4.add(panel42);

		panel5.add(panel51);
		panel5.add(panel52);

		panel11.add(campusSizeLabel);
		panel11.add(campusSizeTextField);
		panel12.add(numberOfOrdersLabel);
		panel12.add(numberOfOrdersTextField);
		panel13.add(numberOfCustomersLabel);
		panel13.add(numberOfCustomersTextField);
		panel14.add(chargingGoalLabel);
		panel14.add(chargingGoalSlider);
		panel14.add(sliderValueTextField);

		panel21.add(equalDistributionRadioButton);
		panel22.add(panel221);
		panel22.add(panel222);
		panel22.add(panel223);
		panel22.add(panel224);
		panel221.add(normalDistributionRadioButton);
		panel222.add(startPTLabel);
		panel222.add(startPTTextField);
		panel223.add(endPTLabel);
		panel223.add(endPTTextField);
		panel224.add(sdLabel);
		panel224.add(sdTextField);

		panel31.add(noChargingButton);
		panel32.add(intervalChargingButton);
		panel32.add(intervalLabel);
		panel32.add(intervalTextField);
		panel33.add(idleTimeChargingButton);

		panel41.add(fillerLabel);
		panel41.add(exportLabel);
		panel41.add(runButton);
		panel42.add(operationsPerHourButton);
		panel42.add(missedOrdersSOCGoalButton);
		panel42.add(graficButton);

		panel51.add(outputLabel);
		panel52.add(errorLabel);

		chargingGoalSlider.setPaintTicks(true);
		chargingGoalSlider.setPaintLabels(true);
		chargingGoalSlider.setMajorTickSpacing(100);
		chargingGoalSlider.setMinorTickSpacing(25);

		sliderValueTextField.setText(String.valueOf(chargingGoalSlider.getValue()));
		chargingGoalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int value = chargingGoalSlider.getValue();
				sliderValueTextField.setText(String.valueOf(value));
			}
		});

		sliderValueTextField.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				int value = Integer.valueOf(sliderValueTextField.getText());
				chargingGoalSlider.setValue(value);
			}
		});

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
						DataDashboard.getCampusSize());
				RunSimulation r = new RunSimulation(knots);
				System.out.println("Run-Button clicked!");

			}
		});

		graficButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (operationsPerHourButton.isSelected() == true) {
					System.out.println("Button clicked, Graph will be visible soon!");
					Application.launch(GraficChargingOperationsPerHour.class, new String[] {});
				} else {
					System.out.println("Button clicked, Graph will be visible soon!");
					Application.launch(GraficMissedOrdersChargingGoal.class, new String[] {});
				}

			}
		});
		distributionGroup.add(normalDistributionRadioButton);
		distributionGroup.add(equalDistributionRadioButton);

		chargingGroup.add(noChargingButton);
		chargingGroup.add(intervalChargingButton);
		chargingGroup.add(idleTimeChargingButton);

		graficGroup.add(operationsPerHourButton);
		graficGroup.add(missedOrdersSOCGoalButton);

		// STYLIING

		Color green = new Color(135, 211, 124);
		Color white = new Color(255, 255, 255);
		Color red = new Color(255, 0, 0);

		panel1.setBackground(green);
		panel11.setBackground(green);
		panel12.setBackground(green);
		panel13.setBackground(green);
		panel14.setBackground(green);

		panel2.setBackground(white);
		panel21.setBackground(white);
		panel222.setBackground(white);
		panel221.setBackground(white);
		panel223.setBackground(white);
		panel224.setBackground(white);

		panel31.setBackground(green);
		panel32.setBackground(green);
		panel33.setBackground(green);

		panel41.setBackground(white);
		panel42.setBackground(white);

		panel51.setBackground(green);
		panel52.setBackground(green);

		runButton.setForeground(red);

		equalDistributionRadioButton.setSelected(true);
		intervalChargingButton.setSelected(true);
		operationsPerHourButton.setSelected(true);

		// TODO: default values + falsche eingaben auffangen
	}

}
