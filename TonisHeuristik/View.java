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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

import javax.swing.*;

public class View extends JFrame {

	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel north = new JPanel(new GridLayout(3, 1));
	JPanel east = new JPanel(new GridLayout(2, 1));
	JPanel west = new JPanel(new GridLayout(10, 1));
	JPanel south = new JPanel(new GridLayout(3, 1));
	JPanel center = new JPanel(new FlowLayout());

	JPanel n1 = new JPanel(new GridLayout(1, 4));
	JPanel n2 = new JPanel(new GridLayout(1, 4));
	JPanel n3 = new JPanel(new GridLayout(1, 4));
	JPanel n11 = new JPanel(new FlowLayout());
	JPanel n12 = new JPanel(new FlowLayout());
	JPanel n13 = new JPanel(new FlowLayout());
	JPanel n21 = new JPanel(new FlowLayout());
	JPanel n22 = new JPanel(new FlowLayout());
	JPanel n23 = new JPanel(new FlowLayout());
	JPanel n24 = new JPanel(new FlowLayout());
	JPanel n34 = new JPanel(new FlowLayout());

	JPanel s1 = new JPanel(new GridLayout());
	JPanel s2 = new JPanel(new GridLayout());
	JPanel s3 = new JPanel(new GridLayout(1, 3));

	JPanel e1 = new JPanel(new GridLayout(1, 1));
	JPanel e2 = new JPanel(new GridLayout(7, 1));
	JPanel e24 = new JPanel(new GridLayout());

	JPanel w1 = new JPanel(new GridLayout());
	JPanel w2 = new JPanel(new GridLayout());
	JPanel w3 = new JPanel(new GridLayout());
	JPanel w4 = new JPanel(new GridLayout());
	JPanel w5 = new JPanel(new GridLayout());
	JPanel w6 = new JPanel(new GridLayout(1, 2));
	JPanel w7 = new JPanel(new GridLayout(1, 2));
	JPanel w8 = new JPanel(new GridLayout(1, 2));
	JPanel w9 = new JPanel(new GridLayout(1, 3));
	JPanel w10 = new JPanel(new GridLayout());
	JPanel w61 = new JPanel(new GridLayout());
	JPanel w62 = new JPanel(new GridLayout());
	JPanel w71 = new JPanel(new GridLayout());
	JPanel w72 = new JPanel(new GridLayout());
	JPanel w81 = new JPanel(new GridLayout());
	JPanel w82 = new JPanel(new GridLayout());
	JPanel w91 = new JPanel(new GridLayout());
	JPanel w92 = new JPanel(new GridLayout());
	JPanel w93 = new JPanel(new GridLayout());

	JPanel fillerPanel = new JPanel(new GridLayout());

	JLabel campusSizeLabel = new JLabel("Radius of Campus [m]:");
	JLabel numberOfOrdersLabel = new JLabel("Number of Orders");
	JLabel numberOfCustomersLabel = new JLabel("Number of Customers:");
	JLabel chargingGoalLabel = new JLabel("Charging Goal [Wh]:");
	JLabel startPTLabel = new JLabel("Start Peaktime [min]:");
	JLabel endPTLabel = new JLabel("End Peaktime [min]:");
	JLabel sdLabel = new JLabel("Stand-Dev:");
	static JLabel outputLabel = new JLabel("Output:");
	JLabel dayDurationLabel = new JLabel("Duration Working Day [min]:");
	JLabel availableChargingLabel = new JLabel("Available Charging Stations:");
	JLabel diyLabel = new JLabel("Choose your y and x axis:");
	JLabel emergencyLabel = new JLabel("Definition Emergency Charging");
	JLabel percentLabel = new JLabel("Trigger at [%/100]:");
	JLabel intervalLabel = new JLabel("Charging Frequency [min] :");
	static JLabel exportLabel = new JLabel("");
	JLabel idleTimeTriggerLabel = new JLabel("Required idletime [min] :");

	static JTextField campusSizeTextField = new JTextField("750");
	static JTextField numberOfOrdersTextField = new JTextField("250");
	static JTextField numberOfCustomersTextField = new JTextField("10");
	JSlider chargingGoalSlider = new JSlider(0, (int) DataDashboard.batteryCapacity, 300);
	static JTextField sliderValueTextField = new JTextField("300");
	static JTextField startPTTextField = new JTextField("300");
	static JTextField endPTTextField = new JTextField("420");
	static JTextField sdTextField = new JTextField("100");
	static JTextField dayDurationTextField = new JTextField("480");
	static JTextField percentTextField = new JTextField("0.3");
	static JTextField idleTimeTriggerTextField = new JTextField("10");

