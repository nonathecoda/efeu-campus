package BachelorarbeitAntonia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Methods {

	// erstellt die Bot Liste
	static public ArrayList<Bot> createBotList(Customer depot) {

		ArrayList<Bot> bots = new ArrayList<>();
		for (int i = 0; i < Data.numberOfBots; i++) {

			bots.add(new Bot(i, Data.batteryCapacity, depot, 0));

		}
		return bots;
	}

	// erstellt zufällige Aufträge, mit Depot als Pickup oder als DeliveryPoint
	static public ArrayList<Order> createOrderList(ArrayList<Customer> knots, Customer depot) {
		ArrayList<Order> orderList = new ArrayList<>();

		int startingTime = 0;
		Customer pickup = null;
		Customer delivery = null;

		Random generator = new Random(45);

		// erstelle Liste mit Zufallszahlen
		int[] randomListe = new int[Data.numberOfOrders * 3];
		for (int i = 0; i < (randomListe.length - 3); i = i + 3) {
			randomListe[i] = generator.nextInt(Data.durationWorkdayInMinutes);
			randomListe[i + 1] = generator.nextInt(knots.size());
			randomListe[i + 2] = generator.nextInt(knots.size());
		}

		// erstellt orders, die das Depot entweder als Pickup oder als Delivery haben
		for (int i = 0; i < Data.numberOfOrders; i++) {

			int ran = generator.nextInt(2);

			switch (ran) {
			case 0:
				pickup = knots.get(randomListe[i * 3 + 2]); // nicht depot
				delivery = depot;// Depot
				startingTime = randomListe[i * 3];
				break;
			case 1:
				pickup = depot; // Depot
				delivery = knots.get(randomListe[i * 3 + 1]); // nicht depot
				startingTime = randomListe[i * 3];
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
					currentOrder.getDeliveryID()) / Data.velocity;
			double batteryConsumption = executionTime * Data.dechargingSpeed;

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

		chargingTime = Math.min(chargingTime, ((Data.batteryCapacity - currentBot.getSoc()) / Data.chargingSpeed));
		double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(), chargingStation)
				/ Data.velocity;

		currentBot.setTimeTracker((int) (currentBot.getTimeTracker() + minutesToChargingStation + chargingTime));
		currentBot.setSoc(currentBot.getSoc() + (chargingTime * Data.chargingSpeed));
		currentBot.setLocation(chargingStation);
	}

	static Customer chooseChargingStation(Bot currentBot, Order nextOrder) {

		/*
		 * nicht abgesichtert: was machen, wenn keine ChargingStations in reach?
		 * 
		 * nicht überprüft, ob chargingstation!=depot available ist!
		 */

		Customer chargingStation = null;

		if (currentBot.getLocation() == MainMethod.depot) {
			chargingStation = MainMethod.depot;

		} else {

			ArrayList<Customer> chargingStationsInReach = new ArrayList<>();
			for (int i = 0; i < Campus.chargingStations.size(); i++) {

				double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(),
						Campus.chargingStations.get(i)) / Data.velocity;
				double batteryConsumption = minutesToChargingStation * Data.dechargingSpeed;

				if (batteryConsumption < currentBot.getSoc()) {
					chargingStationsInReach.add(Campus.chargingStations.get(i));
				}

			}

			double minimalDistance = Data.campusSize * 4;

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
