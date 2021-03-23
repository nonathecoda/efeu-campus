package Dashboard;

import java.util.ArrayList;
import java.util.Random;

//erstellt angegebene Anzahl an Knoten auf kartesischem Koordinatensystem
public class Campus {

	static Customer chargingStationOneRandom;
	static Customer chargingStationTwoRandom;

	// static ArrayList<DSCustomer> chargingStations = new ArrayList<DSCustomer>();
	static ArrayList<Customer> knots = new ArrayList<>();

	
	
	// public DSCampus(int amountCustomers, int size) {
	static ArrayList<Customer> getKnots(int amountCustomers, int size) {

		knots.clear();
		
		// zufallszahlen für x/y koordinaten (<size) werden generiert
		Random generator = new Random(87);
		double[] randomListe = new double[amountCustomers * 2];
		for (int i = 0; i < (randomListe.length - 2); i = i + 2) {
			randomListe[i] = generator.nextDouble() * size;
			randomListe[i + 1] = generator.nextDouble() * size;
		}

		// erzeugt Knoten auf Koordinatensystem
		for (Integer i = 0; i < amountCustomers; i++) {
			double x = randomListe[i * 2];
			double y = randomListe[i * 2 + 1];
			Customer k = new Customer(x, y, i);
			knots.add(k);
		}


		return knots;
	}

	static public ArrayList<Customer> getChargingStations() {
		chargingStationOneRandom = knots.get(knots.size() / 3);
		chargingStationTwoRandom = knots.get(knots.size() / 4);

		ArrayList<Customer> chargingStations = new ArrayList<>();
		chargingStations.add(chargingStationOneRandom);
		chargingStations.add(chargingStationTwoRandom);
		chargingStations.add(getDepot());
		return chargingStations;
	}

	static public Customer getDepot() {

		Customer depotKnot = RunSimulation.knots.get(knots.size() / 2);
		return depotKnot;
	}

}
