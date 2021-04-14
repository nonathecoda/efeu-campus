package Dashboard;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GraphSOCGoalMissedOrders {

	public static void initFX(JFXPanel fxPanel) {

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Charging Goal");
		yAxis.setLabel("Late Orders");
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle("Number of late orders depending on SOC Goal");
		XYChart.Series seriesIntervalCharging = new XYChart.Series();
		XYChart.Series seriesIntervalMissed = new XYChart.Series();
		seriesIntervalCharging.setName("Interval Charging");

		for (int i = (int) (DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge()); i < 556; i = i + 10) {

			View.sliderValueTextField.setText("" + i);
			View.intervalChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
					DataDashboard.getCampusSize());
			TestRun r = new TestRun(knots);

			seriesIntervalCharging.getData().add(new XYChart.Data(i, TestRun.lateOrders));
			seriesIntervalMissed.getData().add(new XYChart.Data(i, TestRun.missedOrders));

		}

		XYChart.Series seriesIdleCharging = new XYChart.Series();
		seriesIdleCharging.setName("Idletime Charging");

		for (int i = (int) (DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge()); i < 556; i = i + 10) {

			View.sliderValueTextField.setText("" + i);
			View.opportunityChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
					DataDashboard.getCampusSize());
			TestRun r = new TestRun(knots);

			seriesIdleCharging.getData().add(new XYChart.Data(i, TestRun.lateOrders));

		}

		XYChart.Series seriesEmergencyCharging = new XYChart.Series();
		seriesEmergencyCharging.setName("Emergency Charging");

		for (int i = (int) (DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge()); i < 556; i = i + 10) {

			View.sliderValueTextField.setText("" + i);
			View.emergencyChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
					DataDashboard.getCampusSize());
			TestRun r = new TestRun(knots);

			seriesEmergencyCharging.getData().add(new XYChart.Data(i, TestRun.lateOrders));

		}

		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().addAll(seriesIntervalCharging, seriesIdleCharging, seriesEmergencyCharging,
				seriesIntervalMissed);
		///
		Node lab = seriesIntervalMissed.getNode().lookup(".chart-series-line");

		Color color = Color.LIGHT_GRAY;

		String rgb = String.format("%d, %d, %d", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));

		lab.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
		//
		lineChart.setCreateSymbols(false);
		fxPanel.setScene(scene);

	}

}
