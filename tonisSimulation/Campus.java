package testYourCode;
import java.util.ArrayList;

//erstellt angegebene Anzahl an Knoten auf 2D Koordinatensystem von 0-50 auf x- und y-Achse
public class Campus {
	int amountCustomers;
	ArrayList<Knot> knots = new ArrayList<>();
	int depotRandom;

	public Campus(int amountCustomers) {
		this.amountCustomers = amountCustomers;

		// erzeugt Knoten auf Koordinatensystem 0-2000 meter
		for (Integer i = 0; i < amountCustomers; i++) {
			double x = Math.random() * 1000;
			double y = Math.random() * 1000;
			Knot k = new Knot(x, y, i, 0);
			knots.add(k);
		}

		// zufällige Zuteilung Depot, Ladestationen, sichergehen, dass nicht gleich.
		depotRandom = amountCustomers + 1;
		int chargingStationOneRandom = amountCustomers + 1;
		int chargingStationTwoRandom = amountCustomers + 1;

		while (true) {
			depotRandom = (int) (Math.random() * amountCustomers);
			chargingStationOneRandom = (int) (Math.random() * amountCustomers);
			chargingStationTwoRandom = (int) (Math.random() * amountCustomers);

			if (chargingStationOneRandom != depotRandom && chargingStationOneRandom != chargingStationTwoRandom
					&& chargingStationTwoRandom != depotRandom) {
				break;
			}
		}

		try {
			knots.get(depotRandom).setCharacter(1);
			knots.get(chargingStationOneRandom).setCharacter(-1);
			knots.get(chargingStationTwoRandom).setCharacter(-1);
			System.out.println("Assignment depot/charging stations successful.");
			System.out.println("Depot ID: " + depotRandom);
			System.out.println("Charging Station 1 ID: " + chargingStationOneRandom);
			System.out.println("Charging Station 2 ID: " + chargingStationTwoRandom);
		} catch (Exception e) {
			System.out.println("Assignment depot/charging stations not successful.");
			System.out.println("Depot ID: " + depotRandom);
			System.out.println("Charging Station 1 ID: " + chargingStationOneRandom);
			System.out.println("Charging Station 2 ID: " + chargingStationTwoRandom);
		}
	}

	ArrayList<Knot> getKnots() {
		ArrayList<Knot> dummy = knots;
		return dummy;
	}

	Knot getDepot() {
		return knots.get(depotRandom);
	}
}
