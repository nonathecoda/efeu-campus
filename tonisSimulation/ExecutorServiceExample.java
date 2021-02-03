package testYourCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Millisekunden sollen minuten sein!

public class ExecutorServiceExample {

	static Campus campus = new Campus(10);
	static ArrayList<Knot> knots = campus.getKnots();
	static Knot depot = campus.getDepot();
	static ArrayList<ServiceBot> bots = createBotList(depot);
	static ArrayList<Order> orderList = createOrderList(knots, depot);
	static double velocity = 5;

	private static class Result {
		private final int wait;

		public Result(int code) {
			this.wait = code;
		}
	}

	static public ArrayList<ServiceBot> createBotList(Knot depot) {
		ArrayList<ServiceBot> bots = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			ServiceBot dummyBot = new ServiceBot(i, 1.0, depot);
			bots.add(dummyBot);
		}

		return bots;
	}

	static public ArrayList<Order> createOrderList(ArrayList<Knot> knots, Knot depot) {
		ArrayList<Order> orderList = new ArrayList<>();
		Knot randomPickUp = depot;
		Knot randomDelivery = depot;
		int randomStartingTime = 0;
		int randomHandlingTime = 0;

		for (int i = 0; i < 10; i++) {
			// 1 = pickup, 2 = delivery
			int ran = (int) ((Math.random() * (3 - 1)) + 1);
			switch (ran) {
			case 1:
				randomPickUp = knots.get((int) (Math.random() * knots.size())); // nicht depot
				randomDelivery = depot;// Depot
				randomStartingTime = (int) (Math.random() * 480);
				randomHandlingTime = (int) ((Math.random() * (30 - 5)) + 5);
				break;
			case 2:
				randomPickUp = depot; // Depot
				randomDelivery = knots.get((int) (Math.random() * knots.size())); // nicht depot
				randomStartingTime = (int) (Math.random() * 480);
				randomHandlingTime = (int) ((Math.random() * (30 - 5)) + 5);
				break;

			}

			Order dummyOrder = new Order(randomPickUp, randomDelivery, randomStartingTime, randomHandlingTime, true);
			orderList.add(dummyOrder);
		}

		// Liste nach startingTime sortieren
		Collections.sort(orderList, Comparator.comparing(Order::getStartingTime));

		return orderList;
	}

	public static Result compute(ServiceBot bot) throws InterruptedException {

		int iD = bot.getID();
		long timeTracker = 0;

		while (timeTracker <= 480) {

			switch (iD) { // nur, damit die Bots nicht gleichzeitig starten
			case 0:
				System.out.println("\nBot " + iD + " startet jetzt, CurrentTime: " + System.currentTimeMillis());
				break;
			case 1:
				Thread.sleep(1);
				timeTracker = timeTracker + 1;
				System.out.println("\nBot " + iD + " startet jetzt, CurrentTime: " + System.currentTimeMillis());
				break;
			case 2:
				Thread.sleep(2);
				timeTracker = timeTracker + 2;
				System.out.println("\nBot " + iD + " startet jetzt, CurrentTime: " + System.currentTimeMillis());
				break;
			case 3:
				Thread.sleep(3);
				timeTracker = timeTracker + 3;
				System.out.println("\nBot " + iD + " startet jetzt, CurrentTime: " + System.currentTimeMillis());
				break;
			case 4:
				Thread.sleep(4);
				timeTracker = timeTracker + 4;
				System.out.println("\nBot " + iD + " startet jetzt, CurrentTime: " + System.currentTimeMillis());
				break;
			}

			for (int i = 0; i < orderList.size(); i++) { // i < orderList.size()

				Order currentOrder = orderList.get(i);

				System.out.println(
						"\nBot Nr. " + iD + " berichtet: Order Nr. " + i + " ist " + currentOrder.getUnhandled());

				if (currentOrder.getUnhandled() == false) {

					System.out.println("\nOrder Nr. " + i + " von Bot Nr. " + iD + " nicht ausgeführt");

				} else {

					currentOrder.setUnhandled(false);
					System.out.println("\nOrder Nr. " + i + " von Bot Nr. " + iD + " auf " + currentOrder.getUnhandled()
							+ " gesetzt.");

					Knot currentLocation = bot.getLocation();
					Knot pickUp = currentOrder.getPickUpID();
					Knot delivery = currentOrder.getDeliveryID();

					// calculate distance from initial point to pickup to delivery:

					double overallDistance = currentOrder.calculateDistance(currentLocation, pickUp, delivery);
					double minutesNeeded = overallDistance / velocity / 60;

					System.out.println("\n Bot Nr.: " + bot.getID() + ", Startzeit: " + System.currentTimeMillis()
							+ ", Order Nr. " + i + ", Distanz: " + Math.round(overallDistance) + " Meter, Zeitaufwand: "
							+ Math.round(minutesNeeded) + " Minuten");

					bot.setLocation(delivery);
					Thread.sleep((long) (minutesNeeded));
					timeTracker = timeTracker + (long) minutesNeeded;

					// jetzt deine Heuristik!

					double soc = bot.getSoc();

					if (soc < 0.3) {
						double time = 1.0; // wie lange laden?
						bot.charge(soc, time);
						timeTracker = timeTracker + (long) time;

					} else {

						try {
							Order nextOrder = orderList.get(i + 1);
							double batteryConsumption = nextOrder.calculateBatteryConsumption(overallDistance);
							if (batteryConsumption > (soc - 0.3)) {
								double time = 1.0; // wie lange laden?
								bot.charge(soc, time);
								timeTracker = timeTracker + (long) time;
							} else {
								long idleTime = nextOrder.getStartingTime() - timeTracker;
								if (idleTime > 30) { // wie lange muss IdleTime mindestens sein?
									double time = 1.0; // wie lange laden?
									bot.charge(soc, time);
									timeTracker = timeTracker + (long) time;
								}

							}

						} catch (Exception e) {
							System.out.println("Letzte Order des Tages :)");
						}

					}

					System.out.println("\nBot Nr. " + iD + " hat Order Nr. " + i + " ausgeführt.");
				}

			}

		}

		int wait = 50;

		// Thread.sleep(wait);

		return new Result(wait);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		List<Callable<Result>> tasks = new ArrayList<Callable<Result>>();

		for (final ServiceBot bot : bots) {
			Callable<Result> c = new Callable<Result>() {
				@Override
				public Result call() throws Exception {
					return compute(bot);
				}
			};
			tasks.add(c);
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		// some other exectuors you could try to see the different behaviours
		// ExecutorService exec = Executors.newFixedThreadPool(3);
		// ExecutorService exec = Executors.newSingleThreadExecutor();
		try {
			long start = System.currentTimeMillis();
			List<Future<Result>> results = exec.invokeAll(tasks);
			int sum = 0;
			for (Future<Result> fr : results) {
				sum += fr.get().wait;
				// System.out.println(String.format("Task waited %d ms", fr.get().wait));
			}
			long elapsed = System.currentTimeMillis() - start;
			System.out.println(String.format("\nElapsed time: %d ms", elapsed));
			System.out.println(String.format("... but computing tasks waited for total of %d ms; speed-up of %.2fx",
					sum, sum / (elapsed * 1d)));
		} finally {
			exec.shutdown();
		}
	}
}