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

		int init = 0;
		int end = 0;
		int increment = 0;
		JTextField txtField = new JTextField();

		switch (x) {
		case "Campus Size":
			init = 10;
			end = 1500;
			increment = 50;
			txtField = View.campusSizeTextField;
			break;
		case "Number of Customers":
			init = 5;
			end = 100;
			increment = 5;
			txtField = View.numberOfCustomersTextField;
			break;
		case "Number of Orders":
			init = 50;
			end = 1000;
			increment = 50;
			txtField = View.numberOfOrdersTextField;
			break;
		case "Charging Goal":
			init = (int) (DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge());
			end = (int) DataDashboard.batteryCapacity;
			increment = 10;
			txtField = View.sliderValueTextField;
			break;
		case "Charging Frequency (only Interval)":
			init = 10;
			end = 120;
			increment = 10;
			txtField = View.intervalTextField;
			break;
		}

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Interval Charging");
		for (int i = init; i < end; i = i + increment) {

			txtField.setText("" + i);
			View.intervalChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
					DataDashboard.getCampusSize());
			TestRun r = new TestRun(knots);
			int yValue = 0;
			switch (y) {
			case "Late Orders":
				yValue = TestRun.lateOrders;
				break;
			case "Missed Orders":
				yValue = TestRun.missedOrders;
				break;
			case "Average Delay per Order":
				yValue = TestRun.avgDelay;
				break;
			}
			series1.getData().add(new XYChart.Data(i, yValue));

		}

		if (x.contains("Charging Frequency (only Interval)") == false) {
			XYChart.Series series2 = new XYChart.Series();
			series2.setName("Emergency Charging");
			for (int i = init; i < end; i = i + increment) {

				txtField.setText("" + i);
				View.emergencyChargingButton.setSelected(true);

				ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
						DataDashboard.getCampusSize());
				TestRun r = new TestRun(knots);
				int yValue = 0;
				switch (y) {
				case "Late Orders":
					yValue = TestRun.lateOrders;
					break;
				case "Missed Orders":
					yValue = TestRun.missedOrders;
					break;
				case "Average Delay per Order":
					yValue = TestRun.avgDelay;
					break;
				}
				series2.getData().add(new XYChart.Data(i, yValue));

			}

			XYChart.Series series3 = new XYChart.Series();
			series3.setName("Opportunity Charging");
			for (int i = init; i < end; i = i + increment) {

				txtField.setText("" + i);
				View.idleTimeChargingButton.setSelected(true);

				ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
						DataDashboard.getCampusSize());
				TestRun r = new TestRun(knots);
				int yValue = 0;
				switch (y) {
				case "Late Orders":
					yValue = TestRun.lateOrders;
					break;
				case "Missed Orders":
					yValue = TestRun.missedOrders;
					break;
				case "Average Delay per Order":
					yValue = TestRun.avgDelay;
					break;
				}
				series3.getData().add(new XYChart.Data(i, yValue));

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
