package Dashboard;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.scene.chart.XYChart;

public class RegressionData {

	// Endogene parameter:
	// trigger at xx%
	// charging goal
	// charge while cs reachable (jetzt immer damit gerechnet)
	// how much idletime to trigger opportunity charging

	static void getOCData() {
		DecimalFormat df = new DecimalFormat("0.00");

		try {

			FileWriter txtWriter = new FileWriter("OCRegression.csv");

			System.out.println("Successfully wrote to the file, wait...");

			txtWriter.append("trigger");
			txtWriter.append(";");
			txtWriter.append("charginggoal");
			txtWriter.append(";");
			txtWriter.append("idletimetrigger");
			txtWriter.append(";");
			txtWriter.append("missedOrders");
			txtWriter.append(";");
			txtWriter.append("lateOrders");
			txtWriter.append(";");
			txtWriter.append("averageDelay");
			txtWriter.append(";");
			txtWriter.append("averageSOC");
			txtWriter.append(";");
			txtWriter.append("consumptionPeakTime");
			txtWriter.append('\n');

			// nur opportunity charging

			View.opportunityChargingButton.setSelected(true);

			for (double i = 0.05; i <= 0.5; i = i + 0.05) {

				for (int j = (int) (DataDashboard.batteryCapacity
						* DataDashboard.getEmergencyCharge()); j <= DataDashboard.batteryCapacity; j = j + 10) {

					for (int k = 5; k <= 30; k = k + 5) {

						View.percentTextField.setText("" + i);
						View.sliderValueTextField.setText("" + j);
						View.idleTimeTriggerTextField.setText("" + k);

						ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
								DataDashboard.getCampusSize());
						RunSimulation r = new RunSimulation(knots);

						String iString = String.valueOf(i);
						iString = iString.replaceAll("\\.", ",");

						String avgSocString = String.valueOf(df.format(RunSimulation.averageSOC));
						avgSocString = avgSocString.replaceAll("\\.", ",");

						String consumptionString = String.valueOf(df.format(RunSimulation.consumptionPeakTime));
						consumptionString = consumptionString.replaceAll("\\.", ",");

						txtWriter.append(iString);
						txtWriter.append(";");
						txtWriter.append("" + j);
						txtWriter.append(";");
						txtWriter.append("" + k);
						txtWriter.append(";");
						txtWriter.append("" + RunSimulation.missedOrders);
						txtWriter.append(";");
						txtWriter.append("" + RunSimulation.lateOrders);
						txtWriter.append(";");
						txtWriter.append("" + RunSimulation.avgDelay);
						txtWriter.append(";");
						txtWriter.append(avgSocString);
						txtWriter.append(";");
						txtWriter.append(consumptionString);
						txtWriter.append('\n');

					}

				}

			}
			txtWriter.flush();
			txtWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println("DONE.");

	}

