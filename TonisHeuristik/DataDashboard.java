package Dashboard;

import java.util.ArrayList;
import java.util.Random;

public class DataDashboard {

	static int numberOfBots = 5;
	static double velocity = 90; // Geschwindigkeit Roboter in m/min, 90 m/min entsprechen 1.5 m/sec
	static double chargingSpeed = 16.6666667; // Wh/minute
	static double dechargingSpeed = 6.6666667; // Wh/Minute
	static double batteryCapacity = 556.0;
	static int defaultDelay = 30; // wie viel später soll eine verpasste Order angesetzt werden?

	public static int getCampusSize() {// Maße des quadratischen Campus in Metern
		return Integer.valueOf(View.campusSizeTextField.getText());
	}

	public static double getEmergencyCharge() {
		return Double.valueOf(View.percentTextField.getText());
	}

	public static int getNumberOfCustomers() {
		return Integer.valueOf(View.numberOfCustomersTextField.getText());
	}

	public static int getNumberOfOrders() {
		return Integer.valueOf(View.numberOfOrdersTextField.getText());
	}

	public static int getChargingFrequency() {
		return Integer.valueOf(View.intervalTextField.getText());
	}

	public static int getMean() {
		return Integer.valueOf(View.startPTTextField.getText());
	}

	public static int getStandardDeviation() {
		return Integer.valueOf(View.sdTextField.getText());
	}

	public static int getChargingGoal() {
		return Integer.valueOf(View.sliderValueTextField.getText());
	}

	public static int getStartPT() {
		return Integer.valueOf(View.startPTTextField.getText());
	}

	public static int getEndPT() {// 
		return Integer.valueOf(View.endPTTextField.getText());
	}
	
	public static int getIdleTimeTrigger() {
		return Integer.valueOf(View.idleTimeTriggerTextField.getText());
	}

	public static int getDayDurationTextField() {
		return Integer.valueOf(View.dayDurationTextField.getText());
	}

	public static boolean getNormalDistribution() {
		return View.normalDistributionRadioButton.isSelected();
	}

	public static boolean getEmergencyDefinition() {
		boolean result = true;
		if (View.underxRadioButton.isSelected() == true) {
			result = true;
		} else if (View.notReachableButton.isSelected() == true) {
			result = false;
		}
		return result;
	}

	public static ArrayList<Customer> getAvailableChargingStations() {
		ArrayList<Customer> chargingStations = new ArrayList<>();
		chargingStations.clear();
		String selection = (String) View.chargingStationComboBox.getSelectedItem();
		if (selection.contains("Only charge at depot.")) {
			chargingStations.add(Campus.getDepot());

		} else {

			Random generator = new Random(1);

			for (int i = 0; i < 2; i++) {
				chargingStations.add(
						RunSimulation.customers.get((int) (generator.nextDouble() * (RunSimulation.customers.size() - 1) + 1)));
			}

			chargingStations.add(Campus.getDepot());

		}
		
		return chargingStations;
	}

	public static int getHeuristicIterator() {
		int heuristicIterator = 0;

		if (View.noChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsNoConstraints.csv");
			heuristicIterator = 0;
		} else if (View.opportunityChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsOpportunityHeuristik.csv");
			heuristicIterator = 1;
		} else if (View.intervalChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsIntervalHeuristik.csv");
			heuristicIterator = 2;
		} else if (View.emergencyChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsEmergencyHeuristik.csv");
			heuristicIterator = 3;
		}
		return heuristicIterator;
	}

	// SOC nicht über 80% laden
	// Paketaufkommen normal und gleichverteilung
	// Verteilung zu wann gehen Fahrzeuge laden
	// wie wirkt sich SOC Goal auf performance aus?
	// Ergebnisse visualisieren
//multKorr
	//[1] 0.9992448
}
