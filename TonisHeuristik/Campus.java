package Dashboard;

import java.util.ArrayList;
import java.util.Random;

//erstellt angegebene Anzahl an Knoten auf kartesischem Koordinatensystem
public class Campus {

	// static ArrayList<DSCustomer> chargingStations = new ArrayList<DSCustomer>();
	static ArrayList<Customer> knots = new ArrayList<>();

	// public DSCampus(int amountCustomers, int size) {
	static ArrayList<Customer> getKnots(int amountCustomers, int size) {
		knots.clear();
		Random generator = new Random(1);
		double x;
		double y;
		int[] occupied = new int[2];
		occupied[0] = 0;
		occupied[1] = 0;

		Customer depot = new Customer(0, 0, 0, occupied);
		knots.add(depot);

		for (int i = 0; i < DataDashboard.getNumberOfCustomers(); i++) {

			double radius = generator.nextDouble() * DataDashboard.getCampusSize();
			double degree = generator.nextDouble() * 360;

			x = radius * Math.cos(degree);
			y = radius * Math.sin(degree);

			knots.add(new Customer(x, y, i + 1, occupied));
		}

		return knots;

	}

	static public Customer getDepot() {
		Customer depot = knots.get(0);
		return depot;
	}

}
