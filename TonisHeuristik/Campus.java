package BachelorarbeitAntonia;

import java.util.ArrayList;
import java.util.Random;

//erstellt angegebene Anzahl an Knoten auf kartesischem Koordinatensystem
public class Campus {
	int amountCustomers;
	int size;
	int depotRandom;
	Customer chargingStationOneRandom;
	Customer chargingStationTwoRandom;
	Customer depotKnot;
	static ArrayList<Customer> chargingStations = new ArrayList<Customer>();
	ArrayList<Customer> knots = new ArrayList<>();

	public Campus(int amountCustomers, int size) {
		this.amountCustomers = amountCustomers;
		this.size = size;

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

		depotKnot = knots.get(knots.size() / 2);// irgendein Knoten wird als Depot definiert
		chargingStationOneRandom = knots.get(knots.size() / 3);// irgendein Knoten wird als
		chargingStationTwoRandom = knots.get(knots.size() / 4);// Chargingstation definiert

		chargingStations.add(chargingStationOneRandom);
		chargingStations.add(chargingStationTwoRandom);
		chargingStations.add(depotKnot);

	}

	ArrayList<Customer> getKnots() {
		ArrayList<Customer> dummy = knots;
		return dummy;
	}

	Customer getDepot() {
		return depotKnot;
		// return knots.get(depotRandom);
	}

}