	static String[] chargingStations = { "Charge at depot and two customers.", "Only charge at depot." };
	static String[] xValues = { "Campus Size", "Number of Customers", "Number of Orders", "Charging Goal",
			"Trigger Emergency charging", "Charging Frequency (only Interval)" };
	static String[] yValues = { "Late Orders", "Missed Orders", "Average Delay per Order" };

	static JComboBox chargingStationComboBox = new JComboBox(chargingStations);
	static JComboBox xComboBox = new JComboBox(xValues);
	static JComboBox yComboBox = new JComboBox(yValues);

	static JRadioButton normalDistributionRadioButton = new JRadioButton("Normaldistribution");
	JRadioButton equalDistributionRadioButton = new JRadioButton("Equaldistribution");
	static JRadioButton noChargingButton = new JRadioButton("No Battery constraints");
	static JRadioButton intervalChargingButton = new JRadioButton("Interval charging");
	static JRadioButton opportunityChargingButton = new JRadioButton("Opportunity charging");
	static JRadioButton emergencyChargingButton = new JRadioButton("Emergency charging only");
	static JRadioButton operationsPerHourInMinutesButton = new JRadioButton(
			"Charging Operations per hour (in minutes)");
	static JRadioButton operationsPerHourInTimesButton = new JRadioButton("Charging Operations per hour (#)");
	static JRadioButton chargingReasonButton = new JRadioButton("Charging Reasons");
	static JRadioButton diyButton = new JRadioButton("Configure own graph");
	static JRadioButton underxRadioButton = new JRadioButton("Charge under 30%");
	static JRadioButton notReachableButton = new JRadioButton("Charge while CS reachable");
	static JTextField intervalTextField = new JTextField("20");

	ButtonGroup distributionGroup = new ButtonGroup();
	ButtonGroup chargingGroup = new ButtonGroup();
	ButtonGroup graficGroup = new ButtonGroup();
	ButtonGroup chargingStationGroup = new ButtonGroup();
	ButtonGroup emergencyGroup = new ButtonGroup();

	JButton runButton = new JButton("Run simulation");
	JButton graficButton = new JButton("Show graph");
	JButton ecDataButton = new JButton("get whole EC data");
	JButton icDataButton = new JButton("get whole IC data");
	JButton ocDataButton = new JButton("get whole OC data");

	JSeparator sep = new JSeparator();

