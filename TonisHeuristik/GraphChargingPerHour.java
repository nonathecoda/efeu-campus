package Dashboard;

import java.io.*;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

class ChargingProcess {
	int chargingMoment, chargingDuration;
	

	ChargingProcess(int chargingMoment, int chargingDuration) {
		this.chargingDuration = chargingDuration;
		this.chargingMoment = chargingMoment;
		
	}

	void setChargingDuration(int i) {
		this.chargingDuration = i;
	}

	void setChargingMoment(int i) {
		this.chargingMoment = i;
	}

	int getChargingDuration() {
		return this.chargingDuration;
	}

	int getChargingMoment() {
		return this.chargingMoment;
	}

	
}

public class GraphChargingPerHour {

	public static void initFX(JFXPanel fxPanel) {

		String rowIdleTime;
		ArrayList<ChargingProcess> list1 = new ArrayList<>();
		BufferedReader csvReaderTC;

		View.opportunityChargingButton.setSelected(true);
		ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		TestRun r = new TestRun(knots);

		try {
			csvReaderTC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsOpportunityHeuristik.csv"));

			while ((rowIdleTime = csvReaderTC.readLine()) != null) {
				String[] data = rowIdleTime.split(";");

				try {
					list1.add(new ChargingProcess(Integer.parseInt(data[10]), Integer.parseInt(data[11])));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderTC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// +1? bei ungraden Brüchen
		int amountHours = (int) (DataDashboard.getDayDurationTextField() / 60);

		System.out.println("Hours: " + amountHours);

		ArrayList<ArrayList<Integer>> list2 = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < amountHours; i++) {
			list2.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < list1.size(); i++) {
			int chargingMoment = list1.get(i).getChargingMoment();
			for (int k = 0; k < amountHours; k++) {
				if (chargingMoment >= k * 60 && chargingMoment < ((k * 60) + 60)) {
					list2.get(k).add(list1.get(i).getChargingDuration());
					break;
				}
			}
		}

		

		int sumIdleCharging = 0;
		for (int i = 0; i < list2.size(); i++) {
			for (int k = 0; k < list2.get(i).size(); k++) {
				sumIdleCharging += list2.get(i).get(k);
			}

		}

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Hour");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Duration of charging operations in minutes");
		BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
		XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		series2.setName("Idletime Charging");

		for (int i = 0; i < list2.size(); i++) {
			int sumPerHour = 0;
			for (int k = 0; k < list2.get(i).size(); k++) {
				sumPerHour += list2.get(i).get(k);
			}

			series2.getData().add(createData("" + (i + 1), sumPerHour, sumIdleCharging));
		}
		/////////////////////

		String rowEmergency;
		ArrayList<ChargingProcess> list1b = new ArrayList<>();
		BufferedReader csvReaderEC;

		View.emergencyChargingButton.setSelected(true);
		ArrayList<Customer> knots3 = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		TestRun r3 = new TestRun(knots3);

		try {
			csvReaderEC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsEmergencyHeuristik.csv"));

			while ((rowEmergency = csvReaderEC.readLine()) != null) {
				String[] data = rowEmergency.split(";");

				try {
					list1b.add(new ChargingProcess(Integer.parseInt(data[10]), Integer.parseInt(data[11])));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderEC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<ArrayList<Integer>> list2b = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < amountHours; i++) {
			list2b.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < list1b.size(); i++) {
			int chargingMoment = list1b.get(i).getChargingMoment();
			for (int k = 0; k < amountHours; k++) {
				if (chargingMoment >= k * 60 && chargingMoment < ((k * 60) + 60)) {
					list2b.get(k).add(list1b.get(i).getChargingDuration());
					break;
				}
			}
		}

		

		int sumEmergencyCharging = 0;
		for (int i = 0; i < list2b.size(); i++) {
			for (int k = 0; k < list2b.get(i).size(); k++) {
				sumEmergencyCharging += list2b.get(i).get(k);
			}

		}

		XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
		series3.setName("Emergency Charging");

		for (int i = 0; i < list2b.size(); i++) {
			int sumPerHour = 0;
			for (int k = 0; k < list2b.get(i).size(); k++) {
				sumPerHour += list2b.get(i).get(k);
			}

			series3.getData().add(createData("" + (i + 1), sumPerHour, sumEmergencyCharging));
		}

		//////////////////

		String rowIntervalCharging;
		ArrayList<ChargingProcess> list1a = new ArrayList<>();
		BufferedReader csvReaderICC;

		View.intervalChargingButton.setSelected(true);
		ArrayList<Customer> knots2 = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		TestRun r2 = new TestRun(knots2);

		try {
			csvReaderICC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsIntervalHeuristik.csv"));

			while ((rowIntervalCharging = csvReaderICC.readLine()) != null) {
				String[] data = rowIntervalCharging.split(";");

				try {
					list1a.add(new ChargingProcess(Integer.parseInt(data[10]), Integer.parseInt(data[11])));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderICC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// +1? bei ungraden Brüchen

		ArrayList<ArrayList<Integer>> list2a = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < amountHours; i++) {
			ArrayList<Integer> chargingOperationsThisHour = new ArrayList<>();
			list2a.add(chargingOperationsThisHour);
		}

		for (int i = 0; i < list1a.size(); i++) {
			int chargingMoment = list1a.get(i).getChargingMoment();

			for (int k = 0; k < amountHours; k++) {
				if (chargingMoment >= k * 60 && chargingMoment < ((k * 60) + 60)) {
					list2a.get(k).add(list1a.get(i).getChargingDuration());
					break;
				}
			}

		}

		int sumInterval = 0;
		for (int i = 0; i < list2a.size(); i++) {
			for (int k = 0; k < list2a.get(i).size(); k++) {
				sumInterval += list2a.get(i).get(k);
			}

		}

		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Interval Charging");

		for (int i = 0; i < list2a.size(); i++) {
			int sumPerHour = 0;
			for (int k = 0; k < list2a.get(i).size(); k++) {
				sumPerHour += list2a.get(i).get(k);
			}

			series1.getData().add(createData("" + (i + 1), sumPerHour, sumInterval));
		}

		//////////////////

		chart.getData().addAll(series1, series2, series3);

		Scene scene = new Scene(chart);
		fxPanel.setScene(scene);
	}

	private static XYChart.Data<String, Number> createData(String country, double value, double sum) {

		DecimalFormat df = new DecimalFormat("0.0");

		XYChart.Data data = new XYChart.Data(country, value);

		double percentage = value * 100 / sum;

		String text = df.format(percentage) + "%";

		StackPane node = new StackPane();
		Label label = new Label(text);
		label.setRotate(-90);
		Group group = new Group(label);
		StackPane.setAlignment(group, Pos.BOTTOM_CENTER);
		StackPane.setMargin(group, new Insets(0, 0, 5, 0));
		node.getChildren().add(group);
		data.setNode(node);

		return data;
	}

}
