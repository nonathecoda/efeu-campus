package Dashboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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

public class GraficMissedOrdersChargingGoal extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Line Chart Sample");

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Charging Goal");
		yAxis.setLabel("Missed Orders");
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle("Number of missed orders depending on SOC Goal");
		XYChart.Series seriesIntervalCharging = new XYChart.Series();
		seriesIntervalCharging.setName("Interval Charging");

		for (int i = 0; i < 556; i = i + 10) {

			View.sliderValueTextField.setText("" + i);
			View.intervalChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(), DataDashboard.getCampusSize());
			RunSimulation r = new RunSimulation(knots);

			seriesIntervalCharging.getData().add(new XYChart.Data(i, RunSimulation.missedOrders));

		}

		XYChart.Series seriesIdleCharging = new XYChart.Series();
		seriesIdleCharging.setName("Idletime Charging");

		for (int i = 0; i < 556; i = i + 10) {

			View.sliderValueTextField.setText("" + i);
			View.idleTimeChargingButton.setSelected(true);

			ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(), DataDashboard.getCampusSize());
			RunSimulation r = new RunSimulation(knots);

			seriesIdleCharging.getData().add(new XYChart.Data(i, RunSimulation.missedOrders));

		}

		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().addAll(seriesIntervalCharging, seriesIdleCharging);
		lineChart.setCreateSymbols(false);
		stage.setScene(scene);
		stage.show();

	}

}
