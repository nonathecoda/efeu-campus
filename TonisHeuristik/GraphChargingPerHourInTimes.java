package Dashboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

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

public class GraphChargingPerHourInTimes {

	public static void initFX(JFXPanel fxPanel) {

		// +1? bei ungraden Brüchen
		int amountHours = (int) (DataDashboard.getDayDurationTextField() / 60);

		System.out.println("Hours: " + amountHours);

		String rowOpportunity;
		ArrayList<ChargingProcess> list1a = new ArrayList<>();
		BufferedReader csvReaderIC;

		View.intervalChargingButton.setSelected(true);
		ArrayList<Customer> knotsA = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		RunSimulation rA = new RunSimulation(knotsA);

		try {
			csvReaderIC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsIntervalHeuristik.csv"));

			while ((rowOpportunity = csvReaderIC.readLine()) != null) {
				String[] data = rowOpportunity.split(";");

				try {
					list1a.add(new ChargingProcess(Integer.parseInt(data[10]), Integer.parseInt(data[11])));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderIC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Hours: " + amountHours);

		ArrayList<ArrayList<Integer>> list2a = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < amountHours; i++) {
			list2a.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < list1a.size(); i++) {

			int chargingDuration = list1a.get(i).getChargingDuration();
			if (chargingDuration != 0) {
				int chargingMoment = list1a.get(i).getChargingMoment();
				for (int k = 0; k < amountHours; k++) {
					if (chargingMoment >= k * 60 && chargingMoment < ((k * 60) + 60)) {
						list2a.get(k).add(chargingMoment);
						break;
					}
				}
			}

		}

		int sumIntervalCharging = 0;
		for (int i = 0; i < list2a.size(); i++) {
			for (int k = 0; k < list2a.get(i).size(); k++) {
				sumIntervalCharging += list2a.get(i).size();
			}

		}

		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Interval Charging");

		for (int i = 0; i < list2a.size(); i++) {
			int sumPerHour = 0;
			for (int k = 0; k < list2a.get(i).size(); k++) {
				sumPerHour += list2a.get(i).size();
			}

			series1.getData().add(createData("" + (i + 1), sumPerHour, sumIntervalCharging));
		}

		///////////////////// 7

		XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		series2.setName("Opportunity Charging");

		String rowOpportunityCharge;
		ArrayList<ChargingProcess> list1b = new ArrayList<>();
		BufferedReader csvReaderOC;

		View.opportunityChargingButton.setSelected(true);
		ArrayList<Customer> knotsB = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		RunSimulation rB = new RunSimulation(knotsB);

		try {
			csvReaderOC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsOpportunityHeuristik.csv"));

			while ((rowOpportunityCharge = csvReaderOC.readLine()) != null) {
				String[] data = rowOpportunityCharge.split(";");

				try {
					list1b.add(new ChargingProcess(Integer.parseInt(data[10]), Integer.parseInt(data[11])));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderOC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<ArrayList<Integer>> list2b = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < amountHours; i++) {
			list2b.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < list1b.size(); i++) {

			int chargingDuration = list1b.get(i).getChargingDuration();
			if (chargingDuration != 0) {
				int chargingMoment = list1b.get(i).getChargingMoment();
				for (int k = 0; k < amountHours; k++) {
					if (chargingMoment >= k * 60 && chargingMoment < ((k * 60) + 60)) {
						list2b.get(k).add(chargingMoment);
						break;
					}
				}
			}

		}

		int sumIdleCharging = 0;
		for (int i = 0; i < list2b.size(); i++) {
			for (int k = 0; k < list2b.get(i).size(); k++) {
				sumIdleCharging += list2b.get(i).size();
			}

		}

		for (int i = 0; i < list2b.size(); i++) {
			int sumPerHour = 0;
			for (int k = 0; k < list2b.get(i).size(); k++) {
				sumPerHour += list2b.get(i).size();
			}

			series2.getData().add(createData("" + (i + 1), sumPerHour, sumIdleCharging));
		}

		///////////////////
		XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
		series3.setName("Emergency Charging");
		String rowEmergency;
		ArrayList<ChargingProcess> list1c = new ArrayList<>();
		BufferedReader csvReaderEC;

		View.emergencyChargingButton.setSelected(true);
		ArrayList<Customer> knotsC = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		RunSimulation rC = new RunSimulation(knotsC);

		try {
			csvReaderEC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsEmergencyHeuristik.csv"));

			while ((rowEmergency = csvReaderEC.readLine()) != null) {
				String[] data = rowEmergency.split(";");

				try {
					list1c.add(new ChargingProcess(Integer.parseInt(data[10]), Integer.parseInt(data[11])));
				} catch (Exception e) {

				}

			}
			csvReaderEC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<ArrayList<Integer>> list2c = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < amountHours; i++) {
			list2c.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < list1c.size(); i++) {

			int chargingDuration = list1c.get(i).getChargingDuration();
			if (chargingDuration != 0) {
				int chargingMoment = list1c.get(i).getChargingMoment();
				for (int k = 0; k < amountHours; k++) {
					if (chargingMoment >= k * 60 && chargingMoment < ((k * 60) + 60)) {
						list2c.get(k).add(list1c.get(i).getChargingMoment());
						break;
					}
				}
			}

		}

		int sumEmergencyCharging = 0;
		for (int i = 0; i < list2c.size(); i++) {
			for (int k = 0; k < list2c.get(i).size(); k++) {
				sumEmergencyCharging += list2c.get(i).size();
			}

		}

		for (int i = 0; i < list2c.size(); i++) {
			int sumPerHour = 0;
			for (int k = 0; k < list2c.get(i).size(); k++) {
				sumPerHour += list2c.get(i).size();
			}

			series3.getData().add(createData("" + (i + 1), sumPerHour, sumEmergencyCharging));
		}

		/////////////////////

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Hour");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Number of charging operations");
		BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);

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
