package Dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Methods {

	// erstellt die Bot Liste
	static public ArrayList<Bot> createBotList(Customer depot) {

		ArrayList<Bot> bots = new ArrayList<>();
		
		for (int i = 0; i < DataDashboard.numberOfBots; i++) {

			ArrayList<Double> dummyList = new ArrayList<>();

			bots.add(new Bot(i, DataDashboard.batteryCapacity, depot, 0, dummyList, 0));

		}
		return bots;
	}

	// erstellt zufällige Aufträge, mit Depot als Pickup oder als DeliveryPoint
	static public ArrayList<Order> createOrderList(ArrayList<Customer> knots, Customer depot) {
		ArrayList<Order> orderList = new ArrayList<>();

		int startingTime = 0;
		Customer pickup = null;
		Customer delivery = null;

		Random r = new Random(45);

		// erstelle Liste mit Zufallszahlen
		int gaussMean = DataDashboard.getStartPT() + ((DataDashboard.getEndPT() - DataDashboard.getStartPT()) / 2);
		int gaussStanDev = DataDashboard.getStandardDeviation();
		int[] randomList = new int[DataDashboard.getNumberOfOrders() * 3];
		for (int i = 0; i < (randomList.length - 3); i = i + 3) {

			int gaussDummy = (int) Math.round(r.nextGaussian() * gaussStanDev + gaussMean);

			if (gaussDummy >= 0 && gaussDummy <= DataDashboard.durationWorkdayInMinutes) {
				
				randomList[i] = r.nextInt(knots.size());
				randomList[i + 1] = r.nextInt(knots.size());

				if (DataDashboard.getNormalDistribution() == true) {
					randomList[i + 2] = gaussDummy;
				} else {
					randomList[i + 2] = r.nextInt(DataDashboard.durationWorkdayInMinutes);
				}
			} else {
				i = i - 3;
			}
		}

		// erstellt orders, die das Depot entweder als Pickup oder als Delivery haben
		for (int i = 0; i < DataDashboard.getNumberOfOrders(); i++) {

			int ran = r.nextInt(2);

			switch (ran) {
			case 0:

				pickup = knots.get(randomList[i * 3]); // nicht depot
				delivery = depot;// Depot
				startingTime = randomList[i * 3 + 2];
				break;
			case 1:
				pickup = depot; // Depot

				delivery = knots.get(randomList[i * 3 + 1]); // nicht depot
				startingTime = randomList[i * 3 + 2];
				break;

			}

			Order dummyOrder = new Order(pickup, delivery, startingTime);

			orderList.add(dummyOrder);
		}
		// Liste nach startingTime sortieren
		Collections.sort(orderList, Comparator.comparing(Order::getStartingTime));
		return orderList;
	}

	public static double calculateDistance(Customer currentLocation, Customer pickUp, Customer delivery) {
		double dist1X = Math.abs(currentLocation.getX() - pickUp.getX());
		double dist1Y = Math.abs(currentLocation.getY() - pickUp.getY());
		double dist1 = (Math.sqrt(Math.pow(dist1X, 2) + Math.pow(dist1Y, 2))); // Luftlinie von currentLocation zu
																				// pickUp (pythagoras)

		double dist2X = Math.abs(pickUp.getX() - delivery.getX());
		double dist2Y = Math.abs(pickUp.getY() - delivery.getY());
		double dist2 = (Math.sqrt(Math.pow(dist2X, 2) + Math.pow(dist2Y, 2))); // Luftlinie von pickUp zu delivery
																				// (pythagoras)

		double overallDistance = dist1 + dist2;

		return overallDistance;
	}

	public static double calculateDistance(Customer currentLocation, Customer destination) {
		double dist1X = Math.abs(currentLocation.getX() - destination.getX());
		double dist1Y = Math.abs(currentLocation.getY() - destination.getY());
		double overallDistance = (Math.sqrt(Math.pow(dist1X, 2) + Math.pow(dist1Y, 2))); // Luftlinie von
																							// currentLocation zu
		// pickUp (pythagoras)

		return overallDistance;
	}

	// Weise Bot zu Order zu, wenn er die Order noch schafft
	public static int assignRobotToTask(Order currentOrder, ArrayList<Bot> bots) {
		int result = 600;

		Collections.sort(bots, Comparator.comparing(Bot::getTimeTracker));

		for (int i = 0; i < bots.size(); i++) {
			
			Bot dummyBot = bots.get(i);

			double executionTime = Methods.calculateDistance(dummyBot.getLocation(), currentOrder.getPickUpID(),
					currentOrder.getDeliveryID()) / DataDashboard.velocity;
			double batteryConsumption = executionTime * DataDashboard.dechargingSpeed;

			if (dummyBot.getTimeTracker() <= currentOrder.getStartingTime() && dummyBot.getSoc() > batteryConsumption) {
				result = i;
				// System.out.println("Assigned Bot ID: " + dummyBot.getId());
				break;
			}
		}
		

		return result;
	}

	static void charge(Bot currentBot, double chargingTime, Customer chargingStation) {
		/*
		 * wird im moment voll aufgeladen
		 */

		chargingTime = Math.min(chargingTime, ((DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed));
		double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(), chargingStation)
				/ DataDashboard.velocity;

		currentBot.setTimeTracker((int) (currentBot.getTimeTracker() + minutesToChargingStation + chargingTime));
		currentBot.setSoc(currentBot.getSoc() + (chargingTime * DataDashboard.chargingSpeed));
		currentBot.setLocation(chargingStation);
	}

	static Customer chooseChargingStation(Bot currentBot, Order nextOrder) {

		/*
		 * nicht abgesichtert: was machen, wenn keine ChargingStations in reach?
		 * 
		 * nicht überprüft, ob chargingstation!=depot available ist!
		 */

		Customer chargingStation = null;

		if (currentBot.getLocation() == Campus.getDepot()) {
			chargingStation = Campus.getDepot();

		} else {

			ArrayList<Customer> chargingStationsInReach = new ArrayList<>();
			for (int i = 0; i < Campus.getChargingStations().size(); i++) {

				double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(),
						Campus.getChargingStations().get(i)) / DataDashboard.velocity;
				double batteryConsumption = minutesToChargingStation * DataDashboard.dechargingSpeed;

				if (batteryConsumption < currentBot.getSoc()) {
					chargingStationsInReach.add(Campus.getChargingStations().get(i));
				}

			}

			double minimalDistance = DataDashboard.getCampusSize() * 4;

			for (int i = 0; i < chargingStationsInReach.size(); i++) {

				double distance = Methods.calculateDistance(currentBot.getLocation(), chargingStationsInReach.get(i),
						nextOrder.getPickUpID());

				if (distance < minimalDistance) {

					minimalDistance = distance;
					chargingStation = chargingStationsInReach.get(i);
				}
			}

		}

		return chargingStation;
	}

}
