package Dashboard;

public class DataDashboard {

	static int numberOfBots = 5;
	static int durationWorkdayInMinutes = 480; // wie lange dauert der Arbeitstag? (480min = 8h)
	static double velocity = 90; // Geschwindigkeit Roboter in m/min, 90 m/min entsprechen 1.5 m/sec
	static double chargingSpeed = 16.6666667; // Wh/minute
	static double dechargingSpeed = 6.6666667; // Wh/Minute
	public static double batteryCapacity = 556.0;

	public static int getCampusSize() {// Maße des quadratischen Campus in Metern
		return Integer.valueOf(View.campusSizeTextField.getText());
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

	public static int getStartPT() {// Maße des quadratischen Campus in Metern
		return Integer.valueOf(View.startPTTextField.getText());
	}

	public static int getEndPT() {// Maße des quadratischen Campus in Metern
		return Integer.valueOf(View.endPTTextField.getText());
	}

	public static boolean getNormalDistribution() {
		return View.normalDistributionRadioButton.isSelected();
	}

	// SOC nicht über 80% laden
	// Paketaufkommen normal und gleichverteilung
	// Verteilung zu wann gehen Fahrzeuge laden
	// wie wirkt sich SOC Goal auf performance aus?
	// Ergebnisse visualisieren

}
