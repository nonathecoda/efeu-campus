package Dashboard;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextField;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GraphAllrounder {

	public static void initFX(JFXPanel fxPanel) {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		// String x = String.valueOf(View.xComboBox.getSelectedObjects());
		String x = String.valueOf(View.xComboBox.getSelectedItem());
		String y = String.valueOf(View.yComboBox.getSelectedItem());

		System.out.println(x);
		System.out.println(y);

		xAxis.setLabel(x);
		yAxis.setLabel(y);
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle(y + " depending on " + x);

		double init = 0;
		double end = 0;
		double increment = 0;
		JTextField txtField = new JTextField();

		switch (x) {
		case "Campus Size":
			init = 10;
			end = 7000;
			increment = 50;
			txtField = View.campusSizeTextField;
			break;
		case "Number of Customers":
			init = 5;
			end = 300;
			increment = 5;
			txtField = View.numberOfCustomersTextField;
			break;
		case "Number of Orders":
			init = 25;
			end = 1000;
			increment = 25;
			txtField = View.numberOfOrdersTextField;
			break;
		case "Charging Goal":
			init = (int) (DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge());
			end = (int) DataDashboard.batteryCapacity;
			increment = 10;
			txtField = View.sliderValueTextField;
			break;
		case "Trigger Emergency charging":
			init = 0.01;
			end = 1.0;
			increment = 0.1;
			txtField = View.percentTextField;
			break;
		case "Charging Frequency (only Interval)":
			init = 10;
			end = DataDashboard.getDayDurationTextField();
			increment = 10;
			txtField = View.intervalTextField;
			break;
		}

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Interval Charging");
		for (double i = init; i < end; i = i + increment) {
			Object j = (double) i;
			if (x.contains("Trigger Emergency charging") == false) {
				j = (int) i;
			}

			txtField.setText("" + j);
			View.intervalChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
					DataDashboard.getCampusSize());
			RunSimulation r = new RunSimulation(knots);
			int yValue = 0;
			switch (y) {
			case "Late Orders":
				yValue = RunSimulation.lateOrders;
				break;
			case "Missed Orders":
				yValue = RunSimulation.missedOrders;
				break;
			case "Average Delay per Order":
				yValue = RunSimulation.avgDelay;
				break;
			}
			series1.getData().add(new XYChart.Data(j, yValue));

		}

		if (x.contains("Charging Frequency (only Interval)") == false) {
			XYChart.Series series2 = new XYChart.Series();
			series2.setName("Emergency Charging");
			for (double i = init; i < end; i = i + increment) {

				Object j = (double) i;
				if (x.contains("Trigger Emergency charging") == false) {
					j = (int) i;
				}

				txtField.setText("" + j);
				View.emergencyChargingButton.setSelected(true);

				ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
						DataDashboard.getCampusSize());
				RunSimulation r = new RunSimulation(knots);
				int yValue = 0;
				switch (y) {
				case "Late Orders":
					yValue = RunSimulation.lateOrders;
					break;
				case "Missed Orders":
					yValue = RunSimulation.missedOrders;
					break;
				case "Average Delay per Order":
					yValue = RunSimulation.avgDelay;
					break;
				}
				series2.getData().add(new XYChart.Data(j, yValue));

			}

			XYChart.Series series3 = new XYChart.Series();
			series3.setName("Opportunity Charging");
			for (double i = init; i < end; i = i + increment) {
				Object j = (double) i;
				if (x.contains("Trigger Emergency charging") == false) {
					j = (int) i;
				}
				txtField.setText("" + j);
				View.opportunityChargingButton.setSelected(true);

				ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
						DataDashboard.getCampusSize());
				System.out.println("j: " + j);
				RunSimulation r = new RunSimulation(knots);
				int yValue = 0;
				switch (y) {
				case "Late Orders":
					yValue = RunSimulation.lateOrders;
					break;
				case "Missed Orders":
					yValue = RunSimulation.missedOrders;
					break;
				case "Average Delay per Order":
					yValue = RunSimulation.avgDelay;
					break;
				}
				series3.getData().add(new XYChart.Data(j, yValue));

			}
			lineChart.getData().addAll(series1, series2, series3);
		}
		Scene scene = new Scene(lineChart, 800, 600);

		if (x.contains("Charging Frequency (only Interval)") == true) {
			lineChart.getData().add(series1);
		}
		lineChart.setCreateSymbols(false);
		fxPanel.setScene(scene);
	}
}
