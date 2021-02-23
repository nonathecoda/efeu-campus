import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MainPool {

	static CampusPool campus = new CampusPool(DataPool.numberOfCustomers, DataPool.campusSize);
	static ArrayList<KnotPool> knots = campus.getKnots();
	static KnotPool depot = campus.getDepot();
	static ArrayList<OrderPool> orderList = MethodsPool.createOrderList(knots, depot);
	static ArrayList<RoboterPool> bots = new ArrayList<>();

	public static void main(String[] args) {
		
		//Liste an Robotern erstellen
		for (int i = 9; i < DataPool.numberOfBots + 9; i++) {
			RoboterPool bot = new RoboterPool(i, DataPool.batteryCapacity, depot, 0);
			System.out.println("Bot id: " + bot.getId());
			bots.add(bot);
		}

		ExecutorService exec = Executors.newFixedThreadPool(DataPool.numberOfBots);

		List<Callable<Output>> tasks = new ArrayList<>();
		
		//executorService die "Tasks" übergeben -> führt für jede Order die executeOrder-Methode aus
		for (int i = 0; i < orderList.size(); i++) { // das ist doppeltgemoppelt :)
			KnotPool pickUpID = orderList.get(i).getPickUpID();
			KnotPool deliveryID = orderList.get(i).getDeliveryID();
			int startingTime = orderList.get(i).getStartingTime();

			tasks.add(executeOrder(pickUpID, deliveryID, startingTime));
		}
		
		//executorservice "sammelt" das Ergebnis für jede einzelne Order ein
		try {
			int sum = 0;
			List<Future<Output>> results = exec.invokeAll((Collection<Callable<Output>>) tasks);
			for (Future future : results) {
				Output putput = (Output) future.get();
				sum +=putput.getInt();
				
			}
			System.out.println("MissedOrders GESAMT: " + sum);
		} catch (Exception e) {

		}

		exec.shutdown();
	}

	
	private static Callable<Output> executeOrder(KnotPool pickUpID, KnotPool deliveryID, int startingTime) {
		return new Callable<Output>() {
			@Override
			public Output call() throws Exception {

				//warum 2. executeOrder methode? habe versucht, die ganze methode in Methods.Pool zu snychronisieren, klappt aber noch nicht
				Output result = MethodsPool.executeOrder(pickUpID, deliveryID, startingTime);
				
				//hier sollte jetzt die ChargingHeuristik aufgerufen werden, kommt, sobald ich meinen Fehler behoben habe
				
				return result;
				
			}

		};
	}
}