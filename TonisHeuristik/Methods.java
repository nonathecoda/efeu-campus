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

			bots.add(new Bot(i, DataDashboard.batteryCapacity, depot, 0, dummyList, 0, 0));

		}
		return bots;
	}

	// erstellt zufällige Aufträge, mit Depot als Pickup oder als DeliveryPoint
	static public ArrayList<Order> createOrderList() {
		ArrayList<Order> orderList = new ArrayList<>();

		double mean = (DataDashboard.getEndPT() - DataDashboard.getStartPT()) / 2;

		int earliest = 0;
		Customer pickup = null;
		Customer delivery = null;

		Random generator = new Random(1);

		// Order(Customer pickUpID, Customer deliveryID, int earliest, int latest, int
		// isTakenByBot, boolean delayed

		for (int i = 0; i < DataDashboard.getNumberOfOrders(); i++) {

			int k = generator.nextInt(2);

			switch (k) {
			case 0:
				pickup = TestRun.customers
						.get((int) Math.round(generator.nextDouble() * (DataDashboard.getNumberOfCustomers() - 1) + 1));
				delivery = Campus.getDepot();
				break;
			case 1:
				pickup = Campus.getDepot();
				delivery = TestRun.customers
						.get((int) Math.round(generator.nextDouble() * (DataDashboard.getNumberOfCustomers() - 1) + 1));
				break;
			}
			if (DataDashboard.getNormalDistribution() == true) {
				int gauss = 0;
				boolean gaussInRange = false;
				while (gaussInRange == false) {
					gauss = (int) Math.round(generator.nextGaussian() * DataDashboard.getStandardDeviation() + mean);
					if (gauss >= 0 && gauss <= DataDashboard.getDayDurationTextField() - 60) {
						gaussInRange = true;
					}
				}
				earliest = gauss;
			} else {
				earliest = (int) Math.round(generator.nextDouble() * (DataDashboard.getDayDurationTextField() - 60));
			}

			orderList.add(
					new Order(i, pickup, delivery, earliest, earliest + 60, DataDashboard.numberOfBots + 1, false));
		}
		Collections.sort(orderList, Comparator.comparing(Order::getEarliest));

		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).setId(i);
		}

		return orderList;

	}

	public static double calculateDistance(Customer currentLocation, Customer pickUp, Customer delivery) {
		double dist1X = Math.abs(currentLocation.getX() - pickUp.getX());
		double dist1Y = Math.abs(currentLocation.getY() - pickUp.getY());
		double dist1 = Math.sqrt(Math.pow(dist1X, 2) + Math.pow(dist1Y, 2)); // Luftlinie von currentLocation zu
																				// pickUp (pythagoras)

		double dist2X = Math.abs(pickUp.getX() - delivery.getX());
		double dist2Y = Math.abs(pickUp.getY() - delivery.getY());
		double dist2 = Math.sqrt(Math.pow(dist2X, 2) + Math.pow(dist2Y, 2)); // Luftlinie von pickUp zu delivery
																				// (pythagoras)

		double overallDistance = dist1 + dist2;

		return overallDistance;
	}

	public static double calculateDistance(Customer currentLocation, Customer destination) {

		double dist1X = Math.abs(currentLocation.getX() - destination.getX());
		double dist1Y = Math.abs(currentLocation.getY() - destination.getY());
		double overallDistance = Math.sqrt(Math.pow(dist1X, 2) + Math.pow(dist1Y, 2)); // Luftlinie von
																						// currentLocation zu
		// pickUp (pythagoras)

		return overallDistance;
	}

	static void assignNextBots(ArrayList<Order> orderList, int index, ArrayList<Bot> bots) {

		ArrayList<Bot> botCopy = new ArrayList<Bot>();

		for (int i = 0; i < bots.size(); i++) {
			botCopy.add(bots.get(i));
		}
		try {

			for (int i = index + DataDashboard.numberOfBots; i < index + DataDashboard.numberOfBots * 2; i++) {
				Order currentOrder = orderList.get(i);
				currentOrder.setIsTaken(DataDashboard.numberOfBots + 1);
			}

			for (int i = index + DataDashboard.numberOfBots; i < index + DataDashboard.numberOfBots * 2; i++) {
				double minimalDistance = DataDashboard.getCampusSize() * 2;
				Order currentOrder = orderList.get(i);
				if (currentOrder.getIsTaken() == DataDashboard.numberOfBots + 1) {
					Bot assignedBot = null;
					for (int j = 0; j < botCopy.size(); j++) {
						Bot currentBot = botCopy.get(j);
						double distance = calculateDistance(currentBot.getLocation(), currentOrder.getPickUpID());

						if (distance < minimalDistance) {
							minimalDistance = distance;
							assignedBot = currentBot;
						}

					}
					botCopy.remove(assignedBot);
					if (assignedBot == null) {

						System.out.println("FEHLER: Order :" + currentOrder.getId() + ", botCopy size: "
								+ botCopy.size() + ", Minimal Distance: " + minimalDistance);
					}
					currentOrder.setIsTaken(assignedBot.getId());
				}
				// System.out.println("Order: " + currentOrder.getId() + ", Bot: " +
				// assignedBot.getId());
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	static Order identifyNextOrder(Bot bot, int index, ArrayList<Order> orders) {
		Order order = null;

		for (int i = index + 1; i < index + DataDashboard.numberOfBots * 2; i++) {
			int assignedBotId = orders.get(i).getIsTaken();
			System.out.println("assignedBotId: " + assignedBotId + " Order: " + orders.get(i).getId());
			if (assignedBotId == bot.getId()) {
				order = orders.get(i);
				break;
			}

		}

		if (order == null) {
			System.out.println("Fehler: ");
		}

		return order;
	}

	static void charge(Bot currentBot, double chargingTime, Customer chargingStation) {

		chargingTime = Math.min(chargingTime,
				((DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed));

		int minutesToChargingStation = (int) Math
				.round(Methods.calculateDistance(currentBot.getLocation(), chargingStation) / DataDashboard.velocity);

		if (chargingStation != Campus.getDepot()) {

			int[] occupied = new int[2];
			occupied[0] = currentBot.getTimeTracker();
			occupied[1] = (int) Math.round(currentBot.getTimeTracker() + minutesToChargingStation + chargingTime);
			chargingStation.setOccupied(occupied);
		}
		currentBot.setTimeTracker(
				(int) Math.round(currentBot.getTimeTracker() + minutesToChargingStation + chargingTime));
		currentBot.setSoc(currentBot.getSoc() + (chargingTime * DataDashboard.chargingSpeed));
		currentBot.setLocation(chargingStation);

	}

	static Customer chooseChargingStation(Bot currentBot, Order nextOrder) {

		Customer chargingStation = null;

		if (currentBot.getLocation() == Campus.getDepot()) {
			chargingStation = Campus.getDepot();

		} else {

			for (int i = 0; i < DataDashboard.getAvailableChargingStations().size(); i++) {

				double minimalDuration = DataDashboard.getDayDurationTextField();
				double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(),
						DataDashboard.getAvailableChargingStations().get(i)) / DataDashboard.velocity;
				double batteryConsumption = minutesToChargingStation * DataDashboard.dechargingSpeed;

				if (batteryConsumption < currentBot.getSoc() && (DataDashboard.getAvailableChargingStations().get(i)
						.checkIfFree(currentBot.getTimeTracker(), minutesToChargingStation) == true)) {

					if (minutesToChargingStation < minimalDuration) {
						minimalDuration = minutesToChargingStation;
						chargingStation = DataDashboard.getAvailableChargingStations().get(i);
					}
				}
			}
		}

		if (chargingStation == null) {
			View.outputLabel.setText("Bot Nr. " + currentBot.getId() + " couldn't reach a charging station.");
		}

		return chargingStation;
	}

	static Customer chooseClosestChargingStation(Bot currentBot) {

		Customer chargingStation = null;

		if (currentBot.getLocation() == Campus.getDepot()) {
			chargingStation = Campus.getDepot();

		} else {
			double minimalDuration = DataDashboard.getDayDurationTextField();

			for (int i = 0; i < DataDashboard.getAvailableChargingStations().size(); i++) {

				double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(),
						DataDashboard.getAvailableChargingStations().get(i)) / DataDashboard.velocity;
				double batteryConsumption = minutesToChargingStation * DataDashboard.dechargingSpeed;

				if (batteryConsumption < currentBot.getSoc() && (DataDashboard.getAvailableChargingStations().get(i)
						.checkIfFree(currentBot.getTimeTracker(), minutesToChargingStation) == true)) {

					if (minutesToChargingStation < minimalDuration) {
						minimalDuration = minutesToChargingStation;
						chargingStation = DataDashboard.getAvailableChargingStations().get(i);
					}

				}

			}

		}

		return chargingStation;
	}

}
