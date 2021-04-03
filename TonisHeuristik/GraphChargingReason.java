package Dashboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

class chargingReason {
	String before, after;

	chargingReason(String before, String after) {
		this.before = before;
		this.after = after;
	}

	void setBefore(String before) {
		this.before = before;
	}

	void setAfter(String after) {
		this.after = after;
	}

	String getBefore() {
		return this.before;
	}

	String getAfter() {
		return this.after;
	}
}

public class GraphChargingReason {
	public static void initFX(JFXPanel fxPanel) {

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Charging Reasons");
		yAxis.setLabel("Amount");
		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
		barChart.setTitle("Charging Reasons per Heuristic");

		XYChart.Series seriesIntervalCharging = new XYChart.Series();
		seriesIntervalCharging.setName("Interval Charging");
		View.intervalChargingButton.setSelected(true);
		ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		TestRun r = new TestRun(knots);

		ArrayList<chargingReason> chargingReasonList = new ArrayList<>();
		BufferedReader csvReaderInterval;
		String row;

		try {
			csvReaderInterval = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsIntervalHeuristik.csv"));

			while ((row = csvReaderInterval.readLine()) != null) {
				String[] data = row.split(";");

				try {
					chargingReasonList.add(new chargingReason(data[8], data[9]));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderInterval.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		int sumEmergencyCharges = 0;
		int sumIntervalCharges = 0;
		int sumOverallCharging;
		for (int i = 0; i < chargingReasonList.size(); i++) {
			String dummyBefore = chargingReasonList.get(i).getBefore();
			String dummyAfter = chargingReasonList.get(i).getAfter();
			if (dummyBefore.contains("Yes")) {
				sumEmergencyCharges++;
			}
			if (dummyAfter.contains("Last charging")) {
				sumIntervalCharges++;
			}
		}
		sumOverallCharging = sumEmergencyCharges + sumIntervalCharges;
		seriesIntervalCharging.getData().add(new XYChart.Data("Emergency Charging", sumEmergencyCharges));
		seriesIntervalCharging.getData().add(new XYChart.Data(
				"Last charging " + DataDashboard.getChargingFrequency() + " minutes ago", sumIntervalCharges));
		seriesIntervalCharging.getData().add(new XYChart.Data("Total number of charging", sumOverallCharging));

		///////////////////////////

		XYChart.Series seriesEmergencyCharging = new XYChart.Series();
		seriesEmergencyCharging.setName("Emergency Charging");
		View.emergencyChargingButton.setSelected(true);
		ArrayList<Customer> knots1 = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		TestRun r1 = new TestRun(knots1);

		ArrayList<chargingReason> chargingReasonList1 = new ArrayList<>();
		BufferedReader csvReaderEmergency;
		String row1;

		try {
			csvReaderEmergency = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsEmergencyHeuristik.csv"));

			while ((row1 = csvReaderEmergency.readLine()) != null) {
				String[] data = row1.split(";");

				try {
					chargingReasonList1.add(new chargingReason(data[8], data[9]));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderEmergency.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		int sumEmergencyCharges1 = 0;
		int sumOverallCharging1;
		for (int i = 0; i < chargingReasonList1.size(); i++) {
			String dummyBefore = chargingReasonList1.get(i).getBefore();
			if (dummyBefore.contains("Yes")) {
				sumEmergencyCharges1++;
			}
		}
		sumOverallCharging1 = sumEmergencyCharges1;
		seriesEmergencyCharging.getData().add(new XYChart.Data("Emergency Charging", sumEmergencyCharges1));
		seriesEmergencyCharging.getData().add(new XYChart.Data("Total number of charging", sumOverallCharging1));

		///////////////////////////

		XYChart.Series seriesIdleTime = new XYChart.Series();
		seriesIdleTime.setName("Idletime Charging");
		View.idleTimeChargingButton.setSelected(true);
		ArrayList<Customer> knots2 = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
				DataDashboard.getCampusSize());
		TestRun r2 = new TestRun(knots2);

		ArrayList<chargingReason> chargingReasonList2 = new ArrayList<>();
		BufferedReader csvReaderIdleTime;
		String row2;

		try {
			csvReaderIdleTime = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsIdleTimeHeuristik.csv"));

			while ((row2 = csvReaderIdleTime.readLine()) != null) {
				String[] data = row2.split(";");

				try {
					chargingReasonList2.add(new chargingReason(data[8], data[9]));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderIdleTime.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		int sumOverallCharging2;
		int sumEmergencyCharges2 = 0;
		int sumIdleTimeCharges = 0;
		int sumSOCnotenough = 0;

		for (int i = 0; i < chargingReasonList2.size(); i++) {
			String dummyBefore = chargingReasonList2.get(i).getBefore();
			String dummyAfter = chargingReasonList2.get(i).getAfter();
			if (dummyBefore.contains("Yes")) {
				sumEmergencyCharges2++;
			}
			if (dummyAfter.contains("idletime")) {
				sumIdleTimeCharges++;
			}
			if (dummyAfter.contains("SOC not enough for next order.")) {
				sumSOCnotenough++;
			}

		}

		sumOverallCharging2 = sumEmergencyCharges2 + sumIdleTimeCharges + sumSOCnotenough;
		seriesIdleTime.getData().add(new XYChart.Data("Total number of charging", sumOverallCharging2));
		seriesIdleTime.getData().add(new XYChart.Data("Emergency Charging", sumEmergencyCharges2));
		seriesIdleTime.getData().add(new XYChart.Data("Opportunity charging", sumIdleTimeCharges));
		seriesIdleTime.getData().add(new XYChart.Data("SOC not enough for next order.", sumSOCnotenough));

		/////////////////////////

		Scene scene = new Scene(barChart, 800, 1600);
		barChart.getData().addAll(seriesIntervalCharging, seriesIdleTime, seriesEmergencyCharging);
		fxPanel.setScene(scene);

	}
}