	static void getICData() {

		DecimalFormat df = new DecimalFormat("0.00");
		try {

			FileWriter txtWriter = new FileWriter("ICRegression.csv");

			System.out.println("Successfully wrote to the file, wait...");

			txtWriter.append("trigger");
			txtWriter.append(";");
			txtWriter.append("charginggoal");
			txtWriter.append(";");
			txtWriter.append("chargingFrequency");
			txtWriter.append(";");
			txtWriter.append("missedOrders");
			txtWriter.append(";");
			txtWriter.append("lateOrders");
			txtWriter.append(";");
			txtWriter.append("averageDelay");
			txtWriter.append(";");
			txtWriter.append("averageSOC");
			txtWriter.append(";");
			txtWriter.append("consumptionPeakTime");
			txtWriter.append('\n');

			// nur opportunity charging

			View.intervalChargingButton.setSelected(true);

			for (double i = 0.05; i <= 0.5; i = i + 0.05) {

				for (int j = (int) (DataDashboard.batteryCapacity
						* DataDashboard.getEmergencyCharge()); j <= DataDashboard.batteryCapacity; j = j + 10) {

					for (int k = 10; k <= DataDashboard.getDayDurationTextField(); k = k + 10) {

						View.percentTextField.setText("" + i);
						View.sliderValueTextField.setText("" + j);
						View.intervalTextField.setText("" + k);

						ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
								DataDashboard.getCampusSize());
						RunSimulation r = new RunSimulation(knots);

						String iString = String.valueOf(i);
						iString = iString.replaceAll("\\.", ",");

						String avgSocString = String.valueOf(df.format(RunSimulation.averageSOC));
						avgSocString = avgSocString.replaceAll("\\.", ",");

						String consumptionString = String.valueOf(df.format(RunSimulation.consumptionPeakTime));
						consumptionString = consumptionString.replaceAll("\\.", ",");

						txtWriter.append(iString);
						txtWriter.append(";");
						txtWriter.append("" + j);
						txtWriter.append(";");
						txtWriter.append("" + k);
						txtWriter.append(";");
						txtWriter.append("" + RunSimulation.missedOrders);
						txtWriter.append(";");
						txtWriter.append("" + RunSimulation.lateOrders);
						txtWriter.append(";");
						txtWriter.append("" + RunSimulation.avgDelay);
						txtWriter.append(";");
						txtWriter.append(avgSocString);
						txtWriter.append(";");
						txtWriter.append(consumptionString);
						txtWriter.append('\n');

					}

				}

			}
			txtWriter.flush();
			txtWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println("DONE.");

	}

	static void getECData() {

		DecimalFormat df = new DecimalFormat("0.00");
		try {

			FileWriter txtWriter = new FileWriter("ECRegression.csv");

			System.out.println("Successfully wrote to the file, wait...");

			txtWriter.append("trigger");
			txtWriter.append(";");
			txtWriter.append("charginggoal");
			txtWriter.append(";");
			txtWriter.append("missedOrders");
			txtWriter.append(";");
			txtWriter.append("lateOrders");
			txtWriter.append(";");
			txtWriter.append("averageDelay");
			txtWriter.append(";");
			txtWriter.append("averageSOC");
			txtWriter.append(";");
			txtWriter.append("consumptionPeakTime");
			txtWriter.append('\n');

			// nur opportunity charging

			View.emergencyChargingButton.setSelected(true);

			for (double i = 0.05; i <= 0.5; i = i + 0.05) {

				for (int j = (int) (DataDashboard.batteryCapacity
						* DataDashboard.getEmergencyCharge()); j <= DataDashboard.batteryCapacity; j = j + 10) {

					View.percentTextField.setText("" + i);
					View.sliderValueTextField.setText("" + j);

					ArrayList<Customer> knots = Campus.getKnots(DataDashboard.getNumberOfCustomers(),
							DataDashboard.getCampusSize());
					RunSimulation r = new RunSimulation(knots);

					String iString = String.valueOf(i);
					iString = iString.replaceAll("\\.", ",");

					String avgSocString = String.valueOf(df.format(RunSimulation.averageSOC));
					avgSocString = avgSocString.replaceAll("\\.", ",");

					String consumptionString = String.valueOf(df.format(RunSimulation.consumptionPeakTime));
					consumptionString = consumptionString.replaceAll("\\.", ",");

					txtWriter.append(iString);
					txtWriter.append(";");
					txtWriter.append("" + j);
					txtWriter.append(";");
					txtWriter.append("" + RunSimulation.missedOrders);
					txtWriter.append(";");
					txtWriter.append("" + RunSimulation.lateOrders);
					txtWriter.append(";");
					txtWriter.append("" + RunSimulation.avgDelay);
					txtWriter.append(";");
					txtWriter.append(avgSocString);
					txtWriter.append(";");
					txtWriter.append(consumptionString);
					txtWriter.append('\n');

				}

			}
			txtWriter.flush();
			txtWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println("DONE.");

	}
}
