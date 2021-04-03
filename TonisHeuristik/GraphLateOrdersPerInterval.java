package Dashboard;

import java.util.ArrayList;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GraphLateOrdersPerInterval {

	public static void initFX(JFXPanel fxPanel) {

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Charging Interval in minutes");
		yAxis.setLabel("Late Orders");
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle("Number of late orders depending on Charging Intervals");
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Interval Charging");

		for (int i = 1; i < 12; i++) {

			View.intervalTextField.setText("" + (i * 10));

			View.intervalChargingButton.setSelected(true);
			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
					DataDashboard.getCampusSize());
			TestRun r = new TestRun(knots);

			series1.getData().add(new XYChart.Data(i * 10, TestRun.lateOrders));
		}

		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().addAll(series1);
		lineChart.setCreateSymbols(false);
		fxPanel.setScene(scene);

	}

}
