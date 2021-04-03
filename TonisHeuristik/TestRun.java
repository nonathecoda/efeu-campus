package Dashboard;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestRun {

	static ArrayList<Customer> customers;
	static int lateOrders;
	static int missedOrders;
	static int avgDelay;
	static ArrayList<List<String>> results = new ArrayList<>();

	public TestRun(ArrayList<Customer> customers) {

		FileWriter csvWriter = CSVWriter.startCSV();
		DecimalFormat df = new DecimalFormat("0.00");
		int timeTrackerBeforeExecution = -1;
		double socBeforeExecution = -1;
		double socAfterCharging = -1;
		int chargingMoment = -1;
		int durationChargingProcess = -1;
		int earliest = -1;
		int latest = -1;
		String chargingDecision = "No decision made.";
		String emergencyCharging;
		lateOrders = 0;
		missedOrders = 0;

		this.customers = customers;
		Customer depot = Campus.getDepot();
		ArrayList<Bot> bots = Methods.createBotList(depot);
		ArrayList<Order> orders = Methods.createOrderList();
		ArrayList<ArrayList<Integer>> overallExecutionDuration = new ArrayList<>();
		ArrayList<ArrayList<Integer>> overallChargingDuration = new ArrayList<>();
		ArrayList<Order> ordersBeforeList = Methods.createOrderList();
		double batteryConsumption;
		int chargingTime;

		double sumExecutionDurationOrders = 0;
		for (int i = 0; i < orders.size(); i++) {
			orders.get(i).setDelayed(false);
			// ordersBeforeList.add(orders.get(i));
		}
		for (int i = 0; i < bots.size(); i++) {
			overallExecutionDuration.add(new ArrayList<Integer>());
			overallChargingDuration.add(new ArrayList<Integer>());
		}

		results.clear();

		int b = 0;
		for (int o = 0; o < orders.size(); o++) {

			if (b == bots.size()) {
				b = 0;
			}
			Bot currentBot = bots.get(b);

//			if (o < DataDashboard.numberOfBots) {
//				orders.get(o).setIsTaken(bots.get(o).getId());
//			}
//
//			if (o % DataDashboard.numberOfBots == 0) {
//				System.out.println("\nASSIGN NEXT BUNCH\n");
//				Methods.assignNextBots(orders, o, bots);
//			}

			Order currentOrder = orders.get(o);
			earliest = currentOrder.getEarliest();
			latest = currentOrder.getLatest();
			emergencyCharging = "No";

			// Bot currentBot = bots.get(currentOrder.getIsTaken());

			System.out.println("Order: " + currentOrder.getId() + ", Bot: " + currentBot.getId());

			int executionTime = (int) Math.round(Methods.calculateDistance(currentBot.getLocation(),
					currentOrder.getPickUpID(), currentOrder.getDeliveryID()) / DataDashboard.velocity);
			batteryConsumption = executionTime * DataDashboard.dechargingSpeed;

			currentBot.socList.add(socBeforeExecution);
			b++;

			boolean emergencyDefinition = DataDashboard.getEmergencyDefinition();

			if (emergencyDefinition == true) {
				System.out.println("TRUE");
				if ((currentBot.getSoc() - batteryConsumption) <= (DataDashboard.batteryCapacity
						* DataDashboard.getEmergencyCharge())) {
					int timeTrackerBeforeCharging = currentBot.getTimeTracker();
					Customer chargingStation = Methods.chooseClosestChargingStation(currentBot);
					chargingTime = (int) Math.round(
							(DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed);

					emergencyCharging = "Yes, charging at " + currentBot.getTimeTracker() + " for " + chargingTime
							+ " minutes.";
					Methods.charge(currentBot, chargingTime, chargingStation);
					overallChargingDuration.get(currentBot.getId())
							.add(currentBot.getTimeTracker() - timeTrackerBeforeCharging);
				}
			} else if (emergencyDefinition == false) {

				System.out.println("FALSE");
				Customer chargingStation = Methods.chooseClosestChargingStation(currentBot);
				double minutesToChargingStation = Methods.calculateDistance(currentOrder.getDeliveryID(),
						chargingStation) / DataDashboard.velocity;
				double batteryConsumptionToChargingStation = minutesToChargingStation * DataDashboard.dechargingSpeed;

				if ((currentBot.getSoc() - batteryConsumption
						- batteryConsumptionToChargingStation) <= (DataDashboard.batteryCapacity
								* DataDashboard.getEmergencyCharge())) {
					int timeTrackerBeforeCharging = currentBot.getTimeTracker();

					chargingTime = (int) Math.round(
							(DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed);

					emergencyCharging = "Yes, charging at " + currentBot.getTimeTracker() + " for " + chargingTime
							+ " minutes.";
					Methods.charge(currentBot, chargingTime, chargingStation);
					overallChargingDuration.get(currentBot.getId())
							.add(currentBot.getTimeTracker() - timeTrackerBeforeCharging);
				}
			}

			currentBot.setTotalExecutionTime(executionTime, currentOrder.getEarliest());
			timeTrackerBeforeExecution = currentBot.getTimeTracker();
			socBeforeExecution = currentBot.getSoc();

			if ((currentBot.getTimeTracker() + executionTime) <= currentOrder.getLatest()) {

				overallExecutionDuration.get(currentBot.getId()).add(executionTime);

				currentBot.setTimeTracker(currentBot.getTimeTracker() + currentBot.getTotalExecutionTime());
				currentBot.setLocation(currentOrder.getDeliveryID());
				if (DataDashboard.getHeuristicIterator() != 0) {
					currentBot.setSoc(currentBot.getSoc() - batteryConsumption);
				}

				try {
					chargingMoment = currentBot.getTimeTracker();
					Order nextOrder = orders.get(o + bots.size());
					// Order nextOrder = Methods.identifyNextOrder(currentBot, o, orders);
					System.out.println("Next order: " + nextOrder.getId() + ", Bot: " + currentBot.getId());
					switch (DataDashboard.getHeuristicIterator()) {

					case 0:
						// no battery constraints
						chargingDecision = "I don't need no battery.";
						break;
					case 1:
						// opportunity charging
						chargingDecision = OpportunityCharging.chargingHeuristic(currentOrder, nextOrder, currentBot,
								batteryConsumption);
						break;
					case 2:
						// interval charging
						chargingDecision = ChargeInIntervals.chargingHeuristic(currentOrder, nextOrder, currentBot,
								batteryConsumption);
						break;

					}
					socAfterCharging = currentBot.getSoc();
					durationChargingProcess = currentBot.getTimeTracker() - chargingMoment;
					overallChargingDuration.get(currentBot.getId()).add(durationChargingProcess);

				} catch (IndexOutOfBoundsException e) {

					chargingDecision = "Letzte Order des Tages, keine Ladungen mehr.";

				}

				// Order ausführen + charging decision treffen
			} else if ((currentBot.getTimeTracker() + executionTime) > currentOrder.getLatest()) {

				chargingMoment = currentBot.getTimeTracker();
				socAfterCharging = currentBot.getSoc();
				durationChargingProcess = currentBot.getTimeTracker() - chargingMoment;

				if (currentOrder.getLatest() <= DataDashboard.getDayDurationTextField()
						- (DataDashboard.defaultDelay)) {
					earliest = currentOrder.getEarliest();
					latest = currentOrder.getLatest();
					currentOrder.setEarliest(currentOrder.getEarliest() + DataDashboard.defaultDelay);
					currentOrder.setLatest(currentOrder.getLatest() + DataDashboard.defaultDelay);
					orders.remove(currentOrder);

					int newIndex = 0;
					for (int i = 0; i < orders.size(); i++) {
						if (currentOrder.getEarliest() > orders.get(i).getEarliest()) {
							newIndex = i + 1;
						}
					}
					orders.add(newIndex, currentOrder);
					o--;
					currentOrder.setDelayed(true);
					// Methods.assignNextBots(orders, o, bots);

					chargingDecision = "Bot too late - Order is late.";
				} else {
					chargingDecision = "Bot too late - Order is missed.";
					missedOrders++;
				}
			}

			List<String> result = Arrays.asList(String.valueOf(currentOrder.getId()),
					String.valueOf(currentOrder.getPickUpID().getID()),
					String.valueOf(currentOrder.getDeliveryID().getID()), earliest + " - " + latest,
					String.valueOf(currentBot.getId()), String.valueOf(timeTrackerBeforeExecution),
					String.valueOf(df.format(socBeforeExecution)), String.valueOf(executionTime), emergencyCharging,
					chargingDecision, String.valueOf(df.format(socAfterCharging)), String.valueOf(chargingMoment),
					String.valueOf(durationChargingProcess));

			results.add(result);

		}

		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getDelayed() == true) {
				lateOrders++;
			}
		}

		ArrayList<Integer> delayList = new ArrayList<>();
		for (int i = 0; i < ordersBeforeList.size(); i++) {
			Order before = ordersBeforeList.get(i);
			for (int k = 0; k < orders.size(); k++) {
				Order after = orders.get(k);
				if (after.getId() == before.getId()) {
					delayList.add(after.getLatest() - before.getLatest());
				}
			}
		}
		int delay = 0;
		for (int i = 0; i < delayList.size(); i++) {
			delay += delayList.get(i);
		}
		avgDelay = delay / delayList.size();

		ArrayList<Integer> execDurationPerBot = new ArrayList<>();
		for (int i = 0; i < overallExecutionDuration.size(); i++) {
			int sum = 0;
			for (int j = 0; j < overallExecutionDuration.get(i).size(); j++) {
				sum += overallExecutionDuration.get(i).get(j);
			}
			execDurationPerBot.add(sum);
		}

		ArrayList<Integer> chargingTimePerBot = new ArrayList<>();
		for (int i = 0; i < overallChargingDuration.size(); i++) {
			int sum = 0;
			for (int j = 0; j < overallChargingDuration.get(i).size(); j++) {
				sum += overallChargingDuration.get(i).get(j);
			}
			chargingTimePerBot.add(sum);
		}

		Collections.sort(bots, Comparator.comparing(Bot::getId));
		String outputString = "<html><body>Output: <br><br>";
		ArrayList<Double> averageSocList = new ArrayList<>();
		for (Bot dummyBot : bots) {
			Double averageSocDummy = 0.0;
			for (Double dummy : dummyBot.getSocList()) {
				averageSocDummy += dummy;
			}
			averageSocDummy = averageSocDummy / dummyBot.socList.size();
			averageSocList.add(averageSocDummy);
			outputString = (outputString + "Average Soc Bot " + dummyBot.getId() + ": " + df.format(averageSocDummy)
					+ " Wh<br>");
		}
		outputString = outputString + "<br>Late Orders: " + lateOrders + "<br>Missed Orders: " + missedOrders
				+ "</body></html>";

		View.outputLabel.setText(outputString);
		CSVWriter.endCSV(csvWriter, averageSocList, execDurationPerBot, chargingTimePerBot);

	}

}
