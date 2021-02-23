
import java.util.ArrayList;
import java.util.Random;

//erstellt angegebene Anzahl an Knoten auf kartesischem Koordinatensystem
public class CampusPool {
	int amountCustomers;
	int size;
	int depotRandom;
	KnotPool chargingStationOneRandom;
	KnotPool chargingStationTwoRandom;
	KnotPool depotKnot;
	static ArrayList<KnotPool> chargingStations = new ArrayList<KnotPool>();
	ArrayList<KnotPool> knots = new ArrayList<>();

	public CampusPool(int amountCustomers, int size) {
		this.amountCustomers = amountCustomers;
		this.size = size;

		// zufallszahlen für x/y koordinaten (<size) werden generiert
		Random generator = new Random(700);
		double[] randomListe = new double[amountCustomers * 2];
		for (int i = 0; i < (randomListe.length - 2); i = i + 2) {
			randomListe[i] = generator.nextDouble() * size;
			randomListe[i + 1] = generator.nextDouble() * size;
		}

		// erzeugt Knoten auf Koordinatensystem
		for (Integer i = 0; i < amountCustomers; i++) {
			double x = randomListe[i * 2];
			double y = randomListe[i * 2 + 1];
			KnotPool k = new KnotPool(x, y, i, 0, true);
			knots.add(k);
		}

		depotKnot = knots.get(knots.size() / 2);// irgendein Knoten wird als Depot definiert
		chargingStationOneRandom = knots.get(knots.size() / 3);// irgendein Knoten wird als
		chargingStationTwoRandom = knots.get(knots.size() / 4);// Chargingstation definiert

		chargingStations.add(chargingStationOneRandom);
		chargingStations.add(chargingStationTwoRandom);

	}

	ArrayList<KnotPool> getKnots() {
		ArrayList<KnotPool> dummy = knots;
		return dummy;
	}

	KnotPool getDepot() {
		return depotKnot;
		// return knots.get(depotRandom);
	}

}
