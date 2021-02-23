import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MethodsPool {

	static synchronized public Output executeOrder(KnotPool pickUpID, KnotPool deliveryID, int startingTime) {

		AtomicInteger missedOrders = new AtomicInteger(0); // ist entweder 0 oder 1, je nachdem ob die Order ausgeführt
															// werden konnte

		AtomicLong minutesNeeded = new AtomicLong(); // wie lange dauert die Ausführung er Order?

		double soc = DataPool.batteryCapacity;

		AtomicInteger atomInt = new AtomicInteger(0); // ich nutze AtomicInteger und nicht int, um Threadsicherheit zu
														// gewährleisten

		for (int i = atomInt.intValue(); i < DataPool.numberOfBots; i = atomInt.getAndIncrement()) { // gehe Bots durch,
																										// bis die Id
																										// des Bots der
																										// id des
																										// Threads
																										// entspricht
																										// (-> Thread 1
																										// bearbeitet
																										// immer Bot 1
																										// etc.)

			if (Thread.currentThread().getId() == MainPool.bots.get(i).getId()) {

				RoboterPool bot = MainPool.bots.get(i);

				System.out.println(Thread.currentThread().getName() + " starting time: " + startingTime
						+ " timeTracker: " + bot.timeTracker);

				if (startingTime - bot.timeTracker < 0) { // also: die jetzige Zeit ist schon fortgeschrittener als der
															// gewollte Ausführungszeitpunkt
					missedOrders.incrementAndGet(); // order verpasst.
					break;
				}

				if (bot.timeTracker > DataPool.durationWorkdayInMinutes) { // also: Arbeitstag ist vorbei
					missedOrders.incrementAndGet(); // order verpasst.
					break;
				}

				System.out.println(
						Thread.currentThread().getName() + " Waiting time: " + (startingTime - bot.timeTracker));
				try {
					Thread.currentThread().sleep(startingTime - bot.timeTracker); // Thread macht nichts, bis der
																					// Ausführungszeitpunkt beginnt
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				bot.setTimeTracker(startingTime);
				System.out.println(Thread.currentThread().getName() + " timeTracker: " + bot.timeTracker);

				minutesNeeded.addAndGet(
						(long) (MethodsPool.calculateDistance(MainPool.bots.get(i).getLocation(), pickUpID, deliveryID)
								/ DataPool.velocity)); // teile Distanz der Order durch Geschwindigkeit des Bots
				try {
					Thread.currentThread().sleep(minutesNeeded.intValue()); // Thread schläft so lange, wie die
																			// Ausführung der Order dauern würde
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				bot.setTimeTracker(bot.timeTracker + minutesNeeded.intValue());
				System.out.println(Thread.currentThread().getName() + " Minutes Needed: " + minutesNeeded.intValue());
				System.out.println(Thread.currentThread().getName() + " timeTracker: " + bot.timeTracker);

				// soc muss noch angepasst werden

				break;
			}
		}

		/*
		 * waitingTime.set(startingTime - MainPool.timeTracker.intValue());
		 * 
		 * if (waitingTime.intValue() < 0) { System.out.println("WaitingTime: " +
		 * waitingTime.intValue() + " " + Thread.currentThread().getName());
		 * missedOrders.incrementAndGet(); } else {
		 * 
		 * // Thread.sleep(waitingTime);
		 * MainPool.timeTracker.addAndGet(waitingTime.intValue());
		 * 
		 * AtomicInteger atomInt = new AtomicInteger(0); for (int i =
		 * atomInt.intValue(); i < DataPool.numberOfBots; i = atomInt.getAndIncrement())
		 * { if (Thread.currentThread().getId() == MainPool.bots.get(i).getId()) {
		 * minutesNeeded.addAndGet( (long)
		 * (MethodsPool.calculateDistance(MainPool.bots.get(i).getLocation(), pickUpID,
		 * deliveryID) / DataPool.velocity));
		 * 
		 * if (MainPool.timeTracker.intValue() + minutesNeeded.longValue() >
		 * DataPool.durationWorkdayInMinutes) { missedOrders.incrementAndGet(); break; }
		 * 
		 * // Thread.sleep(minutesNeeded.longValue());
		 * MainPool.timeTracker.addAndGet(minutesNeeded.intValue());
		 * 
		 * double batteryConsumption = minutesNeeded.longValue() *
		 * DataPool.dechargingSpeed;
		 * 
		 * MainPool.bots.get(i).setSoc(MainPool.bots.get(i).getSoc() -
		 * batteryConsumption); soc = MainPool.bots.get(i).getSoc();
		 * MainPool.bots.get(i).setLocation(deliveryID);
		 * System.out.println("order durchgeführt von Thread " +
		 * Thread.currentThread().getId() + " mit Bot " + MainPool.bots.get(i).getId());
		 * break; } }
		 * 
		 * }
		 */

		Output output = new Output(missedOrders.intValue(), soc);
		System.out.println("MissedOrders: " + missedOrders.intValue() + " " + Thread.currentThread().getName());

		return output;

	}

	// erstellt zufällige Aufträge, mit Depot als Pickup oder als DeliveryPoint
	synchronized static public ArrayList<OrderPool> createOrderList(ArrayList<KnotPool> knots, KnotPool depot) {
		ArrayList<OrderPool> orderList = new ArrayList<>();

		int startingTime = 0;
		KnotPool pickup = null;
		KnotPool delivery = null;
		int j = DataPool.numberOfCustomers;
		Random generator = new Random(2);

		// erstelle Liste mit Zufallszahlen
		int[] randomListe = new int[DataPool.numberOfOrders * 3];
		for (int i = 0; i < (randomListe.length - 3); i = i + 3) {
			randomListe[i] = generator.nextInt(DataPool.durationWorkdayInMinutes);
			randomListe[i + 1] = generator.nextInt(knots.size());
			randomListe[i + 2] = generator.nextInt(knots.size());
		}

		// erstellt orders, die das Depot entweder als Pickup oder als Delivery haben
		for (int i = 0; i < DataPool.numberOfOrders; i++) {

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

			OrderPool dummyOrder = new OrderPool(pickup, delivery, startingTime);

			orderList.add(dummyOrder);
		}
		// Liste nach startingTime sortieren
		Collections.sort(orderList, Comparator.comparing(OrderPool::getStartingTime));
		return orderList;
	}

	synchronized public static double calculateDistance(KnotPool currentLocation, KnotPool pickUp, KnotPool delivery) {
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
	/*
	 * synchronized public static void charge(double time, BotThreadPool bot) { try
	 * { Thread.sleep((long) (time)); double newSoc = bot.getSoc() + (time *
	 * DataPool.chargingSpeed); bot.setSoc(newSoc); } catch (Exception e) {
	 * System.out.println("Fehler in Charge-Methode."); }
	 * 
	 * }
	 */
	/*
	 * synchronized public static void resetCampus() {
	 * BotThreadPool.averageSocList.clear();
	 * 
	 * for (int i = 0; i < CampusPool.chargingStations.size(); i++) {
	 * CampusPool.chargingStations.get(i).setIsAvailable(true); } for (int i = 0; i
	 * < MainPool.syncOrderList.size(); i++) {
	 * MainPool.syncOrderList.get(i).setHandled(-1); }
	 * 
	 * }
	 */
}