	public View() {
		Container c;
		c = getContentPane();

		c.add(basePanel);

		basePanel.add(north, BorderLayout.NORTH);
		basePanel.add(east, BorderLayout.CENTER);
		basePanel.add(west, BorderLayout.WEST);
		basePanel.add(south, BorderLayout.SOUTH);
		// basePanel.add(center, BorderLayout.CENTER);

		north.add(n1);
		north.add(n2);
		north.add(n3);

		n1.add(noChargingButton);
		n1.add(opportunityChargingButton);
		n1.add(intervalChargingButton);
		n1.add(emergencyChargingButton);
		n2.add(n21);
		n2.add(n22);
		n2.add(n23);
		n2.add(n24);
		n22.add(idleTimeTriggerLabel);
		n22.add(idleTimeTriggerTextField);
		n23.add(intervalLabel);
		n23.add(intervalTextField);
		n3.add(emergencyLabel);
		n3.add(underxRadioButton);
		n3.add(notReachableButton);
		n3.add(n34);
		n34.add(percentLabel);
		n34.add(percentTextField);

		south.add(s1);
		south.add(s2);
		south.add(s3);

		s2.add(runButton);
		s3.add(ocDataButton);
		s3.add(icDataButton);
		s3.add(ecDataButton);

		east.add(e1);
		east.add(e2);

		e1.add(outputLabel);
		// e1.add(exportLabel);
		e2.add(operationsPerHourInMinutesButton);
		e2.add(operationsPerHourInTimesButton);
		e2.add(chargingReasonButton);
		e2.add(diyButton);
		e2.add(diyLabel);
		e2.add(e24);
		e24.add(yComboBox);
		e24.add(xComboBox);
		e2.add(graficButton);

		west.add(w1);
		west.add(w2);
		west.add(w3);
		west.add(w4);
		west.add(w5);
		west.add(w6);
		west.add(w7);
		west.add(w8);
		west.add(w9);
		west.add(w10);

		w1.add(campusSizeLabel);
		w1.add(campusSizeTextField);
		w2.add(dayDurationLabel);
		w2.add(dayDurationTextField);
		w3.add(numberOfCustomersLabel);
		w3.add(numberOfCustomersTextField);
		w4.add(numberOfOrdersLabel);
		w4.add(numberOfOrdersTextField);
		w5.add(equalDistributionRadioButton);
		w5.add(normalDistributionRadioButton);
		w6.add(w61);
		w6.add(w62);
		w7.add(w71);
		w7.add(w72);
		w8.add(w81);
		w8.add(w82);
		w9.add(w91);
		w9.add(w92);
		w9.add(w93);
		w10.add(availableChargingLabel);
		w10.add(chargingStationComboBox);

		w62.add(startPTLabel);
		w62.add(startPTTextField);
		w72.add(endPTLabel);
		w72.add(endPTTextField);
		w82.add(sdLabel);
		w82.add(sdTextField);
		w91.add(chargingGoalLabel);
		w92.add(chargingGoalSlider);
		w93.add(sliderValueTextField);

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

		percentTextField.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				Double value = Double.valueOf(percentTextField.getText()) * 100;
				String text = "Charge under " + String.valueOf(value) + "%";
				underxRadioButton.setText(text);
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

		ocDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegressionData.getOCData();
				System.out.println("Test-Button clicked!");

			}
		});
		icDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegressionData.getICData();
				System.out.println("Test-Button clicked!");

			}
		});
		ecDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegressionData.getECData();
				System.out.println("Test-Button clicked!");

			}
		});

		graficButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (operationsPerHourInMinutesButton.isSelected() == true) {
					System.out.println("Button clicked, Graph will be visible soon!");
					GraphChargingPerHour obj = new GraphChargingPerHour();

					JFrame frame = new JFrame("FX");
					final JFXPanel fxPanel = new JFXPanel();
					frame.setSize(1000, 600);
					frame.add(fxPanel);
					frame.setVisible(true);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							obj.initFX(fxPanel);
						}
					});

				} else if (operationsPerHourInTimesButton.isSelected() == true) {
					System.out.println("Button clicked, Graph will be visible soon!");
					GraphChargingPerHourInTimes obj = new GraphChargingPerHourInTimes();

					JFrame frame = new JFrame("FX");
					final JFXPanel fxPanel = new JFXPanel();
					frame.setSize(1000, 600);
					frame.add(fxPanel);
					frame.setVisible(true);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							obj.initFX(fxPanel);
						}
					});

				} else if (chargingReasonButton.isSelected() == true) {
					GraphChargingReason obj = new GraphChargingReason();
					JFrame frame = new JFrame("FX");
					final JFXPanel fxPanel = new JFXPanel();
					frame.setSize(1000, 600);
					frame.add(fxPanel);
					frame.setVisible(true);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							obj.initFX(fxPanel);
						}
					});
				} else if (diyButton.isSelected() == true) {
					GraphAllrounder obj = new GraphAllrounder();
					JFrame frame = new JFrame("FX");
					final JFXPanel fxPanel = new JFXPanel();
					frame.setSize(1000, 600);
					frame.add(fxPanel);
					frame.setVisible(true);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							obj.initFX(fxPanel);
						}
					});
				}

			}
		});

		distributionGroup.add(normalDistributionRadioButton);
		distributionGroup.add(equalDistributionRadioButton);

		chargingGroup.add(noChargingButton);
		chargingGroup.add(intervalChargingButton);
		chargingGroup.add(opportunityChargingButton);
		chargingGroup.add(emergencyChargingButton);

		graficGroup.add(operationsPerHourInMinutesButton);
		graficGroup.add(chargingReasonButton);
		graficGroup.add(diyButton);
		graficGroup.add(operationsPerHourInTimesButton);

		emergencyGroup.add(notReachableButton);
		emergencyGroup.add(underxRadioButton);

		// STYLIING

		Color green = new Color(135, 211, 124);
		Color white = new Color(255, 255, 255);
		Color red = new Color(255, 0, 0);

		n1.setBackground(green);
		n2.setBackground(green);
		n3.setBackground(green);
		n34.setBackground(green);
		n21.setBackground(green);
		n22.setBackground(green);
		n23.setBackground(green);
		n24.setBackground(green);

		s1.setBackground(green);
		s2.setBackground(green);
		s3.setBackground(green);

		runButton.setForeground(red);

		normalDistributionRadioButton.setSelected(true);
		opportunityChargingButton.setSelected(true);
		operationsPerHourInMinutesButton.setSelected(true);
		notReachableButton.setSelected(true);

		// TODO: default values + falsche eingaben auffangen
	}

}
